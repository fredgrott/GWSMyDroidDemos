package com.grottworkshop.gwskenburnsviewdemo;

import android.os.Bundle;

import com.grottworkshop.gwskenburnsview.KenBurnsView;

/**
 * Created by fgrott on 9/1/2015.
 */
public class SingleImageActivity extends KenBurnsActivity {

    private KenBurnsView mImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image);
        mImg = (KenBurnsView) findViewById(R.id.img);


    }


    @Override
    protected void onPlayClick() {
        mImg.resume();
    }


    @Override
    protected void onPauseClick() {
        mImg.pause();
    }
}
