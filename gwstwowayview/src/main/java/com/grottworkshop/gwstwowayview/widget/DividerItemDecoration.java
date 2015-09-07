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
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;

import com.grottworkshop.gwstwowayview.R;


/**
 * {@link android.support.v7.widget.RecyclerView.ItemDecoration} that draws
 * vertical and horizontal dividers between the items of the target
 * {@link android.support.v7.widget.RecyclerView}.
 * Created by fgrott on 9/2/2015.
 */
@SuppressWarnings("unused")
public class DividerItemDecoration extends ItemDecoration {
    private final ItemSpacingOffsets mItemSpacing;

    private final Drawable mVerticalDivider;
    private final Drawable mHorizontalDivider;

    public DividerItemDecoration(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DividerItemDecoration(Context context, AttributeSet attrs, int defStyle) {
        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.twowayview_DividerItemDecoration, defStyle, 0);

        final Drawable divider = a.getDrawable(R.styleable.twowayview_DividerItemDecoration_android_divider);
        if (divider != null) {
            mVerticalDivider = mHorizontalDivider = divider;
        } else {
            mVerticalDivider = a.getDrawable(R.styleable.twowayview_DividerItemDecoration_twowayview_verticalDivider);
            mHorizontalDivider = a.getDrawable(R.styleable.twowayview_DividerItemDecoration_twowayview_horizontalDivider);
        }

        a.recycle();

        mItemSpacing = createSpacing(mVerticalDivider, mHorizontalDivider);
    }

    public DividerItemDecoration(Drawable divider) {
        this(divider, divider);
    }

    public DividerItemDecoration(Drawable verticalDivider, Drawable horizontalDivider) {
        mVerticalDivider = verticalDivider;
        mHorizontalDivider = horizontalDivider;
        mItemSpacing = createSpacing(mVerticalDivider, mHorizontalDivider);
    }

    private static ItemSpacingOffsets createSpacing(Drawable verticalDivider,
                                                    Drawable horizontalDivider) {
        final int verticalSpacing;
        if (horizontalDivider != null) {
            verticalSpacing = horizontalDivider.getIntrinsicHeight();
        } else {
            verticalSpacing = 0;
        }

        final int horizontalSpacing;
        if (verticalDivider != null) {
            horizontalSpacing = verticalDivider.getIntrinsicWidth();
        } else {
            horizontalSpacing = 0;
        }

        final ItemSpacingOffsets spacing = new ItemSpacingOffsets(verticalSpacing, horizontalSpacing);
        spacing.setAddSpacingAtEnd(true);

        return spacing;
    }
    //TODO ItemDecoration.onDrawOver depreciated, fix
    @SuppressWarnings("deprecation")
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent) {
        final BaseLayoutManager lm = (BaseLayoutManager) parent.getLayoutManager();

        final int rightWithPadding = parent.getWidth() - parent.getPaddingRight();
        final int bottomWithPadding = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final int childLeft = lm.getDecoratedLeft(child);
            final int childTop = lm.getDecoratedTop(child);
            final int childRight = lm.getDecoratedRight(child);
            final int childBottom = lm.getDecoratedBottom(child);

            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            final int bottomOffset = childBottom - child.getBottom() - lp.bottomMargin;
            if (bottomOffset > 0 && childBottom < bottomWithPadding) {
                final int top = childBottom - bottomOffset;
                final int bottom = top + mHorizontalDivider.getIntrinsicHeight();

                mHorizontalDivider.setBounds(childLeft, top, childRight, bottom);
                mHorizontalDivider.draw(c);
            }

            final int rightOffset = childRight - child.getRight() - lp.rightMargin;
            if (rightOffset > 0 && childRight < rightWithPadding) {
                final int left = childRight - rightOffset;
                final int right = left + mVerticalDivider.getIntrinsicWidth();

                mVerticalDivider.setBounds(left, childTop, right, childBottom);
                mVerticalDivider.draw(c);
            }
        }
    }

    //TODO: depreciated fix
    @SuppressWarnings("deprecation")
    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        mItemSpacing.getItemOffsets(outRect, itemPosition, parent);
    }
}
