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

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Created by florentchampigny on 26/04/15.
 * A placeholder view used to add a transparent padding on top of a Scroller
 * RecyclerView : use RecyclerViewMaterialAdapter
 * ListView : use ListViewMaterialAdapter (smoother if use RecyclerView)
 * ScrollView : add a MaterialViewPagerHeaderView on top of your ScrollView (with LinearLayout vertical)
 * Created by fgrott on 8/29/2015.
 */
@SuppressWarnings("unused")
public class MaterialViewPagerHeaderView extends View {
    public MaterialViewPagerHeaderView(Context context) {
        super(context);
    }

    public MaterialViewPagerHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialViewPagerHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaterialViewPagerHeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void setMaterialHeight() {
        //get the MaterialViewPagerAnimator attached to this activity
        //to retrieve the declared header height
        //and set it as current view height (+10dp margin)

        MaterialViewPagerAnimator animator = MaterialViewPagerHelper.getAnimator(getContext());
        if (animator != null) {
            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = Math.round(Utils.dpToPx(animator.getHeaderHeight() + 10, getContext()));
            super.setLayoutParams(params);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    setMaterialHeight();
                    getViewTreeObserver().removeOnPreDrawListener(this);
                    return false;
                }
            });
        }
    }
}
