package com.example.thekhaeng.androidpokemongochallenge.http;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by TheKhaeng on 9/5/2016.
 */

@GsonTypeAdapterFactory
public abstract class MyAdapterFactory implements TypeAdapterFactory {

    // Static factory method to access the package
    // private generated implementation
    public static TypeAdapterFactory create() {
//        return new AutoValueGson_MyAdapterFactory();
        return null;
    }

}
