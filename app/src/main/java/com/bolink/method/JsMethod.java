package com.bolink.method;


import android.webkit.WebView;

import com.bolink.bean.BannerUrls;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by xulu on 2017/8/24.
 * Android调用JS方法
 * note
 * 1.参数如果是字符串，需要加 '  '
 * 2.如果是list或[]，不能加‘  ’否则会被解析为string
 */

public class JsMethod {
    WebView webView;

    public JsMethod(WebView webView) {
        this.webView = webView;
    }

    public void NotifyNetState(boolean netAvilable) {
        String alert = "javascript:NotifyNetState(" + netAvilable + ");";
        webView.loadUrl(alert);
    }

    public void QuerryAdavance(String result) {
        String alert = "javascript:QuerryAdavance(" + result + ");";
        webView.loadUrl(alert);
    }

    public void QuerryAdavanceAbNormal(String abnormal) {
        String alert = "javascript:QuerryAdavanceAbNormal(" + abnormal + ");";
        webView.loadUrl(alert);
    }

    //更新通话信息
    public void NotifyCallStatus(String msg) {
        //!!!特别注意，如果传的参数是String,要额外加两个 '' 否则会报  "Uncaught ReferenceError: 通话中207 is not defined", source: (1)
        String alert = "javascript:NotifyCallStatus('" + msg + "');";
        webView.loadUrl(alert);
    }

    //验证密码结果
    public void NotifyCheckPwd(int msg) {
        String alert = "javascript:NotifyCheckPwd(" + msg + ");";
        webView.loadUrl(alert);
    }

    //通知js拿到钱了
    public void getMoney(double msg) {
        String alert = "javascript:getMoney(" + msg + ");";
        webView.loadUrl(alert);
    }

    //获取到mac地址
    public void getMacAddress(String msg) {
        String alert = "javascript:getMacAddress('" + msg + "');";
        webView.loadUrl(alert);
    }

    //获取到扫码结果，发起支付
    public void ScanResult(String msg) {
        String alert = "javascript:ScanResult('" + msg + "');";
        webView.loadUrl(alert);
    }

    //更新banner图片地址
    public void updateSwiper(List<BannerUrls> bannerUrlsList) {
        String alert = "javascript:updateSwiper(" + (new Gson().toJson(bannerUrlsList)) + ");";
        webView.loadUrl(alert);
    }

    //更新全屏状态
    public void getCheckStatus(boolean check) {
        String alert = "javascript:getCheckStatus(" + check + ");";
        webView.loadUrl(alert);
    }

    //更新版本名称
    public void CurrentVersion(String versionname) {
        String alert = "javascript:CurrentVersion('" + versionname + "');";
        webView.loadUrl(alert);
    }

//    //回调打开扫描器的结果
//    public void OpenScanResult(String msg) {
//        String alert = "javascript:OpenScanResult('" + msg + "');";
//        webView.loadUrl(alert);
//    }

    //回调展示loading
    public void ShowLoading(String msg) {
        String alert = "javascript:ShowLoading('" + msg + "');";
        webView.loadUrl(alert);
    }

    //回调展示loading
    public void HideLoading(String msg) {
        String alert = "javascript:HideLoading('" + msg + "');";
        webView.loadUrl(alert);
    }

    //自动登录
    public void AutoLogin(String account, String password, String macadress) {
        String alert = "javascript:AutoLogin('" + account + "','" + password + "','" + macadress + "');";
        webView.loadUrl(alert);
    }

    //统计的未上报的找零订单
    public void NotReportOrders(String msg) {
        String alert = "javascript:NotReportOrders(" + msg + ");";
        webView.loadUrl(alert);
    }

    //告知js当前版本
    public void getVersionCode(int versioncode){
        String alert = "javascript:getVersionCode(" + versioncode + ");";
        webView.loadUrl(alert);
    }

}
