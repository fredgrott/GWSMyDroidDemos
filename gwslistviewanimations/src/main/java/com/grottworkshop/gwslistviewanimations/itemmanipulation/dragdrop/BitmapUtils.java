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
package com.grottworkshop.gwslistviewanimations.itemmanipulation.dragdrop;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by fgrott on 9/2/2015.
 */
class BitmapUtils {

    private BitmapUtils() {
    }

    /**
     * Returns a bitmap showing a screenshot of the view passed in.
     */
    @NonNull
    static Bitmap getBitmapFromView(@NonNull final View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

}
