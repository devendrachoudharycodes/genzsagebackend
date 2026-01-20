package com.genzsage.genzsage.auth;


import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
    private String deviceId;
    private String otherMetadata;
}
