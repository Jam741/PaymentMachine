package com.bolink.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.bolink.R;
import com.bolink.bean.Messages;
import com.bolink.bean.UpdateInfo;
import com.bolink.retrofit.HttpApi;
import com.bolink.retrofit.ToByteConvertFactory;
import com.bolink.retrofit.UpdataInfoParser;
import com.bolink.rx.RxBus;
import com.bolink.service.DownloadMethod;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.bolink.bean.Messages.JS_LOADING_CANCEL;
import static com.bolink.bean.Messages.LOADING_SHOW;
import static com.bolink.bean.Messages.PRINT_MSG;

/**
 * Created by xulu on 2017/10/23.
 */

public class UpdateUtil {
    private static final String TAG = "UpdateUtil";
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
                    RxBus.get().post(new Messages(JS_LOADING_CANCEL, ""));
                    Log.e(TAG, "CheckUpdate: "+ bytes);
                    if (bytes != null) {
                        InputStream inputStream = new ByteArrayInputStream(bytes);
                        UpdateInfo info = UpdataInfoParser.getUpdataInfo(inputStream);
                        inputStream.close();
                        Log.e(TAG, "CheckUpdate: "+ info.getVersion()+info.getDescription());
                        if (Integer.parseInt(versionCode) < Integer.parseInt(info.getVersion())) {
                            File updateFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), context.getResources().getString(R.string.apk_name));
                            if (updateFile.exists()) {
//                                installAPK(updateFile, context);
                                SilentInatall(updateFile.getAbsolutePath());
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

    //静默安装
    public static boolean SilentInatall(String apkPath) {
        boolean result = false;
        DataOutputStream dataOutputStream = null;
        BufferedReader errorSteam = null;
        Process process = null;
        try {
            //申请su权限
            process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            //执行 pm intall 命令
            String command = "pm install -r " + apkPath + "\n";
            dataOutputStream.write(getBytes(command));
            dataOutputStream.flush();
            dataOutputStream.write(getBytes("exit\n"));
            dataOutputStream.flush();
            process.waitFor();

            errorSteam = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String msg = "";
            String line;
            while ((line = errorSteam.readLine()) != null) {
                msg += line;

            }
            CommontUtils.writeSDFile("silent install msg", msg);
            if (!msg.contains("Failure")) {
                result = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (errorSteam != null) {
                    errorSteam.close();
                }
                if (process != null){
                    process.destroy();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return result;
    }

    private static byte[] getBytes(String target) {
        return target.getBytes(Charset.forName("UTF-8"));
    }
}
