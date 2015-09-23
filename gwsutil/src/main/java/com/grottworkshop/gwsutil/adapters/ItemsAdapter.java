/**
 * Copyright (C) 2015 Alex Vasilkov
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.grottworkshop.gwsutil.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 * Simple {@link BaseAdapter} implementation to use with any {@link List}.<br/>
 * {@link #getView(int, android.view.View, android.view.ViewGroup) getView} method is divided into
 * {@link #createView(Object, int, android.view.ViewGroup, android.view.LayoutInflater) createView} and
 * {@link #bindView(Object, int, android.view.View) bindView} methods.
 * Created by fgrott on 9/23/2015.
 */
@SuppressWarnings("unused")
public abstract class ItemsAdapter<T> extends BaseAdapter {

    private List<T> mItemsList;
    private final WeakReference<Context> contextRef;
    private final LayoutInflater layoutInflater;

    public ItemsAdapter(Context context) {
        contextRef = new WeakReference<>(context);
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * Sets list to this adapter and calls {@link #notifyDataSetChanged()} to update underlying {@link android.widget.ListView}.<br/>
     * You can pass {@code null} to clear the adapter
     */
    public void setItemsList(List<T> list) {
        mItemsList = list;
        notifyDataSetChanged();
    }

    public List<T> getItemsList() {
        return mItemsList;
    }

    protected Context getContext() {
        return contextRef.get();
    }

    protected LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    @Override
    public int getCount() {
        return mItemsList == null ? 0 : mItemsList.size();
    }

    @Override
    public T getItem(int position) {
        if (mItemsList == null || position < 0 || position >= mItemsList.size()) return null;
        return mItemsList.get(position);
    }

    /**
     * Default implementation of this adapter uses pos as id. So we can find item by it's id with no problems.
     */
    public T getItem(long id) {
        return getItem((int) id);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        T item = mItemsList.get(pos);
        if (convertView == null) convertView = createView(item, pos, parent, layoutInflater);
        bindView(item, pos, convertView);
        return convertView;
    }

    protected abstract View createView(T item, int pos, ViewGroup parent, LayoutInflater inflater);

    protected abstract void bindView(T item, int pos, View convertView);

}
