package com.dushanz.bookmanager.dto.security;

import lombok.Data;

@Data
public class AuthTokenDTO {
    private String token;
    private String status;
    private String message;
    private String expiryDate;


    public AuthTokenDTO(String token, String status, String message, String expiryDate) {
        this.token = token;
        this.status = status;
        this.message = message;
        this.expiryDate = expiryDate;
    }
}
