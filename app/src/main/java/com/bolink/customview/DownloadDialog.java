package com.bolink.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bolink.R;

/**
 * Created by xulu on 2017/10/20.
 */

public class DownloadDialog extends Dialog{
    public DownloadDialog(@NonNull Context context) {
        super(context);
    }

    public DownloadDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    protected DownloadDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    TextView textView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_download);
        textView = ((TextView) findViewById(R.id.download_percent));
        progressBar = ((ProgressBar) findViewById(R.id.download_progress));
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
    }
    public void setProgress(int progress){
        progressBar.setProgress(progress);
        textView.setText(progress+"%");
    }
}
