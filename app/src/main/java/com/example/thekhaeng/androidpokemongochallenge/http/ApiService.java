package com.example.thekhaeng.androidpokemongochallenge.http;


import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService{
    String BASE_URL = "http://sleepingforless.com:6789";

    @GET( "pokemon" )
    Call<MessageDao> checkServer();

    @FormUrlEncoded
    @POST( "pokemon/login" )
    Call<LoginTokenDao> login( @Field( "email" ) String email, @Field( "password" ) String password );

    @GET( "pokemon/catchable" )
    Call<Dao> catchable(@Query( "latitude" ) String lat, @Query( "longitude" ) String lng);

}
