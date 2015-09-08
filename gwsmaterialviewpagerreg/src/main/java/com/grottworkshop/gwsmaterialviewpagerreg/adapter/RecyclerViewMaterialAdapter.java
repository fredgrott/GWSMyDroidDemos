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
package com.grottworkshop.gwsmaterialviewpagerreg.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grottworkshop.gwsmaterialviewpagerreg.R;

/**
 * RecyclerViewMaterialAdapter class
 * Created by fgrott on 8/29/2015.
 */
@SuppressWarnings("unused")
public class RecyclerViewMaterialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //the constants value of the header view
    static final int TYPE_PLACEHOLDER = Integer.MIN_VALUE;

    //the size taken by the header
    private int mPlaceholderSize = 1;

    //the actual RecyclerView.Adapter
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

    /**
     * Construct the RecyclerViewMaterialAdapter, which inject a header into an actual RecyclerView.Adapter
     *
     * @param adapter The real RecyclerView.Adapter which displays content
     */
    public RecyclerViewMaterialAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.mAdapter = adapter;
    }

    /**
     * Construct the RecyclerViewMaterialAdapter, which inject a header into an actual RecyclerView.Adapter
     *
     * @param adapter         The real RecyclerView.Adapter which displays content
     * @param placeholderSize The number of placeholder items before real items, default is 1
     */
    public RecyclerViewMaterialAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, int placeholderSize) {
        this.mAdapter = adapter;
        mPlaceholderSize = placeholderSize;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mPlaceholderSize)
            return TYPE_PLACEHOLDER;
        else
            return mAdapter.getItemViewType(position - mPlaceholderSize); //call getItemViewType on the adapter, less mPlaceholderSize
    }

    //dispatch getItemCount to the actual adapter, add mPlaceholderSize
    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + mPlaceholderSize;
    }

    //add the header on first position, else display the true adapter's cells
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case TYPE_PLACEHOLDER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.material_view_pager_placeholder, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }
            default:
                return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    //dispatch onBindViewHolder on the actual mAdapter
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_PLACEHOLDER:
                break;
            default:
                mAdapter.onBindViewHolder(holder, position - mPlaceholderSize);
                break;
        }
    }
}