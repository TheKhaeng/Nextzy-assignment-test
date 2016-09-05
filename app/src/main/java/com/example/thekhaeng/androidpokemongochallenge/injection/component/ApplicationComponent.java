package com.example.thekhaeng.androidpokemongochallenge.injection.component;

import android.app.Application;
import android.content.Context;

import com.example.thekhaeng.androidpokemongochallenge.injection.ApplicationContext;
import com.example.thekhaeng.androidpokemongochallenge.injection.module.ApiModule;
import com.example.thekhaeng.androidpokemongochallenge.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component( modules = {ApplicationModule.class, ApiModule.class} )
public interface ApplicationComponent{


    @ApplicationContext
    Context context();
    Application application();

}
