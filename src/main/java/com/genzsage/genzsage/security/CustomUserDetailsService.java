package com.genzsage.genzsage.security;

import com.genzsage.genzsage.sage.Sage;
import com.genzsage.genzsage.sage.SageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private SageRepository sageRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Sage sage =sageRepository.findByIdentityOrEmailOrPhoneNumber(username);
        if (sage == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return User.builder()
                .username(sage.getIdentity())
                .password(sage.getPasswordHash())
                .roles("USER")
                .build();
    }
}
