package com.App.Yogesh.Response;

public class AuthResponse {
    public AuthResponse(){};
    public AuthResponse(String message, String token) {
        super();
        this.message = message;
        this.token = token;
    }
    public AuthResponse(String message) {
        super();
        this.message = message;
    }


    private String token;
 private String message;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
