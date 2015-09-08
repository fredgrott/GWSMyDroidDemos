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
package com.grottworkshop.gwscirclereveal.animation;

import android.animation.Animator;
import android.view.animation.Interpolator;

import java.lang.ref.WeakReference;

/**
 * SupportAnimatorLollipop class
 * Created by fgrott on 8/30/2015.
 */
final class SupportAnimatorLollipop extends SupportAnimator{

    WeakReference<Animator> mAnimator;

    SupportAnimatorLollipop(Animator animator, RevealAnimator target) {
        super(target);
        mAnimator = new WeakReference<>(animator);
    }

    @Override
    public boolean isNativeAnimator() {
        return true;
    }

    @Override
    public Object get() {
        return mAnimator.get();
    }


    @Override
    public void start() {
        Animator a = mAnimator.get();
        if(a != null) {
            a.start();
        }
    }

    @Override
    public void setDuration(int duration) {
        Animator a = mAnimator.get();
        if(a != null) {
            a.setDuration(duration);
        }
    }

    @Override
    public void setInterpolator(Interpolator value) {
        Animator a = mAnimator.get();
        if(a != null) {
            a.setInterpolator(value);
        }
    }

    @Override
    public void addListener(final AnimatorListener listener) {
        Animator a = mAnimator.get();
        if(a == null) {
            return;
        }

        if(listener == null){
            a.addListener(null);
            return;
        }

        a.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                listener.onAnimationStart();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                listener.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                listener.onAnimationCancel();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                listener.onAnimationRepeat();
            }
        });
    }

    @Override
    public boolean isRunning() {
        Animator a = mAnimator.get();
        return a != null && a.isRunning();
    }

    @Override
    public void cancel() {
        Animator a = mAnimator.get();
        if(a != null){
            a.cancel();
        }
    }

    @Override
    public void end() {
        Animator a = mAnimator.get();
        if(a != null){
            a.end();
        }
    }

    @Override
    public void setupStartValues() {
        Animator a = mAnimator.get();
        if(a != null){
            a.setupStartValues();
        }
    }

    @Override
    public void setupEndValues() {
        Animator a = mAnimator.get();
        if(a != null){
            a.setupEndValues();
        }
    }
}
