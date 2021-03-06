package com.bolink.method;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.bolink.BuildConfig;
import com.bolink.R;
import com.bolink.bean.BoxMomey;
import com.bolink.bean.Messages;
import com.bolink.bean.Orders;
import com.bolink.bean.PrintMsg;
import com.bolink.bean.Users;
import com.bolink.hardware.AndroidRom;
import com.bolink.hardware.LinePhoneUtil;
import com.bolink.hardware.MoneyPaperUtil;
import com.bolink.rx.RxBus;
import com.bolink.utils.CommontUtils;
import com.bolink.utils.FormatUtil;
import com.bolink.utils.SharedPreferenceUtil;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static com.bolink.bean.Messages.CHECK_UPDATE;
import static com.bolink.bean.Messages.CLEAR_CACHE;
import static com.bolink.bean.Messages.MacAddress;
import static com.bolink.bean.Messages.NOT_REPORT_ORDERS;
import static com.bolink.bean.Messages.PRINT_MSG;
import static com.bolink.bean.Messages.PRINT_TICKET;
import static com.bolink.bean.Messages.REQUEST_SPECIE_COUNT;
import static com.bolink.bean.Messages.REQUEST_SPECIE_OUT;
import static com.bolink.bean.Messages.SCAN_CLOSE;
import static com.bolink.bean.Messages.SCAN_OPEN;

/**
 * Created by xulu on 2017/8/22.
 * JS调用Android方法
 */

public class AndroidMethod {

    Context context;
//    SQLiteDatabase db;

