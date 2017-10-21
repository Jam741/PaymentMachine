package com.bolink.hardware;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.widget.Toast;

import com.printsdk.cmd.PrintCmd;
import com.printsdk.usbsdk.UsbDriver;

import com.bolink.R;
import com.bolink.hardware.Print.Constant;
import com.bolink.hardware.Print.Utils;

/**
 * Created by xulu on 2017/9/26.
 */

public class PrintUtil {

    private static class UtilHolder{
        private static final PrintUtil util = new PrintUtil();
    }
    private PrintUtil(){}
    public static PrintUtil get(){
        return UtilHolder.util;
    }

    /*
 *  BroadcastReceiver when insert/remove the device USB plug into/from a USB port
 *  创建一个广播接收器接收USB插拔信息：当插入USB插头插到一个USB端口，或从一个USB端口，移除装置的USB插头
 */
    BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                mSerial.usbAttached(intent);
                mSerial.openUsbDevice(SERIAL_BAUDRATE);
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                mSerial.usbDetached(intent);
            }
        }
    };

    final int SERIAL_BAUDRATE = UsbDriver.BAUD115200;
    UsbDriver mSerial;
    private boolean status_flag = false;
    Context contexts;
    public void init(Context context){
        contexts = context;
        mSerial = new UsbDriver(
                (UsbManager) context.getSystemService(Context.USB_SERVICE), context);
        Utils.getDriver(mSerial, mUsbReceiver, context);
        // 打开设备
        if (mSerial.openUsbDevice(SERIAL_BAUDRATE)) {
            Toast.makeText(context, context.getString(R.string.USB_Driver_Success),
                    Toast.LENGTH_SHORT).show();
            status_flag = true;
        } else {
            Toast.makeText(context, context.getString(R.string.USB_Driver_Failed),
                    Toast.LENGTH_SHORT).show();
            status_flag = false;
        }
    }
    public void print(String content){
        if(CheckPrinterStatus()!=0){
            Toast.makeText(contexts,"CheckPrinterStatus:"+CheckPrinterStatus(),Toast.LENGTH_LONG).show();
            return;
        }

        String etstring = content+contexts.getResources().getString(R.string.Test_print_string);
//        String etstring = content;
        if (etstring != null && !"".equals(etstring)) {
//            getCommonSettings(); // 常规设置
//            byte[] etBytes = PrintCmd.PrintString(etstring, 0);
//            mSerial.write(etBytes, etBytes.length);
            // 小票主要内容
            CleanPrinter();// 清理缓存，缺省模式
            mSerial.write(PrintCmd.PrintString(etstring, 0));
            mSerial.write(PrintCmd.PrintFeedline(2)); // 打印走纸2行
            SetFeedCutClean(cutter);
        }
    }
    // 清理缓存，缺省模式
    private void CleanPrinter() {
//		mSerial.write(PrintCmd.SetClean());             // 清理缓存
        mSerial.write(PrintCmd.SetBold(0));             // 粗体设置
        mSerial.write(PrintCmd.SetAlignment(0));        // 对齐方式
        mSerial.write(PrintCmd.SetSizetext(0, 0));      // 字符大小
    }
    // 常规设置
    // 常用全局变量
    private int rotate = 0;       // 默认为:0, 0 正常、1 90度旋转
    private int align = 0;        // 默认为:1, 0 靠左、1  居中、2:靠右
    private int underLine = 0;    // 默认为:0, 0 取消、   1 下划1、 2 下划2
    private int linespace = 40;   // 默认40, 常用：30 40 50 60
    private int cutter = 1;       // 默认0，  0 全切、1 半切
    private void getCommonSettings(){
        mSerial.write(PrintCmd.SetAlignment(align));    // 对齐方式
        mSerial.write(PrintCmd.SetRotate(rotate));      // 字体旋转
        mSerial.write(PrintCmd.SetUnderline(underLine));// 下划线
        mSerial.write(PrintCmd.SetLinespace(linespace));// 行间距
    }
    // 走纸换行、切纸、清理缓存
    private void SetFeedCutClean(int iMode) {
        mSerial.write(PrintCmd.PrintFeedline(5));      // 走纸换行
        mSerial.write(PrintCmd.PrintCutpaper(iMode));  // 切纸类型
        mSerial.write(PrintCmd.SetClean());            // 清理缓存
    }
    // 检测打印机状态
    private int CheckPrinterStatus() {
        int iRet = -1;
        getMsgByLanguage();
        StringBuilder sMsg = new StringBuilder();

        byte[] bRead1 = new byte[1];
        byte[] bWrite1 = PrintCmd.GetStatus1();
        mSerial.read(bRead1, bWrite1);

        byte[] bRead2 = new byte[1];
        byte[] bWrite2 = PrintCmd.GetStatus2();
        mSerial.read(bRead2, bWrite2);

        byte[] bRead3 = new byte[1];
        byte[] bWrite3 = PrintCmd.GetStatus3();
        mSerial.read(bRead3, bWrite3);

        byte[] bRead4 = new byte[1];
        byte[] bWrite4 = PrintCmd.GetStatus4();
        mSerial.read(bRead4, bWrite4);

        byte[] etBytes = new byte[4];
        int iIndex = 0;
        etBytes[iIndex++] = bRead1[0];
        etBytes[iIndex++] = bRead2[0];
        etBytes[iIndex++] = bRead3[0];
        etBytes[iIndex++] = bRead4[0];

        int iStatus = PrintCmd.CheckStatus(etBytes);
        Toast.makeText(contexts,"PrintCmd.CheckStatus:"+iStatus,Toast.LENGTH_LONG).show();
        // 0 打印机正常 、1 打印机未连接或未上电、2 打印机和调用库不匹配
        // 3 打印头打开 、4 切刀未复位 、5 打印头过热 、6 黑标错误 、7 纸尽 、8 纸将尽
        switch (iStatus) {
            case 0:
                sMsg.append(normal); // 正常
                iRet = 0;
                break;
            case 1:
                sMsg.append(notConnectedOrNotPopwer);
                break;
            case 3:
                sMsg.append(printerHeadOpen); // 打印头打开
                break;
            case 4:
                sMsg.append(cutterNotReset);
                break;
            case 5:
                sMsg.append(printHeadOverheated);
                break;
            case 6:
                sMsg.append(blackMarkError);
                break;
            case 7:
                sMsg.append(paperExh); // 纸尽==缺纸
                break;
            case 8:
                sMsg.append(paperWillExh); // 纸将尽
                iRet = 0;
                break;
            default:
                sMsg.append(abnormal); // 异常
                break;
        }
//        ShowMessage(sMsg.toString());
        return iRet;
    }
    // 通过系统语言判断Message显示
    // 状态相关变量
    private String receive = "", state = "";                                     // 接收提示、状态类型
    private String normal = "",notConnectedOrNotPopwer = "",notMatch = "",       // 对应状态值
            printerHeadOpen = "", cutterNotReset = "", printHeadOverheated = "",
            blackMarkError = "",paperExh = "",paperWillExh = "",abnormal = "",obtainFailed= "";
    private void getMsgByLanguage() {
        if (Utils.isZh(contexts)) {
            receive = Constant.Receive_CN;
            state = Constant.State_CN;
            normal = Constant.Normal_CN;
            notConnectedOrNotPopwer = Constant.NoConnectedOrNoOnPower_CN;
            notMatch = Constant.PrinterAndLibraryNotMatch_CN;
            printerHeadOpen = Constant.PrintHeadOpen_CN;
            cutterNotReset = Constant.CutterNotReset_CN;
            printHeadOverheated = Constant.PrintHeadOverheated_CN;
            blackMarkError = Constant.BlackMarkError_CN;
            paperExh = Constant.PaperExhausted_CN;
            paperWillExh = Constant.PaperWillExhausted_CN;
            abnormal = Constant.Abnormal_CN;
            obtainFailed = Constant.StateObtainFailed_CN;
        } else {
            receive = Constant.Receive_US;
            state = Constant.State_US;
            normal = Constant.Normal_US;
            notConnectedOrNotPopwer = Constant.NoConnectedOrNoOnPower_US;
            notMatch = Constant.PrinterAndLibraryNotMatch_US;
            printerHeadOpen = Constant.PrintHeadOpen_US;
            cutterNotReset = Constant.CutterNotReset_US;
            printHeadOverheated = Constant.PrintHeadOverheated_US;
            blackMarkError = Constant.BlackMarkError_US;
            paperExh = Constant.PaperExhausted_US;
            paperWillExh = Constant.PaperWillExhausted_US;
            abnormal = Constant.Abnormal_US;
            obtainFailed = Constant.StateObtainFailed_US;
        }
    }
    public void Destroy(){
        contexts = null;
    }
}
