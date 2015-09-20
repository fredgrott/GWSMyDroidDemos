package com.grottworkshop.gwsmaterialviewpagerregdemo.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grottworkshop.gwsmaterialviewpagerreg.MaterialViewPagerHelper;
import com.grottworkshop.gwsmaterialviewpagerregdemo.R;
import com.grottworkshop.gwsobservablescroll.ObservableScrollView;

/**
 * Created by fgrott on 9/13/2015.
 */
public class ScrollFragment extends Fragment {

    private ObservableScrollView mScrollView;

    public static ScrollFragment newInstance() {
        return new ScrollFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scroll, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);

        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
    }
}
