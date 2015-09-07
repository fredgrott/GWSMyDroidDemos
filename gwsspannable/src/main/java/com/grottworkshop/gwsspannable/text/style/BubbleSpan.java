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
import android.text.style.ReplacementSpan;

import java.util.Random;


/**
 * BubbleSpan class
 * Created by fgrott on 9/7/2015.
 */
@SuppressWarnings("unused")
public class BubbleSpan extends ReplacementSpan {


    private Paint mPaint;

    static Random random = new Random();
    private RectF mRectF = new RectF();

    private int[] mColors = new int[20];

    public BubbleSpan() {
        initPaint();
        initColors();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        mPaint.setAntiAlias(true);
    }

    private void initColors() {
        for(int index = 0 ; index < mColors.length ; index++) {
            mColors[index] = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        }
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        //return text with relative to the Paint
        return (int) paint.measureText(text, start, end);
    }

    /**
     *
     * @param canvas the canvas object
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
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        float charx = x;
        for(int i = start ; i<end; i++) {
            String charAt = extractText(text, i, i + 1);
            float charWidth = paint.measureText(charAt);
            mRectF.set(charx, top, charx += charWidth, bottom);
            mPaint.setColor(mColors[i % mColors.length]);
            canvas.drawOval(mRectF, mPaint);
        }
        canvas.drawText(text, start, end, x, y, paint);
    }

    /**
     *
     * @param text the char sequence
     * @param start the start of the char sequence
     * @param end the end of the char sequence
     * @return the text
     */
    private String extractText(CharSequence text, int start, int end) {
        return text.subSequence(start, end).toString();
    }
}