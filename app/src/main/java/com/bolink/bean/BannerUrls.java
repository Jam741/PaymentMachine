package com.bolink.bean;

/**
 * Created by xulu on 2017/10/17.
 */

public class BannerUrls {
    String imgUrl;
    String fileName;
    boolean del;
    boolean download;//是否下载成功

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    @Override
    public String toString() {
        return "BannerUrls{" +
                "imgUrl='" + imgUrl + '\'' +
                ", fileName='" + fileName + '\'' +
                ", del=" + del +
                '}';
    }
}
