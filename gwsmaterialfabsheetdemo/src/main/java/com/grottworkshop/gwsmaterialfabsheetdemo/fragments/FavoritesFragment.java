package com.grottworkshop.gwsmaterialfabsheetdemo.fragments;

import com.grottworkshop.gwsmaterialfabsheetdemo.R;

/**
 * Created by fgrott on 9/20/2015.
 */
public class FavoritesFragment extends NotesListFragment {

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_favorites;
    }

    @Override
    protected int getNumColumns() {
        return 1;
    }

    @Override
    protected int getNumItems() {
        return 7;
    }
}
