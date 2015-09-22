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
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.view.Menu;

import com.grottworkshop.gwsbase.bus.AppCompatActivityOnActivityResultEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnAttachFragmentEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnAttachToWindowEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnConfigurationChangedEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnContentChangedEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnCreateEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnCreateOptionsMenuEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnCreateSupportNavigateUpTaskStackEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnDestroyEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnLowMemoryEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnMenuOpenedEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnPanelClosedEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnPauseEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnPostCreateEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnPostResumeEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnPrepareOptionsMenuEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnRestartEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnRestoreInstanceStateEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnResumeEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnSaveInstanceStateEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnStartEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnStateNotSavedEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnStopEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnSupportActionModeFinishedEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnSupportActionModeStartedEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnSupportNavigateUpEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnUserInteractionEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnUserLeaveHintEvent;
import com.grottworkshop.gwsbase.bus.AppCompatActivityOnWindowStartingSupportActionModeEvent;
import com.grottworkshop.gwsviewserver.ViewServer;

import timber.log.Timber;

import static com.grottworkshop.gwsbase.BaseApplication.getBus;
import static com.grottworkshop.gwsbase.BaseApplication.myDebugMode;

/**
 * BaseAppCompatActivity class with system lifecycle methods in order at the top of the class and the
 * user initiated misc lifecycle methods at bottom of the class.
 * Created by fgrott on 9/18/2015.
 */
public class BaseAppCompatActivity extends AppCompatActivity {

    public String TAG = "BaseAppCompatActivity";

    Bundle myOutState;

