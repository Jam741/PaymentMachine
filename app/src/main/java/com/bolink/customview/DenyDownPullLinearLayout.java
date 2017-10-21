package com.bolink.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by xulu on 2017/9/9.
 */

public class DenyDownPullLinearLayout extends LinearLayout{
    public DenyDownPullLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DenyDownPullLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int touchY = (int)ev.getY();
        int height = getHeight();
        Log.e(TAG, "onInterceptTouchEvent: touchY="+touchY+"  height="+height);
        if(touchY<=height/10){
            Log.e(TAG, "onInterceptTouchEvent: 拦截了" );
            return true;
        }
//        return super.onInterceptTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchY = (int)event.getY();
        int height = getHeight();
        Log.e(TAG, "onTouchEvent: touchY="+touchY+"  height="+height);
        if(touchY<=height/10){
            Log.e(TAG, "onTouchEvent: 拦截了");
            return true;
        }
        return super.onTouchEvent(event);
    }

    private static final String TAG = "DenyDownPullLinearLayou";
}
