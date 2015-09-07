/*
 * Copyright 2014 Chiu-ki Chan
 * Modifications Copyright 2015 Fred Grott(GrottWorkShop)
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

package com.grottworkshop.gwsspannable.text.style;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

import com.grottworkshop.gwsspannable.R;


/**
 * RainbowSpan modified from Chiu-Ki Chan' advance-textview example and talk.
 * Created by fgrott on 9/7/2015.
 */
@SuppressWarnings("unused")
public class RainbowSpan extends CharacterStyle implements UpdateAppearance {
    private final int[] colors;

    public RainbowSpan(Context context) {
        colors = context.getResources().getIntArray(R.array.rainbow);
    }

    @Override
    public void updateDrawState(TextPaint paint) {
        paint.setStyle(Paint.Style.FILL);
        Shader shader = new LinearGradient(0, 0, 0, paint.getTextSize() * colors.length, colors, null,
                Shader.TileMode.MIRROR);
        Matrix matrix = new Matrix();
        matrix.setRotate(90);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
    }
}