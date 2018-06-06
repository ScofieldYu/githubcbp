package com.blchina.common.util.date;

import com.blchina.common.util.string.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    public final static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT1 = "yyyyMMddHHmmss";
    public final static String FORMAT2 = "yyyy/MM/dd HH:mm:ss";

    public final static String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 得到当天的时间，包括时分秒。如2015-03-05 14:36:33
     */
    public static long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * 得到相隔几天的时间，包括时分秒。如2015-03-05 14:36:33
     * 负数：今天之前的天
     * 整数：今天之后的天
     */
    public static long getCurrentTime(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTimeInMillis();
    }


    /**
     * 得到基准时间 相隔几天的时间，包括时分秒。如2015-03-05 14:36:33
     * 负数：今天之前的天
     * 整数：今天之后的天
     */
    public static long getDayTime(long baseTime, int days) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(baseTime);
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTimeInMillis();
    }

    /**
     * 得到当天的时间，包括时分秒。如2015-03-05 14:36:33
     */
    public static long getCurrentTimeSecond() {
        return Calendar.getInstance().getTimeInMillis() / 1000;
    }

    /**
     * 得到当天的时间，不包括时分秒。如2015-03-05
     */
    public static long getCurrentDayTime() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * 得到相隔几天的时间，不包括时分秒。如2015-03-05
     * 负数：今天之前的天
     * 整数：今天之后的天
     */
    public static long getCurrentDayTime(int days) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTimeInMillis();
    }


    /**
     * 得到当天和指定小时
     */
    public static long getCurrentDayAndHour(int hourOfDay) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * 得到当天往后顺延天数和指定小时
     */
    public static long getCurrentDayAndHour(int days, int hourOfDay) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTimeInMillis();
    }

    /**
     * 得到指定时间，不包括分秒，如2015-03-05 09:00:00
     */
    public static long getDayAndHour(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * 取得当前小时值（24小时制）
     */
    public static int getCurrentHour() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.HOUR_OF_DAY);
    }


    /**
     * 取得当前的年份
     */
    public static int getCurrentYear() {

        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    /**
     * 得到当前日期的周次
     */
    public static int getWeekOfYear() {
        TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
        Calendar cal = Calendar.getInstance(zone);
        int c = cal.get(cal.WEEK_OF_YEAR);
        return c;
    }

    /**
     * 得到当前日期的月份
     */
    public static int getMonthOfYear() {
        TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
        Calendar cal = Calendar.getInstance(zone);
        int c = cal.get(cal.MONTH); //0-11
        return c + 1;
    }

    /**
     * 得到当前日期的季度
     */
    public static int getQuarterOfYear() {
        int month = getMonthOfYear();
        int quarter = (int) Math.ceil((double) month / 3);
        return quarter;
    }


    /**
     * 取得昨天开始时间，如2015-05-25 00:00:00
     */
    public static Date getYesterday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DAY_OF_MONTH, -1);
        return c.getTime();
    }

    public static long getYesterdayForLong() {
        return dateToLong(getYesterday());
    }


    /**
     * 取得昨天结束时间，如2015-05-25 23:59:59
     */
    public static Date getYesterdayEnd() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        c.add(Calendar.DAY_OF_MONTH, -1);
        return c.getTime();
    }

    public static long getYesterdayEndForLong() {
        return dateToLong(getYesterdayEnd());
    }


    /**
     * 当前周的周一
     */
    public static long getFirstDayOfCurrentWeek() {
        int year = DateUtil.getCurrentYear();
        int week = DateUtil.getWeekOfYear();
        return dateToLong(getFirstDayOfWeek(year, week));
    }

    /**
     * 取当前星期几（1开始）
     */
    public static int getDayOfCurrentWeek() {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);   //周一开始
        int weekDay = c.get(Calendar.DAY_OF_WEEK);
        weekDay = weekDay - 1;
        if (weekDay == 0)
            weekDay = 7;
        return weekDay;
    }

    /**
     * 得到某年某周的第一天
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.WEEK_OF_YEAR, week);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.setFirstDayOfWeek(Calendar.MONDAY);   //周一开始
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//设置周一
        return c.getTime();
    }

    public static long getFirstDayOfWeekForLong(int year, int week) {
        return dateToLong(getFirstDayOfWeek(year, week));
    }

    /**
     * 得到某年某周的最后一天
     */
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.WEEK_OF_YEAR, week);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        c.setFirstDayOfWeek(Calendar.MONDAY);   //周一开始
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday

        return c.getTime();
    }

    public static long getLastDayOfWeekForLong(int year, int week) {
        return dateToLong(getLastDayOfWeek(year, week));
    }


    /**
     * 取当前月的第几天（1开始）
     */
    public static int getDayOfCurrentMonth() {
        Calendar c = Calendar.getInstance();
        int dayOfMonth = c.get(c.DAY_OF_MONTH);

        return dayOfMonth;
    }

    /**
     * 得到某年某月的第一天
     */
    public static Date getFirstDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static long getFirstDayOfMonthForLong(int year, int month) {
        return dateToLong(getFirstDayOfMonth(year, month));
    }

    /**
     * 得到某年某月的最后一天
     */
    public static Date getLastDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    public static long getLastDayOfMonthForLong(int year, int month) {
        return dateToLong(getLastDayOfMonth(year, month));
    }


    /**
     * 得到某年某季度第一天
     */
    public static Date getFirstDayOfQuarter(int year, int quarter) {
        int month = 0;
        if (quarter > 4) {
            return null;
        } else {
            month = (quarter - 1) * 3 + 1;
        }
        return getFirstDayOfMonth(year, month);
    }

    public static long getFirstDayOfQuarterForLong(int year, int quarter) {
        return dateToLong(getFirstDayOfQuarter(year, quarter));
    }


    /**
     * 得到某年某季度最后一天
     */
    public static Date getLastDayOfQuarter(int year, int quarter) {
        int month = 0;
        if (quarter > 4) {
            return null;
        } else {
            month = quarter * 3;
        }
        return getLastDayOfMonth(year, month);
    }

    public static long getLastDayOfQuarterForLong(int year, int quarter) {
        return dateToLong(getLastDayOfQuarter(year, quarter));
    }


    /**
     * 返回基准时间date 后num天的时间，返回long
     */
    public static long addDays(long date, int num) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        c.add(Calendar.DAY_OF_MONTH, num);
        return c.getTimeInMillis();
    }

    public static String formatDateToStringDefault(long date) {
        return new SimpleDateFormat(DEFAULT_FORMAT).format(date);
    }

    public static String formatDateToStringNewDefault() {
        return new SimpleDateFormat(FORMAT1).format(getCurrentTime());
    }

    public static String formatDateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatDateToString(long date, String format) {
        return new SimpleDateFormat(format).format(new Date(date));
    }


    /**
     * 将long形式的字符串格式化输出
     *
     * @param date   如"1421856000000"
     * @param format 如"yyyy-MM-dd"
     * @return
     */
    public static String formatStringToString(String date, String format) {

        Long dt = Long.parseLong(date);
        return formatDateToString(dt, format);
    }

    public static String formatDateStringToString(String date, String format) {
        Long dt = stringToLong(date, format);
        return formatDateToString(dt, format);
    }

    public static long stringToLongDefault(String strTime) {
        return stringToLong(strTime, DEFAULT_FORMAT);
    }

    public static long stringToLong(String strTime, String format) {
        if (StringUtil.isNullorEmpty(strTime))
            return 0;
        Date date = stringToDate(strTime, format);
        if (date == null) {
            return 0;
        } else {
            return dateToLong(date);
        }
    }

    public static Date stringToDate(String strTime, String format) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(strTime);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }


    public static void main(String[] args) {

        Date dt = new Date();

        System.out.println(dt);
        System.out.println(getYesterdayEnd());
        System.out.println(Calendar.getInstance().getTime());

        System.out.println(getLastDayOfWeek(2015, 29));
        System.out.println(dateToLong(getLastDayOfWeek(2015, 29)));

        //return dateToLong(getLastDayOfWeek(year, week));
        System.out.println(formatDateToString(getFirstDayOfCurrentWeek(), "yyyy-MM-dd HH:mm:ss"));
        System.out.println(dateToLong(stringToDate("2015-09-12 00:00:00", "yyyy-MM-dd HH:mm:ss")));
        System.out.println(dateToLong(stringToDate("2015-09-13 00:00:00", "yyyy-MM-dd HH:mm:ss")));
        System.out.println(formatDateToStringDefault(1442073600000L));
        System.out.println(DateUtil.getCurrentDayTime(2));
//
//        System.out.println(formatDateToStringDefault(getDayTime(1440777600000L, 1)));
//        System.out.println(formatDateToStringDefault(getDayTime(1441044000000L, 0)));

    }

}
