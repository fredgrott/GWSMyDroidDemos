/*
 * Copyright 2014 Niek Haarman
 * Modifications Copyright (C) Fred Grott(GrottWorkShop)
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
 */
package com.grottworkshop.gwslistviewanimations.appearance.simple;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.grottworkshop.gwslistviewanimations.appearance.SingleAnimationAdapter;

/**
 * An implementation of the AnimationAdapter class which applies a
 * swing-in-from-the-right-animation to views.
 * Created by fgrott on 9/2/2015.
 */
public class SwingRightInAnimationAdapter extends SingleAnimationAdapter {

    private static final String TRANSLATION_X = "translationX";

    public SwingRightInAnimationAdapter(@NonNull final BaseAdapter baseAdapter) {
        super(baseAdapter);
    }

    @NonNull
    @Override
    protected Animator getAnimator(@NonNull final ViewGroup parent, @NonNull final View view) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_X, parent.getWidth(), 0);
    }
}
