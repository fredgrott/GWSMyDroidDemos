/*
 * Copyright 2014 Jacob Tabak - Timehop
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
 *
 */
package com.grottworkshop.gwsstickyheadersrecyclerview.rendering;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.grottworkshop.gwsstickyheadersrecyclerview.calculation.DimensionCalculator;
import com.grottworkshop.gwsstickyheadersrecyclerview.util.OrientationProvider;

/**
 * Responsible for drawing headers to the canvas provided by the item decoration
 * Created by fgrott on 9/3/2015.
 */
public class HeaderRenderer {

    private final DimensionCalculator mDimensionCalculator;
    private final OrientationProvider mOrientationProvider;

    public HeaderRenderer(OrientationProvider orientationProvider) {
        this(orientationProvider, new DimensionCalculator());
    }

    private HeaderRenderer(OrientationProvider orientationProvider,
                           DimensionCalculator dimensionCalculator) {
        mOrientationProvider = orientationProvider;
        mDimensionCalculator = dimensionCalculator;
    }

    /**
     * Draws a header to a canvas, offsetting by some x and y amount
     *
     * @param recyclerView the parent recycler view for drawing the header into
     * @param canvas       the canvas on which to draw the header
     * @param header       the view to draw as the header
     * @param offset       a Rect used to define the x/y offset of the header. Specify x/y offset by setting
     *                     the {@link Rect#left} and {@link Rect#top} properties, respectively.
     */
    public void drawHeader(RecyclerView recyclerView, Canvas canvas, View header, Rect offset) {
        canvas.save();

        if (recyclerView.getLayoutManager().getClipToPadding()) {
            // Clip drawing of headers to the padding of the RecyclerView. Avoids drawing in the padding
            Rect clipRect = getClipRectForHeader(recyclerView, header);
            canvas.clipRect(clipRect);
        }

        canvas.translate(offset.left, offset.top);

        header.draw(canvas);
        canvas.restore();
    }

    /**
     * Gets a clipping rect for the header based on the margins of the header and the padding of the
     * recycler.
     * FIXME: Currently right margin in VERTICAL orientation and bottom margin in HORIZONTAL
     * orientation are clipped so they look accurate, but the headers are not being drawn at the
     * correctly smaller width and height respectively.
     *
     * @param recyclerView for which to provide a header
     * @param header       for clipping
     * @return a {@link Rect} for clipping a provided header to the padding of a recycler view
     */
    private Rect getClipRectForHeader(RecyclerView recyclerView, View header) {
        Rect headerMargins = mDimensionCalculator.getMargins(header);
        if (mOrientationProvider.getOrientation(recyclerView) == LinearLayout.VERTICAL) {
            return new Rect(
                    recyclerView.getPaddingLeft(),
                    recyclerView.getPaddingTop(),
                    recyclerView.getWidth() - recyclerView.getPaddingRight() - headerMargins.right,
                    recyclerView.getHeight() - recyclerView.getPaddingBottom());
        } else {
            return new Rect(
                    recyclerView.getPaddingLeft(),
                    recyclerView.getPaddingTop(),
                    recyclerView.getWidth() - recyclerView.getPaddingRight(),
                    recyclerView.getHeight() - recyclerView.getPaddingBottom() - headerMargins.bottom);
        }
    }

}
