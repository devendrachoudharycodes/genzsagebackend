package com.genzsage.genzsage.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;


@Service
public class JwtUtil {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(UserDetails user) {
        return generateToken(user, 1000 * 60 * 30); // 30 mins
    }

    public String generateRefreshToken(UserDetails user) {
        return generateToken(user, 1000 * 60 * 60 * 24 * 7); // 7 days
    }

    private String generateToken(UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey())
                .compact();
    }

    // 1. Extract Username (Filter needs this to load the user)
    public String extractUsername(String token) {
        return getTokenPayload(token).getSubject();
    }

    // 2. Validate Token (Only checks expiration now)
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getTokenPayload(token).getExpiration().before(new Date());
    }

    private Claims getTokenPayload(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
