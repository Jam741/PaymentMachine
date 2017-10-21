package com.bolink.bean;

import java.util.List;

/**
 * Created by xulu on 2017/8/23.
 */

public class VideoResult {
    int state;
    String errmsg;
    List<videoDetail> videoList;
//    List<BannerUrls> imgList;

//    public List<BannerUrls> getImgList() {
//        return imgList;
//    }
//
//    public void setImgList(List<BannerUrls> imgList) {
//        this.imgList = imgList;
//    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<videoDetail> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<videoDetail> videoList) {
        this.videoList = videoList;
    }

    public class videoDetail{
        String videoUrl;
        String fileName;
        boolean del;

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
}
