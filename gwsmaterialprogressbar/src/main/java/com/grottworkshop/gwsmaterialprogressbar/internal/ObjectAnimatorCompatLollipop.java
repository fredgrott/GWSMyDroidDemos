/*
 * Copyright 2015 Zhang Hai
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.grottworkshop.gwsmaterialprogressbar.internal;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Path;
import android.os.Build;
import android.util.Property;

/**
 * Created by fgrott on 8/30/2015.
 */
class ObjectAnimatorCompatLollipop {

    private ObjectAnimatorCompatLollipop() {}

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static ObjectAnimator ofArgb(Object target, String propertyName, int... values) {
        return ObjectAnimator.ofArgb(target, propertyName, values);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static <T> ObjectAnimator ofArgb(T target, Property<T, Integer> property,
                                            int... values) {
        return ObjectAnimator.ofArgb(target, property, values);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static ObjectAnimator ofFloat(Object target, String xPropertyName, String yPropertyName,
                                         Path path) {
        return ObjectAnimator.ofFloat(target, xPropertyName, yPropertyName, path);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static <T> ObjectAnimator ofFloat(Object target, String xProperty,
                                             int yProperty, float path) {
        return ObjectAnimator.ofFloat(target, xProperty, yProperty, path);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static ObjectAnimator ofInt(Object target, String xPropertyName, String yPropertyName,
                                       Path path) {
        return ObjectAnimator.ofInt(target, xPropertyName, yPropertyName, path);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static <T> ObjectAnimator ofInt(T target, Property<T, Integer> xProperty,
                                           Property<T, Integer> yProperty, Path path) {
        return ObjectAnimator.ofInt(target, xProperty, yProperty, path);
    }
}
