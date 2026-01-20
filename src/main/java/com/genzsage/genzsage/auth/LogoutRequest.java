package com.genzsage.genzsage.auth;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogoutRequest {
    private String accessToken;
    private String refreshToken;
}
