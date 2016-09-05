package com.example.thekhaeng.androidpokemongochallenge.map;

import android.content.Context;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PoiManager{

    private Context mContext;

    public PoiManager( Context context ){
        mContext = context;
    }


    public Marker markCurrentLocationOnMap( GoogleMap mMap, LatLng latLng, int resId ){
        MarkerOptions mark = getMarkOptionByLatLng(
                latLng,
                "Current Location",
                BitmapDescriptorFactory.fromResource( resId ) );
        Marker currentPOIMarker = mMap.addMarker( mark );
        mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(
                latLng, 13 ) );
        return currentPOIMarker;
    }

    public MarkerOptions getMarkOptionByLatLng( LatLng latLng, String title, BitmapDescriptor icon ){
        return new MarkerOptions()
                .position( latLng )
                .title( title )
                .snippet( null )
                .icon( icon );
    }


    public Marker getMarkerPokemonLocationOnMap( GoogleMap mMap, LatLng latLng, String name, int resId ){
        MarkerOptions mark = getMarkOptionByLatLng(
                latLng,
                name,
                BitmapDescriptorFactory.fromResource( resId ) );
        Marker pokemonPOIMarker = mMap.addMarker( mark );
        mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(
                latLng, 13 ) );
        return pokemonPOIMarker;
    }

}
