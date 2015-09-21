/*
 * Copyright (c) Gustavo Claramunt (AnderWeb) 2014.
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.grottworkshop.gwsdiscreteseekbar;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;


/**
 * Wrapper compatibility class to call some API-Specific methods
 * And offer alternate procedures when possible
 *
 * @hide
 * Created by fgrott on 9/21/2015.
 */
@SuppressWarnings("unused")
public class SeekBarCompat {

    /**
     * Sets the custom Outline provider on API>=21.
     * Does nothing on API<21
     *
     * @param view the view
     * @param markerDrawable the markerDrawable
     */
    public static void setOutlineProvider(View view, final MarkerDrawable markerDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SeekBarCompatDontCrash.setOutlineProvider(view, markerDrawable);
        }
    }

    /**
     * Our DiscreteSeekBar implementation uses a circular drawable on API < 21
     * because we don't set it as Background, but draw it ourselves
     *
     * @param colorStateList the colorStateList
     * @return AlmostRippleDrawable
     */
    public static Drawable getRipple(ColorStateList colorStateList) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return SeekBarCompatDontCrash.getRipple(colorStateList);
        } else {
            return new AlmostRippleDrawable(colorStateList);
        }
    }

    /**
     * As our DiscreteSeekBar implementation uses a circular drawable on API < 21
     * we want to use the same method to set its bounds as the Ripple's hotspot bounds.
     *
     * @param drawable the drawable
     * @param left left
     * @param top top
     * @param right right
     * @param bottom bottom
     */
    public static void setHotspotBounds(Drawable drawable, int left, int top, int right, int bottom) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //We don't want the full size rect, Lollipop ripple would be too big
            int size = (right - left) / 8;
            DrawableCompat.setHotspotBounds(drawable, left + size, top + size, right - size, bottom - size);
        } else {
            drawable.setBounds(left, top, right, bottom);
        }
    }

    /**
     * android.support.v4.view.ViewCompat SHOULD include this once and for all!!
     * But it doesn't...
     *
     * @param view the view
     * @param background background
     */
    @SuppressWarnings("deprecation")
    public static void setBackground(View view, Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            SeekBarCompatDontCrash.setBackground(view, background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    /**
     * Sets the TextView text direction attribute when possible
     *
     * @param textView the textview
     * @param textDirection the textDirection
     * @see android.widget.TextView#setTextDirection(int)
     */
    public static void setTextDirection(TextView textView, int textDirection) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            SeekBarCompatDontCrash.setTextDirection(textView, textDirection);
        }
    }

    public static boolean isInScrollingContainer(ViewParent p) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && SeekBarCompatDontCrash.isInScrollingContainer(p);
    }

    public static boolean isHardwareAccelerated(View view) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && SeekBarCompatDontCrash.isHardwareAccelerated(view);
    }
}
