package com.kajal.cart_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

    public String getUserIdWithValidation(HttpServletRequest request) throws RuntimeException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        return validateAndGetUserId(token);
    }

    public String validateAndGetUserId(String token) {
        try {
            if(!isTokenExpired(token)){
                return getUserId(token);
            }
            else{
                throw new RuntimeException("Token is expired");
            }
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token is expired");
        }
    }

    private boolean isTokenExpired(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.getExpiration().before(new Date());
    }
}
