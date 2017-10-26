package com.bolink.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.bolink.R;
import com.bolink.bean.Messages;
import com.bolink.bean.UpdateInfo;
import com.bolink.retrofit.HttpApi;
import com.bolink.retrofit.ToByteConvertFactory;
import com.bolink.retrofit.UpdataInfoParser;
import com.bolink.rx.RxBus;
import com.bolink.service.DownloadMethod;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.bolink.bean.Messages.LOADING_SHOW;
import static com.bolink.bean.Messages.PRINT_MSG;

/**
 * Created by xulu on 2017/10/23.
 */

public class UpdateUtil {
    public static void CheckUpdate(String versionCode, Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.update_url))
                .addConverterFactory(new ToByteConvertFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        HttpApi httpApi = retrofit.create(HttpApi.class);
        httpApi.rxCheckUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bytes -> {
                    if (bytes != null) {
                        InputStream inputStream = new ByteArrayInputStream(bytes);
                        UpdateInfo info = UpdataInfoParser.getUpdataInfo(inputStream);
                        inputStream.close();
                        if (Integer.parseInt(versionCode) < Integer.parseInt(info.getVersion())) {
                            File updateFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), context.getResources().getString(R.string.apk_name));
                            if (updateFile.exists()) {
                                installAPK(updateFile, context);
                            } else {
                                DownloadMethod.startDownLoadUpdate(info.getApkurl(), context);
                                RxBus.get().post(new Messages(LOADING_SHOW, null));
                            }
                        } else {
                            RxBus.get().post(new Messages(PRINT_MSG, context.getResources().getString(R.string.msg_alreadylatest)));
                        }
                    }
                });
    }

    // 安装apk；
    public static void installAPK(File file, Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
