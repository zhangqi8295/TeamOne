package com.example.qizhang.teamone.model;

//{
//    "access_token":"v^1.1#i^1#p^1#r^0#I^3#f^0#t^H4s ... wu67e3xAhskz4DAAA",
//    "expires_in":7200,
//    "refresh_token":"N/A",
//    "token_type":"Application Access Token"
//  }

public class ApplicationToken {
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String token_type;

    public ApplicationToken() {
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }
}
