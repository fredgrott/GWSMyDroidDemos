/*
 * Copyright (C) 2014 Lucas Rocha
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
package com.grottworkshop.gwstwowayview.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;


import com.grottworkshop.gwstwowayview.TwoWayLayoutManager;


/**
 * Core logic for applying item vertical and horizontal spacings via item
 * offsets. Account for the item lane positions to only apply spacings within
 * the layout.
 * Created by fgrott on 9/2/2015.
 */
class ItemSpacingOffsets {
    private final int mVerticalSpacing;
    private final int mHorizontalSpacing;

    private boolean mAddSpacingAtEnd;

    private final Lanes.LaneInfo mTempLaneInfo = new Lanes.LaneInfo();

    public ItemSpacingOffsets(int verticalSpacing, int horizontalSpacing) {
        if (verticalSpacing < 0 || horizontalSpacing < 0) {
            throw new IllegalArgumentException("Spacings should be equal or greater than 0");
        }

        mVerticalSpacing = verticalSpacing;
        mHorizontalSpacing = horizontalSpacing;
    }

    /**
     * Checks whether the given position is placed just after the item in the
     * first lane of the layout taking items spans into account.
     */
    private boolean isSecondLane(BaseLayoutManager lm, int itemPosition, int lane) {
        if (lane == 0 || itemPosition == 0) {
            return false;
        }

        int previousLane = Lanes.NO_LANE;
        int previousPosition = itemPosition - 1;
        while (previousPosition >= 0) {
            lm.getLaneForPosition(mTempLaneInfo, previousPosition, TwoWayLayoutManager.Direction.END);
            previousLane = mTempLaneInfo.startLane;
            if (previousLane != lane) {
                break;
            }

            previousPosition--;
        }

        final int previousLaneSpan = lm.getLaneSpanForPosition(previousPosition);
        if (previousLane == 0) {
            return (lane == previousLane + previousLaneSpan);
        }

        return false;
    }

    /**
     * Checks whether the given position is placed at the start of a layout lane.
     */
    private static boolean isFirstChildInLane(BaseLayoutManager lm, int itemPosition) {
        final int laneCount = lm.getLanes().getCount();
        if (itemPosition >= laneCount) {
            return false;
        }

        int count = 0;
        for (int i = 0; i < itemPosition; i++) {
            count += lm.getLaneSpanForPosition(i);
            if (count >= laneCount) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks whether the given position is placed at the end of a layout lane.
     */
    private static boolean isLastChildInLane(BaseLayoutManager lm, int itemPosition, int itemCount) {
        final int laneCount = lm.getLanes().getCount();
        if (itemPosition < itemCount - laneCount) {
            return false;
        }

        // TODO: Figure out a robust way to compute this for layouts
        // that are dynamically placed and might span multiple lanes.
        if (lm instanceof SpannableGridLayoutManager ||
                lm instanceof StaggeredGridLayoutManager) {
            return false;
        }

        return true;
    }

    public void setAddSpacingAtEnd(boolean spacingAtEnd) {
        mAddSpacingAtEnd = spacingAtEnd;
    }

    /**
     * Computes the offsets based on the vertical and horizontal spacing values.
     * The spacing computation has to ensure that the lane sizes are the same after
     * applying the offsets. This means we have to shift the spacing unevenly across
     * items depending on their position in the layout.
     */
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        final BaseLayoutManager lm = (BaseLayoutManager) parent.getLayoutManager();

        lm.getLaneForPosition(mTempLaneInfo, itemPosition, TwoWayLayoutManager.Direction.END);
        final int lane = mTempLaneInfo.startLane;
        final int laneSpan = lm.getLaneSpanForPosition(itemPosition);
        final int laneCount = lm.getLanes().getCount();
        final int itemCount = parent.getAdapter().getItemCount();

        final boolean isVertical = lm.isVertical();

        final boolean firstLane = (lane == 0);
        final boolean secondLane = isSecondLane(lm, itemPosition, lane);

        final boolean lastLane = (lane + laneSpan == laneCount);
        final boolean beforeLastLane = (lane + laneSpan == laneCount - 1);

        final int laneSpacing = (isVertical ? mHorizontalSpacing : mVerticalSpacing);

        final int laneOffsetStart;
        final int laneOffsetEnd;

        if (firstLane) {
            laneOffsetStart = 0;
        } else if (lastLane && !secondLane) {
            laneOffsetStart = (int) (laneSpacing * 0.75);
        } else if (secondLane && !lastLane) {
            laneOffsetStart = (int) (laneSpacing * 0.25);
        } else {
            laneOffsetStart = (int) (laneSpacing * 0.5);
        }

        if (lastLane) {
            laneOffsetEnd = 0;
        } else if (firstLane && !beforeLastLane) {
            laneOffsetEnd = (int) (laneSpacing * 0.75);
        } else if (beforeLastLane && !firstLane) {
            laneOffsetEnd = (int) (laneSpacing * 0.25);
        } else {
            laneOffsetEnd = (int) (laneSpacing * 0.5);
        }

        final boolean isFirstInLane = isFirstChildInLane(lm, itemPosition);
        final boolean isLastInLane = !mAddSpacingAtEnd &&
                isLastChildInLane(lm, itemPosition, itemCount);

        if (isVertical) {
            outRect.left = laneOffsetStart;
            outRect.top = (isFirstInLane ? 0 : mVerticalSpacing / 2);
            outRect.right = laneOffsetEnd;
            outRect.bottom = (isLastInLane ? 0 : mVerticalSpacing / 2);
        } else {
            outRect.left = (isFirstInLane ? 0 : mHorizontalSpacing / 2);
            outRect.top = laneOffsetStart;
            outRect.right = (isLastInLane ? 0 : mHorizontalSpacing / 2);
            outRect.bottom = laneOffsetEnd;
        }
    }
}
