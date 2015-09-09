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
package com.grottworkshop.gwswizardpager.model;

import android.app.Fragment;

import com.grottworkshop.gwswizardpager.ui.GeoFragment;

/**
 * GeoPage class
 * Created by fgrott on 8/30/2015.
 */
@SuppressWarnings("unused")
public class GeoPage extends TextPage {

    public GeoPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return GeoFragment.create(getKey());
    }

    public GeoPage setValue(String value) {
        mData.putString(SIMPLE_DATA_KEY, value);
        return this;
    }
}
