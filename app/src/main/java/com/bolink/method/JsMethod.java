package com.bolink.method;


import android.webkit.WebView;

import com.bolink.bean.BannerUrls;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by xulu on 2017/8/24.
 * Android调用JS方法
 */

public class JsMethod {
    WebView webView;

    public JsMethod(WebView webView) {
        this.webView = webView;
    }

    public void NotifyNetState(boolean netAvilable){
        String alert = "javascript:NotifyNetState("+netAvilable+");";
        webView.loadUrl(alert);
    }
    public void QuerryAdavance(String result){
        String alert = "javascript:QuerryAdavance("+result+");";
        webView.loadUrl(alert);
    }
    public void QuerryAdavanceAbNormal(String abnormal){
        String alert = "javascript:QuerryAdavanceAbNormal("+abnormal+");";
        webView.loadUrl(alert);
    }
    //更新通话信息
    public void NotifyCallStatus(String msg){
        //!!!特别注意，如果传的参数是String,要额外加两个 '' 否则会报  "Uncaught ReferenceError: 通话中207 is not defined", source: (1)
        String alert = "javascript:NotifyCallStatus('"+msg+"');";
        webView.loadUrl(alert);
    }
    //验证密码结果
    public void NotifyCheckPwd(int msg){
        String alert = "javascript:NotifyCheckPwd("+msg+");";
        webView.loadUrl(alert);
    }
    //通知js拿到钱了
    public void getMoney(double msg){
        String alert = "javascript:getMoney("+msg+");";
        webView.loadUrl(alert);
    }
    //获取到mac地址
    public void getMacAddress(String msg){
        String alert = "javascript:getMacAddress('"+msg+"');";
        webView.loadUrl(alert);
    }
    //获取到扫码结果，发起支付
    public void ScanResult(String msg){
        String alert = "javascript:ScanResult('"+msg+"');";
        webView.loadUrl(alert);
    }
    //更新banner图片地址
    public void updateSwiper(List<BannerUrls> bannerUrlsList){
        String alert = "javascript:updateSwiper("+(new Gson().toJson(bannerUrlsList))+");";
        webView.loadUrl(alert);
    }

}