package com.bolink.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xulu on 2017/10/24.
 */

public class SharedPreferenceUtil {
    private static SharedPreferences sp = null;

    public static SharedPreferences get(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences("ads", Context.MODE_PRIVATE);
        }
        return sp;
    }

    public static void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(String key, boolean defaultvaule) {
        return sp.getBoolean(key, defaultvaule);
    }

}
