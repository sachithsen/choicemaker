package org.sachith.choicemaker.model.dto;

public class AuthenticationResponse {

    private String token;
    private boolean isAuthenticated;

    public AuthenticationResponse(String token, boolean isAuthenticated) {
        this.token = token;
        this.isAuthenticated = isAuthenticated;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }
}

