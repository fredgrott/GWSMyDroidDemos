package com.grottworkshop.gwsscenetransitions.utils;

import android.animation.ValueAnimator;
import android.graphics.PointF;

import java.lang.ref.WeakReference;


/**
 * BasePointFAnimator class
 * Created by fgrott on 8/25/2015.
 */
public abstract class BasePointFAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener {

    /**
     * A weak reference to the mTarget object on which the property exists, set
     * in the constructor. We'll cancel the animation if this goes away.
     */
    private WeakReference mTarget;

    private PointFProperty mPointFProperty;

    private PointF mTempPointF = new PointF();

    protected BasePointFAnimator(Object target, PointFProperty pointFProperty) {
        mTarget = new WeakReference<>(target);
        mPointFProperty = pointFProperty;
        setFloatValues(0f, 1f);
        addUpdateListener(this);
    }

    protected abstract void applyAnimatedFraction(PointF holder, float fraction);

    @Override
    @SuppressWarnings("unchecked")
    public void onAnimationUpdate(ValueAnimator animation) {
        Object target = mTarget.get();
        if (target == null) {
            // We lost the target reference, cancel.
            cancel();
            return;
        }
        applyAnimatedFraction(mTempPointF, animation.getAnimatedFraction());
        mPointFProperty.set(target, mTempPointF);
    }
}