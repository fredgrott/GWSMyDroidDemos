package com.grottworkshop.gwsmaterialmenudemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.grottworkshop.gwsmaterialmenu.MaterialMenuDrawable;
import com.grottworkshop.gwsmaterialmenu.MaterialMenuIconToolbar;

import static com.grottworkshop.gwsmaterialmenudemo.BaseActivityHelper.generateState;
import static com.grottworkshop.gwsmaterialmenudemo.BaseActivityHelper.intToState;


/**
 * Created by fgrott on 9/20/2015.
 */
public class ToolbarActivity extends AppCompatActivity {

    private MaterialMenuIconToolbar materialMenu;

    private int actionBarMenuState;

    protected BaseActivityHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // random state
                actionBarMenuState = generateState(actionBarMenuState);
                materialMenu.animatePressedState(intToState(actionBarMenuState));
            }
        });
        materialMenu = new MaterialMenuIconToolbar(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN) {
            @Override public int getToolbarViewId() {
                return R.id.toolbar;
            }
        };
        helper = new BaseActivityHelper();
        helper.init(getWindow().getDecorView(), materialMenu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        helper.refreshDrawerState();
        materialMenu.syncState(savedInstanceState);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        materialMenu.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}
