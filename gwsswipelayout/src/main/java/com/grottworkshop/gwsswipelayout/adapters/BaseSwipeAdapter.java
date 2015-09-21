/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 daimajia
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
 *copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *SOFTWARE.
 *
 */
package com.grottworkshop.gwsswipelayout.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.grottworkshop.gwsswipelayout.SwipeLayout;
import com.grottworkshop.gwsswipelayout.impl.SwipeItemMangerImpl;
import com.grottworkshop.gwsswipelayout.interfaces.SwipeAdapterInterface;
import com.grottworkshop.gwsswipelayout.interfaces.SwipeItemMangerInterface;

import java.util.List;


/**
 * BaseSwipeAdapter class
 * Created by fgrott on 9/21/2015.
 */
@SuppressWarnings("unused")
public abstract class BaseSwipeAdapter extends BaseAdapter implements SwipeItemMangerInterface, SwipeAdapterInterface {

    protected SwipeItemMangerImpl mItemManger = new SwipeItemMangerImpl(this);

    /**
     * return the {@link SwipeLayout} resource id, int the view item.
     * @param position the position
     *
     */
    public abstract int getSwipeLayoutResourceId(int position);

    /**
     * generate a new view item.
     * Never bind SwipeListener or fill values here, every item has a chance to fill value or bind
     * listeners in fillValues.
     * to fill it in {@code fillValues} method.
     * @param position the position
     * @param parent the parent
     *
     */
    public abstract View generateView(int position, ViewGroup parent);

    /**
     * fill values or bind listeners to the view.
     * @param position the position
     * @param convertView the convertView
     */
    public abstract void fillValues(int position, View convertView);


    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            v = generateView(position, parent);
            mItemManger.initialize(v, position);
        }else{
            mItemManger.updateConvertView(v, position);
        }
        fillValues(position, v);
        return v;
    }

    @Override
    public void openItem(int position) {
        mItemManger.openItem(position);
    }

    @Override
    public void closeItem(int position) {
        mItemManger.closeItem(position);
    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {
        mItemManger.closeAllExcept(layout);
    }

    @Override
    public List<Integer> getOpenItems() {
        return mItemManger.getOpenItems();
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return mItemManger.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        mItemManger.removeShownLayouts(layout);
    }

    @Override
    public boolean isOpen(int position) {
        return mItemManger.isOpen(position);
    }

    @Override
    public SwipeItemMangerImpl.Mode getMode() {
        return mItemManger.getMode();
    }

    @Override
    public void setMode(SwipeItemMangerImpl.Mode mode) {
        mItemManger.setMode(mode);
    }
}
