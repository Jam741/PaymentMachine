package com.bolink.hardware;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.bolink.R;
import com.bolink.bean.Messages;
import com.bolink.rx.RxBus;
import com.bolink.utils.CommontUtils;
import com.vguang.Vbar;

import static com.bolink.bean.Messages.SCAN_MSG;

/**
 * Created by hejiaming on 2019/2/17.
 *
 * @desciption:
 */
public class ScanHelper {

    public static final String TAG = "ScanHelper";

    private ScanHelper() {

    }

    private Vbar b;

    private boolean isOpenDevice = false;
    private boolean isOpenRead = false;

    private ReadThread readThread;

    private static class SingletonInstance {
        private static final ScanHelper INSTANCE = new ScanHelper();
    }

    public static ScanHelper getInstance() {
        return SingletonInstance.INSTANCE;
    }


    public void scanOpen(Context context) {
        boolean openStatus = getB().vbarOpen();
        Log.d(TAG, "openStatus:" + openStatus);
        isOpenDevice = openStatus;
        if (!openStatus) {
            Toast.makeText(context, context.getString(R.string.msg_open_scan_result), Toast.LENGTH_SHORT).show();
            isOpenRead = false;
            b = null;
            b = new Vbar();
        } else {

//            if (!isOpenRead) {
//                try {
//                    readThread2.start();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }

            if (readThread == null || !readThread.isAlive()) {
                readThread = new ReadThread();
                readThread.start();
            } else {
                Log.d(TAG, "readThread isInterrupted:" + readThread.isInterrupted());
                Log.d(TAG, "readThread isAlive:" + readThread.isAlive());
                if (!readThread.isInterrupted()) {
                    //线程未中断
                    if (!readThread.isAlive()) {
                        readThread = new ReadThread();
                        readThread.start();
                    } else {

                    }

                } else {
                    readThread = new ReadThread();
                    readThread.start();
                }
            }
            isOpenRead = true;

        }
    }


    public void scanClose(Context context) {
        getB().closeDev();
        isOpenDevice = false;
        isOpenRead = false;
    }


    private Vbar getB() {
        if (b == null) {
            b = new Vbar();
        }
        return b;
    }

    private class ReadThread extends Thread {

        public void run() {

            while (true) {

                synchronized (this) {
                    String str = b.getResultsingle();
                    if (isOpenRead) {
                        Log.d(TAG, "getResultsingle:" + str);


                        if (str != null) {
                            RxBus.get().post(new Messages(SCAN_MSG, str));
                            isOpenRead = false;

                        }
                    }
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
        }
    }
}
