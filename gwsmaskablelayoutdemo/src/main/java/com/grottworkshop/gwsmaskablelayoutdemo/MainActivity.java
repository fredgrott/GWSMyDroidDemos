package com.grottworkshop.gwsmaskablelayoutdemo;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.grottworkshop.gwsmaskablelayout.MaskableFrameLayout;

/**
 * Created by fgrott on 9/1/2015.
 */
public class MainActivity extends AppCompatActivity {

    private MaskableFrameLayout mMaskableFrameLayout;
    private Button mBtnAnimate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMaskableFrameLayout = (MaskableFrameLayout) findViewById(R.id.frm_mask_animated);
        mBtnAnimate = (Button) findViewById(R.id.btn_animate);
        mBtnAnimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animate();
            }
        });
    }

    private void animate() {
        Drawable drawable = mMaskableFrameLayout.getDrawableMask();
        if (drawable instanceof AnimationDrawable) {
            AnimationDrawable animDrawable = (AnimationDrawable) drawable;
            animDrawable.selectDrawable(0);
            animDrawable.stop();
            animDrawable.start();
        }
    }

}
