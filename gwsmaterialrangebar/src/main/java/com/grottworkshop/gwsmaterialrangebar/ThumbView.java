/*
 * Copyright 2013, Edmodo, Inc.
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except
 * in compliance with the License. You may obtain a copy of the License in the LICENSE file, or at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations
 * under the License.
 */
package com.grottworkshop.gwsmaterialrangebar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by fgrott on 9/1/2015.
 */
class ThumbView extends View {

    // Private Constants ///////////////////////////////////////////////////////

    // The radius (in dp) of the touchable area around the thumb. We are basing
    // this value off of the recommended 48dp Rhythm. See:
    // http://developer.android.com/design/style/metrics-grids.html#48dp-rhythm
    private static final float MINIMUM_TARGET_RADIUS_DP = 24;

    // Sets the default values for radius, normal, pressed if circle is to be
    // drawn but no value is given.
    private static final float DEFAULT_THUMB_RADIUS_DP = 14;

    // Member Variables ////////////////////////////////////////////////////////

    // Radius (in pixels) of the touch area of the thumb.
    private float mTargetRadiusPx;

    // Indicates whether this thumb is currently pressed and active.
    private boolean mIsPressed = false;

    // The y-position of the thumb in the parent view. This should not change.
    private float mY;

    // The current x-position of the thumb in the parent view.
    private float mX;

    // mPaint to draw the thumbs if attributes are selected

    private Paint mTextPaint;

    private Drawable mPin;

    private String mValue;

    // Radius of the new thumb if selected
    private int mThumbRadiusPx;

    private ColorFilter mPinFilter;

    private float mPinPadding;

    private float mTextYPadding;

    private Rect mBounds = new Rect();

    private Resources mRes;

    private Paint mCirclePaint;

    private float mCircleRadiusPx;

    // Constructors ////////////////////////////////////////////////////////////

    public void init(Context ctx, float y, float thumbRadiusDP, int pinColor, int textColor,
                     float circleRadius, int circleColor) {
        mRes = ctx.getResources();
        mPin = ctx.getResources().getDrawable(R.drawable.rotate);

        mPinPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                15, mRes.getDisplayMetrics());
        mCircleRadiusPx = circleRadius;
        mTextYPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                3.5f, mRes.getDisplayMetrics());
        // If one of the attributes are set, but the others aren't, set the
        // attributes to default
        if (thumbRadiusDP == -1) {
            mThumbRadiusPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    DEFAULT_THUMB_RADIUS_DP,
                    mRes.getDisplayMetrics());
        } else {
            mThumbRadiusPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    thumbRadiusDP,
                    mRes.getDisplayMetrics());
        }
        int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                15, mRes.getDisplayMetrics());

        // Creates the paint and sets the Paint values
        mTextPaint = new Paint();
        mTextPaint.setColor(textColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(textSize);
        // Creates the paint and sets the Paint values
        mCirclePaint = new Paint();
        mCirclePaint.setColor(circleColor);
        mCirclePaint.setAntiAlias(true);

        //Color filter for the selection pin
        mPinFilter = new LightingColorFilter(pinColor, pinColor);

        // Sets the minimum touchable area, but allows it to expand based on
        // image size
        int targetRadius = (int) Math.max(MINIMUM_TARGET_RADIUS_DP, mThumbRadiusPx);

        mTargetRadiusPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                targetRadius,
                mRes.getDisplayMetrics());
        mY = y;
    }

    public ThumbView(Context context) {
        super(context);
    }

    @Override
    public void setX(float x) {
        mX = x;
    }


    @Override
    public float getX() {
        return mX;
    }

    void setXValue(String x) {
        mValue = x;
    }


    @Override
    public boolean isPressed() {
        return mIsPressed;
    }

    void press() {
        mIsPressed = true;
    }

    void setSize(float size, float padding) {
        Log.d("padding", "set padding = " + padding);
        mPinPadding = (int) padding;
        mThumbRadiusPx = (int) size;
        invalidate();
    }

    void release() {
        mIsPressed = false;
    }

    /**
     * Determines if the input coordinate is close enough to this thumb to
     * consider it a press.
     *
     * @param x the x-coordinate of the user touch
     * @param y the y-coordinate of the user touch
     * @return true if the coordinates are within this thumb's target area;
     * false otherwise
     */
    boolean isInTargetZone(float x, float y) {
        return (Math.abs(x - mX) <= mTargetRadiusPx
                && Math.abs(y - mY + mPinPadding) <= mTargetRadiusPx);
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(mX, mY, mCircleRadiusPx, mCirclePaint);
        //Draw pin if pressed
        if (mThumbRadiusPx > 0) {
            mBounds.set((int) mX - mThumbRadiusPx,
                    (int) mY - (mThumbRadiusPx * 2) - (int) mPinPadding,
                    (int) mX + mThumbRadiusPx, (int) mY - (int) mPinPadding);
            mPin.setBounds(mBounds);
            String text = mValue;
            if (mValue.length() > 4) {
                text = mValue.substring(0, 4);
            }
            calibrateTextSize(mTextPaint, text, 8, 24, mBounds.width());
            mTextPaint.getTextBounds(text, 0, text.length(), mBounds);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            mPin.setColorFilter(mPinFilter);
            mPin.draw(canvas);
            canvas.drawText(text,
                    mX, mY - mThumbRadiusPx - mPinPadding + mTextYPadding,
                    mTextPaint);
        }
    }

    private static void calibrateTextSize(Paint paint, String text, float min, float max,
                                          float boxWidth) {
        paint.setTextSize(10);
        paint.setTextSize(Math.max(Math.min((boxWidth / paint.measureText(text)) * 10, max), min));
    }
}
