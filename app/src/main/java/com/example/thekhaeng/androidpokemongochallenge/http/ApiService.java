package com.example.thekhaeng.androidpokemongochallenge.http;


import com.example.thekhaeng.androidpokemongochallenge.http.dao.LoginTokenDao;
import com.example.thekhaeng.androidpokemongochallenge.http.dao.MessageCatchDao;
import com.example.thekhaeng.androidpokemongochallenge.http.dao.MessageDao;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService{
    String BASE_URL = "http://sleepingforless.com:6789";

    @GET( "pokemon" )
    Call<MessageDao> checkServer();

    @FormUrlEncoded
    @POST( "pokemon/login" )
    Call<LoginTokenDao> login( @Field( "email" ) String email, @Field( "password" ) String password );

    @GET( "pokemon/catchable" )
    Call<MessageCatchDao> catchable( @Header( "pokemon_token" ) String token,
                                     @Query( "latitude" ) String lat,
                                     @Query( "longitude" ) String lng );

}
