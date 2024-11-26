package com.univerisity.Domain.models;

public class Login {
    private int isSucces;
    private String token;

    public Login(int isSucces, String token) {
        this.isSucces = isSucces;
        this.token = token;
    }

    public int isSucces() {
        return isSucces;
    }

    public void setSucces(int succes) {
        isSucces = succes;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
