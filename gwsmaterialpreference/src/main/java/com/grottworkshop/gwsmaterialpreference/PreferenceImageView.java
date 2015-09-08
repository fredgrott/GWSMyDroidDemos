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

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.UNSPECIFIED;
import static android.view.View.MeasureSpec.getMode;
import static android.view.View.MeasureSpec.getSize;
import static android.view.View.MeasureSpec.makeMeasureSpec;
import static java.lang.Integer.MAX_VALUE;


/**
 * Extension of ImageView that correctly applies maxWidth and maxHeight.
 * Created by fgrott on 9/2/2015.
 */
@SuppressWarnings("unused")
public class PreferenceImageView extends ImageView {

    private int maxWidth = MAX_VALUE;
    private int maxHeight = MAX_VALUE;

    public PreferenceImageView(Context context) {
        super(context);
    }

    public PreferenceImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PreferenceImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(LOLLIPOP)
    public PreferenceImageView(Context context, AttributeSet attrs, int defStyleAttr,
                               int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setMaxWidth(int maxWidth) {
        super.setMaxWidth(maxWidth);
        this.maxWidth = maxWidth;
    }

    @Override
    public void setMaxHeight(int maxHeight) {
        super.setMaxHeight(maxHeight);
        this.maxHeight = maxHeight;
    }

    @SuppressWarnings("ResourceType")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = getMode(widthMeasureSpec);
        if (widthMode == AT_MOST || widthMode == UNSPECIFIED) {
            int widthSize = getSize(widthMeasureSpec);
            if (maxWidth != MAX_VALUE && (maxWidth < widthSize || widthMode == UNSPECIFIED)) {
                widthMeasureSpec = makeMeasureSpec(maxWidth, AT_MOST);
            }
        }

        int heightMode = getMode(heightMeasureSpec);
        if (heightMode == AT_MOST || heightMode == UNSPECIFIED) {
            int heightSize = getSize(heightMeasureSpec);
            if (maxHeight != MAX_VALUE && (maxHeight < heightSize || heightMode == UNSPECIFIED)) {
                heightMeasureSpec = makeMeasureSpec(maxHeight, AT_MOST);
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}