package com.bolink.hardware;

import android.content.Context;

import com.bolink.R;
import com.bolink.bean.Messages;
import com.bolink.hardware.Scan.SerialPort;
import com.bolink.hardware.Scan.SerialPortFinder;
import com.bolink.rx.RxBus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.Timer;
import java.util.TimerTask;

import static com.bolink.bean.Messages.SCAN_MSG;

/**
 * Created by xulu on 2017/9/26.
 */

public class ScanUtil {

    private static class SingltonHolder{
        private static final ScanUtil util = new ScanUtil();
    }
    private ScanUtil(){}
    public static ScanUtil get(){
        return SingltonHolder.util;
    }

    public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
    private SerialPort mSerialPort = null;
    Context context;
    protected OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    public void init(Context context){
        this.context = context;
        if (mSerialPort == null) {

            String path = context.getString(R.string.scan_device);
            int baudrate = Integer.decode(context.getString(R.string.scan_baud_rate));

			/* Check parameters */
            if ( (path.length() == 0) || (baudrate == -1)) {
                throw new InvalidParameterException();
            }

			/* Open the serial port */
            try {
                mSerialPort = new SerialPort(new File(path), baudrate, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {

            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();

			/* Create a receiving thread */
            mReadThread = new ReadThread();
            mReadThread.start();
        } catch (SecurityException e) {
//            DisplayError(R.string.error_security);
        } catch (InvalidParameterException e) {
//            DisplayError(R.string.error_configuration);
        }

    }
    private class ReadThread extends Thread {
        String msg = "";
        @Override
        public void run() {
            super.run();

            while(!isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[64];
                    if (mInputStream == null) return;
                    size = mInputStream.read(buffer);

                    if (size > 0) {
//                        onDataReceived(buffer, size);
//
//                        if (mReception != null) {
//                            if(size > 3 && buffer[0] == 85 && buffer[1] == -86){
//
//                                mReception.append(bytesToHexString(buffer, size).toUpperCase());
//                            }else{
//                                mReception.append(new String(buffer, 0, size));
//                            }
//                        }
                        String s =new String(buffer, 0, size);
                        msg+=s;
//                        CommontUtils.writeSDFile("  "+size+"scan:",s);
//                        CommontUtils.writeSDFile("scan-msg:",msg);

//                        RxBus.get().post(new Messages(SCAN_MSG,new String(buffer, 0, size)));
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                RxBus.get().post(new Messages(SCAN_MSG,msg));
                                msg = "";
                            }
                        },1000);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
//            RxBus.get().post(new Messages(SCAN_MSG,msg));
        }
    }
    public void Destroy(){
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
        context = null;
    }
}
