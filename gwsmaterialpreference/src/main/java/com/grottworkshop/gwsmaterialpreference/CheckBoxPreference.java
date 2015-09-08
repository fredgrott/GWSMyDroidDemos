/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Jens Driller
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.grottworkshop.gwsmaterialpreference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;

import static android.support.v7.internal.VersionUtils.isAtLeastL;

/**
 * CheckBoxPreference class
 * Created by fgrott on 9/2/2015.
 */
@SuppressWarnings("unused")
public class CheckBoxPreference extends TwoStatePreference {

    public CheckBoxPreference(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public CheckBoxPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public CheckBoxPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public CheckBoxPreference(Context context, AttributeSet attrs, int defStyleAttr,
                              int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[] {
                android.R.attr.summaryOn, android.R.attr.summaryOff, android.R.attr.disableDependentsState
        }, defStyleAttr, defStyleRes);

        setSummaryOn(typedArray.getString(0));
        setSummaryOff(typedArray.getString(1));
        setDisableDependentsState(typedArray.getBoolean(2, false));

        typedArray.recycle();

        setWidgetLayoutResource(R.layout.mp_checkbox_preference);
    }

    @Override @SuppressWarnings("deprecation")
    protected void onBindView(View view) {
        super.onBindView(view);

        CheckBox checkboxView = (CheckBox) view.findViewById(R.id.checkbox);
        checkboxView.setChecked(isChecked());

        if (isAtLeastL()) {
            // remove circular background when pressed
            checkboxView.setBackgroundDrawable(null);
        }

        syncSummaryView(view);
    }
}
