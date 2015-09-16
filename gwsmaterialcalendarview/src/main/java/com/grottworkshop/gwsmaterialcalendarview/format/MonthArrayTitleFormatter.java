/*
 * Copyright 2015 Prolific Interactive
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.grottworkshop.gwsmaterialcalendarview.format;

import android.text.SpannableStringBuilder;

import com.grottworkshop.gwsmaterialcalendarview.CalendarDay;

/**
 * Use an array to generate a month/year label
 * Created by fgrott on 9/16/2015.
 */
@SuppressWarnings("unused")
public class MonthArrayTitleFormatter implements TitleFormatter {

    private final CharSequence[] monthLabels;

    /**
     * Format using an array of month labels
     *
     * @param monthLabels an array of 12 labels to use for months, starting with January
     */
    public MonthArrayTitleFormatter(CharSequence[] monthLabels) {
        if(monthLabels == null) {
            throw new IllegalArgumentException("Label array cannot be null");
        }
        if(monthLabels.length < 12) {
            throw new IllegalArgumentException("Label array is too short");
        }
        this.monthLabels = monthLabels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharSequence format(CalendarDay day) {
        return new SpannableStringBuilder()
                .append(monthLabels[day.getMonth()])
                .append(" ")
                .append(String.valueOf(day.getYear()));
    }
}
