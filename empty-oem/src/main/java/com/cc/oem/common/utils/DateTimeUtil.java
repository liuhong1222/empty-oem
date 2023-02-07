package com.cc.oem.common.utils;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: liutao
 * Date: 2018-03-19
 * Time: 16:05
 *
 * @author wangqiang
 * @since 2018.3.24
 */
@Component
public class DateTimeUtil {
    //private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

    /**
     * DateTimeUtil 类中的一个静态常量
     * 是用来格式化日期时间的格式
     * yyyy-MM-dd HH:mm:ss
     */
    public static String DEFAULTFORMAT = "yyyy-MM-dd HH:mm:ss";

    public static int formateDateToYearMonthInt(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyyMM");
        return Integer.valueOf(dateFormat.format(date));
    }
    /**
     * DateTimeUtil 类中的 getCurDateTime 静态函数
     * 作用：根据默认的 DEFAULTFORMAT 格式 返回当前日期时间
     * 参考结果：2015-03-15 13:35:42
     */
    public static String getCurDateTime() {
        return getCurDateTime(DEFAULTFORMAT);
    }


    /**
     * DateTimeUtil 类中的 getCurDateTime(String pattern) 静态函数
     * 作用： 根据传入的日期时间格式 返回当前的日期时间
     * 参考结果:13:35:42
     */
    public static String getCurDateTime(String pattern) {
        return formatCalendar(Calendar.getInstance(), pattern);
    }


    /**
     * DateTimeUtil 类中的 formatCalendar(Calendar calendar) 静态函数
     * 作用：根据传入的 Calendar 对象 返回 DEFAULTFORMAT 格式的日期时间字符串
     * 参考结果:2015-03-15 13:35:42
     */
    public static String formatCalendar(Calendar calendar) {
        return formatCalendar(calendar, DEFAULTFORMAT);
    }


    /**
     * DateTimeUtil 类中的 formatCalendar(Calendar calendar, String pattern) 静态函数
     * 作用：根据传入的 Calendar 对象 和 传入的 日期格式化pattern 返回 一个日期时间字符串
     * 参考结果:2015-03-15 13:35:42
     */
    public static String formatCalendar(Calendar calendar, String pattern) {
        return new DateTime(calendar).toString(pattern);
    }


    /**
     * DateTimeUtil 类中的 parseDate(String date) 静态函数
     * 作用：根据传入的字符串形式的时间日期转换成Date 对象
     * 参考结果:2015-03-15 00:00:00
     */
    public static Date parseDate(String date) {
        return StringUtils.isEmpty(date) ? null : DateTime.parse(date).toDate();
    }


    /**
     * DateTimeUtil 类中的 parseDate(String date, String pattern) 静态函数
     * 作用：根据传入的日期时间字符串 和 该日期时间的格式化pattern 返回 Date对象
     * 参考结果:1426426542000
     */
    public static Date parseDate(String date, String pattern) {
        return parseDateTime(date, pattern).toDate();
    }


    static DateTime parseDateTime(String date) {
        return StringUtils.isEmpty(date) ? null : DateTime.parse(date);
    }


    static DateTime parseDateTime(String date, String pattern) {
        return StringUtils.isEmpty(date) ? null : DateTime.parse(date, DateTimeFormat.forPattern(pattern));
    }


    /**
     * DateTimeUtil 类中的 formatDate(Date date, String pattern) 静态函数
     * 作用：将传入的 Date对象根据传入的 pattern 格式化成字符串 并返回
     * 参考结果:2015-03-15 13:35:42
     */
    public static String formatDate(Date date, String pattern) {
        return date == null ? "" : new DateTime(date).toString(pattern);
    }


    /**
     * DateTimeUtil 类中的 formatDate(Date date) 静态函数
     * 作用：将传入的 Date对象根据默认的 DEFAULTFORMAT 日期时间格式 格式化成字符串并返回
     * 参考结果:2015-03-15 00:00:00
     */
    public static String formatDate(Date date) {
        return formatDate(date, DEFAULTFORMAT);
    }


