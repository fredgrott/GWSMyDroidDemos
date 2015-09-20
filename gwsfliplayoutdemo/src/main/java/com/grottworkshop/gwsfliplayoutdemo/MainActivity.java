package com.grottworkshop.gwsfliplayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

/**
 * Created by fgrott on 9/20/2015.
 */
public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(new FlipAdapter(MainActivity.this));
    }

}
