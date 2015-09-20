package com.grottworkshop.gwsmaterialprogressbardemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.grottworkshop.gwsmaterialprogressbar.HorizontalProgressDrawable;
import com.grottworkshop.gwsmaterialprogressbar.IndeterminateHorizontalProgressDrawable;
import com.grottworkshop.gwsmaterialprogressbar.IndeterminateProgressDrawable;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by fgrott on 9/20/2015.
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.horizontal_progress_library)
    ProgressBar horizontalProgress;
    @Bind(R.id.indeterminate_horizontal_progress_library)
    ProgressBar indeterminateHorizontalProgress;
    @Bind(R.id.indeterminate_progress_large_library)
    ProgressBar indeterminateProgressLarge;
    @Bind(R.id.indeterminate_progress_library)
    ProgressBar indeterminateProgress;
    @Bind(R.id.indeterminate_progress_small_library)
    ProgressBar indeterminateProgressSmall;
    @Bind(R.id.horizontal_progress_toolbar)
    ProgressBar toolbarHorizontalProgress;
    @Bind(R.id.indeterminate_horizontal_progress_toolbar)
    ProgressBar toolbarIndeterminateHorizontalProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        horizontalProgress.setProgressDrawable(new HorizontalProgressDrawable(this));

        indeterminateHorizontalProgress.setIndeterminateDrawable(
                new IndeterminateHorizontalProgressDrawable(this));

        indeterminateProgressLarge.setIndeterminateDrawable(
                new IndeterminateProgressDrawable(this));
        indeterminateProgress.setIndeterminateDrawable(new IndeterminateProgressDrawable(this));
        indeterminateProgressSmall.setIndeterminateDrawable(
                new IndeterminateProgressDrawable(this));

        HorizontalProgressDrawable toolbarDrawable = new HorizontalProgressDrawable(this);
        toolbarDrawable.setShowTrack(false);
        toolbarDrawable.setUseIntrinsicPadding(false);
        toolbarHorizontalProgress.setProgressDrawable(toolbarDrawable);

        IndeterminateHorizontalProgressDrawable toolbarIndeterminateDrawable =
                new IndeterminateHorizontalProgressDrawable(this);
        toolbarIndeterminateDrawable.setShowTrack(false);
        toolbarIndeterminateDrawable.setUseIntrinsicPadding(false);
        toolbarIndeterminateHorizontalProgress.setIndeterminateDrawable(
                toolbarIndeterminateDrawable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
