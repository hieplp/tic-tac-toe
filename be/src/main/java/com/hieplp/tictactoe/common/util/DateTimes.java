package com.hieplp.tictactoe.common.util;

import java.util.Calendar;
import java.util.Date;

public class DateTimes {
    public static Date getCurrentDate() {
        return new Date();
    }

    public static Date addSeconds(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }
}
