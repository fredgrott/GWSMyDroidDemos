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

import android.os.Bundle;

import java.util.List;

/**
 * BaseSavingState class
 *
 * Usage:
 *
 * In each extended BaseAppCompatActivity and BaseFragmentExtended
 *
 * <code>
 *     public void initOnSaveInstanceStateBody(myOutState){
 *         BaseSavingStateProviderExtendedClass.getInstance().saveinstancestate(myOutState);
 *     }
 *     public void initOnRestoreInstanceStateBody(mySaved){
 *         BaseSavingStateProviderExtendedClass.getInstance().restoreinstancestate(mySaved);
 *     }
 *
 *
 * </code>
 *
 * When app is killed  and restarted it will not restart at the launched activity listed
 * in manifest but start at the last Fragment/Activity user had focus on. Thus, we need to
 * make sure state is saved before that event, hence this solution.
 *
 * Created by fgrott on 9/19/2015.
 */
@SuppressWarnings("unused")
@Deprecated
public abstract class BaseSavingState implements BaseSaveable {



    public List<? extends BaseSaveable> componentsToSave;

    private BaseSavingState mInstance;


    /**
     * Format, ie how to ove-ride
     *
     * <code>
     *
     *     public MyExtendedBaseSavingState(ObjectOne objectOne, ObjectTwo){
     *         componentsToSave = (Arrays.asList(
     *                objectyOne,
     *                objectTwo));
     *     }
     *
     *
     *
     * </code>
     *
     * Make sure to use your extended BaseSavingStateProvider.getInstance to
     * grab a singleton in initGlobalSingletons() of your extended appclass
     * see javadoc of BaseSavingStateProvider
     *
     */
    public  BaseSavingState(){

    }





    @Override
    public void saveInstanceState(Bundle bundle) {

        Bundle instanceState = new Bundle();
        for (BaseSaveable saveable : componentsToSave) {
            saveable.saveInstanceState(instanceState);
        }
        bundle.putBundle("global-state", instanceState);

    }

    @Override
    public void restoreInstanceState(Bundle bundle) {

        if (bundle == null) {
            return;
        }

        Bundle instanceState = bundle.getBundle("global-state");
        if (instanceState == null) {
            return;
        }

        for (BaseSaveable saveable : componentsToSave) {
            saveable.restoreInstanceState(instanceState);
        }
    }
}
