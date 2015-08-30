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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.grottworkshop.gwsobservablescroll.ObservableScrollView;
import com.grottworkshop.gwsobservablescroll.ObservableScrollViewCallbacks;
import com.grottworkshop.gwsobservablescroll.ObservableWebView;
import com.grottworkshop.gwsobservablescroll.ScrollState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.grottworkshop.gwsmaterialviewpagerreg.Utils.colorWithAlpha;
import static com.grottworkshop.gwsmaterialviewpagerreg.Utils.dpToPx;
import static com.grottworkshop.gwsmaterialviewpagerreg.Utils.minMax;
import static com.grottworkshop.gwsmaterialviewpagerreg.Utils.setBackgroundColor;
import static com.grottworkshop.gwsmaterialviewpagerreg.Utils.setElevation;
import static com.grottworkshop.gwsmaterialviewpagerreg.Utils.setScale;


/**
 * <p/>
 * Listen to Scrollable inside MaterialViewPager
 * When notified scroll, dispatch the current scroll to other scrollable
 * <p/>
 * Note : didn't want to translate the MaterialViewPager or intercept Scroll,
 * so added a ViewPager with scrollables containing a transparent placeholder on top
 * <p/>
 * When scroll, animate the MaterialViewPager Header (toolbar, logo, color ...)
 * Created by fgrott on 8/29/2015.
 */
public class MaterialViewPagerAnimator {

    private static final String TAG = MaterialViewPagerAnimator.class.getSimpleName();

    private static final Boolean ENABLE_LOG = false;

    private Context context;

    //contains MaterialViewPager subviews references
    private MaterialViewPagerHeader mHeader;

    //duration of translate header enter animation
    private static final int ENTER_TOOLBAR_ANIMATION_DURATION = 600;

    //reference to the current MaterialViewPager
    protected MaterialViewPager materialViewPager;

    //final toolbar layout elevation (if attr viewpager_enableToolbarElevation = true)
    public final float elevation;

    //max scroll which will be dispatched for all scrollable
    public final float scrollMax;

    // equals scrollMax in DP (saved to avoir convert to dp anytime I use it)
    public final float scrollMaxDp;

    protected float lastYOffset = -1; //the current yOffset
    protected float lastPercent = 0; //the current Percent

    //contains the attributes given to MaterialViewPager from layout
    protected MaterialViewPagerSettings settings;

    //list of all registered scrollers
    protected List<Object> scrollViewList = new ArrayList<>();

    //save all yOffsets of scrollables
    protected HashMap<Object, Integer> yOffsets = new HashMap<>();

    //the last headerYOffset during scroll
    private float headerYOffset = Float.MAX_VALUE;

    //the tmp headerAnimator (not null if animating, else null)
    private Object headerAnimator;

    public MaterialViewPagerAnimator(MaterialViewPager materialViewPager) {

        this.settings = materialViewPager.settings;

        this.materialViewPager = materialViewPager;
        this.mHeader = materialViewPager.materialViewPagerHeader;
        this.context = mHeader.getContext();

        // initialise the scrollMax to headerHeight, so until the first cell touch the top of the screen
        this.scrollMax = this.settings.headerHeight;
        //save in into dp once
        this.scrollMaxDp = dpToPx(this.scrollMax, context);

        //heightMaxScrollToolbar = context.getResources().getDimension(R.dimen.material_viewpager_padding_top);
        elevation = dpToPx(4, context);
    }

    /**
     * When notified for scroll, dispatch it to all registered scrollables
     *
     * @param source
     * @param yOffset
     */
    private void dispatchScrollOffset(Object source, float yOffset) {
        if (scrollViewList != null) {
            for (Object scroll : scrollViewList) {

                //do not re-scroll the source
                if (scroll != null && scroll != source) {
                    setScrollOffset(scroll, yOffset);
                }
            }
        }
    }

