package com.bolink.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by xulu on 2017/9/19.
 */

public class Users extends DataSupport {
    String account;
    String password;
    String timespan;

    public Users(String account, String password, String timespan) {
        this.account = account;
        this.password = password;
        this.timespan = timespan;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTimespan() {
        return timespan;
    }

    public void setTimespan(String timespan) {
        this.timespan = timespan;
    }
}
