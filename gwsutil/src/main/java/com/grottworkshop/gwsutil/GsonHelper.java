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
package com.grottworkshop.gwsutil;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;


/**
 * GsonHelper class
 * Created by fgrott on 9/23/2015.
 */
@SuppressWarnings("unused")
public class GsonHelper {

    private static Boolean hasGson;
    private static Object gson;

    public static boolean hasGson() {
        if (hasGson == null) {
            try {
                Class.forName("com.google.gson.Gson");
                hasGson = true;
            } catch (Exception e) {
                hasGson = false;
            }
        }
        return hasGson;
    }

    public static Gson get() {
        if (gson == null) gson = new Gson();
        return (Gson) gson;
    }

    public static String toJson(Object obj) {
        try {
            return obj == null ? null : get().toJson(obj);
        } catch (Exception e) {
            Log.e("GsonHelper", "Cannot convert object to JSON", e);
            return null;
        }
    }

    public static <T> T fromJson(String str, Class<T> clazz) {
        return fromJson(str, (Type) clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String str, Type type) {
        try {
            return str == null ? null : (T) get().fromJson(str, type);
        } catch (Exception e) {
            //if (BuildConfig.DEBUG) Log.e("GsonHelper", "Cannot parse JSON to object", e);
            return null;
        }
    }

}
