package com.example.thekhaeng.androidpokemongochallenge;

import android.content.Context;

import com.example.thekhaeng.androidpokemongochallenge.injection.component.ApplicationComponent;
import com.example.thekhaeng.androidpokemongochallenge.injection.component.DaggerApplicationComponent;
import com.example.thekhaeng.androidpokemongochallenge.injection.module.ApiModule;
import com.example.thekhaeng.androidpokemongochallenge.injection.module.ApplicationModule;
import com.example.thekhaeng.androidpokemongochallenge.injection.module.ManagerModule;


public abstract class Application extends android.app.Application{

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate(){
        super.onCreate();
        init();
    }

    public static ApplicationImpl get( Context context ){
        return (ApplicationImpl) context.getApplicationContext();
    }

    public abstract void init();

    public ApplicationComponent getComponent(){
        if( mApplicationComponent == null ){
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule( new ApplicationModule( this ) )
                    .apiModule( new ApiModule() )
                    .managerModule( new ManagerModule() )
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent( ApplicationComponent applicationComponent ){
        mApplicationComponent = applicationComponent;
    }
}
