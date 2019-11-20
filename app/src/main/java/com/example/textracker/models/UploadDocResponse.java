package com.example.textracker.models;

public class UploadDocResponse {

    String message;

    public UploadDocResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
