package com.grottworkshop.gwslistviewanimations.itemmanipulation.dragdrop;

import com.grottworkshop.gwslistviewanimations.itemmanipulation.DynamicListView;

/**
 * An interface which provides a callback that is called when an item has moved using
 * the {@link DynamicListView}.
 * Created by fgrott on 9/2/2015.
 */
public interface OnItemMovedListener {

    /**
     * Called when an item that was dragged has been dropped.
     *
     * @param originalPosition the original position of the item that was dragged.
     * @param newPosition the new position of the item that was dragged.
     */
    void onItemMoved(int originalPosition, int newPosition);
}