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

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;



/**
 * A helper class for implementing {@link MaterialMenuDrawable}
 * as an {@link android.app.ActionBar} icon.
 * <p/>
 * In order to preserve default ActionBar icon click state call .
 * Otherwise, adjust your theme to disable pressed background color by setting <code>android:actionBarItemBackground</code>
 * to null and use <code>android:actionButtonStyle</code>, <code>android:actionOverflowButtonStyle</code> to enable other
 * menu icon backgrounds.
 * <p/>
 * Disables ActionBar Up arrow and replaces default drawable using {@link ActionBar#setIcon(android.graphics.drawable.Drawable)}
 * Created by fgrott on 9/14/2015.
 */
@SuppressWarnings("unused")
public abstract class MaterialMenuIcon extends MaterialMenuBase {

    public MaterialMenuIcon(Activity activity, int color, MaterialMenuDrawable.Stroke stroke) {
        super(activity, color, stroke);
    }


    public MaterialMenuIcon(Activity activity, int color, MaterialMenuDrawable.Stroke stroke, int transformDuration) {
        super(activity, color, stroke, transformDuration);
    }

    @Override
    protected View getActionBarHomeView(Activity activity) {
        Resources resources = activity.getResources();
        return activity.getWindow().getDecorView().findViewById(
                resources.getIdentifier("android:id/home", null, null)
        );
    }

    @Override
    protected View getActionBarUpView(Activity activity) {
        Resources resources = activity.getResources();
        ViewGroup actionBarView = (ViewGroup) activity.getWindow().getDecorView().findViewById(
                resources.getIdentifier("android:id/action_bar", null, null)
        );
        View homeView = actionBarView.getChildAt(
                actionBarView.getChildCount() > 1 ? 1 : 0
        );
        return homeView.findViewById(
                resources.getIdentifier("android:id/up", null, null)
        );
    }

    @Override
    protected boolean providesActionBar() {
        return true;
    }

    @Override @TargetApi(14)
    protected void setActionBarSettings(Activity activity) {
        ActionBar actionBar = activity.getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
        }
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
        }
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        if (actionBar != null) {
            actionBar.setIcon(getDrawable());
        }
    }
}
