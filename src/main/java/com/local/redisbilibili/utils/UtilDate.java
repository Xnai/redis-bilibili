package com.local.redisbilibili.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilDate {
    public UtilDate() {}

    public static String currentDate() { return currentDate("yyyyMMddHHmmss"); }

    public static String currentDate(String format) { return toDateStr(new Date(), format); }

    public static String toDateStr(Date date) { return toDateStr(date, "yyyyMMddHHmmss"); }

    public static String toDateStr(Date date, String format) {
        SimpleDateFormat simpleFormatter = new SimpleDateFormat(format);
        return simpleFormatter.format(date);
    }

    public static Date toDate(String dateStr) {
        String format = null;
        if(dateStr.contains("-")){
            format = UtilString.sub("yyyy-MM-dd HH:mm:ss", 0, dateStr.length());
        }else {
            format = UtilString.sub("yyyyMMddHHmmss", 0, dateStr.length());
        }
        return toDate(dateStr, format);
    }

    public static Date toDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try{
            return sdf.parse(dateStr);
        }catch (ParseException ex) {
            throw new RuntimeException("字符串转日期时异常," + ex.getMessage());
        }
    }
}