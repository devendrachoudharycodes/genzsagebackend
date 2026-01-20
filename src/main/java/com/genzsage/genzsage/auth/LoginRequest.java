package com.genzsage.genzsage.auth;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    private String identifier;
    private String password;
    private String deviceId;
    private String otherMetadata;
}
