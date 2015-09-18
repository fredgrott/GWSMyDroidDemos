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

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import com.grottworkshop.gwsbase.bus.ApplicationOnConfigurationChangedEvent;
import com.grottworkshop.gwsbase.bus.ApplicationOnLowMemoryEvent;
import com.grottworkshop.gwsottoutils.ApplicationBus;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import timber.log.Timber;

/**
 * BaseApplication class, extend to use
 * Requires the internet permission fo debug builds as we are using ViewServer.
 * Created by fgrott on 9/17/2015.
 */
@SuppressWarnings("unused")
public class BaseApplication extends Application {

    public static boolean myDebugMode = true;

    private RefWatcher refWatcher;

    private static final ApplicationBus applicationBus = new ApplicationBus();
    protected static ApplicationBus getBus(){
               return applicationBus;
    }

    /**
     * setDebugMode sets the debug mode, in extended app class we do:
     *
     * <code>
     *     public boolean ourDebugMode = BaseApplication.setDebugMode(BuildConfig.DEBUG);
     * </code>
     *
     * Its required, as the default of boolean myDebugMode is true. It will be marked
     * as unused by the IDE but do nto get rid of, obviously. It will set up as
     * part of the new Object sequence before onStart or OnCreate so will flow through
     * properly the app BuildConfig.DEBUG boolean value.
     *
     * @param appBuildConfigDebug the appBuildConfigDebug of the app
     * @return the boolean either true(debug) or false(release)
     */
    public boolean setDebugMode(boolean appBuildConfigDebug){

        return myDebugMode = appBuildConfigDebug;
    }

    public static RefWatcher getRefWatcher(Context context){
        BaseApplication application = (BaseApplication)context.getApplicationContext();
        return application.refWatcher;
    }


    public BaseApplication() {
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public void onCreate() {
        super.onCreate();
        //our event bus, register it
        applicationBus.start();
        //our mem leak watcher, install it
        refWatcher = LeakCanary.install(this);
        //init the cache
        initCache();
        //init the typehelper
        initTypeHelper();


        if(myDebugMode){
            //install our timber debug log
            Timber.plant(new Timber.DebugTree());
            //strictmode setup
            initStrictMode();

        }else{
            Timber.plant(new BaseCrashReportingTree());
        }
    }

    /**
     * only triggers in the emulators
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        //unregister our bus
        applicationBus.stop();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        applicationBus.post(new ApplicationOnConfigurationChangedEvent());
        Timber.tag("BaseApplication");
        Timber.d("configuration changed, posted event to bus");
        initOnConfigurationChangedBody();
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        applicationBus.post(new ApplicationOnLowMemoryEvent());
        Timber.tag("BaseApplication");
        Timber.d("onLowMemory");
        initOnLowMemoryBody();
    }

    /**
     * initOnLowMemoryBody method container
     */
    public void initOnLowMemoryBody(){

    }

    /**
     * BaseCrashReportingTree sets up our specialized
     * timber log tree for app crash reporting integrated
     * with the crash report lib we use
     */
    private static class BaseCrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            BaseCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    BaseCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    BaseCrashLibrary.logWarning(t);
                }
            }
        }
    }




    /**
     * initCache overridden method so that 3rd party dev can set their app cache in
     * their extended BaseApplication class by overriding this one.
     */
    public void initCache(){

    }

    /**
     * API 15 to about API still have leak problems when loading type via assets
     * so we have this method to override to allow the 3rd party dev to initialize
     * the typehelper class they are using.
     */
    public void initTypeHelper(){

    }

    /**
     * Watch prior to L devices, strict mode incorrectly counts instances on device orientations.
     * Some of us use leakcanary instead to detect leaks.
     */
    public void initStrictMode(){

    }

    /**
     * initOnConfigurationChangedBody method container so that 3rd party devs can
     * put some special configuration changed handling.
     */
    public void initOnConfigurationChangedBody(){

    }

}
