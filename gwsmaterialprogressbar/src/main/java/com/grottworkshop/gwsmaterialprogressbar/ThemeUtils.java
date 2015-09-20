package com.grottworkshop.gwsmaterialprogressbar;

import android.content.Context;
import android.content.res.TypedArray;


/**
 * @hide
 * Created by fgrott on 9/20/2015.
 */
public class ThemeUtils {

    private ThemeUtils() {}

    public static int getAttrColor(Context context, int attr) {
        TypedArray a = context.obtainStyledAttributes(null, new int[]{attr});
        try {
            return a.getColor(0, 0);
        } finally {
            a.recycle();
        }
    }

    public static float getAttrFloat(Context context, int attr) {
        TypedArray a = context.obtainStyledAttributes(null, new int[] {attr});
        try {
            return a.getFloat(0, 0);
        } finally {
            a.recycle();
        }
    }
}
