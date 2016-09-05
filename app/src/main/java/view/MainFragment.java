package view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thekhaeng.androidpokemongochallenge.Application;
import com.example.thekhaeng.androidpokemongochallenge.ApplicationImpl;
import com.example.thekhaeng.androidpokemongochallenge.R;
import com.example.thekhaeng.androidpokemongochallenge.manager.PoiManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import javax.inject.Inject;

import timber.log.Timber;


public class MainFragment extends Fragment
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    protected final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;

    @Inject PoiManager poiManager;
    private GoogleMap mMap;
    private Marker currentPOIMarker;

    public MainFragment(){
        super();
    }

    public static MainFragment newInstance(){
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments( args );
        return fragment;
    }

    private synchronized void buildGoogleApiClient(){
        Timber.i( "buildGoogleApiClient: " );
        mGoogleApiClient = new GoogleApiClient.Builder( getActivity() )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API )
                .build();
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
        // init instance with rootView.findViewById here
        // pass savedInstanceState because want to check first fragment create or not?
        if( checkGooglePlayServicesAvailable() ){
            buildGoogleApiClient();
            requestAndSetupGoogleMap();
        }
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
        if( mGoogleApiClient != null ){
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onPause(){
        super.onPause();

        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ){
            // Disconnect Google API Client if available and connected
            Timber.i( "onPause: google api disconnect" );
            mGoogleApiClient.disconnect();
        }
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


    /************************/
    /** Location providing **/
    /************************/
    //<editor-fold desc="Location providing folding">
    @Override
    public void onConnected( @Nullable Bundle bundle ){
        Timber.i( "onConnected: " );
        if( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ){
            return;
        }
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability( mGoogleApiClient );
        if( locationAvailability.isLocationAvailable() ){
            // Call Location Services
            locationRequest = new LocationRequest()
                    .setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );
            findCurrentLocation();
        }else{
            // Do something when Location Provider not available
        }
    }

    @Override
    public void onConnectionSuspended( int i ){

    }

    @Override
    public void onConnectionFailed( @NonNull ConnectionResult connectionResult ){
        Timber.i( "onConnectionFailed: " );
    }

    private void findCurrentLocation(){
        Timber.i( "findCurrentLocation: " );
        if( mGoogleApiClient != null ){
            if( Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission( getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission( getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ){
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates( mGoogleApiClient, locationRequest, locListener );
        }else{
            buildGoogleApiClient();
        }
    }

    private final LocationListener locListener = new LocationListener(){
        @Override
        public void onLocationChanged( Location loc ){
            Double currentLat = loc.getLatitude();
            Double currentLng = loc.getLongitude();
            Timber.i( "onLocationChanged: " + currentLat + ", " + currentLng );
            // resolve POI

            if( currentPOIMarker != null ){
                currentPOIMarker.remove();
            }
            currentPOIMarker = poiManager.markCurrentLocationOnMap( mMap, new LatLng( currentLat, currentLng ) );
        }
    };

    //</editor-fold>

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
//        mMap.getUiSettings().setMapToolbarEnabled( false );
        poiManager.updateCurrentLocationPin( R.drawable.ic_current_location_36dp );
//        poiManager.updateBikeLocationPin( R.drawable.current_poi );
//
//        SharedPreferences prefs = getActivity()
//                .getSharedPreferences( LocationMapManager.PENGUIO_LOCATION, Context.MODE_PRIVATE );
//        Double lat = (double) prefs.getFloat( LocationMapManager.LAT, 0.0f );
//        Double lng = (double) prefs.getFloat( LocationMapManager.LNG, 0.0f );
//        Timber.i( "setUpMap: getBikeLocation " + lat + ", " + lng );
//        bikePOIMarker = PoiManager.getInstance().
//                markBikeLocationOnMap( mMap, new LatLng( lat, lng ) );

    }
    //</editor-fold>
}
