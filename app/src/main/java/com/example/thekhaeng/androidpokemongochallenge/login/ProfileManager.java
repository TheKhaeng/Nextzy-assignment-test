package com.example.thekhaeng.androidpokemongochallenge.login;

import android.content.Context;

import com.example.thekhaeng.androidpokemongochallenge.http.dao.LoginTokenDao;
import com.example.thekhaeng.androidpokemongochallenge.http.dao.PokemonDao;
import com.example.thekhaeng.androidpokemongochallenge.util.VerifiedUtils;

import java.util.ArrayList;
import java.util.Map;

public class ProfileManager{

    private final Context mContext;

    private LoginTokenDao loginToken;
    private Map<String,PokemonDao> mapPokemonDaos;

    public ProfileManager( Context context){
        mContext = context;
    }


    public LoginTokenDao getLoginToken(){
        return loginToken;
    }

    public void setLoginToken( LoginTokenDao loginToken ){
        this.loginToken = loginToken;
    }


    public Map<String,PokemonDao> getPokemonDaos(){
        return mapPokemonDaos;
    }

    public void setPokemonDaos( ArrayList<PokemonDao> pokemonDaos ){
        for( PokemonDao pokemonDao : pokemonDaos ){
            if( !mapPokemonDaos.containsKey( pokemonDao.getId() )){
                mapPokemonDaos.put( pokemonDao.getId(), pokemonDao );
            }
        }
    }

    public void removePokemon( PokemonDao pokemon ){
        if( mapPokemonDaos.containsKey( pokemon.getId() )){
            mapPokemonDaos.remove( pokemon.getId() );
        }
    }
}
