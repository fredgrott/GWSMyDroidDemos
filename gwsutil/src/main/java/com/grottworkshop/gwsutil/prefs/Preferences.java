/**
 * Copyright (C) 2015 Alex Vasilkov
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.grottworkshop.gwsutil.prefs;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Singleton object for simple {@link SharedPreferences} object creation.<br/>
 * Don't forget to initialize it by calling {@link #init(android.content.Context, String) init method}
 * in your {@link android.app.Application} class.
 * Created by fgrott on 9/23/2015.
 */
@SuppressWarnings("unused")
public class Preferences {

    private static Context sAppContext;
    private static String sDefaultPrefsName;

    /**
     * Stores application context for later use.
     */
    public static void init(Context context) {
        sAppContext = context.getApplicationContext();
    }

    /**
     * Stores application context and default prefs name for later use.
     */
    public static void init(Context context, String defaultPrefsName) {
        sAppContext = context.getApplicationContext();
        sDefaultPrefsName = defaultPrefsName;
    }

    /**
     * Returns {@link SharedPreferences} object with default prefs name provided in {@link #init(android.content.Context, String) init method}
     */
    public static SharedPreferences prefs() {
        return prefs(sDefaultPrefsName);
    }

    /**
     * Returns {@link SharedPreferences} object with provided name and mode = <code>Context.MODE_PRIVATE</code>.
     */
    public static SharedPreferences prefs(String name) {
        if (sAppContext == null)
            throw new RuntimeException("Preferences should be initialized by calling Preferences.init(...) method");
        if (name == null) name = "null"; // To be compatible with updated SharedPreferences logic
        return sAppContext.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

}
