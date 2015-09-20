package com.grottworkshop.gwswizardpager.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.grottworkshop.gwswizardpager.R;
import com.grottworkshop.gwswizardpager.model.Page;
import com.grottworkshop.gwswizardpager.model.SingleTextFieldPage;

/**
 * SingleTextFieldFragment class
 * Created by fgrott on 8/30/2015.
 */
public class SingleTextFieldFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private SingleTextFieldPage mPage;
    private TextView mFieldView;

    public static SingleTextFieldFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        SingleTextFieldFragment fragment = new SingleTextFieldFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SingleTextFieldFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (SingleTextFieldPage) mCallbacks.onGetPage(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_single_text_field, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());
        ((TextView) rootView.findViewById(R.id.wizard_text_field_desc)).setText(mPage.getDesc());

        mFieldView = ((TextView) rootView.findViewById(R.id.wizard_text_field));
        mFieldView.setHint(mPage.getTitle());
        mFieldView.setText(mPage.getData().getString(Page.SIMPLE_DATA_KEY));
        return rootView;
    }

    //TODO: onAttach depreciated
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFieldView.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                                          int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(Page.SIMPLE_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        // In a future update to the support library, this should override setUserVisibleHint
        // instead of setMenuVisibility.
        if (mFieldView != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (!menuVisible) {
                //TODO: getView().getWindowToken() may produce null exception
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }
}
