/*
 * Copyright 2013 Leszek Mzyk
 * Modifications Copyright 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.grottworkshop.gwsspannable.text.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.text.style.LeadingMarginSpan;


/**
 * Custom Bullet Span implementation (based on {@link BulletSpan})
 * Default implementation doesn't allow for radius modification
 * Created by fgrott on 9/7/2015.
 */
@SuppressWarnings("unused")
public class BulletSpanWithRadius implements LeadingMarginSpan {
    private final int mGapWidth;
    private final int mBulletRadius;
    private final boolean mWantColor;
    private final int mColor;

    private static Path sBulletPath = null;
    public static final int STANDARD_GAP_WIDTH = 2;
    public static final int STANDARD_BULLET_RADIUS = 4;

    public BulletSpanWithRadius() {
        mGapWidth = STANDARD_GAP_WIDTH;
        mBulletRadius = STANDARD_BULLET_RADIUS;
        mWantColor = false;
        mColor = 0;
    }

    public BulletSpanWithRadius(int gapWidth) {
        mGapWidth = gapWidth;
        mBulletRadius = STANDARD_BULLET_RADIUS;
        mWantColor = false;
        mColor = 0;
    }

    public BulletSpanWithRadius(int bulletRadius, int gapWidth) {
        mGapWidth = gapWidth;
        mBulletRadius = bulletRadius;
        mWantColor = false;
        mColor = 0;
    }

    public BulletSpanWithRadius(int bulletRadius, int gapWidth, int color) {
        mGapWidth = gapWidth;
        mBulletRadius = bulletRadius;
        mWantColor = true;
        mColor = color;
    }

    public int getLeadingMargin(boolean first) {
        return 2 * mBulletRadius + mGapWidth;
    }


    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom,
                                  CharSequence text, int start, int end, boolean first, Layout l) {
        if (((Spanned) text).getSpanStart(this) == start) {
            Paint.Style style = p.getStyle();
            int oldcolor = 0;

            if (mWantColor) {
                oldcolor = p.getColor();
                p.setColor(mColor);
            }

            p.setStyle(Paint.Style.FILL);


            c.drawCircle(x + dir * (mBulletRadius + 1), (top + bottom) / 2.0f, mBulletRadius, p);


            if (mWantColor) {
                p.setColor(oldcolor);
            }
            p.setStyle(style);
        }
    }

}
