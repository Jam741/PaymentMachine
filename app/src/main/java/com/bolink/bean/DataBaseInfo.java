package com.bolink.bean;

import java.util.List;

/**
 * Created by xulu on 2017/11/6.
 */

public class DataBaseInfo <T>{
    String dbname;
    String version;
    List<T> list;
    String storage;

    @Override
    public String toString() {
        return "DataBaseInfo{" +
                "dbname='" + dbname + '\'' +
                ", version='" + version + '\'' +
                ", list=" + list +
                ", storage='" + storage + '\'' +
                '}';
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }
}
