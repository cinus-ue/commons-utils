package com.cinus.date;


import com.cinus.thirdparty.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {


    public static final long MS = 1;
    public static final long SECOND_MS = MS * 1000;
    public static final long MINUTE_MS = SECOND_MS * 60;
    public static final long HOUR_MS = MINUTE_MS * 60;
    public static final long DAY_MS = HOUR_MS * 24;


    public static final String PDATE = "yyyyMMdd";
    public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";
    public static final String NORM_TIME_PATTERN = "HH:mm:ss";
    public static final String NORM_DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String NORM_DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String HTTP_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

    public static final String RFC2822 = "EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z";
    public static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";


    private static ThreadLocal<SimpleDateFormat> NORM_DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        synchronized protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(NORM_DATE_PATTERN);
        }
    };

    private static ThreadLocal<SimpleDateFormat> NORM_TIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        synchronized protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(NORM_TIME_PATTERN);
        }
    };

    private static ThreadLocal<SimpleDateFormat> NORM_DATETIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        synchronized protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(NORM_DATETIME_PATTERN);
        }
    };

    private static ThreadLocal<SimpleDateFormat> HTTP_DATETIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        synchronized protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(HTTP_DATETIME_PATTERN, Locale.US);
        }
    };


    public static String now() {
        return formatDateTime(new Date());
    }


    public static String today() {
        return formatDate(new Date());
    }


    public static Date date() {
        return new Date();
    }


    public static Date date(long date) {
        return new Date(date);
    }

    public static String yearAndSeason(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return yearAndSeason(cal);
    }

    private static String yearAndSeason(Calendar cal) {
        return new StringBuilder().append(cal.get(Calendar.YEAR)).append(cal.get(Calendar.MONTH) / 3 + 1).toString();
    }

    public static String makeDateRFC2822(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(RFC2822, Locale.US);
        return df.format(date);
    }

    public static String makeDateISO8601(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(ISO8601);
        String result = df.format(date);
        result = result.substring(0, result.length() - 2) + ":" + result.substring(result.length() - 2);
        return result;
    }

    public static String getSysDate(String format) {
        return date2String(new Date(), format);
    }

    public static String getSysYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return year;
    }

    public static String getSysMonth() {
        Calendar date = Calendar.getInstance();
        String month = String.valueOf(date.get(Calendar.MONTH) + 1);
        return month;
    }

    public static String getSysDay() {
        Calendar date = Calendar.getInstance();
        String day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
        return day;
    }

    public static Date string2Date(String date, String format) {
        Date d;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);// ????????????
            d = formater.parse(date);
        } catch (Exception e) {
            d = null;
        }
        return d;
    }

    public static String date2String(Date date, String format) {
        if (date == null) {
            return Constants.EMPTY;
        }
        String s;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            s = formater.format(date);
        } catch (Exception e) {
            s = Constants.EMPTY;
        }
        return s;
    }

    public static String friendlyTime(Date time) {
        if (time == null) return "unknown";
        int ct = (int) ((System.currentTimeMillis() - time.getTime()) / 1000);
        if (ct < 3600)
            return Math.max(ct / 60, 1) + "?????????";
        if (ct >= 3600 && ct < 86400)
            return ct / 3600 + "?????????";
        if (ct >= 86400 && ct < 2592000) { //86400 * 30
            int day = ct / 86400;
            return (day > 1) ? "??????" : "??????";
        }
        if (ct >= 2592000 && ct < 31104000) //86400 * 30
            return ct / 2592000 + "?????????";
        return ct / 31104000 + "??????";
    }

    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatDateTime(Date date) {
        return NORM_DATETIME_FORMAT.get().format(date);
    }

    public static String formatDate(Date date) {
        return NORM_DATE_FORMAT.get().format(date);
    }

    public static String formatHttpDate(Date date) {
        return HTTP_DATETIME_FORMAT.get().format(date);
    }

}
