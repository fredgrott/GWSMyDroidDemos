/*
 * Copyright 2015 florent37, Inc.
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.grottworkshop.gwsmaterialviewpagerreg;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.grottworkshop.gwsobservablescroll.ObservableScrollView;

/**
 * Created by fgrott on 8/29/2015.
 */
public class ScrollView extends ObservableScrollView {

    private static final String TAG_LINEARLAYOUT = "TAG_LINEARLAYOUT";
    LinearLayout linearLayout;

    public ScrollView(Context context) {
        super(context);
    }

    public ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private boolean constructLinearLayout(View child) {
        if (linearLayout == null) {

            linearLayout = new LinearLayout(getContext());
            linearLayout.setTag(TAG_LINEARLAYOUT);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            super.addView(linearLayout);

            View placeholder = LayoutInflater.from(getContext()).inflate(R.layout.material_view_pager_placeholder, linearLayout, false);
            linearLayout.addView(placeholder);
        }

        if (child != null && TAG_LINEARLAYOUT.equals(child.getTag())) {
            return false;
        }
        return true;
    }

    @Override
    public void addView(View child) {
        if (constructLinearLayout(child))
            linearLayout.addView(child);
        else
            super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (constructLinearLayout(child))
            linearLayout.addView(child, index);
        else
            super.addView(child, index);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (constructLinearLayout(child))
            linearLayout.addView(child, params);
        else
            super.addView(child, params);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (constructLinearLayout(child))
            linearLayout.addView(child, index, params);
        else
            super.addView(child, index, params);
    }

    @Override
    public void removeAllViews() {
        constructLinearLayout(null);
        linearLayout.removeAllViews();
    }

    @Override
    public void removeViewAt(int index) {
        constructLinearLayout(null);
        linearLayout.removeViewAt(index);
    }

    @Override
    public void removeViews(int start, int count) {
        constructLinearLayout(null);
        linearLayout.removeViews(start, count);
    }
}
