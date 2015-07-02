package com.springapp.breepage.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
    public static final long ONE_MINUTE_STAMP = 60000L;
    private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);

    public static final ThreadLocal<SimpleDateFormat> datetimeFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static final ThreadLocal<SimpleDateFormat> dateFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static long now() {
        return new java.util.Date().getTime();
    }

    public static String format(Date date) {
        return dateFormatter.get().format(date);
    }

    public static String format(Timestamp date) {
        return datetimeFormatter.get().format(date);
    }

    public static String today() {
        return dateFormatter.get().format(new java.util.Date());
    }

    public static Date parseDate(String date) throws ParseException {
        return new Date(dateFormatter.get().parse(date).getTime());
    }

    public static Timestamp parseDatetime(String datetime) throws ParseException {
        return new Timestamp(datetimeFormatter.get().parse(datetime).getTime());
    }

    public static String lastDateOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return dateFormatter.get().format(calendar.getTime());
    }

    public static int compare(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return 0;
        } else if (date1 == null) {
            return -1;
        } else if (date2 == null) {
            return 1;
        } else {
            if (date1.getTime() > date2.getTime()) {
                return 1;
            } else if (date1.getTime() < date2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public static String addOneMinute(String pushTime) { //往后推一分钟
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date;
        try {
            date = df.parse(pushTime);
            Long dateStamp = date.getTime() + ONE_MINUTE_STAMP;
            return df.format(dateStamp);
        } catch (ParseException e) {
            LOG.info("parse date error: can't convert String to Date");
            return null;
        }
    }
}
