package com.vince.utils;

import java.util.Date;

public class DateUtils {

    public static void main(String[] args) {
        System.out.println(nowTimestampStr());
    }
    // 获取当前时间
    public static Date now() {
        return new Date();
    }
    // 获取当前时间戳
    public static long nowTimestamp() {
        return System.currentTimeMillis();
    }
    // 获取当前时间戳
    public static String nowTimestampStr() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String nowTimestampStr(String format) {
        return new java.text.SimpleDateFormat(format).format(new Date());
    }
}
