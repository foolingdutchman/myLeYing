package com.secondproject.mepatch.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liushuai on 16/5/8.
 */
public class TimeUtils {
    //获取时分秒
    public static String formatTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(date);
    }
    //是否整点时刻
    public static boolean isIntTime(Date date) {
        boolean result=false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        if (dateFormat.format(date).split(":")[1].equals("00")) {
            result=true;
        }
        return result;
    }

    //获取int时间数组
    public static int[] getTimeArray (Date date) {
        int[] time=new int[3] ;

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        time[0]=Integer.parseInt(dateFormat.format(date).split(":")[0] );
        time[1]=Integer.parseInt(dateFormat.format(date).split(":")[1] );
        time[2]=Integer.parseInt(dateFormat.format(date).split(":")[2] );
        return time;
    }

  //获取当前int时间数组
    public static int[] getCurrentTimeArray () {
        int[] time=new int[3] ;
        Date date=new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        time[0]=Integer.parseInt(dateFormat.format(date).split(":")[0] );
        time[1]=Integer.parseInt(dateFormat.format(date).split(":")[1] );
        time[2]=Integer.parseInt(dateFormat.format(date).split(":")[2] );
        return time;
    }

    // 获取当前日期
    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    //获取日期数组
    public static int[] getDateArray(Date date) {
        int[] datetime=new int[3];
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        datetime[0]=Integer.parseInt(dateFormat.format(date).split("-")[0] );
        datetime[1]=Integer.parseInt(dateFormat.format(date).split("-")[1] );
        datetime[2]=Integer.parseInt(dateFormat.format(date).split("-")[2] );
        return datetime;
    }
 //获取当前日期数组
    public static int[] getCurrentDateArray() {
        Date date=new Date(System.currentTimeMillis());
        int[] datetime=new int[3];
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        datetime[0]=Integer.parseInt(dateFormat.format(date).split("-")[0] );
        datetime[1]=Integer.parseInt(dateFormat.format(date).split("-")[1] );
        datetime[2]=Integer.parseInt(dateFormat.format(date).split("-")[2] );
        return datetime;
    }

}
