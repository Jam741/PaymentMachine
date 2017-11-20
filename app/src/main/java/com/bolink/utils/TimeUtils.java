package com.bolink.utils;

/**
 * Created by xulu on 2017/9/14.
 */

public class TimeUtils {
    public static String Sec2Time(Long secend){
        if(secend<60){
            return "00:"+Left0(secend);
        }else if(secend>=60&&secend<3600){
            long minetes = secend/60;
            return Left0(minetes)+":"+Left0(secend%60);
        }else if(secend>=3600&&secend<86400){
            long hour = secend/3600;
            long minete = (secend-hour*3600)/60;
            return Left0(hour)+":"+Left0(minete)+":"+Left0(secend%60);
        }else {
            long day = secend/86400;
            long hour = (secend-day*86400)/3600;
            long minete = (secend-day*86400-hour*3600)/60;
            return day+":"+hour+":"+minete+":"+Left0(secend%60);
        }
    }
    public static String Left0(Long target){
        if(target<10){
            return "0"+target;
        }else{
            return ""+target;
        }
    }

}
