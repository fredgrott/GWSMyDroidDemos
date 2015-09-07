/*
 * Copyright 2014 Emil SjÃ¶lander
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
package com.grottworkshop.gwsstickylistheaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * WrapperViewList class
 * Created by fgrott on 9/3/2015.
 */
@SuppressWarnings("unused")
class WrapperViewList extends ListView {

    interface LifeCycleListener {
        void onDispatchDrawOccurred(Canvas canvas);
    }

    private LifeCycleListener mLifeCycleListener;
    private List<View> mFooterViews;
    private int mTopClippingLength;
    private Rect mSelectorRect = new Rect();// for if reflection fails
    private Field mSelectorPositionField;
    private boolean mClippingToPadding = true;
    private boolean mBlockLayoutChildren = false;

    public WrapperViewList(Context context) throws IllegalArgumentException, IllegalAccessException {
        super(context);

        // Use reflection to be able to change the size/position of the list
        // selector so it does not come under/over the header
        try {
            Field selectorRectField = AbsListView.class.getDeclaredField("mSelectorRect");
            selectorRectField.setAccessible(true);
            mSelectorRect = (Rect) selectorRectField.get(this);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                mSelectorPositionField = AbsListView.class.getDeclaredField("mSelectorPosition");
                mSelectorPositionField.setAccessible(true);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean performItemClick(View view, int position, long id) {
        if (view instanceof WrapperView) {
            view = ((WrapperView) view).mItem;
        }
        return super.performItemClick(view, position, id);
    }

    private void positionSelectorRect() {
        if (!mSelectorRect.isEmpty()) {
            int selectorPosition = 0;
            try {
                selectorPosition = getSelectorPosition();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (selectorPosition >= 0) {
                int firstVisibleItem = getFixedFirstVisibleItem();
                View v = getChildAt(selectorPosition - firstVisibleItem);
                if (v instanceof WrapperView) {
                    WrapperView wrapper = ((WrapperView) v);
                    mSelectorRect.top = wrapper.getTop() + wrapper.mItemTop;
                }
            }
        }
    }

    private int getSelectorPosition() throws IllegalAccessException {
        if (mSelectorPositionField == null) { // not all supported andorid
            // version have this variable
            for (int i = 0; i < getChildCount(); i++) {
                if (getChildAt(i).getBottom() == mSelectorRect.bottom) {
                    return i + getFixedFirstVisibleItem();
                }
            }
        } else {
            try {
                return mSelectorPositionField.getInt(this);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        positionSelectorRect();
        if (mTopClippingLength != 0) {
            canvas.save();
            Rect clipping = canvas.getClipBounds();
            clipping.top = mTopClippingLength;
            canvas.clipRect(clipping);
            super.dispatchDraw(canvas);
            canvas.restore();
        } else {
            super.dispatchDraw(canvas);
        }
        mLifeCycleListener.onDispatchDrawOccurred(canvas);
    }

    void setLifeCycleListener(LifeCycleListener lifeCycleListener) {
        mLifeCycleListener = lifeCycleListener;
    }

    @Override
    public void addFooterView(View v) {
        super.addFooterView(v);
        addInternalFooterView(v);
    }

    @Override
    public void addFooterView(View v, Object data, boolean isSelectable) {
        super.addFooterView(v, data, isSelectable);
        addInternalFooterView(v);
    }

    private void addInternalFooterView(View v) {
        if (mFooterViews == null) {
            mFooterViews = new ArrayList<>();
        }
        mFooterViews.add(v);
    }

    @Override
    public boolean removeFooterView(View v) {
        if (super.removeFooterView(v)) {
            mFooterViews.remove(v);
            return true;
        }
        return false;
    }

    boolean containsFooterView(View v) {
        return mFooterViews != null && mFooterViews.contains(v);
    }

    void setTopClippingLength(int topClipping) {
        mTopClippingLength = topClipping;
    }

    int getFixedFirstVisibleItem() {
        int firstVisibleItem = getFirstVisiblePosition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return firstVisibleItem;
        }

        // first getFirstVisiblePosition() reports items
        // outside the view sometimes on old versions of android
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).getBottom() >= 0) {
                firstVisibleItem += i;
                break;
            }
        }

        // work around to fix bug with firstVisibleItem being to high
        // because list view does not take clipToPadding=false into account
        // on old versions of android
        if (!mClippingToPadding && getPaddingTop() > 0 && firstVisibleItem > 0) {
            if (getChildAt(0).getTop() > 0) {
                firstVisibleItem -= 1;
            }
        }

        return firstVisibleItem;
    }

    @Override
    public void setClipToPadding(boolean clipToPadding) {
        mClippingToPadding = clipToPadding;
        super.setClipToPadding(clipToPadding);
    }

    public void setBlockLayoutChildren(boolean block) {
        mBlockLayoutChildren = block;
    }

    @Override
    protected void layoutChildren() {
        if (!mBlockLayoutChildren) {
            super.layoutChildren();
        }
    }
}
