package com.grottworkshop.gwsbase;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.grottworkshop.gwsbase.bus.AppCompatActivityOnConfigurationChangedEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnContentChangedEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnCreateEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnDestroyEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnLowMemoryEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnRestartEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnResumeEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnStateNotSavedEvent;
import com.grottworkshop.gwsviewserver.ViewServer;

import timber.log.Timber;

/**
 * BaseAppCompatActivity class
 * Created by fgrott on 9/18/2015.
 */
public class BaseAppCompatActivity extends AppCompatActivity {

    public String TAG = "BaseAppCompatActivity";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Timber.tag(TAG);
        BaseApplication.getBus().post(new AppCompatActivityOnCreateEvent());
        //vewserver call on debug builds
        if(BaseApplication.myDebugMode){
            ViewServer.get(this).addWindow(this);
            Timber.d("register view hierarchy with view server");
        }
        initOnCreateBody();
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        //unregister the view hierarchy with the view server
        Timber.tag(TAG);
        if(BaseApplication.myDebugMode){
            ViewServer.get(this).removeWindow(this);
            Timber.d("unregister view hierarchy with view server");
        }
        initOnDestroyBody();
        //post out onDestroy event to bus
        BaseApplication.getBus().post(new AppCompatActivityOnDestroyEvent());
    }

    @Override
    public void onResume(){
        super.onResume();
        Timber.tag(TAG);
        //register focus with view server
        if(BaseApplication.myDebugMode){
            ViewServer.get(this).setFocusedWindow(this);
            Timber.d("register view focus with view server");
        }
        initOnResumeBody();
        //post our onResume event to bus
        BaseApplication.getBus().post(new AppCompatActivityOnResumeEvent());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        BaseApplication.getBus().post(new AppCompatActivityOnConfigurationChangedEvent());
        Timber.tag(TAG);
        Timber.d("onConfigurationChanged");
        initOnConfigurationChangedBody();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        BaseApplication.getBus().post(new AppCompatActivityOnRestartEvent());
        Timber.tag(TAG);
        Timber.d("onRestart");
        initOnRestartBody();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        BaseApplication.getBus().post(new AppCompatActivityOnLowMemoryEvent());
        Timber.tag(TAG);
        Timber.d("onLowMemory");
        initOnLowMemoryBody();
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        BaseApplication.getBus().post(new AppCompatActivityOnCreateEvent());
        Timber.tag(TAG);
        Timber.d("onCreate");
        initOnCreateBodyTwo();
    }

    @Override
    public void onContentChanged(){
        super.onContentChanged();
        BaseApplication.getBus().post(new AppCompatActivityOnContentChangedEvent());
        Timber.tag(TAG);
        Timber.d("onContentChanged");
        initOnContentChangedBody();

    }


    @Override
    public void onStateNotSaved(){
        super.onStateNotSaved();
        BaseApplication.getBus().post(new AppCompatActivityOnStateNotSavedEvent());
        Timber.tag(TAG);
        Timber.d("onStateNotSaved");
        initOnStateNotSavedBody();
    }

    /**
     * initOnStateNotSavedBody method container
     */
    public void initOnStateNotSavedBody(){

    }

    /**
     * initOnContentChangedBody method container
     */
    public void initOnContentChangedBody(){

    }

    /**
     * initOnCreateBodyTow method container
     */
    public void initOnCreateBodyTwo(){

    }


    /**
     * initOnLowMemoryBody method container
     */
    public void initOnLowMemoryBody(){}


    /**
     * initOnRestartBody method container
     */
    public void initOnRestartBody(){

    }

    /**
     * initOnConfigurationChangedBody method container
     */
    public void initOnConfigurationChangedBody(){

    }

    /**
     * initOnCreateBody method container as we want to have it so that
     * we can add to the onCreate method without messing up what has
     * to be in the base class onCreate. Thus, this method overridden
     * by 3rd party devs to add to the onCreate method
     */
    public void initOnCreateBody(){

    }

    /**
     * initOnDestroyBody method container for 3rrd party devs to add stuff to
     * the onDestroy method
     */
    public void initOnDestroyBody(){

    }

    /**
     * initOnResumeBoyd method container for 3rd party devs to add stuff to the
     * onResume method
     */
    public void initOnResumeBody(){

    }
}
