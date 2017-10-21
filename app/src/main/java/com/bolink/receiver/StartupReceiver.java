package com.bolink.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bolink.MainActivity;


public class StartupReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		 Intent i = new Intent(context,MainActivity.class);
	        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        //将intent以startActivity传送给操作系统
	        context.startActivity(i);
	}

}
