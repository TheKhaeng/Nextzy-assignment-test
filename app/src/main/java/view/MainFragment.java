package view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thekhaeng.androidpokemongochallenge.R;


public class MainFragment extends Fragment {
    private final static String TAG = MainFragment.class.getSimpleName();

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /*************************/
    /** Fragment life cycle **/
    /*************************/
    //<editor-fold desc="Life cycle folding">

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );

        init( savedInstanceState );

        if( savedInstanceState != null ){
            onRestoreInstanceState( savedInstanceState ); // Restore Instance State here
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.fragment_main, container, false);
        initInstance(rootView, savedInstanceState);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @SuppressWarnings("UnusedParameters")
    private void init( Bundle savedInstanceState ){
        // Initialize Fragment level's variables
        // pass savedInstanceState because want to check first fragment create or not?
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstance( View rootView, Bundle savedInstanceState ) {
        // init instance with rootView.findViewById here
        // pass savedInstanceState because want to check first fragment create or not?
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }


    private void onRestoreInstanceState( Bundle savedInstanceSate ){
        // Restore instance state here
    }
    //</editor-fold>

}
