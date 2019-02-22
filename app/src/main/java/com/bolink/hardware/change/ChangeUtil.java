package com.bolink.hardware.change;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.bolink.R;
import com.bolink.bean.Messages;
import com.bolink.hardware.PrintCom.ComBean;

import com.bolink.hardware.PrintCom.MyFunc;
import com.bolink.rx.RxBus;
import com.printsdk.cmd.PrintCmd;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.Queue;

import static com.bolink.bean.Messages.RESPONSE_SPECIE_COUNT;
import static com.bolink.bean.Messages.VERSION_CODE;

/**
 * Created by xulu on 2017/9/29.
 */

public class ChangeUtil {
    private static class ChangeHolder {
        private static final ChangeUtil holder = new ChangeUtil();
    }

    private ChangeUtil() {
    }


    public static final ChangeUtil get() {
        return ChangeHolder.holder;
    }

    SerialControl ComA;//串口
    Context contexts;

    public void init(Context context) {
        contexts = context;
        ComA = new SerialControl();

        DispQueue = new DispQueueThread();
        DispQueue.start();

        ComA.setPort(context.getResources().getString(R.string.change_device));
        ComA.setBaudRate(context.getResources().getString(R.string.change_baud_rate));
        ComA.setN81("8", "1", "E");
        OpenComPort(ComA);

    }

    /**
     * 获取零钱数量
     */
    public void requestSpecieCount() {
        byte[] msg = new byte[6];//05 10 00 11 00
        msg[0] = 0x05;
        msg[1] = 0x10;
        msg[2] = 0x00;
        msg[3] = 0x11;
        msg[4] = 0x00;

        for (int i = 0; i < msg.length - 1; i++) {
            msg[5] += msg[i];
        }

        String comdStr = MyFunc.ByteArrToHex(msg);

        Log.d("CHANGE", "requestSpecieCount：" + comdStr.replace(" ", ""));
        Log.d("CHANGE", "requestSpecieCount：" + comdStr.trim());

        sendPortData(ComA, comdStr.replace(" ", ""));
    }


    /**
     * 出币
     */
    public void requestSpecieOut(int count) {

        byte[] msg = new byte[6];//05 10 00 11 00
        msg[0] = 0x05;
        msg[1] = 0x10;
        msg[2] = 0x00;
        msg[3] = 0x10;
        msg[4] = (byte) count;

        for (int i = 0; i < msg.length - 1; i++) {
            msg[5] += msg[i];
        }


        String comdStr = MyFunc.ByteArrToHex(msg);

        Log.d("CHANGE", "requestSpecieOut：" + comdStr.replace(" ", ""));
        Log.d("CHANGE", "requestSpecieOut：" + comdStr.trim());

        sendPortData(ComA, comdStr.replace(" ", ""));

    }

    private void ShowMessage(String msg) {
        Toast.makeText(contexts, msg, Toast.LENGTH_SHORT).show();
    }

    //----------------------------------------------------串口控制类
    DispQueueThread DispQueue;//刷新显示线程

    private class SerialControl extends SerialHelper {
        public SerialControl() {
        }

        @Override
        protected void onDataReceived(final ComBean ComRecData) {
            DispQueue.AddQueue(ComRecData);// 线程定时刷新显示(推荐)
        }
    }

    //----------------------------------------------------刷新显示线程
    private class DispQueueThread extends Thread {
        private Queue<ComBean> QueueList = new LinkedList<ComBean>();

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                final ComBean ComData;
                while ((ComData = QueueList.poll()) != null) {
                    ((Activity) contexts).runOnUiThread(new Runnable() {
                        public void run() {
                            DispRecData(ComData);
                        }
                    });
                    try {
                        Thread.sleep(200);// 显示性能高的话，可以把此数值调小。
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        public synchronized void AddQueue(ComBean ComData) {
            QueueList.add(ComData);
        }
    }

    //----------------------------------------------------显示接收数据
    int m_iRecValue = -1;//接收值
    int m_iStatusCount = 0;    //返回状态计时

    private void DispRecData(ComBean ComRecData) {

        m_iStatusCount = 0;

        StringBuilder sMsg = new StringBuilder();

        try {

            sMsg.append(MyFunc.ByteArrToHex(ComRecData.bRec));

            String recStr = MyFunc.ByteArrToHex(ComRecData.bRec);

            String[] recStrArr = recStr.split(" ");

            String com = recStrArr[3];

            if (com.equals("AA")) {
                //出币回调
            } else if (com.equals("04")) {
                //查询回调
                String data = recStrArr[4];
                if (data.equals("00")) {
                    //不缺币
                    Log.d("CHANGE", "不缺币 ：" + data);
                    RxBus.get().post(new Messages(RESPONSE_SPECIE_COUNT, 0));
                } else {
                    //缺币
                    Log.d("CHANGE", "缺币 ：" + data);
                    RxBus.get().post(new Messages(RESPONSE_SPECIE_COUNT, 2));
                }

            }

            Log.d("CHANGE", MyFunc.ByteArrToHex(ComRecData.bRec));
        } catch (Exception ex) {
            Log.d("DispRecData:", ex.getMessage());
        }
    }


    public void Destroy() {
        if (ComA != null) {
            CloseComPort(ComA);
        }
        contexts = null;
    }


    //----------------------------------------------------关闭串口
    private void CloseComPort(SerialHelper ComPort) {
        if (ComPort != null) {
            ComPort.stopSend();
            ComPort.close();
        }
    }

    //----------------------------------------------------打开串口
    private void OpenComPort(SerialHelper ComPort) {
        try {
            ComPort.open();
        } catch (SecurityException e) {
            ShowMessage("打开串口失败:没有串口读/写权限!");
        } catch (IOException e) {
            ShowMessage("打开串口失败:未知错误!");
        } catch (InvalidParameterException e) {
            ShowMessage("打开串口失败:参数错误!");
        }
    }


    //----------------------------------------------------串口发送
    private void sendPortData(SerialHelper ComPort, String sOut) {
        if (ComPort != null && ComPort.isOpen()) {
            if (false) {
                ComPort.sendTxt(sOut);
            } else {
                ComPort.sendHex(sOut);
            }
        }
    }
}
