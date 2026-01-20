package com.genzsage.genzsage.auth;


import com.genzsage.genzsage.sage.Sage;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.beans.Encoder;



@Component
@AllArgsConstructor
public class SageMapper {

    private PasswordEncoder passwordEncoder;


    public  Sage mapToSage(RegisterSageRequest request) {
      return Sage.builder()
                .identity(request.getIdentity())
                .profileName(request.getProfileName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .birthDate(request.getBirthDate())
                .country(request.getCountry())
                .languagePreference(0L)
                .profilePicUrl(null)
                .isPrivate(request.isPrivate())
                .bio(request.getBio())
                .build();
    }
}
