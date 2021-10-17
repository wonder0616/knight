package com.knight.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 功能描述
 *
 * @author knight
 * @since 2021-07-26
 */
@Slf4j
public class DateUtil {
    private static int baseYear = 0;

    private static int baseMonth = 0;

    private static final long ONE_DAY_MS = 3600 * 1000 * 24;

    /**
     * 时间格式化 yyyyMMddHHmmss
     */
    public static final String DATE_FORMAT_YMDHMS = "yyyyMMddHHmmssSSS";

    static {
        try {
            Calendar timeBase = DateUtils.toCalendar(DateUtils.parseDate("19700101", "yyyyMMdd"));
            baseYear = timeBase.get(Calendar.YEAR);
            baseMonth = timeBase.get(Calendar.MONTH);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 获取给定毫秒数的第二天
     *
     * @param milliseconds long
     * @return Date
     */
    public static Date getAddDayZero(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        date.setTime(milliseconds);
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return zero;
    }

    /**
     * 获取给定毫秒数的当天
     *
     * @param milliseconds long
     * @return Date
     */
    public static Date getDayZero(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        date.setTime(milliseconds);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return zero;
    }

    /**
     * 是否跨天
     *
     * @param starTime Long
     * @param endTime  Long
     * @return boolean
     */
    public static boolean isAcrossDay(Long starTime, Long endTime) {
        if (starTime == null || endTime == null) {
            log.error("【DateTools】 param is null starTime:{}, endTime:{}, ", starTime, endTime);
            return false;
        }

        return !DateUtils.isSameDay(new Date(starTime), new Date(endTime));
    }

    /**
     * 获取时间所在的月份偏移
     *
     * @param time 时间毫秒数
     * @return 月份偏移
     */
    public static int getMonthOffset(long time) {
        log.info("getMonthOffset time:{}", time);
        Calendar calendar = DateUtils.toCalendar(new Date(time));
        int yearOffset = calendar.get(Calendar.YEAR) - baseYear;
        int monthOffset = calendar.get(Calendar.MONTH) - baseMonth;
        int sum = yearOffset * 12 + monthOffset;
        log.info("getMonthOffset sum:{}", sum);
        return sum;
    }

    /**
     * 相差天数
     *
     * @param leftTime  时间毫秒数
     * @param rightTime 时间毫秒数
     * @return int
     */
    public static int getBetweenDays(long leftTime, long rightTime) {
        // 获取相差的天数
        Long betWeenDays = (leftTime - rightTime) / ONE_DAY_MS;
        return betWeenDays.intValue();
    }

    /**
     * 将long类型时间转为字符串 yyyy-MM-dd
     *
     * @param longTime long类型时间
     * @return yyyy-MM-dd
     */
    public static String formatDate(Date longTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(longTime);
    }

    /**
     * 根据传入日期转换成字符串形式
     *
     * @param date long类型时间
     * @return yyyyMMdd
     */
    public static String getStringDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 将Date类型时间转为字符串 yyyymmddhhmmss
     *
     * @param longTime long类型时间
     * @return yyyymmddhhmmss
     */
    public static String formatDateTime(Date longTime) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
        return format.format(longTime);
    }

    /**
     * 将Date类型时间转为毫秒
     *
     * @param longTime Date类型时间
     * @return 时间毫秒值
     */
    public static long formatTimeInMillis(Date longTime) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat(DATE_FORMAT_YMDHMS).parse(formatDateTime(longTime)));
        return calendar.getTimeInMillis();
    }

}
