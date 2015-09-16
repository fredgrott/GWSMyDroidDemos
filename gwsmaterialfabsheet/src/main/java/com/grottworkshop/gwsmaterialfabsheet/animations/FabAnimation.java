/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Gordon Wong
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
package com.grottworkshop.gwsmaterialfabsheet.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.Interpolator;

import com.grottworkshop.gwsarcanimations.ArcAnimator;
import com.grottworkshop.gwsarcanimations.Side;

/**
 * Animates the FAB when showing and hiding the material sheet.
 * Created by fgrott on 9/16/2015.
 */
@SuppressWarnings("unused")
public class FabAnimation {

    protected View fab;
    protected Interpolator interpolator;

    public FabAnimation(View fab, Interpolator interpolator) {
        this.fab = fab;
        this.interpolator = interpolator;
    }

    /**
     * Animates the FAB as if the FAB is morphing into a sheet.
     *
     * @param endX The X coordinate that the FAB will be moved to.
     * @param endY The Y coordinate that the FAB will be moved to.
     * @param arcDegrees Amount of arc in FAB movement animation.
     * @param scaleFactor Amount to scale FAB.
     * @param duration Duration of the animation in milliseconds. Use 0 for no animation.
     * @param listener Listener for animation events.
     */
    public void morphIntoSheet(float endX, float endY, int arcDegrees, float scaleFactor,
                               long duration, AnimationListener listener) {
        morph(endX, endY, arcDegrees, scaleFactor, duration, listener);
    }

    /**
     * Animates the FAB as if a sheet is being morphed into a FAB.
     *
     * @param endX The X coordinate that the FAB will be moved to.
     * @param endY The Y coordinate that the FAB will be moved to.
     * @param arcDegrees Amount of arc in FAB movement animation.
     * @param scaleFactor Amount to scale FAB.
     * @param duration Duration of the animation in milliseconds. Use 0 for no animation.
     * @param listener Listener for animation events.
     */
    public void morphFromSheet(float endX, float endY, int arcDegrees, float scaleFactor,
                               long duration, AnimationListener listener) {
        fab.setVisibility(View.VISIBLE);
        morph(endX, endY, arcDegrees, scaleFactor, duration, listener);
    }

    protected void morph(float endX, float endY, float arcDegrees, float scaleFactor,
                         long duration, AnimationListener listener) {
        // Move the FAB
        startArcAnim(fab, endX, endY, arcDegrees, Side.LEFT, duration, interpolator, listener);

        // Scale the size of the FAB
        fab.animate().scaleXBy(scaleFactor).scaleYBy(scaleFactor).setDuration(duration)
                .setInterpolator(interpolator).start();
    }

    protected void startArcAnim(View view, float endX, float endY, float degrees, Side side,
                                long duration, Interpolator interpolator, final AnimationListener listener) {
        // Setup animation
        // Cast end coordinates to ints so that the FAB will be animated to the same position even
        // when there are minute differences in the coordinates
        ArcAnimator anim = ArcAnimator.createArcAnimator(view, (int) endX, (int) endY, degrees,
                side);
        anim.setDuration(duration);
        anim.setInterpolator(interpolator);
        // Add listener
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (listener != null) {
                    listener.onStart();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener != null) {
                    listener.onEnd();
                }
            }
        });
        // Start animation
        anim.start();
    }

}
