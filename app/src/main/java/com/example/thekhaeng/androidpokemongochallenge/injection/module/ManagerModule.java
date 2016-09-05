package com.example.thekhaeng.androidpokemongochallenge.injection.module;


import android.content.Context;

import com.example.thekhaeng.androidpokemongochallenge.manager.LoginManager;
import com.example.thekhaeng.androidpokemongochallenge.manager.PoiManager;
import com.example.thekhaeng.androidpokemongochallenge.util.VerifiedUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ManagerModule{

    @Provides
    @Singleton
    public PoiManager providePoiManager( Context context ){
        return new PoiManager( context );
    }

    @Provides
    @Singleton
    public VerifiedUtils provideVerifiedUtils( Context context ){
        return new VerifiedUtils( context );
    }

    @Provides
    @Singleton
    public LoginManager provideLoginManager( Context context, VerifiedUtils verifiedUtils ){
        return new LoginManager( context , verifiedUtils);
    }
}
