/*
 * Copyright 2015 Zhang Hai
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
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
package com.grottworkshop.gwsmaterialprogressbar.internal;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * Created by fgrott on 8/30/2015.
 */
public class ThemeUtils {

    private ThemeUtils() {}

    public static int getAttrColor(Context context, int attr) {
        TypedArray a = context.obtainStyledAttributes(null, new int[]{attr});
        try {
            return a.getColor(0, 0);
        } finally {
            a.recycle();
        }
    }

    public static float getAttrFloat(Context context, int attr) {
        TypedArray a = context.obtainStyledAttributes(null, new int[] {attr});
        try {
            return a.getFloat(0, 0);
        } finally {
            a.recycle();
        }
    }
}
