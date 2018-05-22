package com.bilinedev.ikasmariagitma.util

import android.app.ProgressDialog
import android.content.Context
import android.os.Build
import android.support.v4.app.FragmentManager
import android.view.Window
import com.bilinedev.ikasmariagitma.ui.dialog.ConfirmationDialog
import com.bilinedev.ikasmariagitma.ui.dialog.CreateTopicDialog
import com.bilinedev.ikasmariagitma.ui.dialog.MessageDialog
import com.bilinedev.ikasmariagitma.ui.dialog.PasswordDialog

/**
 * Created by Chandra on 7/14/17.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */

object DialogUtil {
    fun showPasswordDialog(fragmentManager: FragmentManager): PasswordDialog {
        val ft = fragmentManager.beginTransaction()
        val dialog = PasswordDialog()
        dialog.isCancelable = false
        ft.add(dialog, null)
        ft.commitAllowingStateLoss()
        return dialog
    }

    fun showMessage(fragmentManager: FragmentManager, message: String): MessageDialog {
        val ft = fragmentManager.beginTransaction()
        val dialog = MessageDialog.newInstance(message)
        dialog.isCancelable = false
        //dialog.show(fragmentManager, "dialogMessage")
        ft.add(dialog, null)
        ft.commitAllowingStateLoss()
        return dialog
    }

    fun showConfirmation(fragmentManager: FragmentManager, message: String): ConfirmationDialog {
        val ft = fragmentManager.beginTransaction()
        val dialog = ConfirmationDialog.newInstance(message)
        dialog.isCancelable = false
        //dialog.show(fragmentManager, "dialogConfirmation")
        ft.add(dialog, null)
        ft.commitAllowingStateLoss()
        return dialog
    }

    fun showCreateTopic(fragmentManager: FragmentManager): CreateTopicDialog {
        val dialog = CreateTopicDialog.newInstance()
        dialog.isCancelable = false
        dialog.show(fragmentManager, "dialogTopic")
        return dialog
    }

    fun createProgressDialog(context: Context, message: String, cancelable: Boolean): ProgressDialog {
        val dialog = ProgressDialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        }
        dialog.setMessage(message)
        dialog.setCancelable(cancelable)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        return dialog
    }
}
