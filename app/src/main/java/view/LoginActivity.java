package view;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.thekhaeng.androidpokemongochallenge.R;

public class LoginActivity extends AppCompatActivity{

    /*************************/
    /** Activity life cycle **/
    /*************************/
    //<editor-fold desc="Activity life cycle folding">
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        initInstances();

        //Add fragment to activity
        if( savedInstanceState == null ){
            //First create
            getSupportFragmentManager().beginTransaction()
                    .add( R.id.contentContainer, LoginFragment.newInstance() )
                    .commit();
        }
    }

    private void initInstances(){
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
    //</editor-fold>


    /** for show Hamburger icon at Action Bar **/
//    @Override
//    protected void onPostCreate( @Nullable Bundle savedInstanceState ){
//        super.onPostCreate( savedInstanceState );
//        actionBarDrawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged( Configuration newConfig ){
//        super.onConfigurationChanged( newConfig );
//        actionBarDrawerToggle.onConfigurationChanged( newConfig );
//    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ){
        return super.onOptionsItemSelected( item );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ){
        super.onSaveInstanceState( outState );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ){
        super.onRestoreInstanceState( savedInstanceState );
    }
}