    //    public AndroidMethod(Context context, SQLiteDatabase db) {
//        this.context = context;
////        this.db = LitePal.getDatabase();
//    }
    public AndroidMethod(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void Toast(String msg) {
//        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
        RxBus.get().post(new Messages(PRINT_MSG, msg));
    }

    @JavascriptInterface
    public void WriteLog(String msg) {
        CommontUtils.writeSDFile("wwwwwwwwweb log :", msg);
    }

    @JavascriptInterface
    public void CreateOrder(String orderId, String carNumber, String payTime, double orderPrice, double currentPrice) {
//        CommontUtils.writeSDFile("create order", "start====" + orderId + " " + carNumber + "  " + payTime + "  " + orderPrice + "  " + currentPrice);
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setCarNumber(carNumber);
        orders.setPayTime(payTime);
        orders.setOrderPrice(orderPrice);
        orders.setCurrentPrice(currentPrice);
//        CommontUtils.writeSDFile("create order", orders.toString());
        orders.save();
    }

    @JavascriptInterface
    public void CreateOrder(String orderId, String carNumber, String payTime, double orderPrice, double currentPrice, String oid) {
//        CommontUtils.writeSDFile("create order", "start====" + orderId + " " + carNumber + "  " + payTime + "  " + orderPrice + "  " + currentPrice);
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setCarNumber(carNumber);
        orders.setPayTime(payTime);
        orders.setOrderPrice(orderPrice);
        orders.setCurrentPrice(currentPrice);
        orders.setOid(oid);
//        CommontUtils.writeSDFile("create order", orders.toString());
        orders.save();
    }

    @JavascriptInterface
    public void CreateOrder(String orderId, int cash, double getMoney, String payTime, String plate_number, double orderPrice, double currentPrice, String oid) {
//        CommontUtils.writeSDFile("create order", "start====" + orderId + " " + carNumber + "  " + payTime + "  " + orderPrice + "  " + currentPrice);
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setCarNumber(plate_number);
        orders.setPayTime(payTime);
        orders.setOrderPrice(orderPrice);
        orders.setCurrentPrice(currentPrice);
        orders.setOid(oid);
//        CommontUtils.writeSDFile("create order", orders.toString());
        orders.save();
        UpdateOrderMoney(orderId, cash, getMoney, payTime, plate_number, orderPrice, currentPrice, oid);
    }

    @JavascriptInterface
    public void UpdateOrderMoney(String orderId, int cash, double getMoney) {
//        CommontUtils.writeSDFile("UpdateOrderMoney", orderId + "   " + cash + "   " + getMoney);
        List<Orders> orders = DataSupport.where("orderId like ?", orderId).find(Orders.class);
        if (null != orders && orders.size() > 0) {
            Orders order = orders.get(0);
            switch (cash) {
                case 1:
                    int cash1 = order.getCash1();
                    order.setCash1(++cash1);
                    break;
                case 5:
                    int cash5 = order.getCash5();
                    order.setCash5(++cash5);
                    break;
                case 10:
                    int cash10 = order.getCash10();
                    order.setCash10(++cash10);
                    break;
                case 20:
                    int cash20 = order.getCash20();
                    order.setCash20(++cash20);
                    break;
                case 50:
                    int cash50 = order.getCash50();
                    order.setCash50(++cash50);
                    break;
                case 100:
                    int cash100 = order.getCash100();
                    order.setCash100(++cash100);
                    break;
            }
            order.setGetMoney(order.getGetMoney() + cash);
            order.save();
        }
    }

    @JavascriptInterface
    public void UpdateOrderMoney(String orderId, int cash, double getMoney, String payTime, String plate_number, double orderPrice, double currentPrice, String oid) {
//        CommontUtils.writeSDFile("UpdateOrderMoney", orderId + "   " + cash + "   " + getMoney);
        List<Orders> orders = DataSupport.where("orderId like ?", orderId).find(Orders.class);
        if (null != orders && orders.size() > 0) {
            Orders order = orders.get(0);
            switch (cash) {
                case 1:
                    int cash1 = order.getCash1();
                    order.setCash1(++cash1);
                    break;
                case 5:
                    int cash5 = order.getCash5();
                    order.setCash5(++cash5);
                    break;
                case 10:
                    int cash10 = order.getCash10();
                    order.setCash10(++cash10);
                    break;
                case 20:
                    int cash20 = order.getCash20();
                    order.setCash20(++cash20);
                    break;
                case 50:
                    int cash50 = order.getCash50();
                    order.setCash50(++cash50);
                    break;
                case 100:
                    int cash100 = order.getCash100();
                    order.setCash100(++cash100);
                    break;
            }
            order.setGetMoney(order.getGetMoney() + cash);
            order.setPayTime(payTime);
            order.save();
        } else {
            //如果没查到订单就创建订单
            CreateOrder(orderId, cash, getMoney, payTime, plate_number, orderPrice, currentPrice, oid);
        }
    }


    @JavascriptInterface
    public void setTradeNo(String orderId, String tradeno) {
        List<Orders> orders = DataSupport.where("orderId like ?", orderId).find(Orders.class);
        if (null != orders && orders.size() > 0) {
            Orders order = orders.get(0);
            order.setTrade_no(tradeno);
            order.save();
        }
    }

    @JavascriptInterface
    public void IsPay(String orderId, int isPay) {
        List<Orders> orders = DataSupport.where("orderId like ?", orderId).find(Orders.class);
        if (null != orders && orders.size() > 0) {
            Orders order = orders.get(0);
            order.setIsPay(isPay);
            order.save();
        }

    }

    @JavascriptInterface
    public void IsCharge(String orderId, int IsCharge) {
        List<Orders> orders = DataSupport.where("orderId like ?", orderId).find(Orders.class);
        if (null != orders && orders.size() > 0) {
            Orders order = orders.get(0);
            order.setIsCharge(IsCharge);
            order.save();
        }
    }

    @JavascriptInterface
    public void IsReport(String orderId, int isReport) {
        CommontUtils.writeSDFile("", ">>>" + orderId + isReport + "<<<");
        List<Orders> orders = DataSupport.where("orderId like ?", orderId).find(Orders.class);
        CommontUtils.writeSDFile("", ">>>orders.size  = " + orders.size() + "<<<");
        if (null != orders && orders.size() > 0) {
            Orders order = orders.get(0);
            order.setIsReport(isReport);
            order.save();
            CommontUtils.writeSDFile("", ">>>orders.更新了 " + "<<<");
        }
    }

    @JavascriptInterface
    public void NotReportOrders() {
        List<Orders> orders = DataSupport.where("isReport like ? and getMoney > ? and isCharge like ?", "0", "0", "0").find(Orders.class);
        String json_orders = "";
        if (null != orders && orders.size() > 0) {
            Gson gson = new Gson();
//           OrdersNotReport notReport = new OrdersNotReport();
//           notReport.setOrders(orders);
//           json_orders = gson.toJson(notReport);
            double change = 0;
            for (Orders order : orders) {
                if (order.getIsPay() == 1) {
                    //如果支付了，找零是 投入的钱-订单金额
                    change = FormatUtil.DoubleFormat(order.getOrderPrice() - order.getGetMoney());
                } else {
                    //如果没有支付，找零即投入金额
                    change = order.getGetMoney();
                }
                order.setChange(change + "");
                if (order.getTrade_no() == null) {
                    order.setTrade_no("");
                }
                if (order.getOid() == null) {
                    order.setOid("");
                }
            }
            json_orders = gson.toJson(orders);
        }
        CommontUtils.writeSDFile("NotReportOrders", ">>>" + json_orders + "<<<");
        RxBus.get().post(new Messages(NOT_REPORT_ORDERS, json_orders));

    }

    //统计箱子里的钱
    @JavascriptInterface
    public void GetBoxMoney() {
        List<Orders> orders = DataSupport.findAll(Orders.class);
        int total = 0, m1 = 0, m5 = 0, m10 = 0, m20 = 0, m50 = 0, m100 = 0;
        if (null != orders && orders.size() > 0) {

            for (Orders order : orders) {
//                total += order.getGetMoney();
                m1 += order.getCash1();
                m5 += order.getCash5();
                m10 += order.getCash10();
                m20 += order.getCash20();
                m50 += order.getCash50();
                m100 += order.getCash100();
            }
            total = m1 + m5 * 5 + m10 * 10 + m20 * 20 + m50 * 50 + m100 * 100;

        }
        BoxMomey box = new BoxMomey(total, m1, m5, m10, m20, m50, m100);
        Gson g = new Gson();
        RxBus.get().post(new Messages(Messages.QUERRY_ADVANCE, g.toJson(box)));
//        return g.toJson(box);
    }

    //统计没有充值话费的订单，视为异常订单
    @JavascriptInterface
    public void AbNormalOrder() {
        List<Orders> orders = DataSupport.where("isCharge like ?", "0").find(Orders.class);
        List<Orders> orderreturn = new ArrayList<>();
        if (null != orders && orders.size() > 0) {
            for (Orders order : orders) {
                //只有投入过钱了才统计
                if (order.getGetMoney() > 0) {
                    if (order.getIsPay() == 0) {
                        //如果没有支付停车费，那么所有投入的钱都是没有充值的钱
                        order.setUnCharge(order.getGetMoney());
                        orderreturn.add(order);
                    } else {
                        //如果支付了停车费，投入的钱-付停车费的钱就是没有充值的钱
                        if (order.getGetMoney() > order.getOrderPrice()) {
                            //投入的钱大于订单金额，才能有剩余的钱 出现的异常里
                            order.setUnCharge(FormatUtil.DoubleFormat(order.getGetMoney() - order.getOrderPrice()));
                            order.setPay(order.getOrderPrice());
                            orderreturn.add(order);
                        } else {
                            //小于订单金额，不会支付停车费
                            //等于订单金额，刚好支付则不统计
                        }
                    }
                }
            }
            Gson g = new Gson();
//            CommontUtils.writeSDFile("AbNormalOrder", g.toJson(orderreturn));
            RxBus.get().post(new Messages(Messages.QUERRY_ADVANCE_ABNORMAL, g.toJson(orderreturn)));
        }
    }

    //清除数据库-order表
    @JavascriptInterface
    public void clearOrders() {
        DataSupport.deleteAll(Orders.class);
    }

    //呼叫总机
    @JavascriptInterface
    public void CallHost() {
        RxBus.get().post(new Messages(Messages.CALL_HOST, null));
    }

    //注册-linephone
    @JavascriptInterface
    public void Regist_LinePhone(String accout, String pwd, String domain, String host) {
        WriteLog(accout + "  " + pwd + "  " + domain + "  " + host + "  ");
        LinePhoneUtil.initLinePhone(accout, pwd, domain, host);
    }

    //呼叫总机-linephone
    @JavascriptInterface
    public void CallHost_LinePhone() {
        RxBus.get().post(new Messages(Messages.CALL_HOST_LINEPHONE, null));
    }

    //新账号登录
    @JavascriptInterface
    public void CreateUser(String account, String pwd) {
        Users users = new Users(account, pwd, System.currentTimeMillis() / 1000 + "");
        users.save();
    }

    //账号更新
    @JavascriptInterface
    public void UpdateUser(String account, String pwd) {
//        List<Users> usersList = DataSupport.where("account like ?", account).find(Users.class);
//        if (null != usersList && usersList.size() > 0) {
//            Users users = usersList.get(0);
//            users.setPassword(pwd);
//            users.setTimespan(System.currentTimeMillis() / 1000 + "");
//            users.save();
//        } else {
        DataSupport.deleteAll(Users.class);
        CreateUser(account, pwd);
//        }
    }

    //退出登录
    @JavascriptInterface
    public void UpdateUser_Exit() {
        DataSupport.deleteAll(Users.class);
    }

    //验证高级登录密码
    @JavascriptInterface
    public void IdentifyPwd(String pwd) {
        List<Users> usersList = DataSupport.findAll(Users.class);
        if (null != usersList && usersList.size() > 0) {
            Users users = usersList.get(0);
            if (users.getPassword().equals(pwd)) {
                RxBus.get().post(new Messages(Messages.IDENTIFY_PWD_Y, null));
            } else {
                RxBus.get().post(new Messages(Messages.IDENTIFY_PWD_N, null));
            }
        } else {
            RxBus.get().post(new Messages(Messages.IDENTIFY_PWD_N, null));
        }
    }

    //打开纸币器
    @JavascriptInterface
    public void Open_Paper_Money() {
        RxBus.get().post(new Messages(Messages.JS_LOADING_SHOW, null));
        MoneyPaperUtil.get().init(context);
    }

    //关闭纸币器
    @JavascriptInterface
    public void Close_Paper_Money() {
        MoneyPaperUtil.get().Close();
    }

    //解除屏蔽 status bar
    @JavascriptInterface
    public void UnLockBar() {
        AndroidRom.get().UnLockBar(context);
        SharedPreferences sharedPreference = SharedPreferenceUtil.get(context);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean("BarStatus", false);
        editor.commit();
    }

    //屏蔽 status bar
    @JavascriptInterface
    public void LockBar() {
        AndroidRom.get().LockBar(context);
        SharedPreferences sharedPreference = SharedPreferenceUtil.get(context);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean("BarStatus", true);
        editor.commit();
    }

    //重载webview
    @JavascriptInterface
    public void Reload() {
        RxBus.get().post(new Messages(Messages.RELOAD_WEBVIEW, null));
    }

    //打印内容
    @JavascriptInterface
    public void Print(String title, String park, String terminal, String carnumber, String prepay, String paytype, String paytime, String tale) {

        Log.d("PRINT", "打印小票");
        PrintMsg msg = new PrintMsg();
        msg.setTitle(title);
        msg.setTale(tale);
        Resources res = context.getResources();
        String content = res.getString(R.string.ads_park) + park + "\n"
                + res.getString(R.string.ads_teminal) + terminal + "\n"
                + res.getString(R.string.ads_carnumber) + carnumber + "\n"
                + res.getString(R.string.ads_prepay) + prepay + "\n"
                + res.getString(R.string.ads_paytype) + paytype + "\n"
                + res.getString(R.string.ads_paytime) + paytime + "\n";
        msg.setContent(content);
        RxBus.get().post(new Messages(PRINT_TICKET, msg));
    }

    //打印内容
    @JavascriptInterface
    public void Print(String title, String park, String terminal, String carnumber, String prepay, String paytype, String paytime, String tale,String returncash) {

        Log.d("PRINT", "打印小票");
        PrintMsg msg = new PrintMsg();
        msg.setTitle(title);
        msg.setTale(tale);
        Resources res = context.getResources();
        String content = res.getString(R.string.ads_park) + park + "\n"
                + res.getString(R.string.ads_teminal) + terminal + "\n"
                + res.getString(R.string.ads_carnumber) + carnumber + "\n"
                + res.getString(R.string.ads_prepay) + prepay + "\n"
                + res.getString(R.string.ads_paytype) + paytype + "\n"
                + res.getString(R.string.ads_paytime) + paytime + "\n"
                + res.getString(R.string.ads_returncash) + returncash + "\n" + "(请与管理员联系找零 )";
        msg.setContent(content);
        RxBus.get().post(new Messages(PRINT_TICKET, msg));
    }


    @JavascriptInterface
    public void Print(String title, String park, String terminal, String carnumber, String prepay, String paytype, String paytime, String tale, String returncash, String url) {

        Log.d("PRINT", "打印小票2");
        PrintMsg msg = new PrintMsg();
        msg.setTitle(title);
        msg.setTale(tale);
        Resources res = context.getResources();
        String content = res.getString(R.string.ads_park) + park + "\n"
                + res.getString(R.string.ads_teminal) + terminal + "\n"
                + res.getString(R.string.ads_carnumber) + carnumber + "\n"
                + res.getString(R.string.ads_prepay) + prepay + "\n"
                + res.getString(R.string.ads_paytype) + paytype + "\n"
                + res.getString(R.string.ads_paytime) + paytime + "\n"
                + res.getString(R.string.ads_returncash) + returncash + "\n";
        msg.setContent(content);
        msg.setUrl(url);
//        WriteLog(url);
        RxBus.get().post(new Messages(PRINT_TICKET, msg));
    }


    //查询硬币余额
    @JavascriptInterface
    public void RequestSpecieCount() {
        RxBus.get().post(new Messages(REQUEST_SPECIE_COUNT, null));
    }

    //出币
    @JavascriptInterface
    public void RequestSpecieOut(String count) {
        RxBus.get().post(new Messages(REQUEST_SPECIE_OUT, count));
    }


    //扫码器 启动扫描
    @JavascriptInterface
    public void ScanOpen() {
        RxBus.get().post(new Messages(SCAN_OPEN, null));
    }

    //扫码器 关闭扫描
    @JavascriptInterface
    public void ScanClose() {
        RxBus.get().post(new Messages(SCAN_CLOSE, null));
    }

    //获取mac地址
    @JavascriptInterface
    public void MacAddress() {
        SharedPreferences sp = SharedPreferenceUtil.get(context);
        String mac = sp.getString("MacAddress", "");
        if (mac.equals("")) {
            mac = CommontUtils.getMacAddress(context);
        }

        if (BuildConfig.DEBUG) {
//            mac = "7c:1d:d9:f2:7f:5c";
//            mac = "7c:1d:d9:f2:7f:5c";
        }

        CommontUtils.writeSDFile("MMMMMac address", mac);
        if (null != mac && !"".equals(mac)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("MacAddress", mac);
            editor.commit();
            RxBus.get().post(new Messages(MacAddress, mac));
        }

    }

    //检查更新
    @JavascriptInterface
    public void checkUpdate() {
        RxBus.get().post(new Messages(CHECK_UPDATE, null));
    }

    //清除webview缓存
    @JavascriptInterface
    public void ClearCache() {
        RxBus.get().post(new Messages(CLEAR_CACHE, null));
    }


    //开启守护程序的广播
    @JavascriptInterface
    public void StartGuard() {
        Intent intent = new Intent();
        intent.setAction("startDetect");
        context.sendBroadcast(intent);
    }

    //关闭守护程序的广播
    @JavascriptInterface
    public void StopGuard() {
        Intent intent = new Intent();
        intent.setAction("stopDetect");
        context.sendBroadcast(intent);
    }

    //跳转到新的地址
    @JavascriptInterface
    public void RememberURL(String url) {
//        RxBus.get().post(new Messages(LOGIN_JUMP, url));
        SharedPreferences.Editor editor = SharedPreferenceUtil.get(context).edit();
        editor.putString("JumpURL", url);
        editor.commit();
//        System.out.println("-----------url="+url);
    }


}
