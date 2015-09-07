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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;


/**
 * RoundedBackgroundSpan
 * Created by fgrott on 9/7/2015.
 */
@SuppressWarnings("unused")
public class RoundedBackgroundSpan extends ReplacementSpan {


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
    public  void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint)
    {
        RectF rect = new RectF(x, top, x + measureText(paint, text, start, end), bottom);
        paint.setColor(Color.BLUE);
        canvas.drawRoundRect(rect, 100f, 30f, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText(text, start, end, x, y, paint);
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
    public  int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm)
    {
        return Math.round(measureText(paint, text, start, end));
    }

    /**
     *
     * @param paint the paint object
     * @param text the char sequence
     * @param start the start of the char sequence
     * @param end the end of the char sequence
     * @return measured text
     */
    private float measureText(Paint paint, CharSequence text, int start, int end)
    {
        return paint.measureText(text, start, end);
    }

}