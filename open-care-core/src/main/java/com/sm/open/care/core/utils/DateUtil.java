package com.sm.open.care.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期转化
 */
public class DateUtil {

    private static Logger log = LoggerFactory.getLogger(DateUtil.class);

    public final static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_DATE = "yyyy-MM-dd";
    public final static String FORMAT_TIME = "HH:mm:ss";
    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public final static String FORMAT_yyyyMMdd = "yyyyMMdd";
    public final static String FORMAT_yyyyMM = "yyyyMM";
    public final static String FORMAT_MONTH = "yyyy-MM";
    public final static String TODAY_BEGIN = "00:00:00";
    public final static String TODAY_END = "23:59:59";

    public static Date stringToDate(String dateStr, String formatStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            Date date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDate(String dateStr, String formatStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            Date date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            log.error("parseDate error: " + e);
            return null;
        }
    }

    public static Date parseDate(String dateStr) {
        return stringToDate(dateStr, FORMAT_DATE_TIME);
    }

    public static String formateDate(Date date) {
        return formateDate(date, FORMAT_DATE_TIME);
    }

    /**
     * 根据格式格式化日期为字串
     **/
    public static String formateDate(Date date, String format) {
        SimpleDateFormat sdf = null;
        try {
            if (format == null || format.trim().length() == 0) {
                sdf = new SimpleDateFormat(FORMAT_DATE_TIME);
            } else {
                sdf = new SimpleDateFormat(format);
            }
            return sdf.format(date);
        } catch (Exception e) {
            log.error("formateDate error: " + e);
            return null;
        }
    }

