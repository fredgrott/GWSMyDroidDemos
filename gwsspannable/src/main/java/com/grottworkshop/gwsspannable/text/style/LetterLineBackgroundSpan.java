/**
 * Copyright 2013 Flavien Laurent
 * Modifications Copyright 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.LineBackgroundSpan;

import java.util.Arrays;


/**
 * LetterLineBackgroundSpan
 * Created by fgrott on 9/7/2015.
 */
@SuppressWarnings("unused")
public class LetterLineBackgroundSpan implements LineBackgroundSpan {

    private static final char[] sV = {'a','e','i','o','u','y'};

    private final Paint mCPaint;
    private final Paint mVPaint;
    private RectF mRectF = new RectF();

    public LetterLineBackgroundSpan() {
        mCPaint = new Paint();
        mCPaint.setColor(Color.MAGENTA);
        mCPaint.setAntiAlias(true);
        mVPaint = new Paint();
        mVPaint.setColor(Color.YELLOW);
        mVPaint.setAntiAlias(true);
    }

    /**
     *
     * @param c the canvas object
     * @param p the paint object
     * @param left left
     * @param right right
     * @param top top
     * @param baseline baseline
     * @param bottom bottom
     * @param text the char sequence
     * @param start the start of the char sequence
     * @param end the end of the char sequence
     * @param lnum the line number
     */
    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        float charx = left;
        for(int i = start ; i<end; i++) {
            String charAt = extractText(text, i, i + 1);
            float charWidth = p.measureText(charAt);
            mRectF.set(charx, top, charx += charWidth, bottom);
            if(Arrays.binarySearch(sV, charAt.charAt(0)) >= 0) {
                c.drawRect(mRectF, mVPaint);
            } else {
                c.drawRect(mRectF, mCPaint);
            }
        }
    }

    /**
     *
     * @param text the char sequence
     * @param start the start of the char sequence
     * @param end the end of the cahr sequence
     * @return the text
     */
    private String extractText(CharSequence text, int start, int end) {
        return text.subSequence(start, end).toString();
    }
}
