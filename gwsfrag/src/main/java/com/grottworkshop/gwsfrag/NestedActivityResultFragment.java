package com.grottworkshop.gwsfrag;

import android.app.Fragment;
import android.content.Intent;

import com.grottworkshop.gwsfrag.bus.ActivityResultBus;
import com.grottworkshop.gwsfrag.bus.ActivityResultEvent;
import com.squareup.otto.Subscribe;

/**
 * NestedActivityResultFragment class
 * Usage:
 *
 * in your main activity
 *
 * <code>
 *     @Override
 *     protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
 *           super.onActivityResult(requestCode, resultCode, data);
 *           ActivityResultBus.getInstance().postQueue(new ActivityResultEvent(requestCode, resultCode, data));
 *     }
 *
 * </code>
 *
 * and in the fragment call
 * <code>
 *     getActivity().startActivityForResult(...)
 * </code>
 *
 * Lastly, override `onActivityResult` in your fragment in the standard way.
 *
 * <code>
 *     public class MainFragment extends NestedActivityResultFragment {
 *
 *           @Override
 *           public void onActivityResult(int requestCode, int resultCode, Intent data) {
 *                super.onActivityResult(requestCode, resultCode, data);
 *                // Add your code here
 *                Toast.makeText(getActivity(), "Fragment Got it: " + requestCode + ", " + resultCode, Toast.LENGTH_SHORT).show();
 *           }
 *
 *      }
 *
 * </code>
 * Created by fgrott on 9/12/2015.
 */
@SuppressWarnings("unused")
public class NestedActivityResultFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();
        ActivityResultBus.getInstance().register(mActivityResultSubscriber);
    }

    @Override
    public void onStop() {
        super.onStop();
        ActivityResultBus.getInstance().unregister(mActivityResultSubscriber);
    }

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
