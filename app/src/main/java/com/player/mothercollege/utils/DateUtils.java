package com.player.mothercollege.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/8.
 */
public class DateUtils {

    private static String timeStr;

    public static String getStandardDate(String str) {

        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = format.parse(str);
            long time = date.getTime();//发布时间距1970年的毫秒值
            timeStr = time+"";
        } catch (ParseException e) {
            e.printStackTrace();
        }

        StringBuffer sb = new StringBuffer();

        long t = Long.parseLong(timeStr);
//        long time = System.currentTimeMillis() - (t * 1000);
        long time = System.currentTimeMillis() - t ;
        System.out.println("当前时间:"+time);
        long mill = (long) Math.ceil(time / 1000);//秒前  
        long minute = (long) Math.ceil(time / 60 / 1000.0f);//分钟前
        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);//小时
        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);//天前
        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }
}
