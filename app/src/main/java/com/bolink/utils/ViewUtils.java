package com.bolink.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by caobin on 2017/8/25.
 */

public class ViewUtils {
    private static final String TAG = "ViewUtils";
    public static int getScreenWidth(Activity activity){
        //context.getWindowManager().getDefaultDisplay().getWidth();//被废弃
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//        Log.e(TAG, "getScreenWidth: "+width+"<>"+height );
        return width;
    }
    public static int getVideoHeight(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        return width*9/16;
    }
}
