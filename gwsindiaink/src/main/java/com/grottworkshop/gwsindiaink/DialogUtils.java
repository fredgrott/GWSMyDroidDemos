/*
 * Copyright (C) 2014 Drivemode, Inc. All rights reserved.
 * Modifications Copyright 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 */
package com.grottworkshop.gwsindiaink;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.TextView;


/**
 * DialogUtils class.
 * Created by fgrott on 9/7/2015.
 */
final class DialogUtils {

    /**
     * We set the typeface according to what dialog type it is, if its the ProgressDialog than we point to
     * that method if its the alert dialog than we point to that method.
     * @param helper the TypefaceHelper
     * @param dialog the dialog
     * @param typefaceName the typefaceName
     * @param style the style
     * @param <D> the D generic parameter
     */
    public static <D extends Dialog> void setTypeface(TypefaceHelper helper, D dialog, String typefaceName, int style) {
        if (dialog instanceof ProgressDialog) {
            setTypeface(helper, (ProgressDialog) dialog, typefaceName, style);
        } else if (dialog instanceof AlertDialog) {
            setTypeface(helper, (AlertDialog) dialog, typefaceName, style);
        }
    }

    /**
     * Since this is for the alert dialogs we detect which alert button should be passed
     * @param helper the typefacehelper
     * @param alertDialog the alert dialog
     * @param typefaceName the typefaceName
     * @param style the style
     */
    private static void setTypeface(TypefaceHelper helper, AlertDialog alertDialog, String typefaceName, int style) {
        Button positive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button negative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button neutral = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        TextView message = (TextView) alertDialog.findViewById(android.R.id.message);
        if (positive != null) {
            helper.setTypeface(positive, typefaceName, style);
        }
        if (negative != null) {
            helper.setTypeface(negative, typefaceName, style);
        }
        if (neutral != null) {
            helper.setTypeface(neutral, typefaceName, style);
        }
        if (message != null) {
            helper.setTypeface(message, typefaceName, style);
        }
    }

    /**
     * Since this is for the progressDialog we pass the progressDialog message along with setting
     * the typeface.
     * @param helper the typefacehelper
     * @param progressDialog the progressDialog
     * @param typefaceName the typefaceName
     * @param style the style
     */
    private static void setTypeface(TypefaceHelper helper, ProgressDialog progressDialog, String typefaceName, int style) {
        TextView message = (TextView) progressDialog.findViewById(android.R.id.message);
        if (message != null) {
            helper.setTypeface(message, typefaceName, style);
        }
    }
}
