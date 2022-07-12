package vn.hieplp.todo.common.utils;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 1/5/2022
 * Time: 20:37
 **/
public class DateTimes {
    public static Date getCurrentDate() {
        return new Date();
    }

    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    public static Date addSeconds(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }
}
