package com.genzsage.genzsage.auth;


import com.genzsage.genzsage.sage.Sage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;


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

    public RefreshToken verifyToken(String token, String deviceId, String otherMeta) {
        Optional<RefreshToken> byToken = refreshTokenRepo.findByToken(token);

        // 1. Check if the token exists in the database
        if (byToken.isEmpty()) {
            throw new RuntimeException("Invalid Token: Token not found.");
        }

        RefreshToken existingToken = byToken.get();

        // 2. Check if the token has expired
        if (!existingToken.getExpiryDate().isAfter(Instant.now())) {
            throw new RuntimeException("Invalid Token: Token expired on " + existingToken.getExpiryDate());
        }

        // 3. Check if the device ID matches
        if (!existingToken.getDeviceId().equals(deviceId)) {
            throw new RuntimeException(String.format(
                    "Invalid Token: Device ID mismatch. Expected %s but got %s",
                    existingToken.getDeviceId(), deviceId
            ));
        }

        // 4. Check if the metadata matches
        if (!existingToken.getOtherMeta().equals(otherMeta)) {
            throw new RuntimeException("Invalid Token: Metadata mismatch.");
        }

        // 5. If all checks pass, generate the new token and delete the old one
        RefreshToken tokenNew = generateToken(existingToken.getUser(), deviceId, otherMeta);
        refreshTokenRepo.delete(existingToken);

        return tokenNew;
    }


    public void deleteToken(String refreshToken) {
        refreshTokenRepo.findByToken(refreshToken).ifPresent(refreshTokenRepo::delete);
    }
}