package com.genzsage.genzsage.security;


import com.genzsage.genzsage.sage.Sage;
import com.genzsage.genzsage.sage.SageRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.ProviderManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.List;

@Configuration
public class ApplicationConfig {
    private SageRepository sageRepository;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
          UserDetailsService userDetailsService=new UserDetailsService() {

              @Override
              public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                  Sage s = sageRepository.findByIdentityOrEmailOrPhoneNumber(username);
                  UserDetails user = new UserDetails() {
                      @Override
                      public Collection<? extends GrantedAuthority> getAuthorities() {
                          return List.of(new SimpleGrantedAuthority("USER"));
                      }

                      @Override
                      public @Nullable String getPassword() {
                          return s.getPasswordHash();
                      }

                      @Override
                      public String getUsername() {
                          return username;
                      }

                  };
               return user;
              }};
                  return userDetailsService;}

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
                  DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService());

                  daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
                  return daoAuthenticationProvider;
              }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration  authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }




}


