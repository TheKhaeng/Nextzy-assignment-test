package com.example.thekhaeng.androidpokemongochallenge.bus.event;

import android.os.Bundle;

public class EventBus{
    private int action;
    private Bundle data;

    public static final int SUCCESS_REFRESH_POKEMON = 10;
    public static final int POKEMON_TIME_OUT = 11;
    public static final int ERROR_REFRESH_POKEMON = 40;

    public EventBus( int action ){
        this.action = action;
    }

    public EventBus( int action, Bundle data ){
        this.action = action;
        this.data = data;
    }

    public EventBus( Bundle data ){
        this.data = data;
    }

    public int getAction(){
        return action;
    }

    public void setAction( int param1 ){
        this.action = param1;
    }

    public Bundle getData(){
        return data;
    }

    public void setData( Bundle data ){
        this.data = data;
    }
}

