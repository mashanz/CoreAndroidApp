package com.vrumen.coreandroidapp.util.dialog_java;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.view.Window;

/**
 * Created by Chandra on 9/24/17.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */

public class DialogUtil {

    public static MessageDialog showMessage(FragmentManager fragmentManager, String message) {
        MessageDialog dialog = MessageDialog.newInstance(message);
        dialog.setCancelable(false);
        dialog.show(fragmentManager, "DialogMessage");
        return dialog;
    }

    public static ConfirmationDialog showConfirmation(FragmentManager fragmentManager, String message) {
        ConfirmationDialog dialog = ConfirmationDialog.newInstance(message);
        dialog.setCancelable(false);
        dialog.show(fragmentManager, "dialogConfirmation");
        return dialog;
    }

    public static ProgressDialog createProgressDialog(Context context, String message, Boolean cancelable) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        dialog.setMessage(message);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }
}
