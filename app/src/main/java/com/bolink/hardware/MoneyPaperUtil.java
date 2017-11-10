package com.bolink.hardware;

import android.content.Context;
import android.util.Log;

import com.bolink.R;
import com.bolink.bean.Messages;
import com.bolink.hardware.PaperMoneyITL.ITLDeviceCom;
import com.bolink.rx.RxBus;
import com.ftdi.j2xx.D2xxManager;
import com.ftdi.j2xx.FT_Device;

import static com.bolink.bean.Messages.PAPER_MONEY_OPEN;

/**
 * Created by xulu on 2017/9/26.
 */

public class MoneyPaperUtil {

    public static ITLDeviceCom deviceCom;
    public static D2xxManager ftD2xx = null;
    public static FT_Device ftDev = null;

    private static class SingletonHolder {
        private static final MoneyPaperUtil holder = new MoneyPaperUtil();
    }

    private MoneyPaperUtil() {
    }

    static Context contexts;

    public static final MoneyPaperUtil get() {

        return SingletonHolder.holder;
    }

    public static int count = 0;

    public void init(Context context) {
        contexts = context;
//        CommontUtils.writeSDFile("moneyutil open begin", System.currentTimeMillis() + "");

        deviceCom = new ITLDeviceCom();
        try {
            ftD2xx = D2xxManager.getInstance(contexts);
        } catch (D2xxManager.D2xxException ex) {
            Log.e("SSP FTmanager", ex.toString());
        }
//        ftDev = ftD2xx.openByIndex(contexts, 0);
        openDevice();
        if (ftDev != null) {
            deviceCom.setup(ftDev, 0, false, false, 0);
            deviceCom.start();
//            Toast.makeText(context,"ITL success",Toast.LENGTH_LONG).show();

        } else {
//            Toast.makeText(MainActivity.this, "No USB connection detected!", Toast.LENGTH_SHORT).show();
            count++;
            if (count > 1)
                RxBus.get().post(new Messages(PAPER_MONEY_OPEN, context.getResources().getString(R.string.msg_open_cash_result)));
//            Toast.makeText(context,"ITL fail",Toast.LENGTH_LONG).show();
        }
        Open();
    }

    private void openDevice() {
        if (ftDev != null) {
            if (ftDev.isOpen()) {
                // if open and run thread is stopped, start thread
                SetConfig(9600, (byte) 8, (byte) 2, (byte) 0, (byte) 0);
                ftDev.purge((byte) (D2xxManager.FT_PURGE_TX | D2xxManager.FT_PURGE_RX));
                ftDev.restartInTask();
                return;
            }
        }

        int devCount = 0;

        if (ftD2xx != null) {
            // Get the connected USB FTDI devoces
            devCount = ftD2xx.createDeviceInfoList(contexts);
        } else {
            return;
        }

        D2xxManager.FtDeviceInfoListNode[] deviceList = new D2xxManager.FtDeviceInfoListNode[devCount];
        ftD2xx.getDeviceInfoList(devCount, deviceList);
        // none connected
        if (devCount <= 0) {
            return;
        }
        if (ftDev == null) {
            ftDev = ftD2xx.openByIndex(contexts, 0);
        } else {
            synchronized (ftDev) {
                ftDev = ftD2xx.openByIndex(contexts, 0);
            }
        }
        // run thread
        if (ftDev.isOpen()) {
            SetConfig(9600, (byte) 8, (byte) 2, (byte) 0, (byte) 0);
            ftDev.purge((byte) (D2xxManager.FT_PURGE_TX | D2xxManager.FT_PURGE_RX));
            ftDev.restartInTask();
        }
    }

    public static void SetConfig(int baud, byte dataBits, byte stopBits, byte parity, byte flowControl) {
        if (!ftDev.isOpen()) {
            return;
        }

        // configure our port
        // reset to UART mode for 232 devices
        ftDev.setBitMode((byte) 0, D2xxManager.FT_BITMODE_RESET);
        ftDev.setBaudRate(baud);

        switch (dataBits) {
            case 7:
                dataBits = D2xxManager.FT_DATA_BITS_7;
                break;
            case 8:
                dataBits = D2xxManager.FT_DATA_BITS_8;
                break;
            default:
                dataBits = D2xxManager.FT_DATA_BITS_8;
                break;
        }

        switch (stopBits) {
            case 1:
                stopBits = D2xxManager.FT_STOP_BITS_1;
                break;
            case 2:
                stopBits = D2xxManager.FT_STOP_BITS_2;
                break;
            default:
                stopBits = D2xxManager.FT_STOP_BITS_1;
                break;
        }

        switch (parity) {
            case 0:
                parity = D2xxManager.FT_PARITY_NONE;
                break;
            case 1:
                parity = D2xxManager.FT_PARITY_ODD;
                break;
            case 2:
                parity = D2xxManager.FT_PARITY_EVEN;
                break;
            case 3:
                parity = D2xxManager.FT_PARITY_MARK;
                break;
            case 4:
                parity = D2xxManager.FT_PARITY_SPACE;
                break;
            default:
                parity = D2xxManager.FT_PARITY_NONE;
                break;
        }

        ftDev.setDataCharacteristics(dataBits, stopBits, parity);

        short flowCtrlSetting;
        switch (flowControl) {
            case 0:
                flowCtrlSetting = D2xxManager.FT_FLOW_NONE;
                break;
            case 1:
                flowCtrlSetting = D2xxManager.FT_FLOW_RTS_CTS;
                break;
            case 2:
                flowCtrlSetting = D2xxManager.FT_FLOW_DTR_DSR;
                break;
            case 3:
                flowCtrlSetting = D2xxManager.FT_FLOW_XON_XOFF;
                break;
            default:
                flowCtrlSetting = D2xxManager.FT_FLOW_NONE;
                break;
        }

        ftDev.setFlowControl(flowCtrlSetting, (byte) 0x0b, (byte) 0x0d);
    }

    public void Open() {
        deviceCom.SetDeviceEnable(true);
//        CommontUtils.writeSDFile("moneyutil open end", System.currentTimeMillis() + "");
//        try {
//            Thread.sleep(1000);
//        RxBus.get().post(new Messages(JS_LOADING_CANCEL, ""));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    public void Close() {
        if (deviceCom != null)
            deviceCom.SetDeviceEnable(false);
    }

    public void Destroy() {
        deviceCom = null;
        ftD2xx = null;
        ftDev = null;
        contexts = null;
    }
}
