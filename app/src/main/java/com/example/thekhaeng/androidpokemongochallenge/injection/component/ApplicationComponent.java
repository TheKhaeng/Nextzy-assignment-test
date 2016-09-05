package com.example.thekhaeng.androidpokemongochallenge.injection.component;

import com.example.thekhaeng.androidpokemongochallenge.injection.module.ApiModule;
import com.example.thekhaeng.androidpokemongochallenge.injection.module.ApplicationModule;
import com.example.thekhaeng.androidpokemongochallenge.injection.module.ManagerModule;

import javax.inject.Singleton;

import dagger.Component;
import com.example.thekhaeng.androidpokemongochallenge.login.view.LoginFragment;
import com.example.thekhaeng.androidpokemongochallenge.main.MainFragment;
import com.example.thekhaeng.androidpokemongochallenge.main.PokemonService;

@Singleton
@Component( modules = {ApplicationModule.class, ApiModule.class, ManagerModule.class} )
public interface ApplicationComponent{

    void inject( LoginFragment fragment );
    void inject( PokemonService service );
    void inject( MainFragment fragment );
}
