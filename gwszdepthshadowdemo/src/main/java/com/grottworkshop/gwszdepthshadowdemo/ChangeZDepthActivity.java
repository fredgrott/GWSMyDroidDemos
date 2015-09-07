package com.grottworkshop.gwszdepthshadowdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.grottworkshop.gwszdepthshadow.ZDepth;
import com.grottworkshop.gwszdepthshadow.ZDepthShadowLayout;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fgrott on 9/7/2015.
 */
public class ChangeZDepthActivity extends AppCompatActivity {


    //{@literal @}Bind(R.id.toolBar) Toolbar mToolbar;


    ZDepthShadowLayout mZDepthShadowLayoutToolbar =(ZDepthShadowLayout) findViewById(R.id.zDepthShadowLayout_toolBar);


    ZDepthShadowLayout mZDepthShadowLayoutRect = (ZDepthShadowLayout) findViewById(R.id.zDepthShadowLayout_rect);


    ZDepthShadowLayout mZDepthShadowLayoutOval = (ZDepthShadowLayout) findViewById(R.id.zDepthShadowLayout_oval);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_zdepth);
        ButterKnife.bind(this);

        //setSupportActionBar(mToolbar);
    }

    @OnClick(R.id.button_zDepth_0)
    protected void onClickZDepth0() {
        mZDepthShadowLayoutRect.changeZDepth(ZDepth.Depth0);
        mZDepthShadowLayoutOval.changeZDepth(ZDepth.Depth0);
        mZDepthShadowLayoutToolbar.changeZDepth(ZDepth.Depth0);
    }

    @OnClick(R.id.button_zDepth_1)
    protected void onClickZDepth1() {
        mZDepthShadowLayoutRect.changeZDepth(ZDepth.Depth1);
        mZDepthShadowLayoutOval.changeZDepth(ZDepth.Depth1);
        mZDepthShadowLayoutToolbar.changeZDepth(ZDepth.Depth1);
    }

    @OnClick(R.id.button_zDepth_2)
    protected void onClickZDepth2() {
        mZDepthShadowLayoutRect.changeZDepth(ZDepth.Depth2);
        mZDepthShadowLayoutOval.changeZDepth(ZDepth.Depth2);
        mZDepthShadowLayoutToolbar.changeZDepth(ZDepth.Depth2);
    }

    @OnClick(R.id.button_zDepth_3)
    protected void onClickZDepth3() {
        mZDepthShadowLayoutRect.changeZDepth(ZDepth.Depth3);
        mZDepthShadowLayoutOval.changeZDepth(ZDepth.Depth3);
        mZDepthShadowLayoutToolbar.changeZDepth(ZDepth.Depth3);
    }

    @OnClick(R.id.button_zDepth_4)
    protected void onClickZDepth4() {
        mZDepthShadowLayoutRect.changeZDepth(ZDepth.Depth4);
        mZDepthShadowLayoutOval.changeZDepth(ZDepth.Depth4);
        mZDepthShadowLayoutToolbar.changeZDepth(ZDepth.Depth4);
    }

    @OnClick(R.id.button_zDepth_5)
    protected void onClickZDepth5() {
        mZDepthShadowLayoutRect.changeZDepth(ZDepth.Depth5);
        mZDepthShadowLayoutOval.changeZDepth(ZDepth.Depth5);
        mZDepthShadowLayoutToolbar.changeZDepth(ZDepth.Depth5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
