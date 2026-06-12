package com.genzsage.genzsage.auth;

import com.genzsage.genzsage.globalconfigurations.GlobalResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    // Note: It is best practice to make the Logger private, static, and final.
    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    /**
     * Registers a new Sage user with device and meta information.
     */
    @PostMapping("/register")
    public GlobalResponseDTO<AuthResponse> register(@RequestBody RegisterSageRequest request) {
        logger.info("Incoming /register request body: " + request.toString());
        return authService.registerSage(request);
    }

    /**
     * Authenticates user and returns Access & Refresh tokens.
     * Should link the Refresh Token to the 'deviceInfo' provided.
     */
    @PostMapping("/login")
    public GlobalResponseDTO<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Incoming /login request body: " + loginRequest.toString());
        return authService.login(loginRequest);
    }

    /**
     * Generates a new Access Token using a valid Refresh Token.
     * Logic should verify 'deviceInfo' to ensure token hasn't been hijacked.
     */
    @PostMapping("/refresh")
    public GlobalResponseDTO<AuthResponse> refresh(@RequestBody RefreshTokenRequest refreshRequest) {
        logger.info("Incoming /refresh request body: " + refreshRequest.toString());
        return authService.refreshToken(refreshRequest);
    }

    /**
     * Revokes the Refresh Token to prevent further access.
     */
    @PostMapping("/logout")
    public GlobalResponseDTO<Boolean> logout(@RequestBody LogoutRequest logoutRequest) {
        logger.info("Incoming /logout request body: " + logoutRequest.toString());
        return authService.logout(logoutRequest);
    }

    /**
     * checks if there is already any sage existing in the database with provided credentials
     */
    @PostMapping("/checkAvailability")
    public GlobalResponseDTO<Boolean> checkIfAvailable(@RequestBody SageAvalibilityRequest request) {
        logger.info("Incoming /checkAvailability request body: " + request.toString());
        return authService.checkAvailability(request);
    }
}