package com.grottworkshop.gwsmaterialprogressbardemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by fgrott on 9/20/2015.
 */
public class AboutActivity extends AppCompatActivity {

    @Bind(R.id.about_icon)
    ProgressBar iconProgress;
    @Bind(R.id.about_version)
    TextView versionText;
    @Bind(R.id.about_github)
    TextView githubText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.about_activity);
        ButterKnife.bind(this);

        iconProgress.setIndeterminateDrawable(new IndeterminateProgressDrawable(this));
        String version = getString(R.string.about_version,
                AppUtils.getPackageInfo(this).versionName);
        versionText.setText(version);
        githubText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AppUtils.navigateUp(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
