package com.bolink.hardware;

import android.content.Context;
import android.content.Intent;

/**
 * Created by xulu on 2017/9/29.
 */

public class AndroidRom {
    private static final String HIDE_SYSTEMBAR = "com.outform.hidebar";
    private static final String UNHIDE_SYSTEMBAR = "com.outform.unhidebar";

//    Intent hidebar = new Intent(HIDE_SYSTEMBAR);
//    sendBroadcast(hidebar);
//
//    Intent unhidebar = new Intent(UNHIDE_SYSTEMBAR);
//    getActivity().sendBroadcast(unhidebar);
//

    private static class SingleHolder {
        private static final AndroidRom hollder = new AndroidRom();
    }

    private AndroidRom() {
    }

    public static AndroidRom get() {
        return SingleHolder.hollder;
    }

    public void LockBar(Context context) {
        Intent hidebar = new Intent(HIDE_SYSTEMBAR);
        context.sendBroadcast(hidebar);
    }

    public void UnLockBar(Context context) {
        Intent unhidebar = new Intent(UNHIDE_SYSTEMBAR);
        context.sendBroadcast(unhidebar);
    }
}
