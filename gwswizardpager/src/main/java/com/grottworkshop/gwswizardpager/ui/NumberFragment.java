/*
 * Copyright 2012 Roman Nurik
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.grottworkshop.gwswizardpager.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;

/**
 * NumberFragment class
 * Created by fgrott on 8/30/2015.
 */
public class NumberFragment extends TextFragment {
    public static NumberFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        NumberFragment f = new NumberFragment();
        f.setArguments(args);
        return f;
    }

    @SuppressLint("InlinedApi")
    @Override
    protected void setInputType() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            mEditTextInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            mEditTextInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
    }

}