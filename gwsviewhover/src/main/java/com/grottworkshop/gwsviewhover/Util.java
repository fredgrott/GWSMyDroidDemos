package com.grottworkshop.gwsviewhover;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/**
 * Util class
 * Created by fgrott on 8/28/2015.
 */
@SuppressWarnings("unused")
public class Util {

    public static Bitmap getViewBitmap(View v) {
        if(v.getWidth() == 0 || v.getHeight() == 0)
            return null;
        Bitmap b = Bitmap.createBitmap( v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    public static void reset(View target) {
        target.setAlpha(1);
        target.setScaleX(1);
        target.setScaleY(1);
        target.setTranslationX(0);
        target.setTranslationY(0);
        target.setRotation(0);
        target.setRotationY(0);
        target.setRotationX(0);
        target.setPivotX(target.getMeasuredWidth() / 2.0f);
        target.setPivotY(target.getMeasuredHeight() / 2.0f);
    }

}
