package com.genzsage.genzsage.auth;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;



@Getter
@Setter
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

}

