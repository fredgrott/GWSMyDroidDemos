/*
 * Copyright (C) 2014 Balys Valentukevicius
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.grottworkshop.gwsmaterialmenu;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * MaterialMenuIconCompat class
 * Created by fgrott on 9/14/2015.
 */
@SuppressWarnings("unused")
public class MaterialMenuIconCompat extends MaterialMenuBase {

    public MaterialMenuIconCompat(AppCompatActivity activity, int color, MaterialMenuDrawable.Stroke stroke) {
        super(activity, color, stroke);
    }

    public MaterialMenuIconCompat(AppCompatActivity activity, int color, MaterialMenuDrawable.Stroke stroke, int transformDuration) {
        super(activity, color, stroke, transformDuration);
    }

    @Override
    protected View getActionBarHomeView(Activity activity) {
        return null;
    }

    @Override
    protected View getActionBarUpView(Activity activity) {
        return null;
    }

    @Override
    protected boolean providesActionBar() {
        return false;
    }

    @Override
    protected void setActionBarSettings(Activity activity) {
        ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(getDrawable());
        }

    }
}