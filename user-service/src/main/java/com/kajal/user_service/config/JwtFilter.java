package com.kajal.user_service.config;

import com.kajal.user_service.service.AuthService;
import com.kajal.user_service.service.UserDetailsServiceImpl;
import com.kajal.user_service.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
          token = authHeader.substring(7);
          username = authService.getUserName(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          UserDetails userDetails = userDetailsService.loadUserByUsername(username);
           if (authService.validateToken(token, userDetails)) {
            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            System.out.println(authToken);
            SecurityContextHolder.getContext().setAuthentication(authToken);
          }
        }

        filterChain.doFilter(request, response);
    }
}
