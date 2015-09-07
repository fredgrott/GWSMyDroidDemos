/**
 * Copyright 2013 Flavien Laurent
 * Modifications Copyright 2015 Fred Grott(GrottWorkShop)
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

package com.grottworkshop.gwsspannable.text.style;

import android.graphics.BlurMaskFilter;
import android.graphics.MaskFilter;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;


/**
 * MutableBlurMaskFilterSpan class
 * Created by fgrott on 9/7/2015.
 */
@SuppressWarnings("unused")
public class MutableBlurMaskFilterSpan extends CharacterStyle implements UpdateAppearance {


    private float mRadius;
    private MaskFilter mFilter;

    /**
     *
     * @param radius the radius
     */
    public MutableBlurMaskFilterSpan(float radius) {
        mRadius = radius;
    }

    /**
     *
     * @param radius the radius
     */
    public void setRadius(float radius) {
        mRadius = radius;
        mFilter = new BlurMaskFilter(mRadius, BlurMaskFilter.Blur.NORMAL);
    }

    /**
     *
     * @return the radius
     */
    public float getRadius() {
        return mRadius;
    }

    /**
     *
     * @return the MaskFilter
     */
    public MaskFilter getFilter() {
        return mFilter;
    }

    /**
     *
     * @param ds the TextPaint object
     */
    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setMaskFilter(mFilter);
    }
}