package com.example.thekhaeng.androidpokemongochallenge.injection.component;

import view.MainActivity;
import com.example.thekhaeng.androidpokemongochallenge.injection.PerActivity;
import com.example.thekhaeng.androidpokemongochallenge.injection.module.ActivityModule;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject( MainActivity mainActivity );

}
