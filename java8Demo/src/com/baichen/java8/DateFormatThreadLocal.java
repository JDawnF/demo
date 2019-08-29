package com.baichen.java8;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// 线程安全类
public class DateFormatThreadLocal {

    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");//指定格式
        }

    };

    public static final Date convert(String source) throws ParseException {
        return df.get().parse(source);
    }
}
