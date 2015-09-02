/*
 * Copyright (C) 2014 Lucas Rocha
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
 */
package com.grottworkshop.gwstwowayview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.util.AttributeSet;

import com.grottworkshop.gwstwowayview.R;


/**
 * {@link android.support.v7.widget.RecyclerView.ItemDecoration} that applies a
 * vertical and horizontal spacing between items of the target
 * {@link android.support.v7.widget.RecyclerView}.
 * Created by fgrott on 9/2/2015.
 */
public class SpacingItemDecoration extends ItemDecoration {
    private final ItemSpacingOffsets mItemSpacing;

    public SpacingItemDecoration(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpacingItemDecoration(Context context, AttributeSet attrs, int defStyle) {
        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.twowayview_SpacingItemDecoration, defStyle, 0);

        final int verticalSpacing =
                Math.max(0, a.getInt(R.styleable.twowayview_SpacingItemDecoration_android_verticalSpacing, 0));
        final int horizontalSpacing =
                Math.max(0, a.getInt(R.styleable.twowayview_SpacingItemDecoration_android_horizontalSpacing, 0));

        a.recycle();

        mItemSpacing = new ItemSpacingOffsets(verticalSpacing, horizontalSpacing);
    }

    public SpacingItemDecoration(int verticalSpacing, int horizontalSpacing) {
        mItemSpacing = new ItemSpacingOffsets(verticalSpacing, horizontalSpacing);
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        mItemSpacing.getItemOffsets(outRect, itemPosition, parent);
    }
}
