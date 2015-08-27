/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 daimajia
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.grottworkshop.gwseasing;

import com.grottworkshop.gwseasing.back.BackEaseIn;
import com.grottworkshop.gwseasing.back.BackEaseInOut;
import com.grottworkshop.gwseasing.back.BackEaseOut;
import com.grottworkshop.gwseasing.bounce.BounceEaseIn;
import com.grottworkshop.gwseasing.bounce.BounceEaseInOut;
import com.grottworkshop.gwseasing.bounce.BounceEaseOut;
import com.grottworkshop.gwseasing.circ.CircEaseIn;
import com.grottworkshop.gwseasing.circ.CircEaseInOut;
import com.grottworkshop.gwseasing.circ.CircEaseOut;
import com.grottworkshop.gwseasing.cubic.CubicEaseIn;
import com.grottworkshop.gwseasing.cubic.CubicEaseInOut;
import com.grottworkshop.gwseasing.cubic.CubicEaseOut;
import com.grottworkshop.gwseasing.elastic.ElasticEaseIn;
import com.grottworkshop.gwseasing.elastic.ElasticEaseInOut;
import com.grottworkshop.gwseasing.elastic.ElasticEaseOut;
import com.grottworkshop.gwseasing.expo.ExpoEaseIn;
import com.grottworkshop.gwseasing.expo.ExpoEaseInOut;
import com.grottworkshop.gwseasing.expo.ExpoEaseOut;
import com.grottworkshop.gwseasing.linear.Linear;
import com.grottworkshop.gwseasing.quad.QuadEaseIn;
import com.grottworkshop.gwseasing.quad.QuadEaseInOut;
import com.grottworkshop.gwseasing.quad.QuadEaseOut;
import com.grottworkshop.gwseasing.quint.QuintEaseIn;
import com.grottworkshop.gwseasing.quint.QuintEaseInOut;
import com.grottworkshop.gwseasing.quint.QuintEaseOut;
import com.grottworkshop.gwseasing.sine.SineEaseIn;
import com.grottworkshop.gwseasing.sine.SineEaseInOut;
import com.grottworkshop.gwseasing.sine.SineEaseOut;

/**
 * Created by fgrott on 8/26/2015.
 */
public enum  Skill {

    BackEaseIn(BackEaseIn.class),
    BackEaseOut(BackEaseOut.class),
    BackEaseInOut(BackEaseInOut.class),

    BounceEaseIn(BounceEaseIn.class),
    BounceEaseOut(BounceEaseOut.class),
    BounceEaseInOut(BounceEaseInOut.class),

    CircEaseIn(CircEaseIn.class),
    CircEaseOut(CircEaseOut.class),
    CircEaseInOut(CircEaseInOut.class),

    CubicEaseIn(CubicEaseIn.class),
    CubicEaseOut(CubicEaseOut.class),
    CubicEaseInOut(CubicEaseInOut.class),

    ElasticEaseIn(ElasticEaseIn.class),
    ElasticEaseInOut(ElasticEaseInOut.class),
    ElasticEaseOut(ElasticEaseOut.class),

    ExpoEaseIn(ExpoEaseIn.class),
    ExpoEaseOut(ExpoEaseOut.class),
    ExpoEaseInOut(ExpoEaseInOut.class),

    QuadEaseIn(QuadEaseIn.class),
    QuadEaseOut(QuadEaseOut.class),
    QuadEaseInOut(QuadEaseInOut.class),

    QuintEaseIn(QuintEaseIn.class),
    QuintEaseOut(QuintEaseOut.class),
    QuintEaseInOut(QuintEaseInOut.class),

    SineEaseIn(SineEaseIn.class),
    SineEaseOut(SineEaseOut.class),
    SineEaseInOut(SineEaseInOut.class),

    Linear(Linear.class);


    private Class easingMethod;

    private Skill(Class clazz) {
        easingMethod = clazz;
    }

    public BaseEasingMethod getMethod(float duration) {
        try {
            return (BaseEasingMethod)easingMethod.getConstructor(float.class).newInstance(duration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("Can not init easingMethod instance");
        }
    }
}
