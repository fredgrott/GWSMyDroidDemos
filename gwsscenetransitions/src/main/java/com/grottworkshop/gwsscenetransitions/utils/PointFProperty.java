package com.grottworkshop.gwsscenetransitions.utils;

import android.graphics.PointF;
import android.util.Property;

/**
 * PointFProperty class
 * Created by fgrott on 8/25/2015.
 */
public abstract class PointFProperty<T> extends Property<T, PointF> {

    public PointFProperty(String name) {
        super(PointF.class, name);
    }

    @Override
    public PointF get(T object) {
        return null;
    }

}
