package com.example.thekhaeng.androidpokemongochallenge.injection.component;

import android.app.Application;
import android.content.Context;

import com.example.thekhaeng.androidpokemongochallenge.injection.ApplicationContext;
import com.example.thekhaeng.androidpokemongochallenge.injection.module.ApiModule;
import com.example.thekhaeng.androidpokemongochallenge.injection.module.ApplicationModule;
import com.example.thekhaeng.androidpokemongochallenge.injection.module.ManagerModule;

import javax.inject.Singleton;

import dagger.Component;
import view.LoginFragment;
import view.MainFragment;

@Singleton
@Component( modules = {ApplicationModule.class, ApiModule.class, ManagerModule.class} )
public interface ApplicationComponent{

    void inject( MainFragment fragment );
    void inject( LoginFragment fragment );
}
