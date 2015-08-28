/*
 * Copyright (C) 2011 The Android Open Source Project
 * Modifications Copyright (C) 2105 Fred Grott(GrottWorkShop)
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
package com.grottworkshop.gwschips;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


/**
 * Created by fgrott on 8/28/2015.
 */
class SingleRecipientArrayAdapter extends ArrayAdapter<RecipientEntry> {
    private final DropdownChipLayouter mDropdownChipLayouter;

    public SingleRecipientArrayAdapter(Context context, RecipientEntry entry,
                                       DropdownChipLayouter dropdownChipLayouter) {
        super(context, dropdownChipLayouter.getAlternateItemLayoutResId(), new RecipientEntry[] {
                entry
        });

        mDropdownChipLayouter = dropdownChipLayouter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return mDropdownChipLayouter.bindView(convertView, parent, getItem(position), position,
                DropdownChipLayouter.AdapterType.SINGLE_RECIPIENT, null);
    }
}
