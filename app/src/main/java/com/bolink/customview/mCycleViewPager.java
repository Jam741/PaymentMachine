package com.bolink.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bolink.component.CycleViewPager;

import java.io.File;
import java.util.List;



/**
 * Created by xulu on 2017/10/21.
 */

public class mCycleViewPager extends CycleViewPager {
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

    /**
     * 提供了实例化自身的方法，供动态添加删除使用
     * @param context
     * @param imgPlayList
     * @return
     */
    public static mCycleViewPager get(Context context, List<File> imgPlayList) {
        mCycleViewPager cycleview = new mCycleViewPager(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        cycleview.setLayoutParams(params);
        cycleview.loadLocalImage(imgPlayList, context);
        cycleview.startAutoRotation(0);
        return cycleview;
    }

//    @Override
//    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
//       if(e1.getX()-e2.getX()>10){
//           ReflectionUtil.invokeMethod(this,"onFlingRight",null,null);
//            return true;
//        }else if(e1.getX()-e2.getY()<-10){
//           ReflectionUtil.invokeMethod(this,"onFlingLeft",null,null);
//            return true;
//        }
//        return false;
//    }
}
