package com.genzsage.genzsage.auth;

import com.genzsage.genzsage.sage.Sage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class SageUserDetails implements UserDetails {

    private final Sage sage;

    // 1. ADD THIS: You need a constructor so the Mapper/Service can pass the Sage entity in
    public SageUserDetails(Sage sage) {
        this.sage = sage;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return sage.getPasswordHash();
    }

    @Override
    public String getUsername() {
        // You are using Identity here; make sure your login form sends the identity!
        return sage.getIdentity();
    }

    // 2. REQUIRED: Spring Security needs these to be 'true' to let the user in
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}