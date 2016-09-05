package com.example.thekhaeng.androidpokemongochallenge.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thekhaeng.androidpokemongochallenge.ApplicationImpl;
import com.example.thekhaeng.androidpokemongochallenge.R;
import com.example.thekhaeng.androidpokemongochallenge.bus.MainBus;
import com.example.thekhaeng.androidpokemongochallenge.bus.event.EventBus;
import com.example.thekhaeng.androidpokemongochallenge.constant.C;
import com.example.thekhaeng.androidpokemongochallenge.http.dao.MessageCatchDao;
import com.example.thekhaeng.androidpokemongochallenge.http.dao.PokemonDao;
import com.example.thekhaeng.androidpokemongochallenge.login.ProfileManager;
import com.example.thekhaeng.androidpokemongochallenge.login.view.LoginActivity;
import com.example.thekhaeng.androidpokemongochallenge.map.PoiManager;
import com.example.thekhaeng.androidpokemongochallenge.util.ViewUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.squareup.otto.Subscribe;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mehdi.sakout.fancybuttons.FancyButton;
import timber.log.Timber;


public class MainFragment extends BaseFragment{
    protected final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static String ERROR_CACTH;

    //    private GoogleApiClient mGoogleApiClient;

    @Inject PoiManager poiManager;
    @Inject PokemonManager pokemonManager;
    @Inject ProfileManager profileManager;
    @BindView( R.id.btn_sign_out ) FancyButton btnSignOut;
    @BindView( R.id.btn_refresh ) FancyButton btnRefresh;
    @BindView( R.id.txt_amount ) FancyButton txtAmount;

    private GoogleMap mMap;
    private Marker currentPOIMarker;
    private Map<String, Marker> pokemonMarkers;
    private Unbinder unbinder;
    private Intent serviceIntent;
    private PokemonService pokemonService;

    private final ServiceConnection mServiceConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected( ComponentName componentName, IBinder binder ){
            Timber.i( "onServiceConnected: " );
            PokemonService.LocalBinder serviceBinder = (PokemonService.LocalBinder) binder;
            pokemonService = (PokemonService) serviceBinder.getService();
        }

