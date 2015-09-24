package com.grottworkshop.gwsbase;

import android.os.Bundle;

/**
 * BasedSavedState, since app class is already a singleton and we will only have one
 * AppCompatActivity class and since we are only annotating POJOs no need to officially
 * make this a singleton.
 * Created by fgrott on 9/24/2015.
 */
@SuppressWarnings("unused")
public class BaseSavedState {

    /**
     * Over-ride with this in the body
     *
     * <code>
     *
     *     InstanceStateManager.saveInstanceState(ourClass, outState);
     * </code>
     *
     * Each class with an instanceState field annotated gets an instanceStateManager
     * call. Than in AppCompatActivity onSaveInstance method
     *
     * <code>
     *     Bundle ourBundle = new Bundle();
     *     ExtendedBaseSavedState.doSaveState(ourBundle);
     * </code>
     *
     * @param outState the outSate
     */
    public void doSaveState(Bundle outState){

    }

    /**
     * Over-ride with this in the body
     *
     * <code>
     *     InstanceStateManager.restoreInstanceState(ourClass, savedInstanceState);
     * </code>
     *
     * A call for each class that has an InstanceField annotated, than in the
     * AppCompatActivity onRestoreState method do this call:
     *
     * <code>
     *     ExtendedBaseSavedState.doRestoreState(savedInstanceState);
     * </code>
     * @param savedInstanceState the savedInstanceState
     */
    public void doRestoreState(Bundle savedInstanceState){

    }
}
