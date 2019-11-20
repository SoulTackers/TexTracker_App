package com.example.textracker.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    String Token;

    public LoginResponse(String token) {
        Token = token;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
