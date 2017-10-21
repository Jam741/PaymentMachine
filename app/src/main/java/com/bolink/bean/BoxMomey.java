package com.bolink.bean;

/**
 * Created by xulu on 2017/9/6.
 */

public class BoxMomey {
    int total;
    int m1;
    int m5;
    int m10;
    int m20;
    int m50;
    int m100;

    public BoxMomey(int total, int m1, int m5, int m10, int m20, int m50, int m100) {
        this.total = total;
        this.m1 = m1;
        this.m5 = m5;
        this.m10 = m10;
        this.m20 = m20;
        this.m50 = m50;
        this.m100 = m100;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getM1() {
        return m1;
    }

    public void setM1(int m1) {
        this.m1 = m1;
    }

    public int getM10() {
        return m10;
    }

    public void setM10(int m10) {
        this.m10 = m10;
    }

    public int getM5() {
        return m5;
    }

    public void setM5(int m5) {
        this.m5 = m5;
    }

    public int getM20() {
        return m20;
    }

    public void setM20(int m20) {
        this.m20 = m20;
    }

    public int getM50() {
        return m50;
    }

    public void setM50(int m50) {
        this.m50 = m50;
    }

    public int getM100() {
        return m100;
    }

    public void setM100(int m100) {
        this.m100 = m100;
    }
}
