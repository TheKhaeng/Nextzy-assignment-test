package com.example.thekhaeng.androidpokemongochallenge.http.dao;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.ParcelPropertyConverter;

import java.util.ArrayList;

import lombok.ToString;

/**
 * Created by TheKhaeng on 9/5/2016.
 */

@Parcel
@ToString
public class MessageCatchDao{
    @SerializedName( "message" ) private String message;
    @ParcelPropertyConverter( PokemonDaoListConverter.class)
    @SerializedName( "data" ) private ArrayList<PokemonDao> pokemonDaos;

    @ParcelConstructor
    public MessageCatchDao(){ }

    public MessageCatchDao( String message, ArrayList<PokemonDao> pokemonDaos ){

        this.message = message;
        this.pokemonDaos = pokemonDaos;
    }

    public ArrayList<PokemonDao> getPokemonDaos(){
        return pokemonDaos;
    }

    public void setPokemonDaos( ArrayList<PokemonDao> pokemonDaos ){
        this.pokemonDaos = pokemonDaos;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage( String message ){
        this.message = message;
    }
}
