package com.grottworkshop.gwsmaterialfabsheet;

/**
 * Interface for FABs that support a set of animations.
 * Created by fgrott on 9/12/2015.
 */
@SuppressWarnings("unused")
public interface AnimatedFab {

    /**
     * Shows the FAB.
     */
    void show();

    /**
     * Shows the FAB and sets the FAB's translation.
     *
     * @param translationX translation X value
     * @param translationY translation Y value
     */
    void show(float translationX, float translationY);

    /**
     * Hides the FAB.
     */
    void hide();

}
