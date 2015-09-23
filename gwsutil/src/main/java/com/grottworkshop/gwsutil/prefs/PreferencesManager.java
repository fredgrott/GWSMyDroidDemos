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

import android.content.SharedPreferences;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;


/**
 * PreferencesManager class
 * Created by fgrott on 9/23/2015.
 */
@SuppressWarnings("unused")
class PreferencesManager<T> {

    private static final String PREFERENCES_NAME = "prefs_manager";
    private static final String OBJECT_SAVED_KEY = ":object";

    @SuppressWarnings("unchecked")
    static <T> void save(T obj) {
        new PreferencesManager<T>().initInternal((Class<? extends T>) obj.getClass()).saveInternal(obj);
    }

    static <T> T get(Class<T> clazz) {
        return new PreferencesManager<T>().initInternal(clazz).restoreInternal(clazz);
    }

    static <T> void remove(Class<T> clazz) {
        new PreferencesManager<T>().initInternal(clazz).removeInternal(clazz);
    }

    static <T> boolean exists(Class<T> clazz) {
        return getPreferencesForClass(clazz).getBoolean(OBJECT_SAVED_KEY, false);
    }

    private SharedPreferences mPrefs;
    private final HashMap<String, Field> mFieldsMap = new HashMap<>();
    private final HashMap<Field, String> mKeysMap = new HashMap<>();

    private PreferencesManager() {
    }

    private PreferencesManager<T> initInternal(Class<? extends T> clazz) {
        mPrefs = getPreferencesForClass(clazz);

        PreferenceField fieldAn;
        String key;

        for (Field f : clazz.getDeclaredFields()) {
            fieldAn = f.getAnnotation(PreferenceField.class);
            if (fieldAn == null) continue;
            key = fieldAn.value();

            if (key == null || key.length() == 0) {
                throw new RuntimeException("\"key\" value of PreferenceField annotation cannot be empty");
            } else if (mFieldsMap.containsKey(key)) {
                throw new RuntimeException("Duplicate key \"" + key + "\" of PreferenceField annotation");
            } else {
                f.setAccessible(true); // removing private fields access restriction
                mFieldsMap.put(key, f);
                mKeysMap.put(f, key);
            }
        }

        return this;
    }

    private void saveInternal(T obj) {
        try {
            SharedPreferences.Editor editor = mPrefs.edit();
            for (Field f : mKeysMap.keySet()) {
                setPrefsValue(f, obj, editor, mKeysMap.get(f));
            }
            editor.putBoolean(OBJECT_SAVED_KEY, true);
            editor.apply();
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Can't access field value", e);
        }
    }

    private T restoreInternal(Class<? extends T> clazz) {
        if (!mPrefs.getBoolean(OBJECT_SAVED_KEY, false)) return null;

        try {
            Constructor<? extends T> constr = clazz.getConstructor();
            T obj = constr.newInstance();

            for (Field f : mKeysMap.keySet()) {
                setInstanceValue(f, obj, mPrefs, mKeysMap.get(f));
            }
            return obj;
        } catch (Exception e) {
            throw new RuntimeException("Can't restore object", e);
        }
    }

    private void removeInternal(Class<? extends T> clazz) {
        if (!mPrefs.getBoolean(OBJECT_SAVED_KEY, false)) return;

        SharedPreferences.Editor editor = mPrefs.edit();
        for (String key : mKeysMap.values()) {
            editor.remove(key);
        }
        editor.putBoolean(OBJECT_SAVED_KEY, false);
        editor.apply();
    }

    private static SharedPreferences getPreferencesForClass(Class<?> clazz) {
        PreferenceObject objectAn = clazz.getAnnotation(PreferenceObject.class);
        if (objectAn == null)
            throw new RuntimeException("Specified object's class should be marked with PreferenceObject annotation");
        String prefsName = objectAn.value();
        if (prefsName.length() == 0)
            throw new RuntimeException("PreferenceObject annotation cannot have empty value");

        return Preferences.prefs(PREFERENCES_NAME + ':' + prefsName);
    }

    private static void setPrefsValue(Field f, Object obj, SharedPreferences.Editor editor, String key) throws IllegalAccessException {
        Class<?> type = f.getType();

        if (type.equals(Boolean.TYPE)) {
            editor.putBoolean(key, f.getBoolean(obj));

        } else if (type.equals(Byte.TYPE)) {
            editor.putInt(key, f.getByte(obj));

        } else if (type.equals(Character.TYPE)) {
            editor.putInt(key, f.getChar(obj));

        } else if (type.equals(Double.TYPE)) {
            PreferencesHelper.putDouble(editor, key, f.getDouble(obj));

        } else if (type.equals(Float.TYPE)) {
            editor.putFloat(key, f.getFloat(obj));

        } else if (type.equals(Integer.TYPE)) {
            editor.putInt(key, f.getInt(obj));

        } else if (type.equals(Long.TYPE)) {
            editor.putLong(key, f.getLong(obj));

        } else if (type.equals(Short.TYPE)) {
            editor.putInt(key, f.getShort(obj));

        } else if (type.equals(String.class)) {
            editor.putString(key, (String) f.get(obj));

        } else if (type.equals(String[].class)) {
            PreferencesHelper.putStringArray(editor, key, (String[]) f.get(obj));

        } else if (type.equals(Date.class)) {
            PreferencesHelper.putDate(editor, key, (Date) f.get(obj));

        } else if (Serializable.class.isAssignableFrom(type)) {
            PreferencesHelper.putSerializable(editor, key, (Serializable) f.get(obj));

        } else {
            throw new RuntimeException("Unsupported field type: " + f.getName() + ", " + type.getName());
        }
    }

    private static void setInstanceValue(Field f, Object obj, SharedPreferences prefs, String key) throws IllegalArgumentException,
            IllegalAccessException {

        Class<?> type = f.getType();

        if (type.equals(Boolean.TYPE)) {
            f.setBoolean(obj, prefs.getBoolean(key, false));

        } else if (type.equals(Byte.TYPE)) {
            f.setByte(obj, (byte) prefs.getInt(key, 0));

        } else if (type.equals(Character.TYPE)) {
            f.setChar(obj, (char) prefs.getInt(key, 0));

        } else if (type.equals(Double.TYPE)) {
            f.setDouble(obj, PreferencesHelper.getDouble(prefs, key, 0D));

        } else if (type.equals(Float.TYPE)) {
            f.setFloat(obj, prefs.getFloat(key, 0F));

        } else if (type.equals(Integer.TYPE)) {
            f.setInt(obj, prefs.getInt(key, 0));

        } else if (type.equals(Long.TYPE)) {
            f.setLong(obj, prefs.getLong(key, 0L));

        } else if (type.equals(Short.TYPE)) {
            f.setShort(obj, (short) prefs.getInt(key, 0));

        } else if (type.equals(String.class)) {
            f.set(obj, prefs.getString(key, null));

        } else if (type.equals(String[].class)) {
            f.set(obj, PreferencesHelper.getStringArray(prefs, key));

        } else if (type.equals(Date.class)) {
            f.set(obj, PreferencesHelper.getDate(prefs, key));

        } else if (Serializable.class.isAssignableFrom(type)) {
            f.set(obj, PreferencesHelper.getSerializable(prefs, key));

        } else {
            throw new RuntimeException("Unsupported field type: " + f.getName() + ", " + type.getSimpleName());
        }
    }

}