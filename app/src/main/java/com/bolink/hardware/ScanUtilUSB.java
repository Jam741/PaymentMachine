package com.bolink.hardware;

import android.content.Context;

import com.bolink.R;
import com.bolink.bean.Messages;
import com.bolink.rx.RxBus;
import com.vguang.Vbar;

import static com.bolink.bean.Messages.SCAN_MSG;
import static com.bolink.bean.Messages.SCAN_OPEN_RESULT;

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
        boolean state = b.vbarOpen();
        String msg;
        if(state){
            msg = "success";
        }else{
            msg = context.getResources().getString(R.string.msg_open_scan_result);
        }
        RxBus.get().post(new Messages(SCAN_OPEN_RESULT,msg));
    }

    public void open() {
        IsRun = true;
        Thread t = new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                super.run();
//                CommontUtils.writeSDFile("scan start:", "==============");
                while (IsRun) {
                    final String str = b.getResultsingle();
//                    Log.e(TAG, "run: ========================" );
                    if (str != null) {
//                        CommontUtils.writeSDFile("scan result:", str);

                        if (str != null && str.length() > 0) {
//                            CommontUtils.writeSDFile("scan result000:", str);
                            RxBus.get().post(new Messages(SCAN_MSG,str));
                            IsRun = false;
                        }

                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            }
        };
        t.start();
    }
    public void close(){
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
