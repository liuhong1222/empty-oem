package com.cc.oem.modules.job.utils;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间工具类
 * 
 * @author liuh
 * @date 2021年1月25日
 */
public class MyDateUtils {
	private static final List<String> formarts = new ArrayList<String>(10);

	/**
	 * 日期为长期的日期
	 */
	public final static String LONG_TIME = "2099-12-31";
	static {
		formarts.add("yyyy-MM");
		formarts.add("yyyy-MM-dd");
		formarts.add("yyyy-MM-dd hh:mm");
		formarts.add("yyyy-MM-dd hh:mm:ss");
		formarts.add("yyyy-MM-dd hh:mm:ss.SSS");

		formarts.add("yyyy/MM");
		formarts.add("yyyy/MM/dd");
		formarts.add("yyyy/MM/dd hh:mm");
		formarts.add("yyyy/MM/dd hh:mm:ss");
		formarts.add("yyyy/MM/dd hh:mm:ss.SSS");

		formarts.add("yyyyMM");
		formarts.add("yyyyMMdd");
		formarts.add("yyyyMMdd hh:mm");
		formarts.add("yyyyMMdd hh:mm:ss");
		formarts.add("yyyyMMdd hh:mm:ss.SSS");
	}

	/**
	 * 按照指定的格式，将日期类型对象转换成字符串，例如：yyyy-MM-dd,yyyy/MM/dd,yyyy/MM/dd hh:mm:ss
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		return formater.format(date);
	}

	/**
	 * 字符串转date，格式自动识别
	 */
	public static Date convert(String source) {
		if (StringUtils.isBlank(source)) {
			return new Date();
		}
		String value = source.trim();
		if (source.matches("^\\d{4}-\\d{1,2}$")) {
			return parseDate(value, formarts.get(0));
		} else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
			return parseDate(value, formarts.get(1));
		} else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
			return parseDate(value, formarts.get(2));
		} else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
			return parseDate(value, formarts.get(3));
		} else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}$")) {
			return parseDate(value, formarts.get(4));
		} else if (source.matches("^\\d{4}/\\d{1,2}$")) {
			return parseDate(value, formarts.get(5));
		} else if (source.matches("^\\d{4}/\\d{1,2}/\\d{1,2}$")) {
			return parseDate(value, formarts.get(6));
		} else if (source.matches("^\\d{4}/\\d{1,2}/\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
			return parseDate(value, formarts.get(7));
		} else if (source.matches("^\\d{4}/\\d{1,2}/\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
			return parseDate(value, formarts.get(8));
		} else if (source.matches("^\\d{4}/\\d{1,2}/\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}$")) {
			return parseDate(value, formarts.get(9));
		} else if (source.matches("^\\d{4}\\d{1,2}$")) {
			return parseDate(value, formarts.get(10));
		} else if (source.matches("^\\d{4}\\d{1,2}\\d{1,2}$")) {
			return parseDate(value, formarts.get(11));
		} else if (source.matches("^\\d{4}\\d{1,2}\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
			return parseDate(value, formarts.get(12));
		} else if (source.matches("^\\d{4}\\d{1,2}\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
			return parseDate(value, formarts.get(13));
		} else if (source.matches("^\\d{4}\\d{1,2}\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}$")) {
			return parseDate(value, formarts.get(14));
		} else {
			return new Date();
		}
	}

	/**
	 * 按照yyyy-MM-dd格式，将 Date 对象转换成字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		return formater.format(date);
	}

	/**
	 * 按照yyyy-MM-dd格式，将 Date 对象转换成字符串
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static String formatDateByDet(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formater.format(date);
	}

	/**
	 * 按照yyyy-MM格式，将 Date 对象转换成字符串
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static String formatDateType(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
		return formater.format(date);
	}



	/**
	 * 按照指定的格式，将字符串转换成日期类型对象，例如：yyyy-MM-dd,yyyy/MM/dd,yyyy/MM/dd hh:mm:ss
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String dateStr, String pattern) {
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		try {
			return formater.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将字符串（yyyy-MM-dd）解析成Date
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseDate(String dateStr) {
		return parseDate(dateStr, "yyyy-MM-dd");
	}

	/**
	 * 将字符串（yyyy-MM-dd）解析成Date
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseDateAll(String dateStr) {
		return parseDate(dateStr, "yyyy-MM-dd HH:mm");
	}
	
	public static String DateAddhours(Date date, int hour,String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hour);// 24小时制
		date = cal.getTime();
		cal = null;
		return format.format(date);
	}

	/**
	 * 将字符串（yyyy-MM-dd）解析成Timestamp
	 * 
	 * @param timeStampStr
	 * @return
	 */
	public static Timestamp parseTimestamp(String timeStampStr) {

		Date date = parseDate(timeStampStr, "yyyy-MM-dd");
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (date != null) {

			return Timestamp.valueOf(formater.format(date));
		}

		return null;
	}

	/**
	 * 将Date (yyyy-MM-dd)解析成Timestamp
	 *
	 * @return
	 */
	public static Timestamp parseTimestamp(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (date != null) {

			return Timestamp.valueOf(formater.format(date));
		}

		return null;
	}

	/**
	 * 给定的日期增加addDay天 并返回Date类型
	 * 
	 * @param date
	 * @param addDay
	 * @return
	 */
	public static Date addDay(Date date, int addDay) {

		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DAY_OF_MONTH, addDay);

		return ca.getTime();
	}

	/**
	 * 日期推算去除周六日
	 * 
	 * @param date
	 * @param workDay
	 * @return
	 */
	public static String getWorkDate(Date date, int workDay) {
		Calendar cal = Calendar.getInstance();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		int days = workDay % 5 + workDay / 5 * 7;
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return sdf.format(cal.getTime());
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param startday
	 * @param endday
	 * @return
	 */
	public static int getIntervalDaysByDate(Date startday, Date endday) {
		if (startday.after(endday)) {
			Date cal = startday;
			startday = endday;
			endday = cal;
		}
		long sl = startday.getTime();
		long el = endday.getTime();
		long ei = el - sl;
		return (int) (ei / (1000 * 60 * 60 * 24));
	}

	/**
	 * 获取两日期的差值 @author Kevin Li @date 2015年1月27日 上午9:41:50 @Title
	 * getItvDaysByDate @Description @param startday @param endday @return
	 * int @throws
	 */
	public static int getItvDaysByDate(Date startday, Date endday) {
		long sl = startday.getTime();
		long el = endday.getTime();
		long ei = el - sl;
		return (int) ei;
	}

	/**
	 * 获取两日期之间的秒数 getSecondsDateByDate @Description @param startday @param
	 * endday @return int @throws
	 */
	public static int getSecondsDateByDate(Date startday, Date endday) {
		long sl = startday.getTime();
		long el = endday.getTime();
		long ei = (el - sl) / 1000;
		return (int) ei;
	}

	/**
	 * 计算两个日期之间相差的小时数 @author Kevin Li @date 2014年9月12日 下午3:31:12 @Title
	 * getIntervalHour @Description @param startday @param endday @return
	 * int @throws
	 */
	public static int getIntervalHour(Date startday, Date endday) {
		long interval = endday.getTime() - startday.getTime();
		long day = interval / (24 * 60 * 60 * 1000);
		long hour = day * 24 + (interval / (60 * 60 * 1000) - day * 24);
		return (int) hour;
	}

	/**
	 * 设置时间为这一天的第一毫秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date setDateToOneDayFirstMilliSecond(Date date) {

		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.HOUR_OF_DAY, ca.getActualMinimum(Calendar.HOUR_OF_DAY));
		ca.set(Calendar.MINUTE, ca.getActualMinimum(Calendar.MINUTE));
		ca.set(Calendar.SECOND, ca.getActualMinimum(Calendar.SECOND));
		ca.set(Calendar.MILLISECOND, ca.getActualMinimum(Calendar.MILLISECOND));

		return ca.getTime();
	}

	/**
	 * 设置时间为这一天的最后一毫秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date setDateToOneDayLastMilliSecond(Date date) {

		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.HOUR_OF_DAY, ca.getActualMaximum(Calendar.HOUR_OF_DAY));
		ca.set(Calendar.MINUTE, ca.getActualMaximum(Calendar.MINUTE));
		ca.set(Calendar.SECOND, ca.getActualMaximum(Calendar.SECOND));
		ca.set(Calendar.MILLISECOND, ca.getActualMaximum(Calendar.MILLISECOND));

		return ca.getTime();
	}

	/**
	 * 取得当前年[Integer类型]
	 * 
	 * @return
	 */
	public static Integer getCurrentYear() {
		Calendar calendar = new GregorianCalendar();
		// 得到当前年
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 取得格式化后的当前系统时间[字符串类型]
	 * 
	 * @return
	 */
	public static String getCurrentSytemTimeForString() {
		Calendar calendar = new GregorianCalendar();
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String systemTime = format.format(calendar.getTime());
		return systemTime;
	}

	/**
	 * 取得格式化后的当前系统时间[Timestamp类型]
	 * 
	 * @return
	 */
	public static Timestamp getCurrentSytemTimeForTimestamp() {
		return Timestamp.valueOf(getCurrentSytemTimeForString());
	}

	/**
	 * 判断当前日期是否超过 一年
	 * 
	 * @param hireDate
	 * @return
	 */
	public static String checkOverCurrentOneYear(Date hireDate) {

		String returnValue = null;

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c = Calendar.getInstance();
		String currentDay = df.format(c.getTime());

		Date day_curDate = parseDate(currentDay);

		int year = getIntervalDaysByDate(day_curDate, hireDate);

		if (year > 365) {
			returnValue = "overOneYears";
		}
		return returnValue;

	}

	/**
	 * 取得当前时间
	 * 
	 * @return
	 */
	public static Date getCurrentDateTime() throws ParseException {
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = calendar.getTime();
		return format.parse(format.format(currentDate));
	}

	/**
	 * 根据beginDate和endDate日期判断currentDate是否在这二个日期之间
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static boolean checkCurrentDateTheTwoTimeBetween(Date currentDate, Date beginDate, Date endDate) {

		Boolean flag = false;

		if (currentDate.getTime() >= beginDate.getTime() && currentDate.getTime() <= endDate.getTime()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断当前日期是周六或者周末
	 * 
	 * @return
	 */
	public static boolean getcurrentWeek() {
		boolean returnValue = true;
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int value = dayOfWeek - 1;
		if (value == 0 || value == 6) {
			returnValue = false;
		}
		return returnValue;
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss EEE格式，将 Date 对象转换成字符串
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static String formatDateWeek(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE");
		return formater.format(date);
	}

	/**
	 * @return 获取当前时间格式 yyyyMMddHHmmssms
	 */
	public static String getDateTimeByMs() {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssms");
		return df.format(new Date());
	}

	/**
	 * @return 获取当前时间格式 yyyyMMddHHmmssms
	 */
	public static String getDateTime() {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(new Date());
	}

	/**
	 * @return 获取当前时间格式 yyyyMMdd
	 */
	public static String getDate() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(new Date());
	}

	/**
	 * @return 获取当前时间格式 yyyy-MM-dd
	 */
	public static String getToday() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}

	/**
	 * @return 获取上月日期格式 yyyy-MM
	 */
	public static String getLastMonth() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);

		return df.format(cal.getTime());
	}

	// 全局的获取当前时间
	public static String getNowTime() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

	// 全局的获取当前时间
	public static String getNowTime1() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.format(new Date());
	}

	// 全局的获取当前时间
	public static Date getNowDateTime() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df.parse(df.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getNowDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return df.parse(df.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串转日期 yyyyMMddHHmmssSSS
	 * 
	 * @return
	 */
	public static Date getDateByLongTime(String str) {
		return parseDate(str, "yyyyMMddHHmmssSSS");
	}

	public static Long getLongTime() {
		Date date = new Date();
		String longTime = formatDate(date, "yyyyMMddHHmmssSSS");
		return Long.valueOf(longTime);
	}

	public static Long getLongTime(Date date) {
		String longTime = formatDate(date, "yyyyMMddHHmmssSSS");
		return Long.valueOf(longTime);
	}

	public static Long getLongTime(Date date, String pattern) {
		String longTime = formatDate(date, pattern);
		return Long.valueOf(longTime);
	}

	public static Long getLongTimeNoMs() {
		Date date = new Date();
		String longTime = formatDate(date, "yyyyMMddHHmmss");
		return Long.valueOf(longTime);
	}

	/**
	 * 返回格式化为yyyyMMdd的昨天日期
	 *
	 * @return java.lang.String
	 */
	public static String getYesterday() {

		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.DAY_OF_MONTH, -1);
		return formatDate(ca.getTime());
	}

	/**
	 * 格式化一个日期字符串，格式为yyyyMMdd
	 *
	 * @return java.lang.String
	 */
	public static String formatDate(String dateStr, String pattern) {
		Date dd;
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			dd = formater.parse(dateStr);
		} catch (ParseException e) {
			formater = new SimpleDateFormat("yyyyMMdd");
			try {
				dd = formater.parse(dateStr);
			} catch (ParseException e1) {
				return "";
			}
		}
		if (dd == null) {
			return "";
		}
		formater = new SimpleDateFormat(pattern);
		return formater.format(dd);
	}

	/**
	 * 今年月份，
	 * 
	 * @return
	 */
	public static int getMonth() {
		return Calendar.getInstance().get(Calendar.MONTH);
	}

	/**
	 * 格式化时间，对结束时间增加23h59m59s @author Kevin Li @date 2015年1月8日 下午4:20:20 @Title
	 * fmtHSM @Description @param date @return Date @throws
	 */
	public static Date fmtHSM(Date date) {
		Date reDate = null;
		if (null != date) {
			Calendar ca = Calendar.getInstance();
			ca.setTime(date);
			ca.add(Calendar.HOUR_OF_DAY, 23);
			ca.add(Calendar.SECOND, 59);
			ca.add(Calendar.MINUTE, 59);
			reDate = ca.getTime();
		}
		return reDate;
	}

	/**
	 * 格式化时间，对结束时间增加23h59m59s @author Kevin Li @date 2015年1月8日 下午4:20:20 @Title
	 * fmtHSM @Description @param date @return Date @throws
	 */
	public static Date fmtHSMStart(Date date) {
		Date reDate = null;
		if (null != date) {
			Calendar ca = Calendar.getInstance();
			ca.setTime(date);
			ca.add(Calendar.HOUR_OF_DAY, 0);
			ca.add(Calendar.SECOND, 0);
			ca.add(Calendar.MINUTE, 0);
			reDate = ca.getTime();
		}
		return reDate;
	}

	/**
	 * 获取昨天的时间 到23h59m59s @author liming @date 2015年4月7日 下午4:41:00 @Title
	 * getYesterdayEnd @Description @return Date @throws
	 */
	public static Date getYesterdayEnd() {
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.DAY_OF_MONTH, -1);
		return fmtHSM(ca.getTime());
	}

	/**
	 * 获取昨天的时间 到0h0m0s @author liming @date 2015年4月7日 下午4:41:42 @Title
	 * getYesterdayStart @Description @return Date @throws
	 */

	public static Date getYesterdayStart() {
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.DAY_OF_MONTH, -1);
		return fmtHSMStart(ca.getTime());
	}

	/**
	 * 获取当前时间加半小时 @author liming @date 2015年4月7日 下午4:41:42 @Title
	 * getYesterdayStart @Description @return Date @throws
	 */

	public static String getCurrentTimeMillis() {
		long curren = System.currentTimeMillis();
		curren += 30 * 60 * 1000;
		Date date = new Date(curren);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * 获取限制的警告时间 @author 2881526645@qq.com @date 2017年3月6日 下午4:04:48 @Title
	 * getLimitAlarmTime @Description @param date @param flag @return Date @throws
	 */
	public static Date getLimitAlarmTime(Date date, int flag) {

		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		int hour = cal.get(Calendar.HOUR_OF_DAY);

		int minute = cal.get(Calendar.MINUTE);

		int second = cal.get(Calendar.SECOND);

		// 时分秒（毫秒数）

		long millisecond = hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000;

		// 凌晨00:00:00

		cal.setTimeInMillis(cal.getTimeInMillis() - millisecond);

		if (flag == 0) {

			return cal.getTime();

		} else if (flag == 1) {

			// 凌晨23:59:59

			cal.setTimeInMillis(cal.getTimeInMillis() + 7 * 60 * 60 * 1000);

		}

		return cal.getTime();

	}


	/**
	 * 获取当月第一天 @author 2881526645@qq.com @date 2017年5月15日 上午10:32:28 @Title
	 * getCurrentMonthFirstDay @Description @return String @throws
	 */
	public static String getCurrentMonthFirstDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return format.format(c.getTime());
	}

	/**
	 * 获取凌晨时间点 @author 2881526645@qq.com @date 2017年5月15日 上午10:35:22 @Title
	 * getMorningTime @Description @param hour 凌晨的时间点 @return long @throws
	 */
	@SuppressWarnings("unused")
	private static long getMorningTime(int hour) {
		long ONE_DAY = 24 * 60 * 60 * 1000;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long ret = cal.getTimeInMillis();
		return ret + ONE_DAY;
	}

	/**
	 * 给指定时间加8个小时
	 * 
	 * @return
	 */
	public static String DateAddhours(String day, int hour) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(day);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (date == null)
			return "";
		System.out.println("front:" + format.format(date)); // 显示输入的日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hour);// 24小时制
		date = cal.getTime();
		System.out.println("after:" + format.format(date)); // 显示更新后的日期
		cal = null;
		return format.format(date);
	}

	public static List<Date> getBetweenDates(Date begin, Date end) {
		List<Date> result = new ArrayList<Date>();
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(begin);
		while (begin.getTime() <= end.getTime()) {
			result.add(tempStart.getTime());
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
			begin = tempStart.getTime();
		}
		return result;
	}

	/**
	 * 日期转换成字符串
	 * @param date
	 * @return str
	 */
	public static String dateToStr(Date date, String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String str = sdf.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * @param str
	 * @return date
	 */
	public static Date strToDate(String str, String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 取得当前时间，指定格式字符串
	 * @return
	 */
	public static Date getCurrentDateTime(String format) {
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date currentDate = calendar.getTime();
		Date date = null;
		try {
			date = sdf.parse(sdf.format(currentDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 取得当前时间中文格式下字符串
	 * @date 2021/4/2
	 * @return java.lang.String
	 */
	public static String getCurrentDateTimeChinese() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月dd日HH时mm分");
		return dateFormat.format(date);
	}

	public static void main(String[] args) {

		// 1494988003000
		// System.out.println(DateUtils.formatDate(new
		// Date(Long.valueOf(1494988003000l)), "yyyy-MM-dd hh:mm:ss"));
		Date date = new Date();
		String startTime = getStartTime(date);
		System.out.println(startTime+"时间的撒范德萨发");
	}

	/**
	 * 得到传入日期当天的开始时间
	 * @return
	 */
	public static String getStartTime(Date date) {
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(date);
		todayStart.set(Calendar.HOUR, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(todayStart.getTime());
	}


}
