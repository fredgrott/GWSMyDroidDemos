package com.grottworkshop.gwszdepthshadowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by fgrott on 9/7/2015.
 */
public class MainActivity extends AppCompatActivity {



    ListView mListView =(ListView) findViewById(R.id.listView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                new String[]{
                        "Simple ZDepthShadowLayout",
                        "Change ZDepth",

                        "Sample FloatingActionMenu"
                }));
    }

    @OnItemClick(R.id.listView)
    protected void onItemClick(int position) {
        Intent intent = null;

        switch (position) {
            case 0:
                intent = new Intent(this, SimpleZDepthActivity.class);
                break;

            case 1:
                intent = new Intent(this, ChangeZDepthActivity.class);
                break;



            case 2:
                intent = new Intent(this, SampleFloatingActionMenuActivity.class);
                break;

            default:
                return;
        }

        startActivity(intent);
    }

}

