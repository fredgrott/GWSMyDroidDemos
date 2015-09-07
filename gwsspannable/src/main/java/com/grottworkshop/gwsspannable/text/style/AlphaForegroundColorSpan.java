/*
 * Copyright 2013 Flavien Laurent
 * Modifications copyright 2015 Fred Grott(GrottWorkShop)
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

package com.grottworkshop.gwsspannable.text.style;

import android.graphics.Color;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;


/**
 * AlphaForegroundColorSpan class.
 * Created by fgrott on 9/7/2015.
 */
@SuppressWarnings("unused")
public class AlphaForegroundColorSpan extends ForegroundColorSpan {

    private float mAlpha;


    /**
     *
     * @param color the color
     */
    public AlphaForegroundColorSpan(int color) {
        super(color);
    }

    /**
     *
     * @param src the source parcel
     */
    public AlphaForegroundColorSpan(Parcel src) {
        super(src);
        mAlpha = src.readFloat();
    }

    /**
     * writes to the parcel
     * @param dest the parcel dest
     * @param flags the flags
     */
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeFloat(mAlpha);
    }

    /**
     *
     * @param ds the text paint
     */
    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        ds.setColor(getAlphaColor());
    }

    /**
     *
     * @param alpha the alpha
     */
    public void setAlpha(float alpha) {
        mAlpha = alpha;
    }

    public float getAlpha() {
        return mAlpha;
    }

    /**
     *
     * @return the alpha color
     */
    private int getAlphaColor() {
        int foregroundColor = getForegroundColor();
        return Color.argb((int) (mAlpha * 255), Color.red(foregroundColor), Color.green(foregroundColor), Color.blue(foregroundColor));
    }

}
