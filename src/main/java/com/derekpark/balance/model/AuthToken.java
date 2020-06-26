package com.derekpark.balance.model;

public class AuthToken {

    public String token;
    public boolean auth;


    public void authenticate() {
        this.auth = true;
    }


    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }


}
