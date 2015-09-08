package com.grottworkshop.gwsscenetransitions.utils;

import android.graphics.PointF;


/**
 * PointFAnimator class
 * Created by fgrott on 8/25/2015.
 */
public class PointFAnimator extends BasePointFAnimator {

    private float mStartTop, mStartLeft, mEndTop, mEndLeft;

    protected PointFAnimator(Object target, PointFProperty pointFProperty) {
        super(target, pointFProperty);
    }

    public static <T> PointFAnimator ofPointF(T target, PointFProperty<T> property, float startLeft,
                                              float startTop, float endLeft, float endTop) {
        PointFAnimator animator = null;
        if (target != null && property != null) {
            animator = new PointFAnimator(target, property);
            animator.mStartLeft = startLeft;
            animator.mStartTop = startTop;
            animator.mEndLeft = endLeft;
            animator.mEndTop = endTop;
        }
        return animator;
    }

    protected void applyAnimatedFraction(PointF holder, float fraction) {
        holder.x = interpolate(fraction, mStartLeft, mEndLeft);
        holder.y = interpolate(fraction, mStartTop, mEndTop);
    }

    protected static float interpolate(float fraction, float startValue, float endValue) {
        float diff = endValue - startValue;
        return startValue + (diff * fraction);
    }

}
