package com.bolink.bean;

/**
 * Created by xulu on 2017/10/24.
 */

public class VideoUrls {
    String videoUrl;
    String fileName;
    boolean del;
    boolean download;//下载是否成功

    @Override
    public String toString() {
        return "videoDetail{" +
                "videoUrl='" + videoUrl + '\'' +
                ", fileName='" + fileName + '\'' +
                ", del=" + del +
                ", download=" + download +
                '}';
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
