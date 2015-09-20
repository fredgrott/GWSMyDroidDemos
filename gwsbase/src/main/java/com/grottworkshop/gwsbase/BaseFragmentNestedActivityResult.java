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


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grottworkshop.gwsbase.bus.ActivityResultEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnActivityCreatedEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnAttachEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnConfigurationChangedEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnCreateEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnCreateOptionsMenuEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnCreateViewEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnDestroyEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnDestroyViewEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnDetachEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnLowMemoryEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnPauseEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnPrepareOptionsMenuEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnResumeEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnSaveInstanceStateEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnStartEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnStopEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnViewCreatedEvent;
import com.grottworkshop.gwsbase.bus.FragmentOnViewStateRestoredEvent;
import com.squareup.otto.Subscribe;

import timber.log.Timber;

/**
 * BaseFragmentNestedActivityResult class with system lifecycle methods in order at the top of the class and the
 * user initiated misc lifecycle methods at bottom of the class.
 *
 * Be careful as onActivityCreated may not be called if fragments are created in a viewpager.
 * Created by fgrott on 9/17/2015.
 */
@SuppressWarnings("unused")
public class BaseFragmentNestedActivityResult extends Fragment {

    public String TAG = "BaseFragmentNestedActivityResult";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        BaseApplication.getBus().post(new FragmentOnAttachEvent());
        Timber.tag(TAG);
        Timber.d("onAttach");
        initOnAttachBody();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getBus().post(new FragmentOnCreateEvent());
        Timber.tag(TAG);
        Timber.d("onCreate");
        initOnCreateBody();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BaseApplication.getBus().post(new FragmentOnCreateViewEvent());
        Timber.tag(TAG);
        Timber.d("onCreateView");
        initOnCreateViewBody();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BaseApplication.getBus().post(new FragmentOnViewCreatedEvent());
        Timber.tag(TAG);
        Timber.d("onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BaseApplication.getBus().post(new FragmentOnActivityCreatedEvent());
        Timber.tag(TAG);
        Timber.d("onActivityCreated");
        initOnActivityCreatedBody();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        BaseApplication.getBus().post(new FragmentOnViewStateRestoredEvent());
        Timber.tag(TAG);
        Timber.d("onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        BaseApplication.getBus().register(mActivityResultSubscriber);
        BaseApplication.getBus().post(new FragmentOnStartEvent());
        Timber.tag(TAG);
        Timber.d("onStart");
        initOnStartBody();
    }


    @Override
    public void onResume() {
        super.onResume();
        BaseApplication.getBus().post(new FragmentOnResumeEvent());
        Timber.tag(TAG);
        Timber.d("onResume");
        initOnResumeBody();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        BaseApplication.getBus().post(new FragmentOnCreateOptionsMenuEvent());
        Timber.tag(TAG);
        Timber.d("onCreateOptionsMenu");
        initOnCreateOptionsMenuBody();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        BaseApplication.getBus().post(new FragmentOnPrepareOptionsMenuEvent());
        Timber.tag(TAG);
        Timber.d("onPrepareOptionsMenu");
        initOnPrepareOptionsMenuBody();
    }


    @Override
    public void onPause() {
        super.onPause();
        BaseApplication.getBus().post(new FragmentOnPauseEvent());
        Timber.tag(TAG);
        Timber.d("onPause");
        initOnPauseBody();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BaseApplication.getBus().post(new FragmentOnSaveInstanceStateEvent());
        Timber.tag(TAG);
        Timber.d("onSaveInstanceState");
        initOnSaveInstanceStateBody();
    }

    @Override
    public void onStop() {
        super.onStop();
        BaseApplication.getBus().unregister(mActivityResultSubscriber);
        BaseApplication.getBus().post(new FragmentOnStopEvent());
        Timber.tag(TAG);
        Timber.d("onStop");
        initOnStopBody();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BaseApplication.getBus().post(new FragmentOnDestroyViewEvent());
        Timber.tag(TAG);
        Timber.d("onDestroyView");
        initOnDestroyViewBody();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.getBus().post(new FragmentOnDestroyEvent());
        Timber.tag(TAG);
        Timber.d("onDestroy");
        initOnDestroyBody();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        BaseApplication.getBus().post(new FragmentOnDetachEvent());
        Timber.tag(TAG);
        Timber.d("onDetach");
        initOnDetachBody();
    }

    //misc user lifecycle initiated methods



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        BaseApplication.getBus().post(new FragmentOnConfigurationChangedEvent());
        Timber.tag(TAG);
        Timber.d("onConfigurationChanged");
        initOnConfigurationChangedBody();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        BaseApplication.getBus().post(new FragmentOnLowMemoryEvent());
        Timber.tag(TAG);
        Timber.d("onLowMemory");
        initOnLowMemoryBody();
    }


    /**
     * initOnDetachBody method container
     */
    public void initOnDetachBody(){

    }

    /**
     * initOnDestroyViewBody method container
     */
    public void initOnDestroyViewBody(){

    }

    /**
     * initOnSaveInstanceStateBody method container
     */
    public void initOnSaveInstanceStateBody(){

    }


    /**
     * initOnPrepareOptionsMenuBody method container
     */
    public void initOnPrepareOptionsMenuBody(){

    }

    /**
     * initOnCreateOptionsMenuBody method container
     */
    public void initOnCreateOptionsMenuBody(){

    }


    /**
     * initOnResumeBody method container
     */
    public void initOnResumeBody(){

    }



    /**
     * initOnActivityCreatedBody method container
     */
    public void initOnActivityCreatedBody(){

    }

    /**
     * initOnCreateViewBody method container
     */
    public void initOnCreateViewBody(){

    }

    /**
     * initOnAttachBody method container
     */
    public void initOnAttachBody(){

    }



    /**
     * initOnStartBody method container
     */
    public void initOnStartBody(){

    }



    /**
     * initOnStopBody method container
     */
    public void initOnStopBody(){

    }



    /**
     * initOnConfigurationChangedBody method container
     */
    public void initOnConfigurationChangedBody(){

    }



    /**
     * initOnCreateBody method container
     */
    public void initOnCreateBody(){

    }



    /**
     * initOnPauseBody method container
     */
    public void initOnPauseBody(){

    }



    /**
     * initOnDestroyBody method container
     */
    public void initOnDestroyBody(){

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
