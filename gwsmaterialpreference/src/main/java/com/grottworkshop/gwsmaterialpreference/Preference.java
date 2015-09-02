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
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.text.TextUtils.isEmpty;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.grottworkshop.gwsmaterialpreference.Typefaces.getRobotoRegular;


/**
 * Created by fgrott on 9/2/2015.
 */
public class Preference extends android.preference.Preference {

    TextView titleView;
    TextView summaryView;

    ImageView imageView;
    View imageFrame;

    private int iconResId;
    private Drawable icon;

    public Preference(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public Preference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public Preference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(LOLLIPOP)
    public Preference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray =
                context.obtainStyledAttributes(attrs, new int[] { android.R.attr.icon }, defStyleAttr,
                        defStyleRes);
        iconResId = typedArray.getResourceId(0, 0);
        typedArray.recycle();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected View onCreateView(ViewGroup parent) {
        LayoutInflater layoutInflater =
                (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.mp_preference, parent, false);

        ViewGroup widgetFrame = (ViewGroup) layout.findViewById(R.id.widget_frame);
        int widgetLayoutResId = getWidgetLayoutResource();
        if (widgetLayoutResId != 0) {
            layoutInflater.inflate(widgetLayoutResId, widgetFrame);
        }
        widgetFrame.setVisibility(widgetLayoutResId != 0 ? VISIBLE : GONE);

        return layout;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        CharSequence title = getTitle();
        titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(title);
        titleView.setVisibility(!isEmpty(title) ? VISIBLE : GONE);
        titleView.setTypeface(getRobotoRegular(getContext()));

        CharSequence summary = getSummary();
        summaryView = (TextView) view.findViewById(R.id.summary);
        summaryView.setText(summary);
        summaryView.setVisibility(!isEmpty(summary) ? VISIBLE : GONE);
        summaryView.setTypeface(getRobotoRegular(getContext()));

        if (icon == null && iconResId > 0) {
            icon = getContext().getResources().getDrawable(iconResId);
        }
        imageView = (ImageView) view.findViewById(R.id.icon);
        imageView.setImageDrawable(icon);
        imageView.setVisibility(icon != null ? VISIBLE : GONE);

        imageFrame = view.findViewById(R.id.icon_frame);
        imageFrame.setVisibility(icon != null ? VISIBLE : GONE);
    }

    @Override
    public void setIcon(int iconResId) {
        super.setIcon(iconResId);
        this.iconResId = iconResId;
    }

    @Override
    public void setIcon(Drawable icon) {
        super.setIcon(icon);
        this.icon = icon;
    }
}
