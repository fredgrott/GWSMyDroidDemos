/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Ozodrukh
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
 *
 */
package com.grottworkshop.gwscirclereveal.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.grottworkshop.gwscirclereveal.animation.RevealAnimator;
import com.grottworkshop.gwscirclereveal.animation.SupportAnimator;
import com.grottworkshop.gwscirclereveal.animation.ViewAnimationUtils;

/**
 * Created by fgrott on 8/30/2015.
 */
public class RevealFrameLayout extends FrameLayout implements RevealAnimator{

    private Path mRevealPath;
    private final Rect mTargetBounds = new Rect();
    private RevealInfo mRevealInfo;
    private boolean mRunning;
    private float mRadius;

    public RevealFrameLayout(Context context) {
        this(context, null);
    }

    public RevealFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RevealFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mRevealPath = new Path();
    }

    @Override
    public void onRevealAnimationStart() {
        mRunning = true;
    }

    @Override
    public void onRevealAnimationEnd() {
        mRunning = false;
        invalidate(mTargetBounds);
    }

    @Override
    public void onRevealAnimationCancel() {
        onRevealAnimationEnd();
    }

    /**
     * Circle radius size
     *
     * @hide
     */
    @Override
    public void setRevealRadius(float radius){
        mRadius = radius;
        mRevealInfo.getTarget().getHitRect(mTargetBounds);
        invalidate(mTargetBounds);
    }

    /**
     * Circle radius size
     *
     * @hide
     */
    @Override
    public float getRevealRadius(){
        return mRadius;
    }

    /**
     * @hide
     */
    @Override
    public void attachRevealInfo(RevealAnimator.RevealInfo info) {
        mRevealInfo = info;
    }

    /**
     * @hide
     */
    @Override
    public SupportAnimator startReverseAnimation() {
        if(mRevealInfo != null && mRevealInfo.hasTarget() && !mRunning) {
            return ViewAnimationUtils.createCircularReveal(mRevealInfo.getTarget(),
                    mRevealInfo.centerX, mRevealInfo.centerY,
                    mRevealInfo.endRadius, mRevealInfo.startRadius);
        }
        return null;
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if(mRunning && child == mRevealInfo.getTarget()){
            final int state = canvas.save();

            mRevealPath.reset();
            mRevealPath.addCircle(mRevealInfo.centerX, mRevealInfo.centerY, mRadius, Path.Direction.CW);

            canvas.clipPath(mRevealPath);

            boolean isInvalided = super.drawChild(canvas, child, drawingTime);

            canvas.restoreToCount(state);

            return isInvalided;
        }

        return super.drawChild(canvas, child, drawingTime);
    }

}
