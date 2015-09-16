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
package com.grottworkshop.gwsmaterialcalendarview;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * An imputable representation of a day on a calendar
 * Created by fgrott on 9/16/2015.
 */
@SuppressWarnings("unused")
public final class CalendarDay implements Parcelable {

    /**
     * Get a new instance set to today
     *
     * @return CalendarDay set to today's date
     */
    public static @NonNull CalendarDay today() {
        return from(CalendarUtils.getInstance());
    }

    /**
     * Get a new instance set to the specified day
     *
     * @param year new instance's year
     * @param month new instance's month as defined by {@linkplain java.util.Calendar}
     * @param day new instance's day of month
     * @return CalendarDay set to the specified date
     */
    @SuppressWarnings("deprecation")
    public static @NonNull CalendarDay from(int year, int month, int day) {
        //TODO: CalendarDar(java.util.Calendar) is depreciated
        return new CalendarDay(year, month, day);
    }

    /**
     * Get a new instance set to the specified day
     *
     * @param calendar {@linkplain Calendar} to pull date information from. Passing null will return null
     *
     * @return CalendarDay set to the specified date
     */
    public static CalendarDay from(@Nullable Calendar calendar) {
        if(calendar == null) {
            return null;
        }
        return from(
                CalendarUtils.getYear(calendar),
                CalendarUtils.getMonth(calendar),
                CalendarUtils.getDay(calendar)
        );
    }

    /**
     * Get a new instance set to the specified day
     *
     * @param date {@linkplain Date} to pull date information from. Passing null will return null.
     *
     * @return CalendarDay set to the specified date
     */
    public static CalendarDay from(@Nullable Date date) {
        if(date == null) {
            return null;
        }
        return from(CalendarUtils.getInstance(date));
    }

    private final int year;
    private final int month;
    private final int day;

    /**
     * Cache for calls to {@linkplain #getCalendar()}
     */
    private transient Calendar _calendar;

    /**
     * Cache for calls to {@linkplain #getDate()}
     */
    private transient Date _date;

    /**
     * Initialized to the current day
     *
     * @see CalendarDay#today()
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public CalendarDay() {
        //TODO: CalendarDar(java.util.Calendar) is depreciated
        this(CalendarUtils.getInstance());
    }

    /**
     * @see CalendarDay#from(Calendar)
     *
     * @param calendar source to pull date information from for this instance
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public CalendarDay(Calendar calendar) {
        //TODO: CalendarDar(java.util.Calendar) is depreciated
        this(
                CalendarUtils.getYear(calendar),
                CalendarUtils.getMonth(calendar),
                CalendarUtils.getDay(calendar)
        );
    }

    /**
     * @see CalendarDay#from(Calendar)
     *
     * @param year new instance's year
     * @param month new instance's month as defined by {@linkplain java.util.Calendar}
     * @param day new instance's day of month
     */
    @Deprecated
    public CalendarDay(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * @see CalendarDay#from(Date)
     *
     * @param date source to pull date information from for this instance
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public CalendarDay(Date date) {
        //TODO: CalendarDar(java.util.Calendar) is depreciated
        this(CalendarUtils.getInstance(date));
    }

    /**
     * Get the year
     *
     * @return the year for this day
     */
    public int getYear() {
        return year;
    }

    /**
     * Get the month, represented by values from {@linkplain Calendar}
     *
     * @return the month of the year as defined by {@linkplain Calendar}
     */
    public int getMonth() {
        return month;
    }

    /**
     * Get the day
     *
     * @return the day of the month for this day
     */
    public int getDay() {
        return day;
    }

    /**
     * Get this day as a {@linkplain Date}
     *
     * @return a date with this days information
     */
    public @NonNull
    Date getDate() {
        if(_date == null) {
            _date = getCalendar().getTime();
        }
        return _date;
    }

    /**
     * Get this day as a {@linkplain Calendar}
     *
     * @return a new calendar instance with this day information
     */
    public @NonNull Calendar getCalendar() {
        if(_calendar == null) {
            _calendar = CalendarUtils.getInstance();
            copyTo(_calendar);
        }
        return _calendar;
    }

    void copyToMonthOnly(@NonNull Calendar calendar) {
        calendar.clear();
        calendar.set(year, month, 1);
        calendar.getTimeInMillis();
    }

    /**
     * Copy this day's information to the given calendar instance
     *
     * @param calendar calendar to set date information to
     */
    public void copyTo(@NonNull Calendar calendar) {
        calendar.clear();
        calendar.set(year, month, day);
        calendar.getTimeInMillis();
    }

    /**
     * Determine if this day is within a specified range
     *
     * @param minDate the earliest day, may be null
     * @param maxDate the latest day, may be null
     * @return true if the between (inclusive) the min and max dates.
     */
    public boolean isInRange(@Nullable CalendarDay minDate, @Nullable CalendarDay maxDate) {
        return !(minDate != null && minDate.isAfter(this)) &&
                !(maxDate != null && maxDate.isBefore(this));
    }

    /**
     * Determine if this day is before the given instance
     *
     * @param other the other day to test
     * @return true if this is before other, false if equal or after
     */
    public boolean isBefore(@NonNull CalendarDay other) {
        if (year == other.year) {
            return ((month == other.month) ? (day < other.day) : (month < other.month));
        }
        else {
            return year < other.year;
        }
    }

    /**
     * Determine if this day is after the given instance
     *
     * @param other the other day to test
     * @return true if this is after other, false if equal or before
     */
    public boolean isAfter(@NonNull CalendarDay other) {

        if (year == other.year) {
            return (month == other.month) ? (day > other.day) : (month > other.month);
        }
        else {
            return year > other.year;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CalendarDay that = (CalendarDay) o;

        return day == that.day && month == that.month && year == that.year;
    }

    @Override
    public int hashCode() {
        return hashCode(year, month, day);
    }

    private static int hashCode(int year, int month, int day) {
        //Should produce hashes like "20150401"
        return (year * 10000) + (month * 100) + day;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "CalendarDay{%d-%d-%d}", year, month + 1, day);
    }

    /*
     * Parcelable Stuff
     */

    @SuppressWarnings("deprecation")
    public CalendarDay(Parcel in) {
        //TODO: CalendarDar(java.util.Calendar) is depreciated
        this(in.readInt(), in.readInt(), in.readInt());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(day);
    }

    public static final Creator<CalendarDay> CREATOR = new Parcelable.Creator<CalendarDay>() {
        public CalendarDay createFromParcel(Parcel in) {
            return new CalendarDay(in);
        }

        public CalendarDay[] newArray(int size) {
            return new CalendarDay[size];
        }
    };
}
