/*
 * Copyright 2014 Chris Renke
 * Modifications Copyright 2015 Fred Grott(GrottWorkShop)
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
 * TabularSpan class.
 * Since the chance of using this span with text sizes <1f is about zero, no need to
 * do workaround for 4.x and 5.x hardware acceleration canvas.drawText bug.
 * Created by fgrott on 9/7/2015.
 */
@SuppressWarnings("unused")
public class TabularSpan extends ReplacementSpan {

    private static final String DEFAULT_DELIMITER_CHARACTERS = ",.";
    private static final String DEFAULT_NUMERAL_CHARACTERS = "0123456789";

    private final String delimiters;
    private final String numerals;

    public TabularSpan() {
        this.delimiters = DEFAULT_DELIMITER_CHARACTERS;
        this.numerals = DEFAULT_NUMERAL_CHARACTERS;
    }

    /**
     *
     * @param delimiters the string delimiters
     * @param numerals the sring numerals
     */
    public TabularSpan(String delimiters, String numerals) {
        this.delimiters = delimiters;
        this.numerals = numerals;
    }

    /**
     * gets the size
     * @param paint the paint object
     * @param text the char sequence text
     * @param start the start of the char sequence
     * @param end the end of the char sequence
     * @param fm the font metrics
     * @return the total width
     */
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        if (fm != null) paint.getFontMetricsInt(fm);

        CharSequence actualText = text.subSequence(start, end);
        float numberWidth = getMaxCharacterWidth(paint, numerals);
        float delimiterWidth = getMaxCharacterWidth(paint, delimiters);
        float totalWidth = 0;

        for (int i = 0; i < actualText.length(); i++) {
            CharSequence character = actualText.subSequence(i, i + 1);
            if (delimiters.contains(character)) {
                totalWidth += delimiterWidth;
            } else if (numerals.contains(character)) {
                totalWidth += numberWidth;
            } else {
                totalWidth += paint.measureText(character, 0, 1);
            }
        }
        return (int) Math.ceil(totalWidth);
    }

    /**
     * draws the char sequence
     * @param canvas the canvas object
     * @param text char sequence text
     * @param start the start
     * @param end the end
     * @param x x
     * @param top the top
     * @param y the y
     * @param bottom the bottom
     * @param paint the paint object
     */
    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y,
                     int bottom, Paint paint) {
        CharSequence actualText = text.subSequence(start, end);
        float numberWidth = getMaxCharacterWidth(paint, numerals);
        float delimiterWidth = getMaxCharacterWidth(paint, delimiters);

        for (int i = 0; i < actualText.length(); i++) {
            CharSequence character = actualText.subSequence(i, i + 1);
            float charWidth = paint.measureText(actualText, i, i + 1);
            float monoWidth;
            if (delimiters.contains(character)) {
                monoWidth = delimiterWidth;
            } else if (numerals.contains(character)) {
                monoWidth = numberWidth;
            } else {
                monoWidth = charWidth;
            }
            float halfFreeSpace = (monoWidth - charWidth) / 2f;
            canvas.drawText(actualText, i, i + 1, x + halfFreeSpace, y, paint);
            x += monoWidth;
        }
    }

    /**
     * gets the max Char width
     * @param paint the paint object
     * @param characters the char sequence
     * @return the max width
     */
    private static float getMaxCharacterWidth(Paint paint, CharSequence characters) {
        float maxWidth = 0;
        for (int i = 0; i < characters.length(); i++) {
            maxWidth = Math.max(paint.measureText(characters, i, i + 1), maxWidth);
        }
        return maxWidth;
    }

}
