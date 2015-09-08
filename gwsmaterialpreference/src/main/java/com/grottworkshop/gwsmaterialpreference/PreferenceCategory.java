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

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.text.TextUtils.isEmpty;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.grottworkshop.gwsmaterialpreference.ThemeUtils.resolveAccentColor;
import static com.grottworkshop.gwsmaterialpreference.Typefaces.getRobotoMedium;


/**
 * PreferenceCategory class
 * Created by fgrott on 9/2/2015.
 */
@SuppressWarnings("unused")
public class PreferenceCategory extends android.preference.PreferenceCategory {

    private int accentColor;

    public PreferenceCategory(Context context) {
        super(context);
        init();
    }

    public PreferenceCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(LOLLIPOP)
    public PreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr,
                              int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        accentColor = resolveAccentColor(getContext());
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected View onCreateView(ViewGroup parent) {
        LayoutInflater layoutInflater =
                (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(R.layout.mp_preference_category, parent, false);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        CharSequence title = getTitle();
        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(title);
        titleView.setTextColor(accentColor);
        titleView.setVisibility(!isEmpty(title) ? VISIBLE : GONE);
        titleView.setTypeface(getRobotoMedium(getContext()));
    }
}
