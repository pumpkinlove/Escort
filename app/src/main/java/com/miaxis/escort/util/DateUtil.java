package com.miaxis.escort.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    public static String getNextDay(String day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(day));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    public static String getLastDay(String day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(day));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    public static String getDayType(String day){
        Calendar pre = Calendar.getInstance();
        pre.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = sdf.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);
            if (diffDay == 0) {
                return day + "  " + "今天";
            } else if (diffDay < 0) {
                return day;
            } else if (diffDay == 1) {
                return day + "  " + "明天";
            } else if (diffDay == 2) {
                return day + "  " + "后天";
            }
        }
        return day;
    }

    public static boolean isYesterday(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar today = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        try {
            today.setTime(sdf.parse(getToday()));
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return today.after(calendar);
    }

    public static int getYear() {
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        return date.get(Calendar.YEAR);
    }

    public static String getCurDateTime() {
        Calendar today = Calendar.getInstance();
        return String.format("%04d%02d%02d%02d%02d%02d", today.get(Calendar.YEAR),
                today.get(Calendar.MONTH)+1, today.get(Calendar.DAY_OF_MONTH),
                today.get(Calendar.HOUR), today.get(Calendar.MINUTE),
                today.get(Calendar.SECOND));
    }

    public static String getCurDateTime24()	{
        Calendar today = Calendar.getInstance();
        return String.format("%04d-%02d-%02d %02d:%02d:%02d", today.get(Calendar.YEAR),
                today.get(Calendar.MONTH)+1, today.get(Calendar.DAY_OF_MONTH),
                today.get(Calendar.HOUR_OF_DAY), today.get(Calendar.MINUTE),
                today.get(Calendar.SECOND));
    }

}
