package com.grottworkshop.gwsmaterialmenudemo;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.SeekBar;

import com.grottworkshop.gwsmaterialmenu.MaterialMenu;
import com.grottworkshop.gwsmaterialmenu.MaterialMenuDrawable;
import com.grottworkshop.gwsmaterialmenu.MaterialMenuView;

import java.util.Random;



/**
 * Created by fgrott on 9/20/2015.
 */
public class BaseActivityHelper implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private MaterialMenuView materialMenuView;
    private int              materialButtonState;
    private MaterialMenu materialIcon;
    private DrawerLayout     drawerLayout;
    private boolean          direction;

    public void init(View parent, MaterialMenu actionBarIcon) {
        SeekBar duration = (SeekBar) parent.findViewById(R.id.item_animation_duration);
        duration.setMax(4600);
        duration.setProgress(2600);
        duration.setOnSeekBarChangeListener(this);

        materialMenuView = (MaterialMenuView) parent.findViewById(R.id.material_menu_button);
        materialMenuView.setOnClickListener(this);
        materialIcon = actionBarIcon;

        parent.findViewById(R.id.switch_item_arrow).setOnClickListener(this);
        parent.findViewById(R.id.switch_item_menu).setOnClickListener(this);
        parent.findViewById(R.id.switch_item_x).setOnClickListener(this);
        parent.findViewById(R.id.switch_item_check).setOnClickListener(this);
        parent.findViewById(R.id.switch_item_show).setOnClickListener(this);
        parent.findViewById(R.id.switch_item_hide).setOnClickListener(this);
        parent.findViewById(R.id.animate_item_arrow).setOnClickListener(this);
        parent.findViewById(R.id.animate_item_menu).setOnClickListener(this);
        parent.findViewById(R.id.animate_item_x).setOnClickListener(this);
        parent.findViewById(R.id.animate_item_check).setOnClickListener(this);

        drawerLayout = ((DrawerLayout) parent.findViewById(R.id.drawer_layout));
        drawerLayout.setScrimColor(Color.parseColor("#66000000"));
        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                materialIcon.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        direction ? 2 - slideOffset : slideOffset
                );
            }

            @Override
            public void onDrawerOpened(android.view.View drawerView) {
                direction = true;
            }

            @Override
            public void onDrawerClosed(android.view.View drawerView) {
                direction = false;
            }
        });

        drawerLayout.postDelayed(new Runnable() {
            @Override public void run() {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        }, 1500);
    }

    @Override public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.animate_item_menu:
                materialMenuView.animateState(MaterialMenuDrawable.IconState.BURGER);
                break;
            case R.id.animate_item_arrow:
                materialMenuView.animateState(MaterialMenuDrawable.IconState.ARROW);
                break;
            case R.id.animate_item_x:
                materialMenuView.animateState(MaterialMenuDrawable.IconState.X);
                break;
            case R.id.animate_item_check:
                materialMenuView.animateState(MaterialMenuDrawable.IconState.CHECK);
                break;
            case R.id.switch_item_menu:
                materialMenuView.setState(MaterialMenuDrawable.IconState.BURGER);
                break;
            case R.id.switch_item_arrow:
                materialMenuView.setState(MaterialMenuDrawable.IconState.ARROW);
                break;
            case R.id.switch_item_x:
                materialMenuView.setState(MaterialMenuDrawable.IconState.X);
                break;
            case R.id.switch_item_check:
                materialMenuView.setState(MaterialMenuDrawable.IconState.CHECK);
                break;
            case R.id.switch_item_show:
                materialMenuView.setVisible(true);
                break;
            case R.id.switch_item_hide:
                materialMenuView.setVisible(false);
                break;
            case R.id.material_menu_button:
                setMainState();
                break;
        }
    }

    @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        materialMenuView.setTransformationDuration(400 + progress);
    }

    @Override public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private void setMainState() {
        materialButtonState = generateState(materialButtonState);
        materialMenuView.animatePressedState(intToState(materialButtonState));
    }

    @SuppressWarnings("ResourceType")
    public void refreshDrawerState() {
        this.direction = drawerLayout.isDrawerOpen(Gravity.START);
    }

    public static int generateState(int previous) {
        int generated = new Random().nextInt(4);
        return generated != previous ? generated : generateState(previous);
    }

    public static MaterialMenuDrawable.IconState intToState(int state) {
        switch (state) {
            case 0:
                return MaterialMenuDrawable.IconState.BURGER;
            case 1:
                return MaterialMenuDrawable.IconState.ARROW;
            case 2:
                return MaterialMenuDrawable.IconState.X;
            case 3:
                return MaterialMenuDrawable.IconState.CHECK;
        }
        throw new IllegalArgumentException("Must be a number [0,3)");
    }
}
