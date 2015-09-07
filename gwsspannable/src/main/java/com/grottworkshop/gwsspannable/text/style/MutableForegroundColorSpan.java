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

import android.graphics.Color;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;


/**
 * MutableForegroundColorSpan class
 * Created by fgrott on 9/7/2015.
 */
@SuppressWarnings("unused")
public class MutableForegroundColorSpan extends ForegroundColorSpan {

    private int mAlpha = 255;
    private int mForegroundColor;

    /**
     *
     * @param alpha the alpha
     * @param color the color
     */
    public MutableForegroundColorSpan(int alpha, int color) {
        super(color);
        mAlpha = alpha;
        mForegroundColor = color;
    }

    /**
     *
     * @param src the parcel
     */
    public MutableForegroundColorSpan(Parcel src) {
        super(src);
        mForegroundColor = src.readInt();
        mAlpha = src.readInt();
    }

    /**
     *
     * @param dest the parcel destination
     * @param flags the flags
     */
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mForegroundColor);
        dest.writeFloat(mAlpha);
    }

    /**
     *
     * @param ds the TextPaint object
     */
    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        ds.setColor(getForegroundColor());
    }

    /**
     * @param alpha from 0 to 255
     */
    public void setAlpha(int alpha) {
        mAlpha = alpha;
    }

    /**
     *
     * @param foregroundColor the foreground color
     */
    public void setForegroundColor(int foregroundColor) {
        mForegroundColor = foregroundColor;
    }

    /**
     *
     * @return the alpha
     */
    public float getAlpha() {
        return mAlpha;
    }

    /**
     *
     * @return the foreground color
     */
    @Override
    public int getForegroundColor() {
        return Color.argb(mAlpha, Color.red(mForegroundColor), Color.green(mForegroundColor), Color.blue(mForegroundColor));
    }
}