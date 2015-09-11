package com.grottworkshop.gwskenburnsviewdemo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by fgrott on 9/1/2015.
 */
public class MainActivity extends ListActivity {

    private static final int POS_SINGLE_IMG = 0;
    private static final int POS_MULTI_IMG = 1;
    private static final int POS_FROM_URL = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.main_options, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);

    }


    //@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //not needed as no super fromListActivity as we do not use ListActivity
        //super.onListItemClick(l, v, position, id);
        switch (position) {
            case POS_SINGLE_IMG:
                startActivity(new Intent(this, SingleImageActivity.class));
                break;
            case POS_MULTI_IMG:
                startActivity(new Intent(this, MultiImageActivity.class));
                break;

        }
    }

}
