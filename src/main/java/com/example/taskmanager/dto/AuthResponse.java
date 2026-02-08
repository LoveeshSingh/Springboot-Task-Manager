package com.example.taskmanager.dto;

public class AuthResponse {
    private String accessToken;
    private String tokenType;

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
