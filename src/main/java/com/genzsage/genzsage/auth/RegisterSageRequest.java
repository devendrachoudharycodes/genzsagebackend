package com.genzsage.genzsage.auth;


import lombok.Data;
import lombok.ToString;
import org.hibernate.boot.model.internal.StrictIdGeneratorResolverSecondPass;

import java.time.LocalDate;


@Data
@ToString
public class RegisterSageRequest {
    private String identity;
    private String email;
    private String password;
    private String phoneNumber;
    private String profileName;
    private LocalDate birthDate;
    private String country;
    private String bio;
    private boolean isPrivate;
    private String profilePicUrl;
    private String deviceInfo; //this is generated client side for device security i.e. no other device can login after taking tokens from this one
    private String otherMeta; // this is any other meta which this user's device identifies itself

}

