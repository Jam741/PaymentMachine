package com.bolink.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;

import com.bolink.R;

/**
 * Created by xulu on 2017/9/29.
 * 仿toast的dialog
 */

public class ToastDialog extends Dialog {
    public ToastDialog(@NonNull Context context) {
        super(context);
    }

    public ToastDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    protected ToastDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    TextView content;
    Window dialogwidow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_toast);
        content = ((TextView) findViewById(R.id.content));
        dialogwidow = getWindow();
        dialogwidow.setGravity(Gravity.CENTER);
    }

    public void setText(String msg) {
        content.setText(msg);
    }

    private static ToastDialog dialog = null;
    private static int DURATION = 2500;
    private static String TEXT = "";

    public static ToastDialog makeText(Context context, String msg, int duration) {
        if (dialog == null)
            dialog = new ToastDialog(context, R.style.toastdialog);
        dialog.setCancelable(false);//点击其他区域不消失
        TEXT = msg;
        if (duration > 0) {
            DURATION = duration;
        }
        return dialog;
    }


    public void show_D() {
//        if (dialogwidow == null)
//            return;
        dialog.show();
        dialog.setText(TEXT);
        new Handler().postDelayed(() -> {
            dialog.cancel();
        }, DURATION);
    }

}
