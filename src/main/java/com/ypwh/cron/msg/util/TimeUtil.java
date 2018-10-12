package com.ypwh.cron.msg.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {


    /**
     * 计算多少个小时的时间
     * @param da
     * @param hour
     * @return
     */
    public static Date dealDate(Date da, int hour){
        Calendar now = Calendar.getInstance();
        now.setTime(da);
        now.set(10, now.get(10) + hour);
        System.out.println(now.getTime());
        return  now.getTime();
    }

    public static void main(String[] args) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(10, now.get(10) + (-24));
        System.out.println(now.getTime());
    }
}



