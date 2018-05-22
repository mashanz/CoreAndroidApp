package com.vrumen.coreandroidapp.util.dialog_java;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.bilinedev.ikasmariagitma.R;

/**
 * Created by Chandra on 9/24/17.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */

public class MessageDialog extends DialogFragment {


    private OnMessageClosed messageClosed;

    public static MessageDialog newInstance(String message) {
        MessageDialog messageDialog = new MessageDialog();
        Bundle bundle = new Bundle();
        bundle.putCharSequence("message", message);
        messageDialog.setArguments(bundle);
        return messageDialog;
    }

    public void setOnMessageClosed(OnMessageClosed messageClosed) {
        this.messageClosed = messageClosed;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_message, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
        TextView txtMessage = (TextView) view.findViewById(R.id.dialog_message);
        txtMessage.setText(getArguments().getCharSequence("message"));

        TextView btnYes = (TextView) view.findViewById(R.id.dialog_ok);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageClosed != null) messageClosed.onClosed();
                dismiss();
            }
        });
    }

    public interface OnMessageClosed {
        void onClosed();
    }
}
