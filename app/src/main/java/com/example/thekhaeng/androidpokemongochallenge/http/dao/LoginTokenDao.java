package com.example.thekhaeng.androidpokemongochallenge.http.dao;


import lombok.ToString;
import timber.log.Timber;

/**
 * Created by TheKhaeng on 9/5/2016.
 */

@ToString
public class LoginTokenDao{
    private String message;
    private String token;

    public LoginTokenDao( String message, String token){
        this.message = message;
        this.token = token;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage( String message ){
        this.message = message;
    }

    public String getToken(){
        Timber.i( "getToken() " +token );
        return token;
    }

    public void setToken( String token ){
        this.token = token;
    }
}
