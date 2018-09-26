package com.mmall.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @program: mmall
 * @description: 时间转换工具类
 * @author: Mr.Tang
 * @create: 2018-09-21 16:34
 **/
public class DateUtil {

    // joda --- time

    // str --- Date

    // Date --- str

    public static final String STANDARD_FORMAT = "yyyy-MM-dd ";

    public static Date strToDate(String dateTimeStr, String formatStr) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);

        return dateTime.toDate();
    }

    public static Date strToDate(String dateTimeStr) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);

        return dateTime.toDate();
    }

    public static String dateToStr(Date date, String formatStr) {

        if (date == null) {
            return StringUtils.EMPTY;
        }

        DateTime dateTime = new DateTime(date);

        return dateTime.toString(formatStr);
    }

    public static String dateToStr(Date date) {

        if (date == null) {
            return StringUtils.EMPTY;
        }

        DateTime dateTime = new DateTime(date);

        return dateTime.toString(STANDARD_FORMAT);
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateUtil.strToDate("2018-5-28 11:11:11", "yyyy-MM-dd HH:mm:ss"));
    }
}
