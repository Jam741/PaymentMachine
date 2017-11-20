package com.bolink.utils;

import java.math.BigDecimal;

/**
 * Created by xulu on 2017/11/17.
 */

public class FormatUtil {

    public static double DoubleFormat(double target) {
//        if(target>0){
        return new BigDecimal(target).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        }
//        return 0;
    }
}
