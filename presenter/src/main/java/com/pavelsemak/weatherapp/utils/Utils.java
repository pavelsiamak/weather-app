package com.pavelsemak.weatherapp.utils;

import java.util.Calendar;
import java.util.Locale;

public class Utils {

    public static final int MILLIS_IN_SEC = 1000;

    public static String longToHourString(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int hour = calendar.get(Calendar.HOUR);
        hour = hour == 0 ? 12: hour;
        String prefix = calendar.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
        return hour + prefix;
    }

    public static String longToTimeString(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int hour = calendar.get(Calendar.HOUR);
        hour = hour == 0 ? 12: hour;
        int minutes = calendar.get(Calendar.MINUTE);
        String minutesStr = minutes >= 10 ? String.valueOf(minutes) : "0" + minutes;
        String prefix = calendar.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
        return hour + ":" + minutesStr + " " + prefix;
    }

    public static String dayOfWeekFromTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        String dayOfWeekStr;
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                dayOfWeekStr = "Sunday";
                break;
            case Calendar.MONDAY:
                dayOfWeekStr = "Monday";
                break;
            case Calendar.TUESDAY:
                dayOfWeekStr = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeekStr = "Wednesday";
                break;
            case Calendar.THURSDAY:
                dayOfWeekStr = "Thursday";
                break;
            case Calendar.FRIDAY:
                dayOfWeekStr = "Friday";
                break;
            case Calendar.SATURDAY:
                dayOfWeekStr = "Saturday";
                break;
            default:
                dayOfWeekStr = "Sunday";
        }

        return dayOfWeekStr;
    }

    public static long translateTime(long time, float offsetHours) {
        Calendar calendar = Calendar.getInstance();
        int currentOffset = calendar.getTimeZone().getOffset(
                calendar.get(Calendar.ERA),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.DAY_OF_WEEK),
                calendar.get(Calendar.MILLISECOND));
        int offset = (int) (offsetHours * 60 * 60 * MILLIS_IN_SEC);
        return time * MILLIS_IN_SEC - (currentOffset - offset);
    }
}
