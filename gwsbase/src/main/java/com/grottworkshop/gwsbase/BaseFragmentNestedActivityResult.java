package com.grottworkshop.gwsbase;

import android.app.Fragment;
import android.content.Intent;

import com.grottworkshop.gwsbase.bus.ActivityResultEvent;
import com.squareup.otto.Subscribe;

/**
 * BaseFragmentNestedActivityResult class
 * Created by fgrott on 9/17/2015.
 */
@SuppressWarnings("unused")
public class BaseFragmentNestedActivityResult extends Fragment {


    private Object mActivityResultSubscriber = new Object() {
        @Subscribe
        public void onActivityResultReceived(ActivityResultEvent event) {
            int requestCode = event.getRequestCode();
            int resultCode = event.getResultCode();
            Intent data = event.getData();
            onActivityResult(requestCode, resultCode, data);
        }
    };
}
