package com.graction.developer.zoocaster.Util.Date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hare on 2017-08-03.
 */

public class DateManager {
    private static final DateManager ourInstance = new DateManager();
    private Date date = new Date();
    private DateFormat dateFormat;
    private SimpleDateFormat simpleDateFormat;

    public enum DateType {
        Hour, HourOfDay, Minute, Day, Month, Year
    }

    public static DateManager getInstance() {
        return ourInstance;
    }

    public String getNow(int dateType) {
        dateFormat = DateFormat.getDateInstance(dateType);
        return dateFormat.format(date);
    }

    public String getNow(DateType type) {
        String pattern = "";
        switch (type) {
            case Year:
                pattern = "yy";
                break;
            case Month:
                pattern = "MM";
                break;
            case Day:
                pattern = "dd";
                break;
            case Hour:
                pattern = "hh";
                break;
            case HourOfDay:
                pattern = "HH";
                break;
            case Minute:
                pattern = "mm";
                break;
        }
        return getDate(pattern);
    }

    public String getDate(String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public int getIntegerDate(String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    public Date parseDate(String date, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(date);
    }

    public String formatDate(long date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    // YEARm MONTH, DAY_OF_MONTH
    private Calendar getCalendarDate(int[] arr) {
        return getCalendarDate(arr[0], arr[1], arr[2]);
    }

    private Calendar getCalendarDate(int year, int month, int day_of_month) {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.YEAR, year);
        date.add(Calendar.MONTH, month);
        date.add(Calendar.DAY_OF_MONTH, day_of_month);
        return date;
    }

    public int[] getTimeArray(Date date) {
        int[] is = {Integer.parseInt(formatDate(date.getTime(), "yyyy")), Integer.parseInt(formatDate(date.getTime(), "MM")), Integer.parseInt(formatDate(date.getTime(), "dd"))};
        return is;
    }

    public boolean compareDate(String start, String end, String pattern) throws ParseException {
        Date startDate = parseDate(start, pattern);
        Date endDate = parseDate(end, pattern);
        return startDate.getTime() < endDate.getTime();
    }

    public boolean isDayInWeek(int[] week){
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        return week[today] == 1 ? true : false;
    }
}