    /**
     * DateTimeUtil 类中的 parseString(String dateStr, String pattern) 静态函数
     * 作用：将传入的String类型的日期时间字符串 dateStr 根据传入的 pattern 格式
     * 转换成Calendar对象并返回
     * 参考结果:2015-03-15 14:18:29
     */
    public static Calendar parseString(String dateStr, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate(dateStr, pattern));
        return calendar;
    }


    /**
     * DateTimeUtil 类中的 convertDateTimeStrFormat
     * (String dateStr, String pattern, String newPattern) 静态函数
     * <p>
     * 作用：将传入的String类型的日期时间字符串 dateStr 根据 dateStr 的pattern
     * 用新的 newPattern格式化成String类型的时间日期字符串 并返回
     * 参考结果:14:24:04
     */
    public static String convertDateTimeStrFormat(String dateStr, String pattern, String newPattern) {
        return parseDateTime(dateStr, pattern).toString(newPattern);
    }


    /**
     * DateTimeUtil 类中的 parseString(String dateStr) 静态函数
     * 作用：将传入的String 类型的时间日期字符串 dateStr 使用DEFAULTFORMAT pattern
     * 转换成 Calendar 对象 并返回
     * 参考结果:2015-03-15 14:31:33
     */
    public static Calendar parseString(String dateStr) {
        return parseString(dateStr, DEFAULTFORMAT);
    }


    /***********************************************************************************************************/


    static DateTime beginTimeOf(DateTime dateTime) {
        return dateTime.withTimeAtStartOfDay();
    }


    static DateTime beginTimeOfMonth(DateTime dateTime) {
        return beginTimeOf(dateTime.dayOfMonth().withMinimumValue());
    }


    static DateTime endTimeOf(DateTime dateTime) {
        return dateTime.millisOfDay().withMaximumValue().toDateTime();
    }


    static DateTime endTimeOfMonth(DateTime dateTime) {
        return endTimeOf(dateTime.dayOfMonth().withMaximumValue()).toDateTime();
    }


    /**
     * 获得今天的开始时间
     *
     * @return
     */
    public static Date getBeginTimeOfToday() {
        return beginTimeOf(DateTime.now()).toDate();
    }


    /**
     * 获得昨天的开始时间
     *
     * @return
     */
    public static Date getBeginTimeOfYesterday() {
        return beginTimeOf(DateTime.now().minusDays(1)).toDate();
    }


    /**
     * 获取给定日期的开始时间
     *
     * @return
     */
    public static Date getBeginTimeOfTheDay(String dateStr, Integer dayOffset) {
        return beginTimeOf(parseDateTime(dateStr).plusDays(dayOffset)).toDate();
    }


    /**
     * 获得本月的第一天
     *
     * @return
     */
    public static Date getFirstDayOfMonth() {
        return beginTimeOfMonth(DateTime.now()).toDate();
    }


    /**
     * 获得上月的第一天
     *
     * @return
     */
    public static Date getFirstDayOfLastMonth() {
        return beginTimeOfMonth(DateTime.now().minusMonths(1)).toDate();
    }


    /**
     * 获取指定日期的开始时间
     *
     * @param dateStr
     * @param monthOffset
     * @return
     */
    public static Date getBeginTimeOfTheMonth(String dateStr, Integer monthOffset) {
        return beginTimeOfMonth(DateTime.parse(dateStr).plusMonths(monthOffset)).toDate();
    }


    /**
     * 获得今天的开始时间
     *
     * @return
     */
    public static Date getEndTimeOfToday() {
        return endTimeOf(DateTime.now()).toDate();
    }


    /**
     * 指定日期的结束拼上23:59:59
     * @param date
     * @return
     */
    public static Date getEndTimeOfDate(Date date) {
        return endTimeOf(new DateTime(date)).plusMillis(-999).toDate();
    }



    public static Date getEndTimeOfYesterday() {
        return endTimeOf(DateTime.now().minusDays(1)).toDate();
    }


    public static Date getEndTimeOfMonth() {
        return endTimeOfMonth(DateTime.now()).toDate();
    }


    /**
     * 获取指定日期的开始时间
     *
     * @return
     */
    public static Date getEndTimeOfTheDay(String dateStr, Integer dayOffset) {
        return endTimeOf(DateTime.parse(dateStr).plusDays(dayOffset)).toDate();
    }


    /**
     * 获得月的结束时间
     *
     * @return
     */
    public static Date getEndTimeOfTheMonth(String dateStr, Integer monthOffset) {
        return endTimeOfMonth(DateTime.parse(dateStr).plusMonths(monthOffset)).toDate();
    }


    /***********************************************************************************************************/
    public static Duration duration(Date start, Date end) {
        return new Duration(start.getTime(), end.getTime());

    }

    public static Date getEndTimeOfMonth(Date date) {
        return endTimeOfMonth(new DateTime(date)).toDate();
    }
    
    public static Date getCurrentDateTime() {
    	return new Date();
    }
    
    
    public static Date getNextMonthOfTheDay(Date date) {
    	return (new DateTime(date).plusMonths(1)).toDate();
    }


}
