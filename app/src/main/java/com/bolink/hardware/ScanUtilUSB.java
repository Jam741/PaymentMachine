package com.bolink.hardware;

import android.content.Context;

import com.bolink.R;
import com.bolink.bean.Messages;
import com.bolink.rx.RxBus;
import com.bolink.utils.CommontUtils;
import com.vguang.Vbar;

import static com.bolink.bean.Messages.SCAN_MSG;
import static com.bolink.bean.Messages.SCAN_OPEN_RESULT;
import static com.bolink.bean.Messages.SCAN_OPEN_RESULT_BEFORE;

/**
 * Created by xulu on 2017/9/28.
 */

public class ScanUtilUSB {
    private static class Holder {
        private static final ScanUtilUSB instance = new ScanUtilUSB();
    }

    private ScanUtilUSB() {
    }

    public static ScanUtilUSB get() {
        return Holder.instance;
    }

    //    IntByReference device;
    Vbar b = new Vbar();
    static boolean IsRun = true;
//    ReadThread read;

    public void init(Context context) {
//        CommontUtils.writeSDFile("scan open begin", System.currentTimeMillis()+"");
        RxBus.get().post(new Messages(SCAN_OPEN_RESULT_BEFORE, null));
        boolean state = b.vbarOpen();
//        CommontUtils.writeSDFile("scan open end", System.currentTimeMillis()+"");
        String msg;
        if (state) {
            msg = "success";
        } else {
            msg = context.getResources().getString(R.string.msg_open_scan_result);
        }
        IsRun = state;
//        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
        CommontUtils.writeSDFile("scan open result", msg);
        RxBus.get().post(new Messages(SCAN_OPEN_RESULT, msg));
    }

    public String init(Context context, String type) {
        CommontUtils.writeSDFile("scan open begin", System.currentTimeMillis() + "");
        RxBus.get().post(new Messages(SCAN_OPEN_RESULT_BEFORE, null));
        boolean state = b.vbarOpen();
        CommontUtils.writeSDFile("scan open end", System.currentTimeMillis() + "");
        String msg;
        if (state) {
            msg = "success";
        } else {
            msg = context.getResources().getString(R.string.msg_open_scan_result);

        }
        IsRun = state;
//        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
        CommontUtils.writeSDFile("scan open result", msg);
//        RxBus.get().post(new Messages(SCAN_OPEN_RESULT,msg));
        return msg;
    }
    public void initTest() {

//        RxBus.get().post(new Messages(SCAN_OPEN_RESULT_BEFORE, null));
        boolean state = b.vbarOpen();
        IsRun = true;
        open();

    }

    public void open() {
        try {
//            CommontUtils.writeSDFile(">>>>>>>>>>oppppen:",IsRun+"");
            IsRun = true;
            Thread t = new Thread() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    super.run();
//                CommontUtils.writeSDFile("scan start:", "==============");
                    while (IsRun) {
//                        CommontUtils.writeSDFile(">>>>>>>>>>new thread:",this.getId()+"");
                        synchronized (this) {
                            final String str = b.getResultsingle();
                            if (str != null) {
                                if (str != null && str.length() > 0) {
                                    RxBus.get().post(new Messages(SCAN_MSG, str));
                                    IsRun = false;
                                }
                            }
//                            System.out.println(">>>>>>>>>>current thread:"+this.getId());
//                            CommontUtils.writeSDFile(">>>>>>>>>>sync thread:",this.getId()+"");
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                CommontUtils.writeSDFile("ScanUtilUSB exception sleep", e.getMessage());
                                CommontUtils.writeSDFile("ScanUtilUSB exception sleep", e.getCause().toString());
                                CommontUtils.writeSDFile("ScanUtilUSB exception sleep", e.getStackTrace().toString());
                            }
                        }

                    }
//                    CommontUtils.writeSDFile(">>>>>>>>>>dead thread :",this.getId()+"");
//                    RxBus.get().post(new Messages(JS_LOADING_CANCEL, null));
                }
            };
            t.start();
        } catch (Exception e) {
            CommontUtils.writeSDFile("ScanUtilUSB exception", e.getMessage());
            CommontUtils.writeSDFile("ScanUtilUSB exception", e.getCause().toString());
            CommontUtils.writeSDFile("ScanUtilUSB exception", e.getStackTrace().toString());
        }

    }

    public void initopen(Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                init(context);
                open();
            }
        }).start();
    }

    public void close() {
        IsRun = false;
    }

    private static final String TAG = "ScanUtilUSB";

//    private class ReadThread implements Runnable {
//
//        @Override
//        public void run() {
//            while (IsRun) {
//                final String str = b.getResultsingle();
//                if (str != null && str.length() > 0) {
//                    CommontUtils.writeSDFile("scan result:",str);
//                    RxBus.get().post(new Messages(SCAN_MSG, "read result:" + str));
//                    IsRun = false;
//                }
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//
//            }
//        }
//    }

    public void Destroy() {
        IsRun = false;
        b.closeDev();
    }
}
