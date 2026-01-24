package com.genzsage.genzsage.auth;


import lombok.Data;

@Data
public class SageAvalibilityRequest {
    public String identity;
    public String email;
    public String phoneNumber;
    public String country;
}
