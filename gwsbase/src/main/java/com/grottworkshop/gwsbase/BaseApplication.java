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

import com.grottworkshop.gwsottoutils.ApplicationBus;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import timber.log.Timber;

/**
 * BaseApplication class, extend to use
 * Created by fgrott on 9/17/2015.
 */
@SuppressWarnings("unused")
public class BaseApplication extends Application {

    public boolean myDebugMode = true;

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


        if(myDebugMode){
            //install our timber debug log
            Timber.plant(new Timber.DebugTree());

        }else{
            //our crash report for app stuff here
            //timber no longer uses Timber.HollowTree
            //need to know replacement
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

    /**
     * BaseCrashReportingTree sets up our specialized
     * timber log tree for app crash reporting integrated
     * with the crash report lib we use
     */
    private static class BaseCrashReportingTree {

    }


}
