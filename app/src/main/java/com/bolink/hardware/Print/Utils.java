package com.bolink.hardware.Print;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;

import com.printsdk.usbsdk.UsbDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class Utils {
	/**
	 * USB驱动操作
	 *
	 * @param mFtDriver
	 * @param mUsbReceiver
	 * @param context
	 */
	public static void getDriver(UsbDriver mFtDriver,
								 BroadcastReceiver mUsbReceiver, Context context) {
		PendingIntent permissionIntent = PendingIntent.getBroadcast(context, 0,
				new Intent("jp.ksksue.sample.USB_PERMISSION"), 0);
		mFtDriver.setPermissionIntent(permissionIntent);
		// Broadcast listen for new devices
		IntentFilter filter = new IntentFilter();
		filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
		filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
		context.registerReceiver(mUsbReceiver, filter);
	}

	/**
	 * 获取当前系统的语言环境
	 *
	 * @param context
	 * @return boolean
	 */
	public static boolean isZh(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		if (language.endsWith("zh"))
			return true;
		else
			return false;
	}

	/**
	 * 获取Assets子文件夹下的文件数据流数组InputStream[]
	 *
	 * @param context
	 * @return InputStream[]
	 */
	@SuppressWarnings("unused")
	private static InputStream[] getAssetsImgaes(String imgPath, Context context) {
		String[] list = null;
		InputStream[] arryStream = null;
		try {
			list = context.getResources().getAssets().list(imgPath);
			arryStream = new InputStream[3];
			for (int i = 0; i < list.length; i++) {
				InputStream is = context.getResources().getAssets()
						.open(imgPath + File.separator + list[i]);
				arryStream[i] = is;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arryStream;
	}

	/*
	 * 未转换为十六进制字节的字符串
	 *
	 * @param paramString
	 *
	 * @return byte[]
	 */
	public static byte[] hexStr2Bytesnoenter(String paramString) {
		String[] paramStr = paramString.split(" ");
		byte[] arrayOfByte = new byte[paramStr.length];

		for (int j = 0; j < paramStr.length; j++) {
			arrayOfByte[j] = Integer.decode("0x" + paramStr[j]).byteValue();
		}
		return arrayOfByte;
	}

	/**
	 * 统计指定字符串中某个符号出现的次数
	 *
	 * @param str
	 * @return int
	 */
	public static int Count(String strData, String str) {
		int iBmpNum = 0;
		for (int i = 0; i < strData.length(); i++) {
			String getS = strData.substring(i, i + 1);
			if (getS.equals(str)) {
				iBmpNum++;
			}
		}
		System.out.println(str + "出现了:" + iBmpNum + "次");
		return iBmpNum;
	}
}
