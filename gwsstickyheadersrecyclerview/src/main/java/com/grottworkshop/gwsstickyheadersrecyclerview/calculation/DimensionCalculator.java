/*
 * Copyright 2014 Jacob Tabak - Timehop
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
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
 *
 */
package com.grottworkshop.gwsstickyheadersrecyclerview.calculation;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import static android.view.ViewGroup.LayoutParams;
import static android.view.ViewGroup.MarginLayoutParams;


/**
 * Helper to calculate various view dimensions
 * Created by fgrott on 9/3/2015.
 */
public class DimensionCalculator {

    /**
     * Returns {@link Rect} representing margins for any view.
     *
     * @param view for which to get margins
     * @return margins for the given view. All 0 if the view does not support margins
     */
    public Rect getMargins(View view) {
        LayoutParams layoutParams = view.getLayoutParams();

        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            return getMarginRect(marginLayoutParams);
        } else {
            return new Rect();
        }
    }

    /**
     * Converts {@link MarginLayoutParams} into a representative {@link Rect}
     *
     * @param marginLayoutParams margins to convert to a Rect
     * @return Rect representing margins, where {@link MarginLayoutParams#leftMargin} is equivalent to
     * {@link Rect#left}, etc.
     */
    private Rect getMarginRect(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return new Rect(
                marginLayoutParams.leftMargin,
                marginLayoutParams.topMargin,
                marginLayoutParams.rightMargin,
                marginLayoutParams.bottomMargin
        );
    }

}
