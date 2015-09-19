package com.grottworkshop.gwsbase;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.grottworkshop.gwsbase.bus.ActivityResultEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnConfigurationChangedEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnCreateEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnDestroyEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnLowMemoryEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnPauseEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnStartEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnStopEvent;
import com.squareup.otto.Subscribe;

import timber.log.Timber;

/**
 * BaseFragmentNestedActivityResult class
 * Created by fgrott on 9/17/2015.
 */
@SuppressWarnings("unused")
public class BaseFragmentNestedActivityResult extends Fragment {

    @Override
    public void onStart(){
        super.onStart();
        BaseApplication.getBus().register(mActivityResultSubscriber);
        BaseApplication.getBus().post(new FragmentOnStartEvent());
        Timber.tag("FragmentNestedActivityResult");
        Timber.d("onStart");
        initOnStartBody();
    }

    /**
     * initOnStartBody method container
     */
    public void initOnStartBody(){

    }

    @Override
    public void onStop(){
        super.onStop();
        BaseApplication.getBus().unregister(mActivityResultSubscriber);
        BaseApplication.getBus().post(new FragmentOnStopEvent());
        Timber.tag("FragmentNestedActivityResult");
        Timber.d("onStop");
        initOnStopBody();
    }

    /**
     * initOnStopBody method container
     */
    public void initOnStopBody(){

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        BaseApplication.getBus().post(new FragmentOnConfigurationChangedEvent());
        Timber.tag("FragmentNestedActivityResult");
        Timber.d("onConfigurationChanged");
        initOnConfigurationChangedBody();
    }

    /**
     * initOnConfigurationChangedBody method container
     */
    public void initOnConfigurationChangedBody(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getBus().post(new FragmentOnCreateEvent());
        Timber.tag("FragmentNestedActivityResult");
        Timber.d("onCreate");
        initOnCreateBody();
    }

    /**
     * initOnCreateBody method container
     */
    public void initOnCreateBody(){

    }

    @Override
    public void onPause() {
        super.onPause();
        BaseApplication.getBus().post(new FragmentOnPauseEvent());
        Timber.tag("FragmentNestedActivityResult");
        Timber.d("onPause");
        initOnPauseBody();
    }

    /**
     * initOnPauseBody method container
     */
    public void initOnPauseBody(){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.getBus().post(new FragmentOnDestroyEvent());
        Timber.tag("FragmentNestedActivityResult");
        Timber.d("onDestroy");
        initOnDestroyBody();
    }

    /**
     * initOnDestroyBody method container
     */
    public void initOnDestroyBody(){

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        BaseApplication.getBus().post(new FragmentOnLowMemoryEvent());
        Timber.tag("FragmentNestedActivityResult");
        Timber.d("onLowMemory");
        initOnLowMemoryBody();
    }

    /**
     * initOnLowMemoryBody method container
     */
    public void initOnLowMemoryBody(){

    }

    private Object mActivityResultSubscriber = new Object() {
        @Subscribe
        public void onActivityResultReceived(ActivityResultEvent event) {
            int requestCode = event.getRequestCode();
            int resultCode = event.getResultCode();
            Intent data = event.getData();
            onActivityResult(requestCode, resultCode, data);
        }
    };
}
