package com.genzsage.genzsage.auth;

import com.genzsage.genzsage.globalconfigurations.GlobalResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * Registers a new Sage user with device and meta information.
     */
    @PostMapping("/register")
    public GlobalResponseDTO<AuthResponse> register(@RequestBody RegisterSageRequest request) {
        return authService.registerSage(request);
    }

    /**
     * Authenticates user and returns Access & Refresh tokens.
     * Should link the Refresh Token to the 'deviceInfo' provided.
     */
    @PostMapping("/login")
    public GlobalResponseDTO<AuthResponse> login(@RequestBody LoginRequest loginRequest) {

        return authService.login(loginRequest);
    }

    /**
     * Generates a new Access Token using a valid Refresh Token.
     * Logic should verify 'deviceInfo' to ensure token hasn't been hijacked.
     */
    @PostMapping("/refresh")
    public GlobalResponseDTO<AuthResponse> refresh(@RequestBody RefreshTokenRequest refreshRequest) {
        return authService.refreshToken(refreshRequest);
    }

    /**
     * Revokes the Refresh Token to prevent further access.
     */
    @PostMapping("/logout")
    public GlobalResponseDTO<Boolean> logout(@RequestBody LogoutRequest logoutRequest) {
        return authService.logout(logoutRequest);
    }
}