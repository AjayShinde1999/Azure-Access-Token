package com.azure.auth.service.dto;

import lombok.Data;

@Data
public class AuthResponse {

    private String token_type;
    private int expires_in;
    private int ext_expires_in;
    private String access_token;
}
