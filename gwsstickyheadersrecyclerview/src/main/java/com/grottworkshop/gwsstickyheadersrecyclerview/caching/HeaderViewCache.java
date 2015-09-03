/*
 * Copyright 2014 Jacob Tabak - Timehop
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
 *
 */
package com.grottworkshop.gwsstickyheadersrecyclerview.caching;

import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.grottworkshop.gwsstickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.grottworkshop.gwsstickyheadersrecyclerview.util.OrientationProvider;

/**
 * An implementation of {@link HeaderProvider} that creates and caches header views
 * Created by fgrott on 9/3/2015.
 */
public class HeaderViewCache implements HeaderProvider {

    private final StickyRecyclerHeadersAdapter mAdapter;
    private final LongSparseArray<View> mHeaderViews = new LongSparseArray<>();
    private final OrientationProvider mOrientationProvider;

    public HeaderViewCache(StickyRecyclerHeadersAdapter adapter,
                           OrientationProvider orientationProvider) {
        mAdapter = adapter;
        mOrientationProvider = orientationProvider;
    }

    @Override
    public View getHeader(RecyclerView parent, int position) {
        long headerId = mAdapter.getHeaderId(position);

        View header = mHeaderViews.get(headerId);
        if (header == null) {
            //TODO - recycle views
            RecyclerView.ViewHolder viewHolder = mAdapter.onCreateHeaderViewHolder(parent);
            mAdapter.onBindHeaderViewHolder(viewHolder, position);
            header = viewHolder.itemView;
            if (header.getLayoutParams() == null) {
                header.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            int widthSpec;
            int heightSpec;

            if (mOrientationProvider.getOrientation(parent) == LinearLayoutManager.VERTICAL) {
                widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
                heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);
            } else {
                widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.UNSPECIFIED);
                heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.EXACTLY);
            }

            int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                    parent.getPaddingLeft() + parent.getPaddingRight(), header.getLayoutParams().width);
            int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                    parent.getPaddingTop() + parent.getPaddingBottom(), header.getLayoutParams().height);
            header.measure(childWidth, childHeight);
            header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
            mHeaderViews.put(headerId, header);
        }
        return header;
    }

    @Override
    public void invalidate() {
        mHeaderViews.clear();
    }
}
