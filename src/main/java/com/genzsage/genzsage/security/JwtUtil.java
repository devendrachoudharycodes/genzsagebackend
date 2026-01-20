package com.genzsage.genzsage.security;

import com.genzsage.genzsage.auth.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;


@Service
public class JwtUtil {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    private long tokenValidityInSeconds = 1000*60*13;

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenValidityInSeconds))
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
