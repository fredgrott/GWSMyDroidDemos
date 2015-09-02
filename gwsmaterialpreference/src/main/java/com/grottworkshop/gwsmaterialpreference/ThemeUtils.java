/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Jens Driller
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.grottworkshop.gwsmaterialpreference;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import static android.graphics.Color.parseColor;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;


/**
 * Created by fgrott on 9/2/2015.
 */
final class ThemeUtils {

    // material_deep_teal_500
    static final int FALLBACK_COLOR = parseColor("#009688");

    private ThemeUtils() {
        // no instances
    }

    static boolean isAtLeastL() {
        return SDK_INT >= LOLLIPOP;
    }

    @TargetApi(LOLLIPOP)
    static int resolveAccentColor(Context context) {
        Resources.Theme theme = context.getTheme();

        // on Lollipop, grab system colorAccent attribute
        // pre-Lollipop, grab AppCompat colorAccent attribute
        // finally, check for custom mp_colorAccent attribute
        int attr = isAtLeastL() ? android.R.attr.colorAccent : R.attr.colorAccent;
        TypedArray typedArray = theme.obtainStyledAttributes(new int[] { attr, R.attr.mp_colorAccent });

        int accentColor = typedArray.getColor(0, FALLBACK_COLOR);
        accentColor = typedArray.getColor(1, accentColor);
        typedArray.recycle();

        return accentColor;
    }

}
