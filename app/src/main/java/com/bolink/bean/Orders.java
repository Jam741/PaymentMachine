package com.bolink.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by xl on 2017/8/21.
 * 订单表
 */

public class Orders extends DataSupport {

    String orderId;
    String carNumber;
    String payTime;
    double orderPrice;//订单总金额
    double prepay;
    double currentPrice;//当前金额
    double getMoney;//投入的所有钱
    int cash1;
    int cash2;
    int cash5;
    int cash10;
    int cash20;
    int cash50;
    int cash100;
    int isPay;//是否支付停车费
    int isCharge;//是否充值话费


    double pay;//投入的钱里用来支付停车费的钱
    double unCharge;//投入的钱里没充值话费的部分

    //2017.11.14增加- 为了保证服务器和本地的记录一致。本地存储未上报的订单
    int isReport;//是否上报了服务器
    String oid;
    String change;
    String mobile;
    String trade_no;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public int getIsReport() {
        return isReport;
    }

    public void setIsReport(int isReport) {
        this.isReport = isReport;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public double getUnCharge() {
        return unCharge;
    }

    public void setUnCharge(double unCharge) {
        this.unCharge = unCharge;
    }

    public double getGetMoney() {
        return getMoney;
    }

    public void setGetMoney(double getMoney) {
        this.getMoney = getMoney;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public double getPrepay() {
        return prepay;
    }

    public void setPrepay(double prepay) {
        this.prepay = prepay;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getCash1() {
        return cash1;
    }

    public void setCash1(int cash1) {
        this.cash1 = cash1;
    }

    public int getCash2() {
        return cash2;
    }

    public void setCash2(int cash2) {
        this.cash2 = cash2;
    }

    public int getCash5() {
        return cash5;
    }

    public void setCash5(int cash5) {
        this.cash5 = cash5;
    }

    public int getCash10() {
        return cash10;
    }

    public void setCash10(int cash10) {
        this.cash10 = cash10;
    }

    public int getCash20() {
        return cash20;
    }

    public void setCash20(int cash20) {
        this.cash20 = cash20;
    }

    public int getCash50() {
        return cash50;
    }

    public void setCash50(int cash50) {
        this.cash50 = cash50;
    }

    public int getCash100() {
        return cash100;
    }

    public void setCash100(int cash100) {
        this.cash100 = cash100;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public int getIsCharge() {
        return isCharge;
    }

    public void setIsCharge(int isCharge) {
        this.isCharge = isCharge;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId='" + orderId + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", payTime='" + payTime + '\'' +
                ", orderPrice=" + orderPrice +
                ", prepay=" + prepay +
                ", currentPrice=" + currentPrice +
                ", getMoney=" + getMoney +
                ", cash1=" + cash1 +
                ", cash2=" + cash2 +
                ", cash5=" + cash5 +
                ", cash10=" + cash10 +
                ", cash20=" + cash20 +
                ", cash50=" + cash50 +
                ", cash100=" + cash100 +
                ", isPay=" + isPay +
                ", isCharge=" + isCharge +
                ", pay=" + pay +
                ", unCharge=" + unCharge +
                ", isReport=" + isReport +
                ", oid='" + oid + '\'' +
                ", change='" + change + '\'' +
                ", mobile='" + mobile + '\'' +
                ", trade_no='" + trade_no + '\'' +
                '}';
    }
}
