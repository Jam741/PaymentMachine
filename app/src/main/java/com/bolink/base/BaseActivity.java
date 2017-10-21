package com.bolink.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by xulu on 2017/9/9.
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        disableStatusBar2();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
//        disableStatusBar();

        super.onWindowFocusChanged(hasFocus);
    }

    public void disableStatusBar() {
        try {
//        @SuppressLint("WrongConstant")
            @SuppressLint("WrongConstant")
            Object service = getSystemService("statusbar");
            Class<?> claz = Class.forName("android.app.StatusBarManager");
            Method method = claz.getMethod("collapse");
            method.invoke(service);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static final int STATUS_BAR_DISABLE_EXPAND = 0x00010000;

    public void disableStatusBar2() {

        Object sbservice = getSystemService("statusbar");

        try {
            Class<?> statusBarManager = Class
                    .forName("android.app.StatusBarManager");
            Method collapse;
            collapse = statusBarManager.getMethod("disable", int.class);
            collapse.invoke(sbservice,
                    Integer.valueOf(STATUS_BAR_DISABLE_EXPAND));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
