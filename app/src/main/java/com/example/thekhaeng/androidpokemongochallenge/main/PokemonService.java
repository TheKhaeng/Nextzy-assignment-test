package com.example.thekhaeng.androidpokemongochallenge.main;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.thekhaeng.androidpokemongochallenge.ApplicationImpl;
import com.example.thekhaeng.androidpokemongochallenge.bus.MainBus;
import com.example.thekhaeng.androidpokemongochallenge.bus.event.EventBus;
import com.example.thekhaeng.androidpokemongochallenge.constant.C;
import com.example.thekhaeng.androidpokemongochallenge.http.ApiService;
import com.example.thekhaeng.androidpokemongochallenge.http.dao.MessageCatchDao;
import com.example.thekhaeng.androidpokemongochallenge.http.dao.PokemonDao;
import com.example.thekhaeng.androidpokemongochallenge.login.ProfileManager;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by TheKhaeng on 9/5/2016.
 */

public class PokemonService extends Service{

    private final IBinder mBinder = new LocalBinder();
    @Inject ApiService apiService;
    @Inject ProfileManager profileManager;

    private int index;
    private int currentCircle;

    @Nullable
    @Override
    public IBinder onBind( Intent intent ){
        return mBinder;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        ( (ApplicationImpl) getApplication() ).getComponent().inject( this );
    }

    @Override
    public int onStartCommand( Intent intent, int flags, int startId ){
        startSearchPokemon();
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
    }


    private void startSearchPokemon(){
        refresh();
    }

    private void searchPokemon(){
        final Handler handler = new Handler();
        handler.postDelayed( new Runnable(){
            @Override
            public void run(){
                refresh();
            }
        }, getRefreshTime() );
    }

    private long getRefreshTime(){
        Random r = new Random();
        long start = 20000;
        long end = 30000;
        return start + (long) ( r.nextDouble() * ( end - start ) ); // random 10 - 30 sec
    }
//
//    public LatLng pointOnCircle( float radius, LatLng midpoint ){
//        // Convert from degrees to radians via multiplication by PI/180
//
//        double angleInDegrees = 180 / ( Math.PI * currentCircle );
//        LatLng point = SphericalUtil.computeOffset( midpoint, radius, angleInDegrees );
//
//        return point;
//    }

    public void refresh(){
        // TODO1: 9/5/2016 search pokemon around 2km
//        double lat;
//        double lng;
//
//        if( currentCircle == 0 ){
//            currentCircle += 1;
//        }else if( 100 * index > 2 * Math.PI * currentCircle * 100 ){
//            currentCircle += 1;
//            index = 0;
//        }else if( currentCircle > 5 ){
//            currentCircle = 0;
//            index = 0;
//        }
//        LatLng latLng = pointOnCircle( 100 * currentCircle, new LatLng( C.BASE_LAT, C.BASE_LNG ) );
//        lat = latLng.latitude;
//        lng = latLng.longitude;
//        index += 1;
//
//        Bundle data = new Bundle();
//        data.putDouble( "test1", lat );
//        data.putDouble( "test2", lng );
//        Timber.i( "refresh: currentCircle" + currentCircle );
//        Timber.i( "refresh: index" + index );
//        Timber.i( "refresh: lat" + lat + " lng" + lng );
//        searchPokemon();
//        MainBus.getInstance().post( new EventBus( 100, data ) );


        apiService.catchable( profileManager.getLoginToken().getToken(),
                Double.toString( C.BASE_LAT ),
                Double.toString( C.BASE_LNG ) )
                .enqueue( new Callback<MessageCatchDao>(){
                    @Override
                    public void onResponse( Call<MessageCatchDao> call, Response<MessageCatchDao> response ){
                        if( response.isSuccessful() ){
                            Timber.i( "onResponse: " + response.body() );
                            MessageCatchDao dao = response.body();
                            profileManager.setPokemonDaos( dao.getPokemonDaos() );
                            startPokemonCountTimeDelete( dao.getPokemonDaos() );
                            MainBus.getInstance().post( new EventBus( EventBus.SUCCESS_REFRESH_POKEMON) );
                        }else{
                            MainBus.getInstance().post( new EventBus( EventBus.ERROR_REFRESH_POKEMON ) );
                        }
                        searchPokemon();
                    }

                    @Override
                    public void onFailure( Call<MessageCatchDao> call, Throwable t ){
                        MainBus.getInstance().post( new EventBus( EventBus.ERROR_REFRESH_POKEMON ) );
                        Timber.e( t, "Unable to load the books data from API." );
                    }

                } );
    }

    public void startPokemonCountTimeDelete( ArrayList<PokemonDao> pokemons ){
        for( final PokemonDao pokemon : pokemons ){
             new DeletePokemonCountTime(pokemon , Long.parseLong( pokemon.getExTime()), 1000 ).start();
        }
    }

    /*****************/
    /** Inner class **/
    /*****************/
    //<editor-fold desc="Inner class folding">
    public class LocalBinder extends Binder{
        public PokemonService getService(){
            return PokemonService.this;
        }
    }
    //</editor-fold>

    private class DeletePokemonCountTime extends CountDownTimer{
        private PokemonDao pokemon;

        public DeletePokemonCountTime( PokemonDao pokemonDao , long millisInFuture, long countDownInterval) {
            super(millisInFuture,countDownInterval);
            this.pokemon = pokemonDao;
        }

        @Override
        public void onTick( long millisUntilFinished ){
            
        }

        @Override
        public void onFinish(){
            Bundle data = new Bundle();
            data.putParcelable( C.POKEMON, Parcels.wrap( pokemon ) );
            profileManager.removePokemon( pokemon );
            MainBus.getInstance().post( new EventBus( EventBus.POKEMON_TIME_OUT, data ) );
        }
    }

}
