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

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ScaleXSpan;


/**
 * KerningSpan applies kerning to multiple lines.
 * Created by fgrott on 9/7/2015.
 */
@SuppressWarnings("unused")
public class KerningSpan {

    /**
     *
     * @param src the char sequence
     * @param kerning the amount of kerning
     * @param start the char sequence start
     * @param end the end of the char sequence
     * @return the builder
     */
    public static Spannable applyKerning(CharSequence src, float kerning, int start, int end)
    {
        if (src == null) return null;
        final int srcLength = src.length();
        if (srcLength < 2) return src instanceof Spannable
                ? (Spannable)src
                : new SpannableString(src);
        if (start < 0)
        if (end > srcLength)

        final String nonBreakingSpace = "\u00A0";
        final SpannableStringBuilder builder = src instanceof SpannableStringBuilder
                ? (SpannableStringBuilder)src
                : new SpannableStringBuilder(src);
        for (int i = src.length(); i >= 1; i--)
        {
            builder.insert(i, nonBreakingSpace);
            builder.setSpan(new ScaleXSpan(kerning), i, i + 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return builder;
    }

}
