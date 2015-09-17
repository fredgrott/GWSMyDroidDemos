package com.grottworkshop.gwsdatetimepicker;



/**
 * DatePickerController interface
 * Created by fgrott on 9/16/2015.
 */
@SuppressWarnings("unused")
interface DatePickerController {
    int getFirstDayOfWeek();

    int getMaxYear();

    int getMinYear();

    SimpleMonthAdapter.CalendarDay getSelectedDay();

    void onDayOfMonthSelected(int year, int month, int day);

    void onYearSelected(int year);

    void registerOnDateChangedListener(DatePickerDialog.OnDateChangedListener onDateChangedListener);

    void tryVibrate();
}