package com.example.thekhaeng.androidpokemongochallenge.http.dao;

import android.os.Parcel;

import org.parceler.ParcelConverter;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheKhaeng on 9/5/2016.
 */

public class PokemonDaoListConverter implements ParcelConverter<List<PokemonDao>>{

    @Override
    public void toParcel( List<PokemonDao> input, Parcel parcel ){
        if( input == null ){
            parcel.writeInt( -1 );
        }else{
            parcel.writeInt( input.size() );
            for( PokemonDao item : input ){
                parcel.writeParcelable( Parcels.wrap( item ), 0 );
            }
        }
    }

    @Override
    public List<PokemonDao> fromParcel( Parcel parcel ){
        int size = parcel.readInt();
        if( size < 0 ) return null;
        List<PokemonDao> items = new ArrayList<PokemonDao>();
        for( int i = 0; i < size; ++i ){
            items.add( (PokemonDao) Parcels.unwrap( parcel.readParcelable( PokemonDao.class.getClassLoader() ) ) );
        }
        return items;
    }


}
