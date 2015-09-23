/**
 * Copyright (C) 2015 Alex Vasilkov
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.grottworkshop.gwsutil.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


/**
 * Views class
 * Created by fgrott on 9/23/2015.
 */
@SuppressWarnings("unused")
public final class Views {

    @SuppressWarnings("unchecked")
    public static <T extends View> T find(View parent, int viewId) {
        return (T) parent.findViewById(viewId);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T find(Activity activity, int viewId) {
        return (T) activity.findViewById(viewId);
    }

    @SuppressWarnings("unchecked")
    public static <T extends ViewGroup.LayoutParams> T getParams(View view) {
        return (T) view.getLayoutParams();
    }

    public static ViewGroup.MarginLayoutParams getMarginParams(View view) {
        return getParams(view);
    }

    public static FrameLayout.LayoutParams getFrameParams(View view) {
        return getParams(view);
    }

    public static RelativeLayout.LayoutParams getRelativeParams(View view) {
        return getParams(view);
    }

    public static LinearLayout.LayoutParams getLinearParams(View view) {
        return getParams(view);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T inflate(Context context, int layoutId) {
        return (T) LayoutInflater.from(context).inflate(layoutId, null, false);
    }

    public static <T extends View> T inflate(View root, int layoutId) {
        return inflateInternal(root, layoutId, false);
    }

    public static <T extends View> T inflateAndAttach(View root, int layoutId) {
        return inflateInternal(root, layoutId, true);
    }

    @SuppressWarnings("unchecked")
    private static <T extends View> T inflateInternal(View root, int layoutId, boolean attach) {
        if (root == null) throw new NullPointerException("Root view cannot be null");
        return (T) LayoutInflater.from(root.getContext()).inflate(layoutId, (ViewGroup) root, attach);
    }

    private Views() {
    }

}
