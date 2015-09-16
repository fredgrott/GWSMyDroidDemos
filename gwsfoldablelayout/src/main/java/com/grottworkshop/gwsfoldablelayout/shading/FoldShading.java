package com.grottworkshop.gwsfoldablelayout.shading;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * FoldShading interface
 * Created by fgrott on 9/15/2015.
 */
@SuppressWarnings("unused")
public interface FoldShading {
    void onPreDraw(Canvas canvas, Rect bounds, float rotation, int gravity);

    void onPostDraw(Canvas canvas, Rect bounds, float rotation, int gravity);
}