    /**
     * 获得年月日 格式 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getYmdDate(Date date) {
        return getDate(date);
    }

    /**
     * 获取中文日期 '2010年01月11日'
     *
     * @return
     */
    public static String getChineseDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return dateFormat.format(date);
    }

    /**
     * 获取中文日期 '01月11日'
     *
     * @return
     */
    public static String getChineseMD(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MM月dd日");
        return dateFormat.format(date);
    }

    /**
     * 获得年月日 格式 yyyy-MM-dd
     *
     * @param date
     * @return
     */

    public static String getDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        return sdf.format(date);
    }

    /**
     * 获得8位数字的日期，年月日 格式 yyyyMMdd
     *
     * @param date
     * @return
     */
    public static String getNumberDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_yyyyMMdd);
        return sdf.format(date);
    }

    /**
     * 获得时间 格式 hh24:mi:ss
     *
     * @param date
     * @return
     */
    public static String getHmsTime(Date date) {
        return geTime(date);
    }

    /**
     * 获得时间 格式 hh24:mi:ss
     *
     * @param date
     * @return
     */
    public static String geTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIME);
        return sdf.format(date);
    }

    /**
     * 获得14位数字的日期与时间， 时间 格式 yyyyMMddHHmmss
     *
     * @param date
     * @return
     */
    public static String geNumbertDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_yyyyMMddHHmmss);
        return sdf.format(date);
    }

    /**
     * 获得时间 格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TIME);
        return sdf.format(date);
    }

    /**
     * 获得时间 格式 yyyy-MM-dd HH:mm:ss
     *
     * @param
     * @return
     */
    public static String getCurrentDateTime() {
        return getDateTime(new Date());
    }

    public static String getWeek(Date date) {
        String week = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK);
        switch (w) {
            case 1:
                week = "星期天";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }

    /**
     * 根据pattern判断字符串是否为合法日期
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static boolean isValidDate(String dateStr, String pattern) {
        boolean isValid = false;
        if (pattern == null || pattern.length() < 1) {
            pattern = "yyyy-MM-dd";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String date = sdf.format(sdf.parse(dateStr));
            if (date.equalsIgnoreCase(dateStr)) {
                isValid = true;
            }
        } catch (Exception e) {
            isValid = false;
        }
        // 如果目标格式不正确，判断是否是其它格式的日期
        if (!isValid) {
            isValid = isValidDatePatterns(dateStr, "");
        }
        return isValid;
    }

    public static boolean isValidDatePatterns(String dateStr, String patterns) {
        if (patterns == null || patterns.length() < 1) {
            patterns = "yyyy-MM-dd;yyyy-MM-dd HH:mm:ss;dd/MM/yyyy;yyyy/MM/dd;yyyy/M/d h:mm";
        }
        boolean isValid = false;
        String[] patternArr = patterns.split(";");
        for (int i = 0; i < patternArr.length; i++) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(patternArr[i]);
                String date = sdf.format(sdf.parse(dateStr));
                if (date.equalsIgnoreCase(dateStr)) {
                    isValid = true;
                    break;
                }
            } catch (Exception e) {
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * @param date 要修改的时间
     * @param day  天数
     * @param hour 小时
     * @param min  分钟
     * @param sec  秒
     * @return
     * @Description: 设置指定时间的天时分秒 正数+负数-
     */
    public static Date setDayTime(Date date, int day, int hour, int min, int sec) {
        Calendar cal = Calendar.getInstance();
        date = addDate(date, day);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, sec);
        return cal.getTime();
    }

    /**
     * @return
     * @Description: 获取当月的第一天
     * @Date: 2016年3月6日 下午4:41:04
     */
    public static String getFirstDatePerMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_MONTH);
        return sdf.format(new Date()) + "-01";
    }

    public static String getFormatDate(String dateStr, String pattern) {
        pattern = StringUtils.isNotBlank(pattern) ? pattern : DEFAULT_FORMAT;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String date = format.format(sdf.parse(dateStr));
            return date;
        } catch (Exception e) {
            System.out.println("日期格转换失败.....");
        }
        return null;
    }

    public static String getFormatDate(Date date, SimpleDateFormat format) {
        if (format == null) {
            format = new SimpleDateFormat(DEFAULT_FORMAT);
        }
        try {
            String strDate = format.format(date);
            return strDate;
        } catch (Exception e) {
            // System.out.println("日期格转换失败::::::::");
            log.info("日期格转换失败::::::::");
        }
        return null;
    }

    public static String getFormatDate(Date date, String pattern) {
        pattern = StringUtils.isNotBlank(pattern) ? pattern : DEFAULT_FORMAT;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String strDate = sdf.format(date);
            return strDate;
        } catch (Exception e) {
            System.out.println("日期格转换失败>>>>>>>");
        }
        return null;
    }

    public static Date getNowDate(String pattern) {
        pattern = StringUtils.isNotBlank(pattern) ? pattern : DEFAULT_FORMAT;
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String strDate = sdf.format(date);
            Date nowDate = sdf.parse(strDate);
            return nowDate;
        } catch (Exception e) {
            System.out.println("无法获得当前日期！");
        }
        return null;
    }

    /**
     * 获取月份
     */
    public static Integer getMonth(Date date) {
        String changedayStr = formateDate(date, FORMAT_yyyyMM);
        return Integer.parseInt(changedayStr);
    }

    /**
     * 获取指定日期
     *
     * @param specifiedDay
     * @param num
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay, int num) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + num);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }

    /**
     * 日期加减天数得到新的日期
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDate(Date date, int day) {
        Calendar fromCal = Calendar.getInstance();
        fromCal.setTime(date);
        fromCal.add(Calendar.DATE, day);
        return fromCal.getTime();

    }

    /**
     * 两个日期相减
     *
     * @return
     * @Date: 2015年8月18日 下午7:10:28
     */
    public static Long subDate(String dateStr1, String dateStr2) {
        try {
            if (StringUtils.isNotBlank(dateStr1) && StringUtils.isNotBlank(dateStr2)) {
                Date date1 = parseDate(dateStr1, FORMAT_DATE);
                Date date2 = parseDate(dateStr2, FORMAT_DATE);
                return date1.getTime() - date2.getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }

    public static boolean compareDate(String dateStr1, String dateStr2, String pattern) {
        pattern = StringUtils.isNotBlank(pattern) ? pattern : DEFAULT_FORMAT;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date date1 = sdf.parse(dateStr1);
            Date date2 = sdf.parse(dateStr2);
            return (date1.getTime() - date2.getTime()) >= 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 字符串的日期格式的计算 .getTime()
     *
     * @throws ParseException
     */
    public static int daysBetween(String smallDate, String bigDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long time1 = sdf.parse(smallDate).getTime();
        long time2 = sdf.parse(bigDate).getTime();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * @Description: 获取两个日期直接的天数值
     * @Param:
     * @Date: 2017/4/7 11:15
     */
    public static int daysBetween(Date smallDate, Date bigDate) {
        long time1 = smallDate.getTime();
        long time2 = bigDate.getTime();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static ArrayList<String> listDays(String smallDate, String bigDate) throws ParseException {
        ArrayList<String> resultDays = new ArrayList<String>();
        int days = daysBetween(smallDate, bigDate);
        if (days <= 0)
            return null;
        for (int i = 0; i <= days; i++) {
            resultDays.add(DateUtil.date2Str(DateUtils.addDays(DateUtil.parseDate(smallDate, "yyyy-MM-dd"), i),
                    "yyyy-MM-dd"));
        }
        return resultDays;
    }

    public static Date str2Date(String str, String format) {
        return parseDate(str, format);
    }

    public static Date str2Date(String str) {
        return parseDate(str);
    }

    public static String date2Str(Date date) {
        if (date == null) {
            return "";
        }
        return formateDate(date, DEFAULT_FORMAT);
    }

    public static String date2Str(Date date, String format) {
        SimpleDateFormat tempFormat = new SimpleDateFormat(format);
        if (date == null) {
            return "";
        }
        return tempFormat.format(date);
    }

    public static ArrayList<String> listMonths(String smallDate, String bigDate) throws ParseException {
        ArrayList<String> resultDays = new ArrayList<String>();
        int months = monthsBetween(smallDate, bigDate);
        if (months <= 0)
            return null;
        for (int i = 0; i < months; i++) {
            resultDays.add(DateUtil.date2Str(DateUtils.addMonths(DateUtil.str2Date(smallDate, FORMAT_MONTH), i), FORMAT_MONTH));
        }
        return resultDays;
    }

    public static int monthsBetween(String smallDate, String bigDate) throws ParseException {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(DateUtil.str2Date(smallDate, FORMAT_MONTH));
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(DateUtil.str2Date(bigDate, FORMAT_MONTH));

        int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

        return year * 12 + month;
    }

    /**
     * 系统时间戳
     *
     * @return
     */
    public static String currentTimestamp() {
        return "" + System.currentTimeMillis();
    }

    /**
     * yyyyMMddHHmmss
     *
     * @return
     */
    public static String currentTimeMillis0() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date());
    }

    /**
     * yyMMddHHmmss
     *
     * @return
     */
    public static String currentTimeMillis1() {
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
        return format.format(new Date());
    }

    /**
     * yyyy/MM/dd HH:mm:ss
     *
     * @return
     */
    public static String currentTimeMillis2() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * yy/MM/dd HH:mm:ss
     *
     * @return
     */
    public static String currentTimeMillis3() {
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * yyyy/MM/dd HH:mm:ss.sss
     *
     * @return
     */
    public static String currentTimeMillis4() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.sss");
        return format.format(new Date());
    }

    /**
     * yy/MM/dd HH:mm:ss.sss
     *
     * @return
     */
    public static String currentTimeMillis5() {
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss.sss");
        return format.format(new Date());
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String currentTimeMillisCN1() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * yy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String currentTimeMillisCN2() {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * yyyy-MM-dd
     *
     * @return
     */
    public static String currentTimeMillisCN3() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    /**
     * yy-MM-dd
     *
     * @return
     */
    public static String currentTimeMillisCN4() {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
        return format.format(new Date());
    }

    /**
     * yyyy-MM-dd HH:mm:ss.sss
     *
     * @return
     */
    public static String currentTimeMillisCN5() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
        return format.format(new Date());
    }

    /**
     * yy-MM-dd HH:mm:ss.sss
     *
     * @return
     */
    public static String currentTimeMillisCN6() {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss.sss");
        return format.format(new Date());
    }

    /**
     * yyMMdd
     *
     * @return
     */
    public static String currentDate1() {
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
        return format.format(new Date());
    }

    /**
     * yy/MM/dd
     *
     * @return
     */
    public static String currentDate2() {
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd");
        return format.format(new Date());
    }

    /**
     * yyyyMMdd
     *
     * @return
     */
    public static String currentDate3() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
    }

    /**
     * yyyy/MM/dd
     *
     * @return
     */
    public static String currentDate4() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(new Date());
    }

    /**
     * HHmmss
     *
     * @return
     */
    public static String currentTime1() {
        SimpleDateFormat format = new SimpleDateFormat("HHmmss");
        return format.format(new Date());
    }

    /**
     * HH:mm:ss
     *
     * @return
     */
    public static String currentTime2() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 获取一天开始日期
     *
     * @return
     */
    public static String getTodayBegin() {
        return getDate(new Date()) + " " + TODAY_BEGIN;
    }

    /**
     * 获取一天结束日期，忽略闰秒
     *
     * @return
     */
    public static String getTodayEnd() {
        return getDate(new Date()) + " " + TODAY_END;
    }

    /**
     * @Description: 获取两个日期指定域的差值
     */
    public static int getLastMonthDays(Date date) {
        Calendar calendar = DateUtils.toCalendar(date);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    /**
     * @Description: 增加指定域的值
     * @Date: 2017/4/12 14:35
     */
    public static Date add(Date date, int field, int amount) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(field, amount);
        date = c.getTime();
        return date;
    }


    /**
     * @Description: 设置指定时间的时、分、秒、毫秒
     * @Date: 2017/5/3 16:55
     */
    public static Date setTime(Date date, int hour, int min, int sec, int millSec) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, sec);
        cal.set(Calendar.MILLISECOND, millSec);
        return cal.getTime();
    }

    /**
     * @Description: 比较两个日期的大小，若bigDate > smallDate 则返回true，否则返回false
     * @Date: 2017/5/3 17:00
     */
    public static boolean compareDate(Date bigDate, Date smallDate) {
        long big = DateUtil.setTime(bigDate, 0, 0, 0, 0).getTime();
        long small = DateUtil.setTime(smallDate, 0, 0, 0, 0).getTime();
        if (big > small) {
            return true;
        }
        return false;
    }

    /**
     * java8(别的版本获取2月有bug) 获取某月最后一天的23:59:59
     *
     * @return
     */
    public static String getLastDayOfMonth(String datestr) {
        if (StringUtils.isBlank(datestr)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
        Date date = strToDateNotDD(datestr);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
        Date dates = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
        return sdf.format(dates);
    }


    /**
     * 将短时间格式字符串转换为时间 yyyy-MM
     *
     * @param strDate
     * @return
     */
    public static Date strToDateNotDD(String strDate) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_MONTH);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }


}
