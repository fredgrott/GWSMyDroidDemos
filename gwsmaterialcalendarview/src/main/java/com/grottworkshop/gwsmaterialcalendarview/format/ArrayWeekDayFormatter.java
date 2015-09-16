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

/**
 * Use an array to supply week day labels
 * Created by fgrott on 9/16/2015.
 */
@SuppressWarnings("unused")
public class ArrayWeekDayFormatter implements WeekDayFormatter {

    private final CharSequence[] weekDayLabels;

    /**
     * @param weekDayLabels an array of 7 labels, starting with Sunday
     */
    public ArrayWeekDayFormatter(CharSequence[] weekDayLabels) {
        if(weekDayLabels == null) {
            throw new IllegalArgumentException("Cannot be null");
        }
        if(weekDayLabels.length != 7) {
            throw new IllegalArgumentException("Array must contain exactly 7 elements");
        }
        this.weekDayLabels = weekDayLabels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharSequence format(int dayOfWeek) {
        return weekDayLabels[dayOfWeek - 1];
    }
}
