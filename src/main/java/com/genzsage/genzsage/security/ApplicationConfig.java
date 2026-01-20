package com.genzsage.genzsage.security;


import com.genzsage.genzsage.auth.SageMapper;
import com.genzsage.genzsage.auth.SageUserDetails;
import com.genzsage.genzsage.sage.Sage;
import com.genzsage.genzsage.sage.SageRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
public class ApplicationConfig {
    private SageRepository sageRepository;
    private SageUserDetailsService sageUserDetailsService;
//    private PasswordEncoder passwordEncoder;


//    @Bean
//    public SageMapper sageMapper() {
//        return new SageMapper(passwordEncoder);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
                  DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(sageUserDetailsService);

                  daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
                  return daoAuthenticationProvider;
              }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration  authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }



}


