package com.bolink.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import cn.jianke.imageswitcher.widget.CycleViewPager;

/**
 * Created by xulu on 2017/10/21.
 */

public class mCycleViewPager extends CycleViewPager{
    public mCycleViewPager(Context context) {
        super(context);
    }

    public mCycleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public mCycleViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
//        super.onLongPress(motionEvent);
    }
}
