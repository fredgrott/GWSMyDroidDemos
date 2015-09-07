/*
 * Copyright (C) 2014 Drivemode, Inc. All rights reserved.
 * Modifications Copyright 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 */

package com.grottworkshop.gwsindiaink;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Hashtable;


/**
 * This is a typeface instance cache.
 * The cache is to avoid memory leak problem when a typeface is loaded.
 * See the link for more details about the memory leak issue.
 * (https://code.google.com/p/android/issues/detail?id=9904)
 *
 * @author hnakagawa
 * Created by fgrott on 9/7/2015.
 */
/* package */ final class TypefaceCache {

    /**
     * the TypefaceCache sInstance and we need this
     * as a private static so as 3rd party developers do
     * not manipulate and misuse.
     */
    private static TypefaceCache sInstance;

    /**
     * the mCache hashtable kept private and final to
     * prevent 3rd party access.
     */
    private final Hashtable<String, Typeface> mCache = new Hashtable<>();

    /**
     * the mApplication instance variable
     */
    private final Application mApplication;

    /**
     *
     * @param application the application instance
     */
    private TypefaceCache(Application application) {
        mApplication = application;
    }

    /**
     * If the cache has an instance for the typeface name, this will return the instance immediately.
     * Otherwise this method will create typeface instance and put it into the cache and return the instance.
     * @param name the typeface name.
     * @return {@link android.graphics.Typeface} instance.
     */
    public synchronized Typeface get(String name) {

        Typeface typeface = mCache.get(name);
        if(typeface == null) {

            try {

                typeface = Typeface.createFromAsset(mApplication.getAssets(), name);
            } catch (Exception exp) {
                Log.d("a typeface exception", String.valueOf(exp));
                return null;
            }

            mCache.put(name, typeface);
        }
        return typeface;
    }

    /**
     * Retrieve this cache.
     * @param context the context.
     * @return the cache instance.
     */
    public static synchronized TypefaceCache getInstance(Context context) {
        if (sInstance == null)

        sInstance = new TypefaceCache((Application)context.getApplicationContext());
        return sInstance;
    }
}
