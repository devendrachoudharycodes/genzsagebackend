package com.genzsage.genzsage.auth;


import com.genzsage.genzsage.sage.Sage;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;


@AllArgsConstructor
@Service // 1. Missing Annotation
public class RefreshTokenService {

    private final RefreshTokenRepo refreshTokenRepo;


    public RefreshToken generateToken(Sage sage, String deviceInfo, String otherMeta) {
        RefreshToken token = RefreshToken.builder()
                .user(sage)
                .expiryDate(Instant.now().plus(15, ChronoUnit.DAYS))
                .deviceId(deviceInfo)
                .otherMeta(otherMeta)
                .token(UUID.randomUUID().toString())
                .build();
        return refreshTokenRepo.save(token);
    }


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

    public RefreshToken verfiyToken(String token, String deviceId, String otherMeta) {
        Optional<RefreshToken> byToken = refreshTokenRepo.findByToken(token);
        boolean verified = byToken.isPresent() && byToken.get().getExpiryDate().isAfter(Instant.now())
                && byToken.get().getDeviceId().equals(deviceId) && byToken.get().getOtherMeta().equals(otherMeta);
        if (verified) {
            RefreshToken tokenNew = generateToken(byToken.get().getUser(), deviceId, otherMeta);
            refreshTokenRepo.delete(byToken.get());
        return tokenNew;
        }
        throw new RuntimeException("Invalid token");
    }


    public void deleteToken(String refreshToken) {
        refreshTokenRepo.findByToken(refreshToken).ifPresent(refreshTokenRepo::delete);
    }
}