        @Override
        public void onServiceDisconnected( ComponentName componentName ){
            pokemonService = null;
        }
    };

    public MainFragment(){
        super();
    }

    public static MainFragment newInstance(){
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments( args );
        return fragment;
    }

    private boolean checkGooglePlayServicesAvailable(){
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable( getActivity() );
        if( result != ConnectionResult.SUCCESS ){
            if( googleAPI.isUserResolvableError( result ) ){
                googleAPI.getErrorDialog( getActivity(), result,
                        PLAY_SERVICES_RESOLUTION_REQUEST ).show();
            }else{
                Timber.i( "This device is not supported." );
            }
            return false;
        }

        return true;
    }
    /*************************/
    /** Fragment life cycle **/
    /*************************/
    //<editor-fold desc="Life cycle folding">
    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );

        ( (ApplicationImpl) getActivity().getApplication() ).getComponent().inject( this );

        init( savedInstanceState );

        if( savedInstanceState != null ){
            onRestoreInstanceState( savedInstanceState ); // Restore Instance State here
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ){
        View rootView = inflater.inflate( R.layout.fragment_main, container, false );
        unbinder = ButterKnife.bind( this, rootView );
        initInstance( rootView, savedInstanceState );
        return rootView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ){
        super.onActivityCreated( savedInstanceState );
    }

    @SuppressWarnings( "UnusedParameters" )
    private void init( Bundle savedInstanceState ){
        // Initialize Fragment level's variables
        // pass savedInstanceState because want to check first fragment create or not?
    }

    @SuppressWarnings( "UnusedParameters" )
    private void initInstance( View rootView, Bundle savedInstanceState ){
        MainBus.getInstance().register( this );
        // init instance with rootView.findViewById here
        // pass savedInstanceState because want to check first fragment create or not?
        pokemonMarkers = new HashMap<String, Marker>();
        ERROR_CACTH = getContext().getString( R.string.error_catch );

        serviceIntent = new Intent( getActivity(), PokemonService.class );
        getActivity().startService( serviceIntent );
        getActivity().bindService( serviceIntent, mServiceConnection, Context.BIND_AUTO_CREATE );


    }

    @Override
    public void onStart(){
        super.onStart();
        if( checkGooglePlayServicesAvailable() ){
            requestAndSetupGoogleMap();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
        getActivity().stopService( serviceIntent );
        if( mServiceConnection != null ){
            getActivity().unbindService( mServiceConnection );
        }
        MainBus.getInstance().unregister( this );
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState( Bundle outState ){
        super.onSaveInstanceState( outState );
        // Save Instance State here
    }


    private void onRestoreInstanceState( Bundle savedInstanceSate ){
        // Restore instance state here
    }
    //</editor-fold>

    @Subscribe
    public void receiveEventBus( EventBus event ){
        int action = event.getAction();
        switch( action ){
            case EventBus.SUCCESS_REFRESH_POKEMON:
                Timber.i( "receiveEventBus: success refresh" );
                updatePokemonLocation( new ArrayList<PokemonDao>( profileManager.getPokemonDaos().values() ) );
                break;
            case EventBus.ERROR_REFRESH_POKEMON:
                Timber.i( "receiveEventBus: error refresh" );
                showError( ERROR_CACTH );
                break;
            case EventBus.POKEMON_TIME_OUT:
                PokemonDao pokemon = Parcels.unwrap( event.getData().getParcelable( C.POKEMON ) );
                removeMarker( pokemon );
                break;

        }
    }

    private void removeMarker( PokemonDao pokemon ){
        if( pokemonMarkers.containsKey( pokemon.getId() ) ){
            pokemonMarkers.get( pokemon.getId() ).remove();
        }
    }


    /****************/
    /** Google map **/
    /****************/
    //<editor-fold desc="Google map folding">
    private void requestAndSetupGoogleMap(){
        SupportMapFragment mapFragment = ( (SupportMapFragment) getChildFragmentManager().findFragmentById( R.id.map ) );
        mapFragment.getMapAsync( new OnMapReadyCallback(){
            @Override
            public void onMapReady( GoogleMap map ){
                setUpMap( map );
            }
        } );
    }

    private void setUpMap( GoogleMap map ){
        mMap = map;
        // disable map toolbar
        mMap.getUiSettings().setMapToolbarEnabled( false );
        // Move the camera instantly to location with a zoom of 15.
        map.moveCamera( CameraUpdateFactory.newLatLngZoom( new LatLng( C.BASE_LAT, C.BASE_LNG ), 15 ) );
        // Zoom in, animating the camera.
        map.animateCamera( CameraUpdateFactory.zoomTo( calculateZoomLevel() ), 1000, null );
        CircleOptions circleOptions = new CircleOptions()
                .center( new LatLng( C.BASE_LAT, C.BASE_LNG ) )
                .strokeColor( ContextCompat.getColor( getContext(), R.color.md_red400 ) )
                .strokeWidth( ViewUtil.dpToPx( 2 ) )
                .radius( 2000 ); // In meters

        mMap.addCircle( circleOptions );

        if( currentPOIMarker != null ){
            currentPOIMarker.remove();
        }
        currentPOIMarker = poiManager.markCurrentLocationOnMap(
                mMap,
                new LatLng( C.BASE_LAT, C.BASE_LNG ),
                R.drawable.ic_current_location_36dp );

    }

    private int calculateZoomLevel(){
        double equatorLength = 40075004; // in meters

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( metrics );

        double widthInPixels = metrics.widthPixels;
        double metersPerPixel = equatorLength / 256;
        int zoomLevel = 1;
        while( ( metersPerPixel * widthInPixels ) > 2000 ){
            metersPerPixel /= 2;
            ++zoomLevel;
        }
        Timber.i( "zoom level = " + zoomLevel );
        return zoomLevel;
    }

    private void refresh(){
        pokemonService.refresh();
    }

    private void updatePokemonLocation( ArrayList<PokemonDao> pokemons ){
        for( PokemonDao pokemon : pokemons ){
            String number = pokemon.getNumber();
            double lat = Double.parseDouble( pokemon.getLat() );
            double lng = Double.parseDouble( pokemon.getLng() );

            if( pokemonMarkers.containsKey( number ) ){
                pokemonMarkers.get( number ).remove();
            }


            Marker pokemonMarker = poiManager.getMarkerPokemonLocationOnMap(
                    mMap,
                    new LatLng( lat, lng ),
                    pokemon.getName(),
                    getResourcesPokemonId( number ) );
            pokemonMarkers.put( number, pokemonMarker );
        }

    }

    private int getResourcesPokemonId( String number ){
        String num = number.replaceFirst( "^0+(?!$)", "" );
        int resourceId = getActivity()
                .getResources()
                .getIdentifier( C.PREFIX_POKEMON + num,
                        "drawable",
                        getActivity().getApplicationContext().getPackageName() );
        return resourceId;
    }
    //</editor-fold>

    private void launchLoginActivity(){
        Intent i = new Intent( getActivity(), LoginActivity.class );
        startActivity( i );
        getActivity().finish();
    }

    /***************************/
    /** ButterKnife click btn **/
    /***************************/
    //<editor-fold desc="Click btn folding">
    @OnClick( {R.id.btn_sign_out, R.id.btn_refresh} )
    public void click( View v ){
        switch( v.getId() ){
            case R.id.btn_sign_out:
                launchLoginActivity();
                ( (ApplicationImpl) getActivity().getApplication() ).resetComponent();
                break;
            case R.id.btn_refresh:
                refresh();
                break;
        }
    }
    //</editor-fold>
}
