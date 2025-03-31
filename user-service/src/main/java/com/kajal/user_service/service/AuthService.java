package com.kajal.user_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final String jwtSecretkey =
            "disjciofp0w9392edjs343bisd3r3x3nbc4y8t3rn9r9r8rcne92numex20u";

    private final int expiryDuration = 24 * 60 * 60 * 1000;

    private SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username, String userId) {
        return Jwts.builder()
                .setId(userId)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiryDuration))
                .signWith(generateKey())
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload();
    }

    public String getUserName(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public String getUserId(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.getId();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = getUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.getExpiration().before(new Date());
    }
}
