/*
 * Copyright 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.grottworkshop.gwsbase;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;

import com.grottworkshop.gwsbase.bus.FragmentOnConfigurationChangedEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnCreateEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnDestroyEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnLowMemoryEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnPauseEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnResumeEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnStartEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnStopEvent;
import com.squareup.leakcanary.RefWatcher;

import timber.log.Timber;

/**
 * BaseFragment
 * Created by fgrott on 9/17/2015.
 */
@SuppressWarnings("unused")
public class BaseFragment extends Fragment {



    @Override
    public void onStart(){
        super.onStart();
        BaseApplication.getBus().post(new FragmentOnStartEvent());
        Timber.tag("Fragment");
        Timber.d("fragment started");
        initOnStartBody();
    }

    /**
     * initOnStartBody method container, overridden by 3rd party devs
     */
    public void initOnStartBody(){

    }

    @Override
    public void onStop(){
        super.onStop();
        BaseApplication.getBus().post(new FragmentOnStopEvent());
        Timber.tag("Fragment");
        Timber.d("fragment stopped");
        initOnStopBody();
    }

    /**
     * initOnStopBody method container
     */
    public void initOnStopBody(){

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //part of leakcanary will watch for leaks when fragment is used
        RefWatcher refWatcher = BaseApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
        BaseApplication.getBus().post(new FragmentOnDestroyEvent());
        Timber.tag("Fragment");
        Timber.d("fragment destroyed");
        initOnDestroyBody();
    }

    /**
     * initOnDestroyBody() method container
     */
    public void initOnDestroyBody(){

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        BaseApplication.getBus().post(new FragmentOnConfigurationChangedEvent());
        Timber.tag("Fragment");
        Timber.d("fragment onConfigurationChanged");
        initOnConfigurationChangedBody();
    }

    /**
     * initOnConfigurationChanged method container
     */
    public void initOnConfigurationChangedBody(){

    }

    @Override
    public void onResume() {
        super.onResume();
        BaseApplication.getBus().post(new FragmentOnResumeEvent());
        Timber.tag("Fragment");
        Timber.d("fragment onResume");
        initOnResumeBody();
    }

    /**
     * initOnResumeBody method container
     */
    public void initOnResumeBody(){

    }

    @Override
    public void onPause() {
        super.onPause();
        BaseApplication.getBus().post(new FragmentOnPauseEvent());
        Timber.tag("Fragment");
        Timber.d("fragment onPause");
        initOnPauseBody();
    }

    /**
     * initOnPauseBody method container
     */
    public void initOnPauseBody(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getBus().post(new FragmentOnCreateEvent());
        Timber.tag("Fragment");
        Timber.d("fragment onCreate");
        initOnCreateBody();
    }

    /**
     * initOnCreateBody method container
     */
    public void initOnCreateBody(){

    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        BaseApplication.getBus().post(new FragmentOnLowMemoryEvent());
        Timber.tag("Fragment");
        Timber.d("onLowMemory");
        initOnLowMemoryBody();
    }

    /**
     * initOnLowMemoryBody method container
     */
    public void initOnLowMemoryBody(){

    }

}
