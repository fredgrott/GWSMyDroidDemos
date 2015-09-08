/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Jens Driller
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.grottworkshop.gwsmaterialpreference;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Hashtable;

/**
 * Typefaces class
 * Created by fgrott on 9/2/2015.
 */
final class Typefaces {

    private static final String TAG = Typefaces.class.getSimpleName();
    private static final Hashtable<String, Typeface> cache = new Hashtable<>();

    private Typefaces() {
        // no instances
    }

    static Typeface get(Context context, String assetPath) {
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    Typeface t = Typeface.createFromAsset(context.getAssets(), assetPath);
                    cache.put(assetPath, t);
                } catch (Exception e) {
                    Log.e(TAG, "Could not get typeface '" + assetPath + "' Error: " + e.getMessage());
                    return null;
                }
            }
            return cache.get(assetPath);
        }
    }

    static Typeface getRobotoRegular(Context context) {
        return get(context, "fonts/Roboto-Regular.ttf");
    }

    static Typeface getRobotoMedium(Context context) {
        return get(context, "fonts/Roboto-Medium.ttf");
    }
}
