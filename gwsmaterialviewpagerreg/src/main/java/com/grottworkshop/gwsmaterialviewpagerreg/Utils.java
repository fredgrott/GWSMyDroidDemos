/*
 * Copyright 2015 florent37, Inc.
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
package com.grottworkshop.gwsmaterialviewpagerreg;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by fgrott on 8/29/2015.
 */
public class Utils {

    /**
     * convert dp to px
     */
    public static float dpToPx(float dp, Context context) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    /**
     * convert px to dp
     */
    public static float pxToDp(float px, Context context) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    /*
     * Create a color from [$color].RGB and then add an alpha with 255*[$percent]
     */
    public static int colorWithAlpha(int color, float percent){
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        int alpha = Math.round(percent*255);

        return Color.argb(alpha,r,g,b);
    }

    public static float minMax(float min, float value, float max) {
        value = Math.min(value, max);
        value = Math.max(min, value);
        return value;
    }


    /**
     * modify the scale of multiples views
     * @param scale the new scale
     * @param views
     */
    public static void setScale(float scale, View... views) {
        for (View view : views) {
            if (view != null) {
                view.setScaleX(scale);
                view.setScaleY(scale);
            }
        }
    }

    /**
     * modify the elevation of multiples views
     * @param elevation the new elevation
     * @param views
     */
    public static void setElevation(float elevation, View... views) {
        for (View view : views) {
            if (view != null)
                ViewCompat.setElevation(view, elevation);
        }
    }

    /**
     * modify the backgroundcolor of multiples views
     * @param color the new backgroundcolor
     * @param views
     */
    public static void setBackgroundColor(int color, View... views) {
        for (View view : views) {
            if (view != null)
                view.setBackgroundColor(color);
        }
    }
}
