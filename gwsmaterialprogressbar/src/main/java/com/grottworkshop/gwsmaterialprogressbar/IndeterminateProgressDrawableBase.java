package com.grottworkshop.gwsmaterialprogressbar;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;


/**
 * Created by fgrott on 9/20/2015.
 */
@SuppressWarnings("unused")
abstract class IndeterminateProgressDrawableBase extends ProgressDrawableBase
        implements Animatable {

    protected Animator[] mAnimators;

    public IndeterminateProgressDrawableBase(Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (isStarted()) {
            invalidateSelf();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {

        if (isStarted()) {
            return;
        }

        for (Animator animator : mAnimators) {
            animator.start();
        }
        invalidateSelf();
    }

    private boolean isStarted() {
        for (Animator animator : mAnimators) {
            if (animator.isStarted()) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        for (Animator animator : mAnimators) {
            animator.end();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        for (Animator animator : mAnimators) {
            if (animator.isRunning()) {
                return true;
            }
        }
        return false;
    }
}
