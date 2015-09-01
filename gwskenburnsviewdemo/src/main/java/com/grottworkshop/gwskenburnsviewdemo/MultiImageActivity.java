package com.grottworkshop.gwskenburnsviewdemo;

import android.os.Bundle;
import android.widget.ViewSwitcher;

import com.grottworkshop.gwskenburnsview.KenBurnsView;
import com.grottworkshop.gwskenburnsview.Transition;

/**
 * Created by fgrott on 9/1/2015.
 */
public class MultiImageActivity extends KenBurnsActivity implements KenBurnsView.TransitionListener {

    private static final int TRANSITIONS_TO_SWITCH = 3;

    private ViewSwitcher mViewSwitcher;

    private int mTransitionsCount = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_image);

        mViewSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);

        KenBurnsView img1 = (KenBurnsView) findViewById(R.id.img1);
        img1.setTransitionListener(this);

        KenBurnsView img2 = (KenBurnsView) findViewById(R.id.img2);
        img2.setTransitionListener(this);
    }


    @Override
    public void onTransitionStart(Transition transition) {

    }


    @Override
    public void onTransitionEnd(Transition transition) {
        mTransitionsCount++;
        if (mTransitionsCount == TRANSITIONS_TO_SWITCH) {
            mViewSwitcher.showNext();
            mTransitionsCount = 0;
        }
    }


    @Override
    protected void onPlayClick() {
        KenBurnsView currentImage = (KenBurnsView) mViewSwitcher.getCurrentView();
        currentImage.resume();
    }


    @Override
    protected void onPauseClick() {
        KenBurnsView currentImage = (KenBurnsView) mViewSwitcher.getCurrentView();
        currentImage.pause();
    }
}
