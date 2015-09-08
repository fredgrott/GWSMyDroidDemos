package com.grottworkshop.gwsreveal;

import android.graphics.Point;
import android.support.annotation.Nullable;

/**
 * RevealAnimator class
 * Created by fgrott on 8/28/2015.
 */
interface RevealAnimator {
    void showOnly(final int previousChild, final int childIndex, @Nullable Point origin);

    void showOnlyNoAnimation(final int previousIndex, final int childIndex);

    boolean isAnimating();

    boolean shouldAnimate();
}
