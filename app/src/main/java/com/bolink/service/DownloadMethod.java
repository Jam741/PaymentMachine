package com.bolink.service;

import android.content.Context;
import android.content.Intent;

import com.bolink.R;
import com.bolink.bean.BannerUrls;
import com.bolink.bean.VideoUrls;

import java.util.List;

/**
 * Created by xulu on 2017/10/23.
 */

public class DownloadMethod {

    /**
     * 下载视频
     * @param details
     * @param index
     * @param context
     */
    public static void startDownLoad(List<VideoUrls> details, int index, Context context) {
        if (details.size() > 0 && index < details.size()) {
//            progressbar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(context, DownloadService.class);
            intent.putExtra("type", "video");
            intent.putExtra("fileUrl", details.get(index).getVideoUrl());
            intent.putExtra("fileName", details.get(index).getFileName());
            context.startService(intent);
        }
    }

    /**
     * 下载图片
     * @param urlsList
     * @param index
     * @param context
     */
    public static void startDownLoadimg(List<BannerUrls> urlsList,int index,Context context){
        if (urlsList.size() > 0 && index < urlsList.size()) {
//            progressbar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(context, DownloadService.class);
            intent.putExtra("type", "image");
            intent.putExtra("fileUrl", urlsList.get(index).getImgUrl());
            intent.putExtra("fileName", urlsList.get(index).getFileName());
            context.startService(intent);
        }
    }

    /**
     * 下载升级包
     * @param url
     * @param context
     */
    public static  void startDownLoadUpdate(String url,Context context) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra("type", "apk");
        intent.putExtra("fileUrl", url);
        intent.putExtra("fileName", context.getResources().getString(R.string.apk_name));
        context.startService(intent);
    }
    
}