    /**
     * When notified for scroll, dispatch it to all registered scrollables
     *
     * @param scroll
     * @param yOffset
     */
    private void setScrollOffset(Object scroll, float yOffset) {
        //do not re-scroll the source
        if (scroll != null && yOffset >= 0) {

            if (scroll instanceof RecyclerView) {
                //RecyclerView.scrollTo : UnsupportedOperationException
                //Moved to the RecyclerView.LayoutManager.scrollToPositionWithOffset
                //Have to be instanceOf RecyclerView.LayoutManager to work (so work with RecyclerView.GridLayoutManager)
                RecyclerView.LayoutManager layoutManager = ((RecyclerView) scroll).getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    linearLayoutManager.scrollToPositionWithOffset(0, (int) -yOffset);
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    staggeredGridLayoutManager.scrollToPositionWithOffset(0, (int) -yOffset);
                }
            } else if (scroll instanceof ScrollView) {
                ((ScrollView) scroll).scrollTo(0, (int) yOffset);
            } else if (scroll instanceof ListView) {
                ((ListView) scroll).scrollTo(0, (int) yOffset);
            } else if (scroll instanceof WebView) {
                ((WebView) scroll).scrollTo(0, (int) yOffset);
            }

            //save the current yOffset of the scrollable on the yOffsets hashmap
            yOffsets.put(scroll, (int) yOffset);
        }
    }

    /**
     * Called when a scroller(RecyclerView/ListView,ScrollView,WebView) scrolled by the user
     *
     * @param source  the scroller
     * @param yOffset the scroller current yOffset
     */
    public void onMaterialScrolled(Object source, float yOffset) {

        //only if yOffset changed
        if (yOffset == lastYOffset)
            return;

        float scrollTop = -yOffset;

        {
            //parallax scroll of the Background ImageView (the KenBurnsView)
            if (mHeader.headerBackground != null) {

                if (this.settings.parallaxHeaderFactor != 0)
                    mHeader.headerBackground.setTranslationY(scrollTop / this.settings.parallaxHeaderFactor);

                if (mHeader.headerBackground.getY() >= 0)
                    mHeader.headerBackground.setY(0);
            }


        }

        if (ENABLE_LOG)
            Log.d("yOffset", "" + yOffset);

        //dispatch the new offset to all registered scrollables
        dispatchScrollOffset(source, minMax(0, yOffset, scrollMaxDp));

        float percent = yOffset / scrollMax;

        percent = minMax(0, percent, 1);
        {

            {
                // change color of toolbar & viewpager indicator &  statusBaground
                setColorPercent(percent);
                lastPercent = percent; //save the percent
            }

            if (mHeader.mPagerSlidingTabStrip != null) { //move the viewpager indicator
                //float newY = ViewHelper.getY(mHeader.mPagerSlidingTabStrip) + scrollTop;

                if (ENABLE_LOG)
                    Log.d(TAG, "" + scrollTop);


                //mHeader.mPagerSlidingTabStrip.setTranslationY(mHeader.getToolbar().getBottom()-mHeader.mPagerSlidingTabStrip.getY());
                if (scrollTop <= 0) {
                    mHeader.mPagerSlidingTabStrip.setTranslationY(scrollTop);
                    mHeader.toolbarLayoutBackground.setTranslationY(scrollTop);

                    //when
                    if (mHeader.mPagerSlidingTabStrip.getY() < mHeader.getToolbar().getBottom()) {
                        float ty = mHeader.getToolbar().getBottom() - mHeader.mPagerSlidingTabStrip.getTop();
                        mHeader.mPagerSlidingTabStrip.setTranslationY(ty);
                        mHeader.toolbarLayoutBackground.setTranslationY(ty);
                    }
                }

            }


            if (mHeader.mLogo != null) { //move the header logo to toolbar

                if (this.settings.hideLogoWithFade) {
                    mHeader.mLogo.setAlpha(1 - percent);
                    mHeader.mLogo.setTranslationY((mHeader.finalTitleY - mHeader.originalTitleY) * percent);
                } else {
                    mHeader.mLogo.setTranslationY((mHeader.finalTitleY - mHeader.originalTitleY) * percent);
                    mHeader.mLogo.setTranslationX((mHeader.finalTitleX - mHeader.originalTitleX) * percent);

                    float scale = (1 - percent) * (1 - mHeader.finalScale) + mHeader.finalScale;
                    setScale(scale, mHeader.mLogo);
                }
            }

            if (this.settings.hideToolbarAndTitle && mHeader.toolbarLayout != null) {
                boolean scrollUp = lastYOffset < yOffset;

                if (scrollUp) {
                    scrollUp(yOffset);
                } else {
                    scrollDown(yOffset);
                }
            }
        }

        if (headerAnimator != null && percent < 1) {
            if (headerAnimator instanceof ObjectAnimator)
                ((ObjectAnimator) headerAnimator).cancel();
            else if (headerAnimator instanceof android.animation.ObjectAnimator)
                ((android.animation.ObjectAnimator) headerAnimator).cancel();
            headerAnimator = null;
        }

        lastYOffset = yOffset;
    }

    private void scrollUp(float yOffset){
        if (ENABLE_LOG)
            Log.d(TAG, "scrollUp");
        followScrollToolbarLayout(yOffset);
    }

    private void scrollDown(float yOffset){
        if (ENABLE_LOG)
            Log.d(TAG, "scrollDown");
        if (yOffset > mHeader.toolbarLayout.getHeight()) {

            animateEnterToolbarLayout(yOffset);

        } else {
            if (headerAnimator != null) {
                mHeader.toolbarLayout.setTranslationY(0);
                followScrollToolbarIsVisible = true;
            } else {
                headerYOffset = Float.MAX_VALUE;
                followScrollToolbarLayout(yOffset);
            }
        }
    }

    /**
     * Change the color of the statusbackground, toolbar, toolbarlayout and pagertitlestrip
     * With a color transition animation
     *
     * @param color    the final color
     * @param duration the transition color animation duration
     */
    public void setColor(int color, int duration) {
        ValueAnimator colorAnim = ObjectAnimator.ofInt(mHeader.headerBackground, "backgroundColor", settings.color, color);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setDuration(duration);
        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int colorAlpha = colorWithAlpha((Integer) animation.getAnimatedValue(), lastPercent);
                mHeader.headerBackground.setBackgroundColor(colorAlpha);
                mHeader.statusBackground.setBackgroundColor(colorAlpha);
                mHeader.toolbar.setBackgroundColor(colorAlpha);
                mHeader.toolbarLayoutBackground.setBackgroundColor(colorAlpha);
                mHeader.mPagerSlidingTabStrip.setBackgroundColor(colorAlpha);
            }
        });
        colorAnim.start();

        //set the new color as MaterialViewPager's color
        this.settings.color = color;

    }

    public void setColorPercent(float percent) {
        // change color of
        // toolbar & viewpager indicator &  statusBaground

        setBackgroundColor(
                colorWithAlpha(this.settings.color, percent),
                mHeader.statusBackground
        );

        if (percent >= 1) {
            setBackgroundColor(
                    colorWithAlpha(this.settings.color, percent),
                    mHeader.toolbar,
                    mHeader.toolbarLayoutBackground,
                    mHeader.mPagerSlidingTabStrip
            );
        } else {
            setBackgroundColor(
                    colorWithAlpha(this.settings.color, 0),
                    mHeader.toolbar,
                    mHeader.toolbarLayoutBackground,
                    mHeader.mPagerSlidingTabStrip
            );
        }

        if (this.settings.enableToolbarElevation && toolbarJoinsTabs())
            setElevation(
                    (percent == 1) ? elevation : 0,
                    mHeader.toolbar,
                    mHeader.toolbarLayoutBackground,
                    mHeader.mPagerSlidingTabStrip,
                    mHeader.mLogo
            );
    }

    private boolean toolbarJoinsTabs() {
        return (mHeader.toolbar.getBottom() == mHeader.mPagerSlidingTabStrip.getTop() + mHeader.mPagerSlidingTabStrip.getTranslationY());
    }

    boolean followScrollToolbarIsVisible = false;
    float firstScrollValue = Float.MIN_VALUE;

    /**
     * move the toolbarlayout (containing toolbar & tabs)
     * following the current scroll
     */
    private void followScrollToolbarLayout(float yOffset) {
        if (mHeader.toolbar.getBottom() == 0)
            return;

        if (toolbarJoinsTabs()) {
            if (firstScrollValue == Float.MIN_VALUE)
                firstScrollValue = yOffset;
            mHeader.toolbarLayout.setTranslationY(firstScrollValue - yOffset);
        } else {
            mHeader.toolbarLayout.setTranslationY(0);
        }

        followScrollToolbarIsVisible = (mHeader.toolbarLayout.getY() >= 0);
    }

    /**
     * Animate enter toolbarlayout
     *
     * @param yOffset
     */
    private void animateEnterToolbarLayout(float yOffset) {
        if (!followScrollToolbarIsVisible && headerAnimator != null) {
            if (headerAnimator instanceof ObjectAnimator)
                ((ObjectAnimator) headerAnimator).cancel();
            else if (headerAnimator instanceof android.animation.ObjectAnimator)
                ((android.animation.ObjectAnimator) headerAnimator).cancel();
            headerAnimator = null;
        }

        if (headerAnimator == null) {
            if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                headerAnimator = android.animation.ObjectAnimator.ofFloat(mHeader.toolbarLayout, "translationY", 0).setDuration(ENTER_TOOLBAR_ANIMATION_DURATION);
                ((android.animation.ObjectAnimator) headerAnimator).addListener(new android.animation.AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        super.onAnimationEnd(animation);
                        followScrollToolbarIsVisible = true;
                    }
                });
                ((android.animation.ObjectAnimator) headerAnimator).start();
            } else {
                headerAnimator = ObjectAnimator.ofFloat(mHeader.toolbarLayout, "translationY", 0).setDuration(ENTER_TOOLBAR_ANIMATION_DURATION);
                ((ObjectAnimator) headerAnimator).addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        followScrollToolbarIsVisible = true;
                    }
                });
                ((ObjectAnimator) headerAnimator).start();
            }
            headerYOffset = yOffset;
        }
    }

    public int getHeaderHeight() {
        return this.settings.headerHeight;
    }

    protected boolean isNewYOffset(int yOffset) {
        if (lastYOffset == -1)
            return true;
        else
            return yOffset != lastYOffset;
    }

    //region register scrollables

    /**
     * Register a RecyclerView to the current MaterialViewPagerAnimator
     * Listen to RecyclerView.OnScrollListener so give to $[onScrollListener] your RecyclerView.OnScrollListener if you already use one
     * For loadmore or anything else
     *
     * @param recyclerView     the scrollable
     * @param onScrollListener use it if you want to get a callback of the RecyclerView
     */
    public void registerRecyclerView(final RecyclerView recyclerView, final RecyclerView.OnScrollListener onScrollListener) {
        if (recyclerView != null) {
            scrollViewList.add(recyclerView); //add to the scrollable list
            yOffsets.put(recyclerView, recyclerView.getScrollY()); //save the initial recyclerview's yOffset (0) into hashmap
            //only necessary for recyclerview

            //listen to scroll
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                boolean firstZeroPassed;

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (onScrollListener != null)
                        onScrollListener.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    if (onScrollListener != null)
                        onScrollListener.onScrolled(recyclerView, dx, dy);

                    int yOffset = yOffsets.get(recyclerView);

                    yOffset += dy;
                    yOffsets.put(recyclerView, yOffset); //save the new offset

                    //first time you get 0, don't share it to others scrolls
                    if (yOffset == 0 && !firstZeroPassed) {
                        firstZeroPassed = true;
                        return;
                    }

                    //only if yOffset changed
                    if (isNewYOffset(yOffset))
                        onMaterialScrolled(recyclerView, yOffset);
                }
            });

            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    setScrollOffset(recyclerView, lastYOffset);
                }
            });
        }
    }

    /**
     * Register a ScrollView to the current MaterialViewPagerAnimator
     * Listen to ObservableScrollViewCallbacks so give to $[observableScrollViewCallbacks] your ObservableScrollViewCallbacks if you already use one
     * For loadmore or anything else
     *
     * @param scrollView                    the scrollable
     * @param observableScrollViewCallbacks use it if you want to get a callback of the RecyclerView
     */
    public void registerScrollView(final ObservableScrollView scrollView, final ObservableScrollViewCallbacks observableScrollViewCallbacks) {
        if (scrollView != null) {
            scrollViewList.add(scrollView);  //add to the scrollable list
            if (scrollView.getParent() != null && scrollView.getParent().getParent() != null && scrollView.getParent().getParent() instanceof ViewGroup)
                scrollView.setTouchInterceptionViewGroup((ViewGroup) scrollView.getParent().getParent());
            scrollView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {

                boolean firstZeroPassed;

                @Override
                public void onScrollChanged(int yOffset, boolean b, boolean b2) {
                    if (observableScrollViewCallbacks != null)
                        observableScrollViewCallbacks.onScrollChanged(yOffset, b, b2);

                    //first time you get 0, don't share it to others scrolls
                    if (yOffset == 0 && !firstZeroPassed) {
                        firstZeroPassed = true;
                        return;
                    }

                    //only if yOffset changed
                    if (isNewYOffset(yOffset))
                        onMaterialScrolled(scrollView, yOffset);
                }

                @Override
                public void onDownMotionEvent() {
                    if (observableScrollViewCallbacks != null)
                        observableScrollViewCallbacks.onDownMotionEvent();
                }

                @Override
                public void onUpOrCancelMotionEvent(ScrollState scrollState) {
                    if (observableScrollViewCallbacks != null)
                        observableScrollViewCallbacks.onUpOrCancelMotionEvent(scrollState);
                }
            });

            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    setScrollOffset(scrollView, lastYOffset);
                }
            });
        }
    }

    /**
     * Register a WebView to the current MaterialViewPagerAnimator
     * Listen to ObservableScrollViewCallbacks so give to $[observableScrollViewCallbacks] your ObservableScrollViewCallbacks if you already use one
     * For loadmore or anything else
     *
     * @param webView                       the scrollable
     * @param observableScrollViewCallbacks use it if you want to get a callback of the RecyclerView
     */
    public void registerWebView(final ObservableWebView webView, final ObservableScrollViewCallbacks observableScrollViewCallbacks) {
        if (webView != null) {
            if (scrollViewList.isEmpty())
                onMaterialScrolled(webView, webView.getCurrentScrollY());
            scrollViewList.add(webView);  //add to the scrollable list
            webView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
                @Override
                public void onScrollChanged(int yOffset, boolean b, boolean b2) {
                    if (observableScrollViewCallbacks != null)
                        observableScrollViewCallbacks.onScrollChanged(yOffset, b, b2);

                    if (isNewYOffset(yOffset))
                        onMaterialScrolled(webView, yOffset);
                }

                @Override
                public void onDownMotionEvent() {
                    if (observableScrollViewCallbacks != null)
                        observableScrollViewCallbacks.onDownMotionEvent();
                }

                @Override
                public void onUpOrCancelMotionEvent(ScrollState scrollState) {
                    if (observableScrollViewCallbacks != null)
                        observableScrollViewCallbacks.onUpOrCancelMotionEvent(scrollState);
                }
            });

            this.setScrollOffset(webView, -lastYOffset);
        }
    }

    //endregion

    public void restoreScroll(float scroll, MaterialViewPagerSettings settings) {
        this.settings = settings;
        onMaterialScrolled(null, scroll);
    }

    public void onViewPagerPageChanged() {
        scrollDown(lastYOffset);
    }
}
