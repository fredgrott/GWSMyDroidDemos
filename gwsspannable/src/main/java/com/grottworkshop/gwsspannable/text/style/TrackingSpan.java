/*
 *
 * Copyright 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.grottworkshop.gwsspannable.text.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;


/**
 * TrackingSpan is not the same as kerning as we may have aline of text
 * where we can make a trade-off between equal whitespace between letters to
 * denote a feeling or tone of message and not having kerning.
 *
 * Because it is not expected to be used on letters of <1f we do not have to
 * adjust the canvas.drawText method calls for the hardware acceleration
 * canvas.drawText bugs on 4.x and Lollipop.
 *
 * Usage:
 *
 * <code>
 *     SpannableStringBuilder builder = new SpannableStringBuilder("WIDE normal");
 *     builder.setSpan(new TrackingSpan(20), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
 * </code>
 * Created by fgrott on 9/7/2015.
 */
@SuppressWarnings("unused")
public class TrackingSpan extends ReplacementSpan {

    private float mTrackingPx;

    /**
     *
     * @param tracking the tracking amount
     */
    public TrackingSpan(float tracking) {
        mTrackingPx = tracking;
    }

    /**
     *
     * @param paint the paint object
     * @param text the char sequence
     * @param start the start of the char sequence
     * @param end the end of the char sequence
     * @param fm the font metrics
     * @return the size
     */
    @Override
    public int getSize(Paint paint, CharSequence text,
                       int start, int end, Paint.FontMetricsInt fm) {
        return (int) (paint.measureText(text, start, end)
                + mTrackingPx * (end - start - 1));
    }

    /**
     *
     * @param canvas the canvas
     * @param text the char sequence
     * @param start the start of the char sequence
     * @param end the end of the char sequence
     * @param x the x
     * @param top the top
     * @param y the y
     * @param bottom the bottom
     * @param paint the paint object
     */
    @Override
    public void draw(Canvas canvas, CharSequence text,
                     int start, int end, float x, int top, int y,
                     int bottom, Paint paint) {
        float dx = x;
        for (int i = start; i < end; i++) {
            canvas.drawText(text, i, i + 1, dx, y, paint);
            dx += paint.measureText(text, i, i + 1) + mTrackingPx;
        }
    }
}
