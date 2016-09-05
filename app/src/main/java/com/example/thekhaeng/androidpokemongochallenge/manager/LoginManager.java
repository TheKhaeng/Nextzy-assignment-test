package com.example.thekhaeng.androidpokemongochallenge.manager;

import android.content.Context;

import com.example.thekhaeng.androidpokemongochallenge.http.LoginTokenDao;
import com.example.thekhaeng.androidpokemongochallenge.util.VerifiedUtils;

public class LoginManager{

    private final VerifiedUtils mVerified;
    private final Context mContext;

    private LoginTokenDao loginToken;

    public LoginManager( Context context, VerifiedUtils verifiedUtils){
        mContext = context;
        mVerified = verifiedUtils;
    }


    public LoginTokenDao getLoginToken(){
        return loginToken;
    }

    public void setLoginToken( LoginTokenDao loginToken ){
        this.loginToken = loginToken;
    }
}
