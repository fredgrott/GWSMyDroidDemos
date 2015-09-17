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

import com.grottworkshop.gwsbase.bus.FragmentOnDestroyEvent;
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
    }

    @Override
    public void onStop(){
        super.onStop();
        BaseApplication.getBus().post(new FragmentOnStopEvent());
        Timber.d("fragment stopped");
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        //part of leakcanary will watch for leaks when fragment is used
        RefWatcher refWatcher = BaseApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
        BaseApplication.getBus().post(new FragmentOnDestroyEvent());
        Timber.d("fragment destroyed");
    }
}
