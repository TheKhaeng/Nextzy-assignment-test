package com.example.thekhaeng.androidpokemongochallenge.main;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;


/**
 * Created by Nonthawit on 6/28/2016.
 */

public class BaseFragment extends Fragment{

    public void showError( final String text ){
        Handler handlerTimer = new Handler();
        handlerTimer.postDelayed(new Runnable(){
            public void run() {
                // do something
                Snackbar.make( getView(), text, Snackbar.LENGTH_LONG ).show();
            }}, 200);
    }

    public void showInfiniteError( String text ){
        Snackbar.make( getView(), text, Snackbar.LENGTH_INDEFINITE ).show();
    }

    public void showError( int id ){
        Snackbar.make( getView(), id, Snackbar.LENGTH_LONG ).show();
    }
}
