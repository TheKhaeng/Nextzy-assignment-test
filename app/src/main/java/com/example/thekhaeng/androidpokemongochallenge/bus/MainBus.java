package com.example.thekhaeng.androidpokemongochallenge.bus;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

public class MainBus extends Bus{
	// Defines a Handler object that's attached to the UI thread
    private final Handler mHandler = new Handler( Looper.getMainLooper() );

    private static MainBus instance;

    public static MainBus getInstance(){
        if( instance == null )
            instance = new MainBus();
        return instance;
    }

	//send event from Service to Activity
	//if MainBus still in UI thread call post directly
	//else if MainBus not in UI thread must call in mHandler
    @Override
    public void post( final Object event ){
        if( Looper.myLooper() == Looper.getMainLooper() ){
            super.post( event );
        }else{
            mHandler.post( new Runnable(){
                @Override
                public void run(){
                    MainBus.super.post( event );
                }
            } );
        }
    }

}
