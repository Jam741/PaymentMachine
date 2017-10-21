package com.bolink.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.bolink.bean.Download;
import com.bolink.bean.Messages;
import com.bolink.network.DownloadAPI;
import com.bolink.network.download.DownloadProgressListener;
import com.bolink.rx.RxBus;
import com.bolink.utils.StringUtils;

import java.io.File;

import io.reactivex.observers.DefaultObserver;

import static com.bolink.bean.Messages.DOWNLOAD_PROGRESS;

/**
 * Created by xulu on 17/9/4.
 */
public class DownloadService extends IntentService {
    private static final String TAG = "DownloadAPIService";

    int downloadCount = 0;

    private String apkUrl = "";
    private String fileName;
    public DownloadService() {
        super("DownloadAPIService");
//        this.apkUrl = url;
//        this.fileName = fileName;
    }

    private File outputFile;
    private String type;
    @Override
    protected void onHandleIntent(Intent intent) {
        this.type = intent.getStringExtra("type");
        this.apkUrl = intent.getStringExtra("videoUrl");
        this.fileName = intent.getStringExtra("fileName");

        download();
    }

    private void download() {
        DownloadProgressListener listener = new DownloadProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                //不频繁发送通知，防止通知栏下拉卡顿
                int progress = (int) ((bytesRead * 100) / contentLength);
                if ((downloadCount == 0) || progress > downloadCount) {
                    Download download = new Download();
                    download.setTotalFileSize(contentLength);
                    download.setCurrentFileSize(bytesRead);
                    download.setProgress(progress);

                    sendNotification(download);
                }
            }
        };
        outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
        Log.e(TAG, "download: outputFile="+outputFile );
        if (outputFile.exists()) {
            outputFile.delete();
        }


        String baseUrl = StringUtils.getHostName(apkUrl);

        new DownloadAPI(baseUrl,listener).downloadAPK(apkUrl, outputFile, new DefaultObserver<Object>() {
            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                downloadCompleted();
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                downloadCompleted();
            }
        });
    }

    private void downloadCompleted() {
        Download download = new Download();
        download.setProgress(100);
        sendIntent(download);

    }

    private void sendNotification(Download download) {
        sendIntent(download);
    }

    private void sendIntent(Download download) {

//        Intent intent = new Intent(MainActivity.MESSAGE_PROGRESS);
//        intent.putExtra("download", download);
//        intent.putExtra("uri",outputFile.getAbsolutePath());
//        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
        download.setURI(outputFile.getAbsolutePath());
//        if(type.equals("apk")){
//            RxBus.get().post(new Messages(DOWNLOAD_PROGRESS_APK,download));
//        }else{
            RxBus.get().post(new Messages(DOWNLOAD_PROGRESS,download));
//        }

    }

}
