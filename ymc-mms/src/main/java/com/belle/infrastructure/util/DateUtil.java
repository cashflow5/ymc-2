package com.belle.infrastructure.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * <p>
 * Title: 时间操作工具
 * </p>
 * <p>
 * Description: SOC基础技术平台
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author sbruan
 * @version 1.0
 */
public class DateUtil {
	public static String formatDate(java.util.Date date) {
		return formatDateByFormat(date, "yyyy-MM-dd");
	}

	public static String formatDateByFormat(java.util.Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static java.util.Date parseDate(java.sql.Date date) {
		return date;
	}

	public static Date parseDate(Date date, String format) {
		SimpleDateFormat sdff = new SimpleDateFormat(format);
		try {
			return sdff.parse(date.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static java.sql.Date parseSqlDate(java.util.Date date) {
		if (date != null) {
			return new java.sql.Date(date.getTime());
		} else {
			return null;
		}
	}

	public static String format(java.util.Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				java.text.DateFormat df = new java.text.SimpleDateFormat(format);
				result = df.format(date);
			}
		} catch (Exception e) {
		}
		return result;
	}

	public static String format(java.util.Date date) {
		return format(date, "yyyy/MM/dd");
	}

	public static String format1(java.util.Date date) {
		return format(date, "yyyy-MM-dd");
	}

	public static int getYear(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.YEAR);
	}

	public static int getMonth(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MONTH) + 1;
	}

	public static int getDay(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.DAY_OF_MONTH);
	}

	public static int getHour(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MINUTE);
	}

	public static int getSecond(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.SECOND);
	}

	public static long getMillis(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	public static int getWeek(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
		dayOfWeek = dayOfWeek - 1;
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		return dayOfWeek;
	}

	public static String getDate(java.util.Date date) {
		return format(date, "yyyy/MM/dd");
	}

	public static String getDate(java.util.Date date, String formatStr) {
		return format(date, formatStr);
	}

	public static String getTime(java.util.Date date) {
		return format(date, "HH:mm:ss");
	}

	public static String getDateTime(java.util.Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            Date
	 * @param day
	 *            int
	 * @return Date
	 */
	public static java.util.Date addDate(java.util.Date date, int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 日期天数相减
	 * 
	 * @param date
	 *            Date
	 * @param day
	 *            int
	 * @return Date
	 */
	public static java.util.Date diffDate(java.util.Date date, int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) - ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 小时相加
	 * 
	 * @param date
	 *            Date
	 * @param hour
	 *            int
	 * @return Date
	 */
	public static java.util.Date addHour(java.util.Date date, int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 1 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 日期相减
	 * 
	 * @param date
	 *            Date
	 * @param date1
	 *            Date
	 * @return int
	 */
	public static int diffDate(java.util.Date date, java.util.Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}

	/**
	 * 日期相减(返回秒值)
	 * 
	 * @param date
	 *            Date
	 * @param date1
	 *            Date
	 * @return int
	 * @author
	 */
	public static Long diffDateTime(java.util.Date date, java.util.Date date1) {
		return (Long) ((getMillis(date) - getMillis(date1)) / 1000);
	}

	public static java.util.Date getdate(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(date);
	}

	public static java.util.Date getdate1(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(date);
	}

	public static java.util.Date getMaxTimeByStringDate(String date) throws Exception {
		String maxTime = date + " 23:59:59";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(maxTime);
	}

	public static java.util.Date getMinTimeByStringDate(String date) throws Exception {
		String maxTime = date + " 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(maxTime);
	}
	
	public static java.util.Date stringToDate(String date, String dataFormat) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
		return sdf.parse(date);
	}

	/**
	 * 获得当前时间
	 * 
	 * @return
	 */
	public static Date getCurrentDateTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = DateUtil.getDateTime(date);
		try {
			return sdf.parse(result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static String getCurrentDateTimeToStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(getCurrentDateTime());
	}

	public static Long getWmsupdateDateTime() {
		Calendar cl = Calendar.getInstance();

		return cl.getTimeInMillis();
	}

	public static void main(String[] args) {
//		System.out.println(diffDate(new Date(), 30).toGMTString());
//		
//		try {
//			/* System.out.println(getWeek(getdate("2008-06-29"))); */
//			Date date = DateUtil.getCurrentDateTime();
//			System.out.println(date);
//			System.out.println(addHour(DateUtil.parseDate("2012-02-10 12:00:00"), -1));
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
	}

	// TODO Everyone don't modify wms team the code

	/****************************** start by wms ******************************/

	public static final Date parseDate(String string) {

		return parseDate(string, DateTimePattern.values());
	}

	public static final Date parseDate(String string, DateTimePattern pattern) {

		return parseDate(string, new DateTimePattern[] { pattern });
	}

	/**
	 * 解析日期时间字符串，得到Date对象
	 * 
	 * @param input
	 *            日期时间字符串
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static Date parseDate(String input, String pattern) {
		try {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(pattern);
			return simpledateformat.parse(input);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static final Date parseDate(String string, DateTimePattern[] patterns) {

		Date date = null;

		try {
			String[] datetimePattern = new String[patterns.length];

			for (int i = 0; i < datetimePattern.length; i++) {

				datetimePattern[i] = patterns[i].getPattern();
			}

			date = DateUtils.parseDate(string, datetimePattern);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return date;
	}

	public static final String formatDate(Date date, DateTimePattern pattern) {

		String string = null;

		try {
			string = DateFormatUtils.format(date, pattern.getPattern());

		} catch (Exception e) {

			e.printStackTrace();
		}

		return string;
	}

	public enum DateTimePattern {

		ISO_DATE("yyyy-MM-dd"), ISO_DATETIME_NO_SECOND("yyyy-MM-dd HH:mm"), ISO_DATETIME("yyyy-MM-dd HH:mm:ss"), ISO_DATETIME_HAS_MILLISECOND("yyyy-MM-dd HH:mm:ss.SSS");

		private String pattern;

		private DateTimePattern(String pattern) {

			this.pattern = pattern;
		}

		public String getPattern() {

			return this.pattern;
		}
	}

	/****************************** end by wms ******************************/

	// TODO Everyone don't modify wms team the code
}
