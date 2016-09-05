package com.example.thekhaeng.androidpokemongochallenge.injection.module;

//import com.ryanharter.auto.value.gson.AutoValueGsonTypeAdapterFactory;

import android.content.Context;

import com.example.thekhaeng.androidpokemongochallenge.BuildConfig;
import com.example.thekhaeng.androidpokemongochallenge.constant.C;
import com.example.thekhaeng.androidpokemongochallenge.http.ApiService;
import com.example.thekhaeng.androidpokemongochallenge.http.MyAdapterFactory;
import com.example.thekhaeng.androidpokemongochallenge.util.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module
public class ApiModule{

    public static final String CACHE_CONTROL = "cache-control";
    public static final String HTTP_CACHE = "http-cache";

    @Provides
    @Named( C.BASE_URL )
    public String provideBaseUrl(){
        return ApiService.BASE_URL;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit( @Named( C.BASE_URL ) String baseUrl,
                                     OkHttpClient okHttpClient,
                                     Converter.Factory gsonFactory ){
        return new Retrofit.Builder()
                .baseUrl( baseUrl )
                // for see http request/response logcat
                .client( okHttpClient )
                .addConverterFactory( gsonFactory )
                .build();
    }


    @Provides
    @Singleton
    public Converter.Factory provideGsonConverterFactory(){
        Gson gson = new GsonBuilder()
//                .registerTypeAdapterFactory( MyAdapterFactory.create())
//                .setDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" )
                .create();
        return GsonConverterFactory.create( gson );
    }


    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient( HttpLoggingInterceptor httpLoggingInterceptor ){
        return new OkHttpClient.Builder()
                .addInterceptor( httpLoggingInterceptor )
                .build();
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor( new HttpLoggingInterceptor.Logger(){
                    @Override
                    public void log( String message ){
                        Timber.d( message );
                    }
                } );
        // Logcat only Debug mode ( NOT logging on Production )
        httpLoggingInterceptor.setLevel( BuildConfig.DEBUG
                ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE );
        return httpLoggingInterceptor;
    }


    @Provides
    @Singleton
    public ApiService provideBookService( Retrofit retrofit ){
        return retrofit.create( ApiService.class );
    }
}
