package com.graction.developer.zoocaster.Util.Date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by JeongTaehyun on 2017-08-03.
 */

/*
 * Date 설정
 */

public class DateManager {
    private static final DateManager ourInstance = new DateManager();
    private Date date = new Date();
    private SimpleDateFormat simpleDateFormat;

    public static DateManager getInstance() {
        return ourInstance;
    }

    public String getDate(String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public boolean isDayInWeek(int[] week){
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        return week[today] == 1 ? true : false;
    }
}
