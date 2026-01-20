package com.genzsage.genzsage.auth;

import com.genzsage.genzsage.globalconfigurations.GlobalResponseDTO;
import com.genzsage.genzsage.sage.Sage;
import com.genzsage.genzsage.sage.SageRepository;
import com.genzsage.genzsage.security.JwtUtil;
import com.genzsage.genzsage.security.SageUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@AllArgsConstructor
@Service
public class AuthService {
    private final SageRepository sageRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final SageUserDetailsService sageUserDetailsService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final SageMapper sageMapper;

    @Transactional
    public GlobalResponseDTO<AuthResponse> registerSage(RegisterSageRequest registerSageRequest) {
        Sage sage = sageMapper.mapToSage(registerSageRequest);
        sageRepository.save(sage);
        String accessToken = jwtUtil.generateToken(new SageUserDetails(sage));
        String refreshToken = refreshTokenService.generateToken(sage, registerSageRequest.getDeviceInfo(), registerSageRequest.getOtherMeta()).getToken();
        return GlobalResponseDTO.success(new AuthResponse(accessToken, refreshToken), "Registration Successful");
    }

    public GlobalResponseDTO<AuthResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {

        RefreshToken refreshToken = refreshTokenService.verfiyToken(refreshTokenRequest.getRefreshToken()
                , refreshTokenRequest.getDeviceId()
                , refreshTokenRequest.getOtherMetadata());

        String accessToken = jwtUtil.generateToken(new SageUserDetails(refreshToken.getUser()));
        return GlobalResponseDTO.success(new AuthResponse(accessToken, refreshToken.getToken()), "Refresh Token");

    }

    @Transactional
    public GlobalResponseDTO<AuthResponse> login(LoginRequest loginRequest) {
        // 1. Fetch the user from the database
        Sage sage = sageRepository.findByIdentityOrEmailOrPhoneNumber(loginRequest.getIdentifier())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + loginRequest.getIdentifier()));

        // 2. Verify the password (assuming you use BCrypt)
        if (!passwordEncoder.matches(loginRequest.getPassword(), sage.getPasswordHash())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        // 3. Generate the Access Token
        String accessToken = jwtUtil.generateToken(new SageUserDetails(sage));

        // 4. Generate/Update the Refresh Token
        // Reusing your existing RefreshTokenService logic
       String refreshToken= refreshTokenService.generateToken(
                sage,
                loginRequest.getDeviceId(),
                loginRequest.getOtherMetadata()
        ).getToken();


        return GlobalResponseDTO.success(
                new AuthResponse(accessToken, refreshToken),
                "Login Successful"
        );
    }

    public GlobalResponseDTO<Boolean> logout(LogoutRequest logoutRequest) {
        refreshTokenService.deleteToken(logoutRequest.getRefreshToken());
        return GlobalResponseDTO.success(true, "Logout Successful");
    }
}
