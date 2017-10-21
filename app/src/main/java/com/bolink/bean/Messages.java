package com.bolink.bean;

/**
 * Created by xulu on 2017/8/24.
 */

public class Messages<T> {

    public static final int NET_ACCESS = 0x000001;
    public static final int NET_DENY = 0x000002;
    public static final int DOWNLOAD_PROGRESS = 0x000003;
    public static final int DOWNLOAD_PROGRESS_APK = 0x000015;
    //高级登录显示钱
    public static final int QUERRY_ADVANCE = 0x000004;
    //查询异常订单-零钱未充值
    public static final int QUERRY_ADVANCE_ABNORMAL = 0x000005;
    //呼叫总机
    public static final int CALL_HOST = 0x000006;
    //拨出 对方接通 建立连接
    public static final int CALL_ESTABLISHED = 0x000007;
    //拨出 对方挂断
    public static final int CALL_ENDED = 0x000008;
    //接到电话 接通
    public static final int CALL_RECEIVED = 0x000009;

    //高级登录密码 验证通过
    public static final int IDENTIFY_PWD_Y = 0x000010;
    //高级登录密码 验证不通过
    public static final int IDENTIFY_PWD_N = 0x000011;

    //纸币器传递消息
    public static final int PAPER_MONEY = 0x000012;
    //扫描仪扫描结果
    public static final int SCAN_MSG = 0x000013;
    //打印机消息
    public static final int PRINT_MSG = 0x000014;
    //重载webview
    public static final int RELOAD_WEBVIEW = 0x000016;
    //打印小票
    public static final int PRINT_TICKET = 0x000017;
    //开启扫码器
    public static final int SCAN_OPEN = 0x000018;
    //关闭扫码器
    public static final int SCAN_CLOSE = 0x000019;
    //获取mac地址
    public static final int MacAddress = 0x000020;
    //检查更新
    public static final int CHECK_UPDATE = 0x000021;
    private int code;
    private T msg;

    public Messages(int code, T msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }
}
