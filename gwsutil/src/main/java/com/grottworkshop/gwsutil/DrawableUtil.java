package com.grottworkshop.gwsutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;

/**
 * From article:
 * http://www.donnfelker.com/quick-and-easy-statelistdrawables-in-android-with-one-png/
 * Author is Donn Felker keeps the orig but tints the states as opposed to DrawableCompat
 * Usage:
 * <code>
 *
 *     myImageView.setBackground(DrawableUtil.getStateListDrawable(context, R.drawable.ic_user_dark, R.color.white, 127));
 * </code>
 * Created by fgrott on 9/5/2015.
 */
@SuppressWarnings("unused")
public class DrawableUtil {

    @SuppressWarnings("deprecation")
    public static StateListDrawable getStateListDrawable(Context context, @DrawableRes int imageResource, @ColorRes int desiredColor, @IntRange(from = 0, to = 255) int disableAlpha) {

        // Create the colorized image (pressed state)
        Bitmap one = BitmapFactory.decodeResource(context.getResources(), imageResource);
        Bitmap oneCopy = Bitmap.createBitmap(one.getWidth(), one.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(oneCopy);
        Paint p = new Paint();
        int color = context.getResources().getColor(desiredColor);
        p.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        c.drawBitmap(one, 0, 0, p);

        // Create the disabled bitmap for the disabled state
        Bitmap disabled = BitmapFactory.decodeResource(context.getResources(), imageResource);
        Bitmap disabledCopy = Bitmap.createBitmap(disabled.getWidth(), disabled.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas disabledCanvas = new Canvas(disabledCopy);
        Paint alphaPaint = new Paint();
        alphaPaint.setAlpha(disableAlpha);
        disabledCanvas.drawBitmap(disabled, 0, 0, alphaPaint);

        StateListDrawable stateListDrawable = new StateListDrawable();

        // Pressed State
        //TODO: BitmapDrawable(bitmap) depreciated, adjust to new way
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new BitmapDrawable(oneCopy));

        // Disabled State
        stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, new BitmapDrawable(disabledCopy) );  // - symbol means opposite, in this case "disabled"

        // Default State
        stateListDrawable.addState(new int[]{}, context.getResources().getDrawable(imageResource));

        return stateListDrawable;
    }
}
