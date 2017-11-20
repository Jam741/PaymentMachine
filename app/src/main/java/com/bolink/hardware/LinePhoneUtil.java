package com.bolink.hardware;

import android.content.Context;

import com.bolink.hardware.Sip.SipManager;
import com.bolink.method.JsMethod;
import com.bolink.utils.TimeUtils;

import org.linphone.core.LinphoneCoreException;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by xulu on 2017/11/10.
 */

public class LinePhoneUtil {
    private static class SingleHolder {
        private static final LinePhoneUtil LinePhoneUtil = new LinePhoneUtil();
    }

    private LinePhoneUtil() {
    }

    public static LinePhoneUtil get() {
        return SingleHolder.LinePhoneUtil;
    }

    public static void init(Context context) {
        mSipManager = new SipManager(context);
//        compositeDisposable = new CompositeDisposable();
    }

    private static SipManager mSipManager;
    private static String accout_c, pwd_c, domain_c, host_c;

    public static void initLinePhone(String accout, String pwd, String domain, String host) {
        accout_c = accout;
        pwd_c = pwd;
        domain_c = domain;
        host_c = host;
//        Toast.makeText()
        try {
            mSipManager.register(accout, pwd, domain);
        } catch (LinphoneCoreException e) {
            e.printStackTrace();
        }
    }

    public static void initLinePhone() {
        try {
            mSipManager.register(accout_c, pwd_c, domain_c);
        } catch (LinphoneCoreException e) {
            e.printStackTrace();
        }
    }

    public static void callLinePhone() {
        try {
            mSipManager.toCall(host_c);
        } catch (LinphoneCoreException e) {
            e.printStackTrace();
        }
    }

    private static CompositeDisposable compositeDisposable;

    public static void LinePhoneTimeCount(boolean IsCalling, JsMethod jsMethod, String state) {
//        compositeDisposable = new CompositeDisposable();
        if (IsCalling) {
            compositeDisposable = new CompositeDisposable();
            compositeDisposable.add(Flowable.interval(1, TimeUnit.SECONDS)
                    .onBackpressureDrop()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
//                        if (!IsCalling)
//                            compositeDisposable.dispose();
//                        else
                        jsMethod.NotifyCallStatus(state + TimeUtils.Sec2Time(aLong));
                    }));
        } else {
            compositeDisposable.dispose();
        }
//        compositeDisposable.add(Flowable.interval(1, TimeUnit.SECONDS)
//                .onBackpressureDrop()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(aLong -> {
//                    if (!IsCalling)
//                        compositeDisposable.dispose();
//                    else
//                        jsMethod.NotifyCallStatus("通话中" + TimeUtils.Sec2Time(aLong));
//                }));
    }
}
