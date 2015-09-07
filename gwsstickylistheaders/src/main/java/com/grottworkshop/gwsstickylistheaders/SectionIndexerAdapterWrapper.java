/*
 * Copyright 2014 Emil SjÃ¶lander
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
package com.grottworkshop.gwsstickylistheaders;

import android.content.Context;
import android.widget.SectionIndexer;

/**
 * SectionIndexerAdapterWrapper class
 * Created by fgrott on 9/3/2015.
 */
class SectionIndexerAdapterWrapper extends
        AdapterWrapper implements SectionIndexer {

    SectionIndexer mSectionIndexerDelegate;

    SectionIndexerAdapterWrapper(Context context,
                                 StickyListHeadersAdapter delegate) {
        super(context, delegate);
        mSectionIndexerDelegate = (SectionIndexer) delegate;
    }

    @Override
    public int getPositionForSection(int section) {
        return mSectionIndexerDelegate.getPositionForSection(section);
    }

    @Override
    public int getSectionForPosition(int position) {
        return mSectionIndexerDelegate.getSectionForPosition(position);
    }

    @Override
    public Object[] getSections() {
        return mSectionIndexerDelegate.getSections();
    }

}
