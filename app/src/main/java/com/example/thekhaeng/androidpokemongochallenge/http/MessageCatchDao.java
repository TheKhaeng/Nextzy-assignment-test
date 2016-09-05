package com.example.thekhaeng.androidpokemongochallenge.http;

import java.util.ArrayList;

/**
 * Created by TheKhaeng on 9/5/2016.
 */
public class MessageCatchDao{
    private String message;
    private ArrayList<PokemonDao> pokemonDaos;

    public MessageCatchDao(){

    }

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
