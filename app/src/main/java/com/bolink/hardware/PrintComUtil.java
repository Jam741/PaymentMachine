package com.bolink.hardware;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.bolink.R;
import com.bolink.bean.Messages;
import com.bolink.hardware.Print.Constant;
import com.bolink.hardware.PrintCom.ComBean;
import com.bolink.hardware.PrintCom.MyFunc;
import com.bolink.hardware.PrintCom.SerialHelper;
import com.bolink.rx.RxBus;
import com.bolink.utils.CommontUtils;
import com.printsdk.cmd.PrintCmd;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

/**
 * Created by xulu on 2017/9/29.
 */

public class PrintComUtil {
    private static class PrintComHolder{
        private static final PrintComUtil holder = new PrintComUtil();
    }
    private PrintComUtil(){};
    public static final PrintComUtil get(){
        return  PrintComHolder.holder;
    }
    SerialControl ComA;//串口
    Context contexts;
    public void init(Context context){
        contexts = context;
        ComA = new SerialControl();
        ComA.setbLoopData(PrintCmd.GetStatus4());

//        DispQueue = new DispQueueThread();
//        DispQueue.start();

        ComA.setPort(context.getResources().getString(R.string.print_device));
        ComA.setBaudRate(context.getResources().getString(R.string.print_baud_rate));
        OpenComPort(ComA);
    }
    //----------------------------------------------------打开串口
    private void OpenComPort(SerialHelper ComPort) {
        try {
            ComPort.open();
        } catch (SecurityException e) {
            RxBus.get().post(new Messages(Messages.PRINT_MSG,"No_read_or_write_permissions"));
//            ShowMessage(getString(R.string.No_read_or_write_permissions));
        } catch (IOException e) {
//            ShowMessage(getString(R.string.Unknown_error));
            RxBus.get().post(new Messages(Messages.PRINT_MSG,"Unknown_error"));
        } catch (InvalidParameterException e) {
//            ShowMessage(getString(R.string.Parameter_error));
            RxBus.get().post(new Messages(Messages.PRINT_MSG,"Parameter_error"));
        }
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
                    ((Activity)contexts).runOnUiThread(new Runnable() {
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
    int m_iStatusCount=0;	//返回状态计时
    private void DispRecData(ComBean ComRecData) {
//        m_blnStatus = false;
        m_iStatusCount = 0;
        getMsgByLanguage(); // 根据系统语言获取状态类型
        StringBuilder sMsg = new StringBuilder();
        try {
            sMsg.append(receive);
            sMsg.append(MyFunc.ByteArrToHex(ComRecData.bRec));
            m_iRecValue = -1;
            m_iRecValue = PrintCmd.CheckStatus4(ComRecData.bRec[0]);
//			Toast.makeText(MainActivity.this, "状态返回值：" + m_iRecValue, Toast.LENGTH_LONG).show();
            byte iValue = ComRecData.bRec[0];
            sMsg.append(state);
            switch(m_iRecValue)
            {
                case 0:
                    sMsg.append(normal);
                    break;
                case 7:
                    sMsg.append(paperExh);
                    errmsg=paperExh;
                    break;
                case 8:
                    sMsg.append(paperWillExh);
                    break;
                default:
//				sMsg.append(abnormal);
                    break;
            }

//            ShowMessage(sMsg.toString());

//            if (m_iRecValue == 8 || m_iRecValue == 0) {
//                // ShowMessage("打印Demo...");
//                QRCodeInfo codeInfo = new QRCodeInfo();
//                codeInfo.setmSide(2);
//                byte[] bQRcode = codeInfo.GetQRBCode("123456abcd", 1);
//                ComA.send(bQRcode);
//                PrintBankQueue();// 银行小票打印
//            }
        } catch (Exception ex) {
            Log.d("DispRecData:",ex.getMessage());
        }
    }
    static String errmsg = "print value 7";
    // 通过系统语言判断Message显示
    String receive = "", state = ""; // 接收提示、状态类型
    String normal = "",paperExh = "",paperWillExh = "",abnormal = "";
    private void getMsgByLanguage() {
        if (isZh()) {
            receive = Constant.Receive_CN;
            state = Constant.State_CN;
            normal = Constant.Normal_CN;
            paperExh = Constant.PaperExhausted_CN;
            paperWillExh = Constant.PaperWillExhausted_CN;
            abnormal = Constant.Abnormal_CN;
        } else {
            receive = Constant.Receive_US;
            state = Constant.State_US;
            normal = Constant.Normal_US;
            paperExh = Constant.PaperExhausted_US;
            paperWillExh = Constant.PaperWillExhausted_US;
            abnormal = Constant.Abnormal_US;
        }
    }
    // 获取当前系统的语言环境
    private boolean isZh() {
        Locale locale = contexts.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }
    public void print(){
//        CommontUtils.writeSDFile("print","status :"+m_iRecValue);
        if (m_iRecValue != 7) {
            // ShowMessage("打印Demo...");
            PrintBankQueue(contexts.getResources().getString(R.string.PrintTest_btn),"100","这是内容这是内容这是内容这是内容这是内容这是内容这是内容");// 银行小票打印
            ComA.startSend();
//            CommontUtils.writeSDFile("print","send finish :");
        }else{
            RxBus.get().post(new Messages(Messages.PRINT_MSG,errmsg));
        }    }
    public void checkstatus(){

    }
    public void PrintTicket(String title,String content,String tale){
        try {
            CleanPrinter();

            ComA.send(PrintCmd.SetAlignment(1));
            ComA.send(PrintCmd.SetSizetext(1, 1));
            ComA.send(PrintCmd.PrintString(title, 0));

            ComA.send(PrintCmd.PrintFeedline(1));

            ComA.send(PrintCmd.SetAlignment(0));
            ComA.send(PrintCmd.SetSizetext(0, 0));
            ComA.send(PrintCmd.PrintString(content, 0));

//            ComA.send(PrintCmd.PrintFeedline(1));

            ComA.send(PrintCmd.PrintString(contexts.getResources().getString(R.string.ads_line), 0));

//            ComA.send(PrintCmd.PrintFeedline(1));

            ComA.send(PrintCmd.PrintString(contexts.getResources().getString(R.string.ads_tail_head)+tale+contexts.getResources().getString(R.string.ads_tail), 0));

            PrintFeedCutpaper(6);
        }catch (Exception e){
            CommontUtils.writeSDFile("printException",e.getMessage());
        }
    }
    /**
     *  1.打印银行排队办理业务排队单
     */
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); // 国际化标志时间格式类
    private void PrintBankQueue(String title,String num,String strData) {
//        getStrDataByLanguage();
        try {
            // 小票标题
//            CommontUtils.writeSDFile("print","clean cache :");
            CleanPrinter(); // 清理缓存，缺省模式
            ComA.send(PrintCmd.SetAlignment(1));
            ComA.send(PrintCmd.SetSizetext(1, 1));
            ComA.send(PrintCmd.PrintString(title, 0));

            ComA.send(PrintCmd.SetAlignment(0));
            ComA.send(PrintCmd.SetSizetext(0, 0));
//            CommontUtils.writeSDFile("print","title over :");
            // 小票号码
            ComA.send(PrintCmd.SetBold(1));
            ComA.send(PrintCmd.SetSizetext(1, 1));
            ComA.send(PrintCmd.SetAlignment(1));
            ComA.send(PrintCmd.PrintString(num, 0));
            ComA.send(PrintCmd.SetBold(0));
            ComA.send(PrintCmd.SetSizetext(0, 0));
            ComA.send(PrintCmd.SetAlignment(0));
            // 小票主要内容
            ComA.send(PrintCmd.PrintString(strData, 0));
            // 二维码
            ComA.send(PrintCmd.PrintFeedline(2));
//            PrintQRCode();  // 二维码打印
            ComA.send(PrintCmd.PrintQrcode("12345", 10, 3, 0));
            ComA.send(PrintCmd.PrintFeedline(2));
            // 日期时间
            ComA.send(PrintCmd.SetAlignment(2));
            ComA.send(PrintCmd.PrintString(sdf.format(new Date()).toString() + "\n\n", 1));
            ComA.send(PrintCmd.SetAlignment(0));
            // 一维条码
            ComA.send(PrintSelfcheck(true));
            ComA.send(PrintCmd.SetAlignment(1));
            ComA.send(PrintCmd.Print1Dbar(2, 100, 0, 2, 10, "  012345678910"));       //  CODE128   6
            ComA.send(PrintCmd.SetAlignment(0));
            ComA.send(PrintSelfcheck(false));
            ComA.send(PrintCmd.PrintFeedline(2));
// // 一维条码
//            ComA.send(PrintSelfcheck(true));
//            ComA.send(PrintCmd.SetAlignment(1));
//            ComA.send(PrintCmd.Print1Dbar(2, 100, 0, 2, 10, "  01234567899"));       //  CODE128   6
//            ComA.send(PrintCmd.SetAlignment(0));
//            ComA.send(PrintSelfcheck(false));
//            ComA.send(PrintCmd.PrintFeedline(2));
// // 一维条码
//            ComA.send(PrintSelfcheck(true));
//            ComA.send(PrintCmd.SetAlignment(1));
//            ComA.send(PrintCmd.Print1Dbar(2, 100, 0, 2, 10, "  01234567898"));       //  CODE128   6
//            ComA.send(PrintCmd.SetAlignment(0));
//            ComA.send(PrintSelfcheck(false));
//            ComA.send(PrintCmd.PrintFeedline(2));
// // 一维条码
//            ComA.send(PrintSelfcheck(true));
//            ComA.send(PrintCmd.SetAlignment(1));
//            ComA.send(PrintCmd.Print1Dbar(2, 100, 0, 2, 10, "  01234567897"));       //  CODE128   6
//            ComA.send(PrintCmd.SetAlignment(0));
//            ComA.send(PrintSelfcheck(false));
//            ComA.send(PrintCmd.PrintFeedline(2));
// // 一维条码
//            ComA.send(PrintSelfcheck(true));
//            ComA.send(PrintCmd.SetAlignment(1));
//            ComA.send(PrintCmd.Print1Dbar(2, 100, 0, 2, 10, "  01234567896"));       //  CODE128   6
//            ComA.send(PrintCmd.SetAlignment(0));
//            ComA.send(PrintSelfcheck(false));
//            ComA.send(PrintCmd.PrintFeedline(2));
// // 一维条码
//            ComA.send(PrintSelfcheck(true));
//            ComA.send(PrintCmd.SetAlignment(1));
//            ComA.send(PrintCmd.Print1Dbar(2, 100, 0, 2, 10, "  01234567895"));       //  CODE128   6
//            ComA.send(PrintCmd.SetAlignment(0));
//            ComA.send(PrintSelfcheck(false));
//            ComA.send(PrintCmd.PrintFeedline(2));
// // 一维条码
//            ComA.send(PrintSelfcheck(true));
//            ComA.send(PrintCmd.SetAlignment(1));
//            ComA.send(PrintCmd.Print1Dbar(2, 100, 0, 2, 10, "  01234567894"));       //  CODE128   6
//            ComA.send(PrintCmd.SetAlignment(0));
//            ComA.send(PrintSelfcheck(false));
//            ComA.send(PrintCmd.PrintFeedline(2));
// // 一维条码
//            ComA.send(PrintSelfcheck(true));
//            ComA.send(PrintCmd.SetAlignment(1));
//            ComA.send(PrintCmd.Print1Dbar(2, 100, 0, 2, 10, "  01234567893"));       //  CODE128   6
//            ComA.send(PrintCmd.SetAlignment(0));
//            ComA.send(PrintSelfcheck(false));
//            ComA.send(PrintCmd.PrintFeedline(2));
// // 一维条码
//            ComA.send(PrintSelfcheck(true));
//            ComA.send(PrintCmd.SetAlignment(1));
//            ComA.send(PrintCmd.Print1Dbar(2, 100, 0, 2, 10, "  01234567892"));       //  CODE128   6
//            ComA.send(PrintCmd.SetAlignment(0));
//            ComA.send(PrintSelfcheck(false));
//            ComA.send(PrintCmd.PrintFeedline(2));
// // 一维条码
//            ComA.send(PrintSelfcheck(true));
//            ComA.send(PrintCmd.SetAlignment(1));
//            ComA.send(PrintCmd.Print1Dbar(2, 100, 0, 2, 10, "  01234567891"));       //  CODE128   6
//            ComA.send(PrintCmd.SetAlignment(0));
//            ComA.send(PrintSelfcheck(false));
//            ComA.send(PrintCmd.PrintFeedline(2));

            // 走纸4行,再切纸,清理缓存
            PrintFeedCutpaper(8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 打印自检页 指令发送函数示例
    public static byte[] PrintSelfcheck(boolean center){
        byte[] bCmd = new byte[3];
        int iIndex=0;
        bCmd[iIndex++]=0x1D;
        bCmd[iIndex++]=0x50;
        if(center){
            bCmd[iIndex++]=0x01;
        }else{
            bCmd[iIndex++]=0x00;
        }
        return bCmd;
    }
    // 走纸换行，再切纸，清理缓存
    private void PrintFeedCutpaper(int iLine) throws IOException {
        ComA.send(PrintCmd.PrintFeedline(iLine));
        ComA.send(PrintCmd.PrintCutpaper(1));
        ComA.send(PrintCmd.SetClean());
    }
    // 清理缓存，缺省模式
    private void CleanPrinter() {
        ComA.send(PrintCmd.SetClean());
    }
    public void Destroy(){
        if (ComA != null) {
            ComA.stopSend();
            ComA.close();
        }
        contexts = null;
    }
}
