package com.grottworkshop.gwsmaterialfabsheetdemo.fragments;

import com.grottworkshop.gwsmaterialfabsheetdemo.R;

/**
 * Created by fgrott on 9/20/2015.
 */
public class AllFragment extends NotesListFragment {

    public static AllFragment newInstance() {
        return new AllFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_all;
    }

    @Override
    protected int getNumColumns() {
        return 2;
    }

    @Override
    protected int getNumItems() {
        return 20;
    }
}
