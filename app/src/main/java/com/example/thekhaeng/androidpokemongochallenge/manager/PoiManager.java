package com.example.thekhaeng.androidpokemongochallenge.manager;

import android.content.Context;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PoiManager{

    private BitmapDescriptor currentLocationBitmap;
    private Context mContext;

    public PoiManager(Context context){
        mContext = context;
    }

    public void updateCurrentLocationPin( int res ){
        currentLocationBitmap = BitmapDescriptorFactory.fromResource( res );
    }

    public Marker markCurrentLocationOnMap( GoogleMap mMap, LatLng latLng ){
        MarkerOptions mark = getMarkOptionByLatLng( latLng, "Current Location", currentLocationBitmap );
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

}
