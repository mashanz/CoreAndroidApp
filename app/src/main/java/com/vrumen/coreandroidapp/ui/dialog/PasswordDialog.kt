package com.vrumen.coreandroidapp.ui.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import com.vrumen.coreandroidapp.R

/**
 * Created by Chandra on 10/14/16.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */

class PasswordDialog : DialogFragment() {

    private var messageClosed: OnMessageClosed? = null

    fun setOnMessageClosed(messageClosed: OnMessageClosed) {
        this.messageClosed = messageClosed
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.dialog_password, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setStyle(DialogFragment.STYLE_NO_FRAME, 0)
        val txtPassword = v.findViewById(R.id.txt_password) as TextView

        val btnYes = v.findViewById(R.id.dialog_yes) as TextView
        btnYes.setOnClickListener {
            if (messageClosed != null) messageClosed!!.onProcess(txtPassword.text.toString())
            dismiss()
        }

        val btnCancel = v.findViewById(R.id.dialog_no) as TextView
        btnCancel.setOnClickListener {
            if (messageClosed != null) messageClosed!!.onCancel()
            dismiss()
        }

    }

    interface OnMessageClosed {
        fun onProcess(password: String)
        fun onCancel()
    }

}