package com.kgc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式解析工具类
 *
 * @Author: 魏小可
 * @Date: 2024-03-18-16:46
 */
public class DateCheckUtil {
    /**
     * 默认日期格式方法(yyyy-MM-dd HH:mm:ss)
     *
     * @param date 格式化的字符串
     * @return 返回Date对象
     * @throws ParseException 抛出解析错误
     */
    public static Date parseString(String date) throws ParseException {
        return parseString(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 默认日期格式方法
     *
     * @param date   格式化的字符串
     * @param patten 格式化格式
     * @return 返回Date对象
     * @throws ParseException 抛出解析错误
     */
    public static Date parseString(String date, String patten) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patten);
        return simpleDateFormat.parse(date);
    }

    /**
     * 解析时间戳
     *
     * @param time 时间戳
     * @return 返回Date对象
     */
    public static Date parseTime(String time) {
        long newTime = Long.parseLong(time);
        return new Date(newTime);
    }

    /**
     * 检查时间是否超时
     *
     * @param time 时间日期
     * @return 返回是否超时 true-未超时 false-超时
     */
    public static boolean checkDateTime(String time) {
        long tarTime = parseTime(time).getTime();
        long newTime = new Date().getTime();
        long targetTime = newTime - tarTime;
        return targetTime > 0 && targetTime < 1800000;
    }
}
