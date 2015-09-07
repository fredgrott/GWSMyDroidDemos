/*
 * Copyright 2014 Chris Renke
 * Modifications Copyright 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.grottworkshop.gwsspannable.text.style;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.CharacterStyle;

import static android.graphics.Color.argb;
import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;


/**
 * ShadowCrossFadeSpan class.
 * Created by fgrott on 9/7/2015.
 */
@SuppressWarnings("unused")
public class ShadowedCrossFadeSpan extends CharacterStyle {

    private final int initialTextColor;
    private final int endTextColor;
    private final int initialShadowColor;
    private final int endShadowColor;
    private final int initialShadowOpacity;
    private final int endShadowOpacity;
    private final int shadowOffsetX;
    private final int shadowOffsetY;
    private final int shadowRadius;

    private int currentColor;
    private int currentShadowColor;
    private int currentShadowOpacity;

    /**
     *
     * @param initialTextColor the initial text color
     * @param endTextColor the end text color
     * @param shadowColor the shadow text color
     * @param initialShadowOpacity the initial shadow opacity
     * @param endShadowOpacity the end shadow opacity
     * @param shadowOffsetX the shadow offset x axis
     * @param shadowOffsetY the shadow offset y axis
     * @param shadowRadius the shadow radius
     */
    public ShadowedCrossFadeSpan(int initialTextColor, int endTextColor, int shadowColor,
                                 int initialShadowOpacity, int endShadowOpacity, int shadowOffsetX, int shadowOffsetY,
                                 int shadowRadius) {
        this(initialTextColor, endTextColor, shadowColor, shadowColor, initialShadowOpacity,
                endShadowOpacity, shadowOffsetX, shadowOffsetY, shadowRadius);
    }

    /**
     *
     * @param initialTextColor the initial text color
     * @param endTextColor the end text color
     * @param initialShadowColor the initial shadow color
     * @param endShadowColor the end shadow color
     * @param initialShadowOpacity the initial shadow opacity
     * @param endShadowOpacity the end shadow opacity
     * @param shadowOffsetX the shadow offset x axis
     * @param shadowOffsetY the sahdow offset y axis
     * @param shadowRadius the sahdow radius
     */
    public ShadowedCrossFadeSpan(int initialTextColor, int endTextColor, int initialShadowColor,
                                 int endShadowColor, int initialShadowOpacity, int endShadowOpacity, int shadowOffsetX,
                                 int shadowOffsetY, int shadowRadius) {
        this.initialTextColor = initialTextColor;
        this.endTextColor = endTextColor;
        this.initialShadowColor = initialShadowColor;
        this.endShadowColor = endShadowColor;
        this.initialShadowOpacity = initialShadowOpacity;
        this.endShadowOpacity = endShadowOpacity;
        this.shadowOffsetX = shadowOffsetX;
        this.shadowOffsetY = shadowOffsetY;
        this.shadowRadius = shadowRadius;

        // Set the span to the initial state.
        setParameter(0);
    }

    /**
     *
     * @param tp the text paint
     */
    @Override public void updateDrawState(TextPaint tp) {
        tp.setColor(currentColor);
        tp.setShadowLayer(shadowRadius, shadowOffsetX, shadowOffsetY, argb(currentShadowOpacity, //
                red(currentShadowColor), green(currentShadowColor), blue(currentShadowColor)));
    }

    /**
     *
     * @param t the float t
     */
    public void setParameter(float t) {
        currentShadowOpacity = param(initialShadowOpacity, endShadowOpacity, t);
        currentShadowColor = Color.rgb(param(red(initialShadowColor), red(endShadowColor), t), //
                param(green(initialShadowColor), green(endShadowColor), t), //
                param(blue(initialShadowColor), blue(endShadowColor), t));
        currentColor = Color.rgb(param(red(initialTextColor), red(endTextColor), t), //
                param(green(initialTextColor), green(endTextColor), t), //
                param(blue(initialTextColor), blue(endTextColor), t));
    }

    /**
     *
     * @param min the min
     * @param max the max
     * @param t the float t
     * @return the parameter
     */
    private static int param(int min, int max, float t) {
        return (int) ((max - min) * t) + min;
    }

}
