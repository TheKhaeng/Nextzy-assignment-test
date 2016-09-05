package com.example.thekhaeng.androidpokemongochallenge;

import timber.log.Timber;

/**
 * Created by TheKhaeng on 9/5/2016.
 */

public class ApplicationImpl extends Application{

    @Override
    public void init(){
        super.onCreate();

        Timber.plant( new Timber.DebugTree(){
            // Add the line number to the tag
            @Override
            protected String createStackElementTag( StackTraceElement element ){
                return super.createStackElementTag( element ) + ":" + element.getLineNumber();
            }
        } );

    }

}

