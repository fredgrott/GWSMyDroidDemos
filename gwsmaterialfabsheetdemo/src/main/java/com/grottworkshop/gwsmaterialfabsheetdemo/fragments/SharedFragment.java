package com.grottworkshop.gwsmaterialfabsheetdemo.fragments;

import com.grottworkshop.gwsmaterialfabsheetdemo.R;

/**
 * Created by fgrott on 9/20/2015.
 */
public class SharedFragment extends NotesListFragment {

    public static SharedFragment newInstance() {
        return new SharedFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_shared;
    }

    @Override
    protected int getNumColumns() {
        return 2;
    }

    @Override
    protected int getNumItems() {
        return 10;
    }
}
