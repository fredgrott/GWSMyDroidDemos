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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;

import static com.grottworkshop.gwsmaterialviewpagerreg.Utils.dpToPx;

/**
 * A class containing references to views inside MaterialViewPager's header
 * Created by fgrott on 8/29/2015.
 */
public class MaterialViewPagerHeader {

    protected Context context;

    protected View toolbarLayout;
    protected Toolbar toolbar;
    protected View mPagerSlidingTabStrip;

    protected View toolbarLayoutBackground;
    protected View headerBackground;
    protected View statusBackground;
    protected View mLogo;

    //positions used to animate views during scroll

    public float finalTabsY;

    public float finalTitleY;
    public float finalTitleHeight;
    public float finalTitleX;

    public float originalTitleY;
    public float originalTitleHeight;
    public float originalTitleX;
    public float finalScale;

    private MaterialViewPagerHeader(Toolbar toolbar){
        this.toolbar = toolbar;
        this.context = toolbar.getContext();
        this.toolbarLayout = (View) toolbar.getParent();
    }

    public static MaterialViewPagerHeader withToolbar(Toolbar toolbar) {
        return new MaterialViewPagerHeader(toolbar);
    }

    public Context getContext() {
        return context;
    }

    public MaterialViewPagerHeader withPagerSlidingTabStrip(View pagerSlidingTabStrip) {
        this.mPagerSlidingTabStrip = pagerSlidingTabStrip;

        mPagerSlidingTabStrip.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                finalTabsY = dpToPx(-2, context);

                mPagerSlidingTabStrip.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });

        return this;
    }

    public MaterialViewPagerHeader withHeaderBackground(View headerBackground) {
        this.headerBackground = headerBackground;
        return this;
    }

    public MaterialViewPagerHeader withStatusBackground(View statusBackground) {
        this.statusBackground = statusBackground;
        return this;
    }

    public MaterialViewPagerHeader withToolbarLayoutBackground(View toolbarLayoutBackground) {
        this.toolbarLayoutBackground = toolbarLayoutBackground;
        return this;
    }

    public int getStatusBarHeight(Context context){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public MaterialViewPagerHeader withLogo(View logo) {
        this.mLogo = logo;

        //when logo get a height, initialise initial & final logo positions
        toolbar.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                originalTitleY = mLogo.getY();
                originalTitleX = mLogo.getX();

                originalTitleHeight = mLogo.getHeight();
                finalTitleHeight = dpToPx(21, context);

                //the final scale of the logo
                finalScale = finalTitleHeight / originalTitleHeight;

                finalTitleY = (toolbar.getPaddingTop() + toolbar.getHeight()) / 2 - finalTitleHeight / 2 - (1 - finalScale) * finalTitleHeight;

                //(mLogo.getWidth()/2) *(1-finalScale) is the margin left added by the scale() on the logo
                //when logo scaledown, the content stay in center, so we have to anually remove the left padding
                finalTitleX = dpToPx(52f, context) - (mLogo.getWidth() / 2) * (1 - finalScale);

                toolbarLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
        return this;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public View getHeaderBackground() {
        return headerBackground;
    }

    public View getStatusBackground() {
        return statusBackground;
    }

    public View getLogo() {
        return mLogo;
    }

}
