package com.bamboo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bamboo on 16-6-3.
 */
public class DateUtil {
    private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");

    //获取当前系统时间 格式为："yyyy/MM//dd"
    public static String getCurrentDate() {
        Date date = new Date();
        return sf.format(date);
    }

    //将时间戳转换成字符串
    public static String getDateToString(long time) {
        Date date = new Date(time);
        return sf.format(date);
    }

    //将字符串转化为时间戳
    public static long getStringToDate(String time) {
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
}
