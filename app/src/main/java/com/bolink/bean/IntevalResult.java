package com.bolink.bean;

import java.util.List;

/**
 * Created by xulu on 2017/8/23.
 */

public class IntevalResult {
    int state;
    String errmsg;
//    String jsBaseUrl;
    List<VideoUrls> videoList;
    List<BannerUrls> imgList;

//    public String getJsBaseUrl() {
//        return jsBaseUrl;
//    }
//
//    public void setJsBaseUrl(String jsBaseUrl) {
//        this.jsBaseUrl = jsBaseUrl;
//    }

    public List<BannerUrls> getImgList() {
        return imgList;
    }

    public void setImgList(List<BannerUrls> imgList) {
        this.imgList = imgList;
    }

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

    public List<VideoUrls> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoUrls> videoList) {
        this.videoList = videoList;
    }

}
