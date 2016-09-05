package com.example.thekhaeng.androidpokemongochallenge.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thekhaeng.androidpokemongochallenge.ApplicationImpl;
import com.example.thekhaeng.androidpokemongochallenge.R;
import com.example.thekhaeng.androidpokemongochallenge.http.ApiService;
import com.example.thekhaeng.androidpokemongochallenge.http.dao.LoginTokenDao;
import com.example.thekhaeng.androidpokemongochallenge.http.dao.MessageDao;
import com.example.thekhaeng.androidpokemongochallenge.login.ProfileManager;
import com.example.thekhaeng.androidpokemongochallenge.main.BaseFragment;
import com.example.thekhaeng.androidpokemongochallenge.main.MainActivity;
import com.example.thekhaeng.androidpokemongochallenge.util.VerifiedUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class LoginFragment extends BaseFragment{

    public static String ERROR_LOGIN ;

    private Unbinder unbinder;
    @Inject VerifiedUtils verifiedUtils;
    @Inject ApiService apiService;
    @Inject ProfileManager loginManager;
    @BindView( R.id.btn_next ) FancyButton btnSignIn;
    @BindView( R.id.edt_email ) EditText edtEmail;
    @BindView( R.id.edt_password ) EditText edtPassword;


    public LoginFragment(){
        super();
    }

    public static LoginFragment newInstance(){
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments( args );
        return fragment;
    }

    /*************************/
    /** Fragment life cycle **/
    /*************************/
    //<editor-fold desc="Life cycle folding">
    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );

        ( (ApplicationImpl) getActivity().getApplication() ).getComponent().inject( this );

        checkServer();

        init( savedInstanceState );

        if( savedInstanceState != null ){
            onRestoreInstanceState( savedInstanceState ); // Restore Instance State here
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ){
        View rootView = inflater.inflate( R.layout.fragment_login, container, false );
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
        // init instance with rootView.findViewById here
        // pass savedInstanceState because want to check first fragment create or not?
        ERROR_LOGIN = getContext().getString( R.string.error_login );
    }

    @Override
    public void onStart(){
        super.onStart();
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
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
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

    private void checkServer(){
            apiService.checkServer().enqueue( new Callback<MessageDao>() {
                @Override
                public void onResponse( Call<MessageDao> call, Response<MessageDao> response ){
                    if ( response.isSuccessful() ) {
                        showError( response.body().getMessage() );
                    }else{
                        showError( ERROR_LOGIN );
                    }
                }

                @Override
                public void onFailure( Call<MessageDao> call, Throwable t ){
                    showError( t.getMessage());
                    Timber.e( t, "Unable to load the books data from API." );
                }
//
            } );
    }


    private void login( String email, String pass){
        String error = verifiedUtils.verifiedSignIn( email,pass );
        if( error == null){
            apiService.login( email, pass).enqueue( new Callback<LoginTokenDao>() {
                @Override
                public void onResponse( Call<LoginTokenDao> call, Response<LoginTokenDao> response ){
                    if ( response.isSuccessful() ) {
                        loginManager.setLoginToken( response.body() );
                        launchMainActivity();
                        Timber.i( "Books data was loaded from API." );
                    }else{
                        showError( ERROR_LOGIN );
                    }
                }

                @Override
                public void onFailure( Call<LoginTokenDao> call, Throwable t ){
                    showError( t.getMessage());
                    Timber.e( t, "Unable to load the books data from API." );
                }
//
            } );
        }else{
            Toast.makeText( getActivity(), error, Toast.LENGTH_SHORT ).show();
        }
    }

    private void launchMainActivity(){
        Intent i = new Intent( getActivity(), MainActivity.class );
        startActivity( i );
        getActivity().finish();
    }


    /***************************/
    /** ButterKnife click btn **/
    /***************************/
    //<editor-fold desc="Click btn folding">
    @OnClick( {R.id.btn_next} )
    public void click( View v ){
        switch( v.getId() ){
            case R.id.btn_next:
                String email = edtEmail.getText().toString();
                String pass = edtPassword.getText().toString();
                login( email, pass );
                break;
        }
    }
    //</editor-fold>

}
