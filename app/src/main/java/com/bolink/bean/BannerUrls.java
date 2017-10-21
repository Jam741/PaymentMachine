package com.bolink.bean;

/**
 * Created by xulu on 2017/10/17.
 */

public class BannerUrls {
    String url;

    public BannerUrls(String url) {
        this.url = url;
    }

    public BannerUrls() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "BannerUrls{" +
                "url='" + url + '\'' +
                '}';
    }
}
