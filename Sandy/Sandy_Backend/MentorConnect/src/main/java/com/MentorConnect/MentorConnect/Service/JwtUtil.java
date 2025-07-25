package com.MentorConnect.MentorConnect.Service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final String jwtSecret = "FYQmsZe7bIeGrRy8ehAzfF0B5SpIAQgwFF8+7uTeN6Y="; // Min 256-bit key
    private final long expiration = 24 * 60 * 60 * 1000; // 24 hours

    private Key getSignKey() {
        byte[] decodeKey = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(decodeKey);
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}


