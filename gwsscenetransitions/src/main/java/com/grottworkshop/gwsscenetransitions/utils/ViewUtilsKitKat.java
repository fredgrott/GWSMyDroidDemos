package com.grottworkshop.gwsscenetransitions.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Property;
import android.view.View;

import java.lang.reflect.Method;


/**
 * Created by fgrott on 8/25/2015.
 */
class ViewUtilsKitKat extends ViewUtils.ViewUtilsJellyBeanMR2 {

    private static final Method METHOD_getTransitionAlpha = ReflectionUtils.getMethod(View.class, "getTransitionAlpha");
    private static final Method METHOD_setTransitionAlpha = ReflectionUtils.getMethod(View.class, "setTransitionAlpha",
            float.class);

    public static final Property<View, Float> VIEW_TRANSITION_ALPHA = new FloatProperty<View>("transitionAlpha") {
        @Override
        public void setValue(View object, float value) {
            ViewUtils.setTransitionAlpha(object, value);
        }

        @Override
        public Float get(View object) {
            return ViewUtils.getTransitionAlpha(object);
        }
    };

    @Override
    public float getTransitionAlpha(View v) {
        return (Float) ReflectionUtils.invoke(v, 1, METHOD_getTransitionAlpha);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean isLaidOut(View v, boolean defaultValue) {
        return v.isLaidOut();
    }

    @Override
    public void setTransitionAlpha(View v, float alpha) {
        ReflectionUtils.invoke(v, null, METHOD_setTransitionAlpha, alpha);
    }

    @Override
    public boolean isTransitionAlphaCompatMode() {
        return false;
    }

    @Override
    public Property<View, Float> getAlphaProperty() {
        return VIEW_TRANSITION_ALPHA;
    }

}
