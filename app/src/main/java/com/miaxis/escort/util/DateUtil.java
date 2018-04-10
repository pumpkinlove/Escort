package com.miaxis.escort.util;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 一非 on 2018/4/10.
 */

public class DateUtil {

    public static String getToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static String getNextDay(String today) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(today));
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

}
