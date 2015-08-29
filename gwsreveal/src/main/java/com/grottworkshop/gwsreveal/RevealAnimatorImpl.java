package com.grottworkshop.gwsreveal;

/**
 * Created by fgrott on 8/28/2015.
 */
abstract class RevealAnimatorImpl implements RevealAnimator {
    protected static final String TAG = "RevealAnimatorImpl";
    protected final ViewRevealAnimator parent;

    RevealAnimatorImpl(final ViewRevealAnimator animator) {
        this.parent = animator;
    }

}
