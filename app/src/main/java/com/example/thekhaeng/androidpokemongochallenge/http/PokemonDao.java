package com.example.thekhaeng.androidpokemongochallenge.http;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TheKhaeng on 9/5/2016.
 */

public class PokemonDao{
    @SerializedName( "expiration_timestamp" ) private  String exTime;
    @SerializedName( "latitude" ) private String lat;
    @SerializedName( "longitude" ) private String lng;
    @SerializedName( "name" ) private  String name;
    @SerializedName( "number" ) private String number;
    @SerializedName( "id" ) private String id;

    public PokemonDao(){}

    public PokemonDao(String exTime, String lat, String lng, String name, String number, String id ){

        this.exTime = exTime;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.number = number;
        this.id = id;
    }

    public String getExTime(){
        return exTime;
    }

    public void setExTime( String exTime ){
        this.exTime = exTime;
    }

    public String getLat(){
        return lat;
    }

    public void setLat( String lat ){
        this.lat = lat;
    }

    public String getLng(){
        return lng;
    }

    public void setLng( String lng ){
        this.lng = lng;
    }

    public String getName(){
        return name;
    }

    public void setName( String name ){
        this.name = name;
    }

    public String getNumber(){
        return number;
    }

    public void setNumber( String number ){
        this.number = number;
    }

    public String getId(){
        return id;
    }

    public void setId( String id ){
        this.id = id;
    }
}
