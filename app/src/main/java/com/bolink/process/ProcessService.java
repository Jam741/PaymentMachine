package com.bolink.process;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.bolink.MainActivity;
import com.bolink.R;
import com.bolink.utils.CommontUtils;

/**
 * Created by xulu on 2017/10/31.
 */

public class ProcessService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //    Context context;
    private static boolean isdetecte = false;

    @Override
    public void onCreate() {
        super.onCreate();
//        context = this;
        System.out.println("create");

//        Notification notification = new Notification(R.mipmap.ic_launcher, "守护中...", System.currentTimeMillis());

        final Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("守护程序")
                .setContentText("守护中")
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis());
        Notification notification = builder.build();
        startForeground(1, notification);

        if (!isdetecte) {
            startDetect();
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("startDetect");
        intentFilter.addAction("stopDetect");

        registerReceiver(receiver, intentFilter);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            System.out.println("PPPPPPPP get receiver action:" + action);
            if (action.equals("startDetect")) {
                if (!isdetecte) {
                    startDetect();
                }
            } else if (action.equals("stopDetect")) {
                isdetecte = false;
            }
        }
    };

    private void startDetect() {
        isdetecte = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isdetecte) {
                    if (!CommontUtils.IsProcessRunningAndFront(getResources().getString(R.string.target_package_name_guard), ProcessService.this)) {
                        Intent startup = new Intent();
                        startup.setClassName(getResources().getString(R.string.target_package_name_guard), getResources().getString(R.string.target_package_activity_guard));
                        startup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startup);
                    }

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        isdetecte = false;
        System.out.println("destroy");
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
