package com.genzsage.genzsage.security;

import com.genzsage.genzsage.auth.SageUserDetails;
import com.genzsage.genzsage.sage.SageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SageUserDetailsService implements UserDetailsService {

    private final SageRepository sageRepository;
    @Autowired
    public SageUserDetailsService(SageRepository sageRepository) {
        this.sageRepository = sageRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return sageRepository.findByIdentityOrEmailOrPhoneNumber(username)
                .map(SageUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