    //main lifecycle methods in order of lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Timber.tag(TAG);
        getBus().post(new AppCompatActivityOnCreateEvent());
        //vewserver call on debug builds
        if(myDebugMode){
            ViewServer.get(this).addWindow(this);
            Timber.d("register view hierarchy with view server");
        }
        initOnCreateBody();
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        getBus().post(new AppCompatActivityOnCreateEvent());
        Timber.tag(TAG);
        Timber.d("onCreate");
        initOnCreateBodyTwo();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        getBus().post(new AppCompatActivityOnAttachFragmentEvent());
        Timber.tag(TAG);
        Timber.d("onAttachFragment");
        initOnAttachFragmentBody();
    }

    @Override
    public void onContentChanged(){
        super.onContentChanged();
        getBus().post(new AppCompatActivityOnContentChangedEvent());
        Timber.tag(TAG);
        Timber.d("onContentChanged");
        initOnContentChangedBody();

    }

    @Override
    public void onStart(){
        super.onStart();
        getBus().post(new AppCompatActivityOnStartEvent());
        Timber.tag(TAG);
        Timber.d("onStart");
        initOnStartBody();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getBus().post(new AppCompatActivityOnRestartEvent());
        Timber.tag(TAG);
        Timber.d("onRestart");
        initOnRestartBody();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        getBus().post(new AppCompatActivityOnActivityResultEvent());
        Timber.tag(TAG);
        Timber.d("onActivityResult");
        initOnActivityResultBody();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        getBus().post(new AppCompatActivityOnRestoreInstanceStateEvent());
        Timber.tag(TAG);
        Timber.d("onRestoreInstanceSte");
        initOnRestoreInstanceStateBody();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        getBus().post(new AppCompatActivityOnPostCreateEvent());
        Timber.tag(TAG);
        Timber.d("onPostCreate");
        initOnPostCreateBody();
    }

    @Override
    public void onResume(){
        super.onResume();
        Timber.tag(TAG);
        //register focus with view server
        if(myDebugMode){
            ViewServer.get(this).setFocusedWindow(this);
            Timber.d("register view focus with view server");
        }
        initOnResumeBody();
        //post our onResume event to bus
        getBus().post(new AppCompatActivityOnResumeEvent());
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getBus().post(new AppCompatActivityOnPostResumeEvent());
        Timber.tag(TAG);
        Timber.d("onPostResume");
        initOnPostResumeBody();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getBus().post(new AppCompatActivityOnAttachToWindowEvent());
        Timber.tag(TAG);
        Timber.d("onAttachToWindow");
        initOnAttachToWindowBody();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getBus().post(new AppCompatActivityOnCreateOptionsMenuEvent());
        Timber.tag(TAG);
        Timber.d("onCreateOptionsMenu");
        initOnCreateOptionsMenuBody();
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getBus().post(new AppCompatActivityOnPrepareOptionsMenuEvent());
        Timber.tag(TAG);
        Timber.d("onPrepareOptionsMenu");
        initOnPrepareOptionsMenuBody();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        getBus().post(new AppCompatActivityOnUserInteractionEvent());
        Timber.tag(TAG);
        Timber.d("onUserInteraction");
        initOnUserInteractionBody();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        getBus().post(new AppCompatActivityOnUserLeaveHintEvent());
        Timber.tag(TAG);
        Timber.d("onUserLeaveHint");
        initOnUserLeaveHintBody();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getBus().post(new AppCompatActivityOnPauseEvent());
        Timber.tag(TAG);
        Timber.d("onPause");
        initOnPauseBody();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getBus().post(new AppCompatActivityOnSaveInstanceStateEvent());
        Timber.tag(TAG);
        Timber.d("onSaveInstanceState");
        myOutState = outState;
        initOnSaveInstanceStateBody(myOutState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getBus().post(new AppCompatActivityOnSaveInstanceStateEvent());
        Timber.tag(TAG);
        Timber.d("onSaveInstanceState");
        initOnSaveInstanceStateBodyTwo();
    }


    @Override
    protected void onStop() {
        super.onStop();
        getBus().post(new AppCompatActivityOnStopEvent());
        Timber.tag(TAG);
        Timber.d("onStop");
        initOnStopBody();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //unregister the view hierarchy with the view server
        Timber.tag(TAG);
        if(myDebugMode){
            ViewServer.get(this).removeWindow(this);
            Timber.d("unregister view hierarchy with view server");
        }
        initOnDestroyBody();
        //post out onDestroy event to bus
        getBus().post(new AppCompatActivityOnDestroyEvent());
    }


    //other mis lifecycle methods


    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        super.onPanelClosed(featureId, menu);
        getBus().post(new AppCompatActivityOnPanelClosedEvent());
        Timber.tag(TAG);
        Timber.d("onPanelClosed");
        initOnPanelClosedBody();
    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);
        getBus().post(new AppCompatActivityOnCreateSupportNavigateUpTaskStackEvent());
        Timber.tag(TAG);
        Timber.d("onCreateSupportNavigateUpTaskStack");
        initOnCreateSupportNavigateUpTaskStackBody();
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        getBus().post(new AppCompatActivityOnMenuOpenedEvent());
        Timber.tag(TAG);
        Timber.d("onMenuOpened");
        initOnMenuOpenedBody();
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {
        super.onSupportActionModeFinished(mode);
        getBus().post(new AppCompatActivityOnSupportActionModeFinishedEvent());
        Timber.tag(TAG);
        Timber.d("onSupportActionModelFinished");
        initOnSupportActionModeFinishedBody();
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {
        super.onSupportActionModeStarted(mode);
        getBus().post(new AppCompatActivityOnSupportActionModeStartedEvent());
        Timber.tag(TAG);
        Timber.d("onSupportActionModeStarted");
        initOnSupportActionModeStartedBody();
    }

    @Override
    public boolean onSupportNavigateUp() {
        getBus().post(new AppCompatActivityOnSupportNavigateUpEvent());
        Timber.tag(TAG);
        Timber.d("onSupportNavigateUp");
        initOnSupportNavigateUpBody();
        return super.onSupportNavigateUp();
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        getBus().post(new AppCompatActivityOnWindowStartingSupportActionModeEvent());
        Timber.tag(TAG);
        Timber.d("onWindowStartingSupportActionMode");
        initOnWindowStartingSupportActionModeBody();
        return super.onWindowStartingSupportActionMode(callback);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getBus().post(new AppCompatActivityOnConfigurationChangedEvent());
        Timber.tag(TAG);
        Timber.d("onConfigurationChanged");
        initOnConfigurationChangedBody();

    }



    @Override
    public void onLowMemory() {
        super.onLowMemory();
        getBus().post(new AppCompatActivityOnLowMemoryEvent());
        Timber.tag(TAG);
        Timber.d("onLowMemory");
        initOnLowMemoryBody();
    }






    @Override
    public void onStateNotSaved(){
        super.onStateNotSaved();
        getBus().post(new AppCompatActivityOnStateNotSavedEvent());
        Timber.tag(TAG);
        Timber.d("onStateNotSaved");
        initOnStateNotSavedBody();
    }

    /**
     * initOnWindowStartingSupportActionModeBody method container
     */
    public void initOnWindowStartingSupportActionModeBody(){}


    /**
     * initOnSupportNavigateUpBody method container
     */
    public void initOnSupportNavigateUpBody(){

    }

    /**
     * initOnSupportActionModeStartedBody method container
     */
    public void initOnSupportActionModeStartedBody(){

    }


    /**
     * initOnSupportActionModeFinishedBody method container
     */
    public void initOnSupportActionModeFinishedBody(){

    }

    /**
     * initOnMenuOpenedBody method container
     */
    public void initOnMenuOpenedBody(){

    }

    /**
     * initOnCreateSupportNavigateUpTaskStackBody method container
     */
    public void initOnCreateSupportNavigateUpTaskStackBody(){

    }

    /**
     * initOnPanelClosedBody method container
     */
    public void initOnPanelClosedBody(){

    }




    /**
     * initOnStopBoyd method container
     */
    public void initOnStopBody(){

    }

    /**
     * initOnSaveInstanceStateBodyTwo method container
     */
    public void initOnSaveInstanceStateBodyTwo(){}


    /**
     * initOnSaveInstanceStateBody method container
     */
    public void initOnSaveInstanceStateBody(Bundle myOutState){}




    /**
     * initOnPauseBody method container
     */
    public void initOnPauseBody(){

    }

    /**
     * initOnUserInteractionBody method container
     */
    public void initOnUserInteractionBody(){

    }


    /**
     * initOnUserLeaveHintBody method container
     */
    public void initOnUserLeaveHintBody(){

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
     * initOnAttachToWindowBody method container
     */
    public void initOnAttachToWindowBody(){

    }

    /**
     * initOnPostResumeBody method container
     */
    public void initOnPostResumeBody(){

    }


    /**
     * initOnPostCreateBody method container
     */
    public void initOnPostCreateBody(){

    }

    /**
     * initOnRestoreInstanceStateBody method container
     */
    public void initOnRestoreInstanceStateBody(){

    }

    /**
     * initOnActivityResultBody method container
     */
    public void initOnActivityResultBody(){}


    /**
     * initOnStartBody method container
     */
    public void initOnStartBody(){

    }

    /**
     * initOnAttachFragmentBody method container
     */
    public void initOnAttachFragmentBody(){

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
