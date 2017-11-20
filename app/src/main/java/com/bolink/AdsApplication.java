package com.bolink;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by xl on 2017/8/21.
 */

public class AdsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        System.out.println("appppppplication0:"+System.currentTimeMillis());
        //因为守护进程是单独运行的，在启动的时候也会运行application，但是对于守护进程是没有意义的。这里判断下进程，如果是主进程就走初始化，如果是守护进程就跳过这个步骤
        String process = getProcessName();
//        System.out.println(process);
        if(process!=null&&process.contains(getResources().getString(R.string.target_package_name)))
            return;
//        System.out.println("not watcher,go on");
//        UMConfigure.init(this, "5a01714caed1792d8d00011e", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
//        UMConfigure.setLogEnabled(true);

//        JAnalyticsInterface.setDebugMode(true);
//        JAnalyticsInterface.init(this);
//        JAnalyticsInterface.initCrashHandler(this);
//        System.out.println("appppppplication1:"+System.currentTimeMillis());
        CrashReport.initCrashReport(getApplicationContext(), "ac20af9ca0", false);
//        System.out.println("appppppplication2:"+System.currentTimeMillis());


//        try {
//            InputStream inputStream = getAssets().open("litepal.xml");
//            DataBaseInfo dataBaseInfo = UpdataInfoParser.getDataBaseInfo(inputStream);
//            int version = Integer.parseInt(dataBaseInfo.getVersion());
//            if (version < 8) {
//                String adsDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ads";
//                File database = new File(adsDir, "adsDB.db");
//                if (database.exists())
//                    database.delete();
//                File datatemp = new File(adsDir, "adsDB.db-journal");
//                if (datatemp.exists())
//                    datatemp.delete();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        System.out.println("appppppplication3:"+System.currentTimeMillis());
        LitePal.initialize(this);
//        System.out.println("appppppplication4:"+System.currentTimeMillis());
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
//        System.out.println("appppppplication5:"+System.currentTimeMillis());
//
//        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//
//            @Override
//            public void onViewInitFinished(boolean arg0) {
//                // TODO Auto-generated method stub
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Log.d("app", " onViewInitFinished is " + arg0);
//            }
//
//            @Override
//            public void onCoreInitFinished() {
//                // TODO Auto-generated method stub
//            }
//        };
//        //x5内核初始化接口
//        QbSdk.initX5Environment(getApplicationContext(),  cb);
//        ImageLoader.init(this,250*1024*1024, new GlideLoader());//指定缓存大小,以及使用哪个loader
    }

    public String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/cmdline");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String processName = bufferedReader.readLine().trim();
            bufferedReader.close();
            return processName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
