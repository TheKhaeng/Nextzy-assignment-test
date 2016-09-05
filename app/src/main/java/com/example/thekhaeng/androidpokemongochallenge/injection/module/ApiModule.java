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
    public OkHttpClient provideOkHttpClient( HttpLoggingInterceptor httpLoggingInterceptor,
                                             Interceptor offlineCacheInterceptor,
                                             Interceptor cacheInterceptor,
                                             Cache cache ){
        return new OkHttpClient.Builder()
                .addInterceptor( httpLoggingInterceptor )
                .addInterceptor( offlineCacheInterceptor )
                // If this request not hit the server, It's use OkHttp Cache.
                .addNetworkInterceptor( cacheInterceptor )
                .cache( cache )
                .build();
    }

    @Provides
    @Singleton
    public Cache provideCache( Context context ){
        Cache cache = null;
        try{
            cache = new Cache( new File( context.getCacheDir(), HTTP_CACHE ),
                    10 * 1024 * 1024 ); // 10 MB
        }catch( Exception e ){
            Timber.e( e, "Could not create Cache!" );
        }
        return cache;
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
    public Interceptor provideCacheInterceptor(){
        return new Interceptor(){
            @Override
            public Response intercept( Chain chain ) throws IOException{
                Response response = chain.proceed( chain.request() );

                // re-write response header to force use of cache
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge( 2, TimeUnit.MINUTES )
                        .build();

                return response.newBuilder()
                        .header( CACHE_CONTROL, cacheControl.toString() )
                        .build();
            }
        };
    }

    @Provides
    @Singleton
    public Interceptor provideOfflineCacheInterceptor( final Context context ){
        return new Interceptor(){
            @Override
            public Response intercept( Chain chain ) throws IOException{
                Request request = chain.request();

                if( !NetworkUtil.isNetworkConnected( context ) ){
                    CacheControl cacheControl = new CacheControl.Builder()
                            // cache life time: 7 day
                            .maxStale( 7, TimeUnit.DAYS )
                            .build();

                    request = request.newBuilder()
                            .cacheControl( cacheControl )
                            .build();
                }

                return chain.proceed( request );
            }
        };
    }

    @Provides
    @Singleton
    public static ApiService provideBookService( Retrofit retrofit ){
        return retrofit.create( ApiService.class );
    }
}
