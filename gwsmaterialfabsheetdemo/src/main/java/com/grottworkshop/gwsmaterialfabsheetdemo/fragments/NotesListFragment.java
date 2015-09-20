package com.grottworkshop.gwsmaterialfabsheetdemo.fragments;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grottworkshop.gwsmaterialfabsheetdemo.R;
import com.grottworkshop.gwsmaterialfabsheetdemo.adapters.NotesAdapter;


/**
 * Created by fgrott on 9/20/2015.
 */
public abstract class NotesListFragment extends Fragment {

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract int getNumColumns();

    protected abstract int getNumItems();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);

        // Setup list
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getNumColumns(),
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new NotesAdapter(getActivity(), getNumItems()));

        return view;
    }

}
