package com.example.thekhaeng.androidpokemongochallenge.injection.module;


import android.content.Context;

import com.example.thekhaeng.androidpokemongochallenge.login.ProfileManager;
import com.example.thekhaeng.androidpokemongochallenge.main.PokemonManager;
import com.example.thekhaeng.androidpokemongochallenge.map.PoiManager;
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
    public ProfileManager provideLoginManager( Context context ){
        return new ProfileManager( context );
    }

    @Provides
    @Singleton
    public PokemonManager providePokemonManager( Context context ){
        return new PokemonManager( context );
    }
}
