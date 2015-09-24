package com.grottworkshop.gwsbase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.InflateException;
import android.view.View;

import timber.log.Timber;


/**
 * BaseAbout
 * Created by fgrott on 9/24/2015.
 */
@SuppressWarnings("unused")
public class BaseAbout {
    private static final String TAG = "AboutBox";

    /**
     * Retrieves the application's version info.
     */
    private static String getVersionString(Context context) {
        PackageManager pman = context.getPackageManager();
        String packageName = context.getPackageName();
        try {
            PackageInfo pinfo = pman.getPackageInfo(packageName, 0);
            Timber.tag(TAG);
            Timber.d("Found version " + pinfo.versionName + " for " + packageName);

            return pinfo.versionName + " [" + pinfo.versionCode + "]";
        } catch (NameNotFoundException nnfe) {
            Timber.tag(TAG);
            Timber.w("Unable to retrieve package info for " + packageName);

            return "(unknown)";
        }
    }

    /**
     * Displays the About box.  An AlertDialog is created in the calling activity's context.
     * <p>
     * The box will disappear if the "OK" button is touched, if an area outside the box is
     * touched, if the screen is rotated ... doing just about anything makes it disappear.
     */
    @SuppressLint("InflateParams")
    public static void display(Activity caller) {
        String versionStr = getVersionString(caller);
        String aboutHeader = caller.getString(R.string.app_name) + " v" + versionStr;

        // Manually inflate the view that will form the body of the dialog.
        View aboutView;
        try {
            aboutView = caller.getLayoutInflater().inflate(R.layout.about_dialog, null);
        } catch (InflateException ie) {
            Timber.tag(TAG);
            Timber.e("Exception while inflating about box: " + ie.getMessage());

            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(caller);
        builder.setTitle(aboutHeader);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setCancelable(true);        // implies setCanceledOnTouchOutside
        builder.setPositiveButton(R.string.ok, null);
        builder.setView(aboutView);
        builder.show();
    }
}
