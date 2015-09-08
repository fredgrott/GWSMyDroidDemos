package com.grottworkshop.gwsscenetransitions;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;

import com.grottworkshop.gwsscenetransitions.utils.RectEvaluator;
import com.grottworkshop.gwsscenetransitions.utils.ViewUtils;


/**
 * ChangeClipBounds captures the {@link android.view.View#getClipBounds()} before and after the
 * scene change and animates those changes during the transition.
 * Created by fgrott on 8/26/2015.
 */
@SuppressWarnings("unused")
public class ChangeClipBounds extends Transition {

    private static final String PROPNAME_CLIP = "android:clipBounds:clip";
    private static final String PROPNAME_BOUNDS = "android:clipBounds:bounds";

    private static final String[] sTransitionProperties = {
            PROPNAME_CLIP,
    };

    public static final Property<View, Rect> VIEW_CLIP_BOUNDS;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            VIEW_CLIP_BOUNDS = new Property<View, Rect>(Rect.class, "clipBounds") {

                @Override
                public void set(View object, Rect value) {
                    ViewUtils.setClipBounds(object, value);
                }

                @Override
                public Rect get(View object) {
                    return ViewUtils.getClipBounds(object);
                }

            };
        } else {
            VIEW_CLIP_BOUNDS = null;
        }
    }

    public ChangeClipBounds() {}

    public ChangeClipBounds(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    private void captureValues(TransitionValues values) {
        View view = values.view;
        if (view.getVisibility() == View.GONE) {
            return;
        }

        Rect clip = ViewUtils.getClipBounds(view);
        values.values.put(PROPNAME_CLIP, clip);
        if (clip == null) {
            Rect bounds = new Rect(0, 0, view.getWidth(), view.getHeight());
            values.values.put(PROPNAME_BOUNDS, bounds);
        }
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(final ViewGroup sceneRoot, TransitionValues startValues,
                                   TransitionValues endValues) {
        if (startValues == null || endValues == null
                || !startValues.values.containsKey(PROPNAME_CLIP)
                || !endValues.values.containsKey(PROPNAME_CLIP)) {
            return null;
        }
        Rect start = (Rect) startValues.values.get(PROPNAME_CLIP);
        Rect end = (Rect) endValues.values.get(PROPNAME_CLIP);
        if (start == null && end == null) {
            return null; // No animation required since there is no clip.
        }

        if (start == null) {
            start = (Rect) startValues.values.get(PROPNAME_BOUNDS);
        } else if (end == null) {
            end = (Rect) endValues.values.get(PROPNAME_BOUNDS);
        }
        if (start.equals(end)) {
            return null;
        }

        ViewUtils.setClipBounds(endValues.view, start);
        RectEvaluator evaluator = new RectEvaluator(new Rect());
        return ObjectAnimator.ofObject(endValues.view, VIEW_CLIP_BOUNDS, evaluator, start, end);
    }

}
