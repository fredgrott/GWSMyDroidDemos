/*
 * Copyright (C) 2013 The Android Open Source Project
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
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
package com.grottworkshop.gwsscenetransitions;

import android.util.SparseArray;
import android.view.View;

import com.grottworkshop.gwsscenetransitions.utils.ArrayMap;
import com.grottworkshop.gwsscenetransitions.utils.LongSparseArray;


/**
 * Created by fgrott on 8/25/2015.
 */
class TransitionValuesMaps {
    ArrayMap<View, TransitionValues> viewValues =
            new ArrayMap<>();
    SparseArray<View> idValues = new SparseArray<View>();
    LongSparseArray<View> itemIdValues = new LongSparseArray<View>();
    ArrayMap<String, View> nameValues = new ArrayMap<String, View>();
}
