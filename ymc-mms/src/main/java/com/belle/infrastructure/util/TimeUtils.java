/**
 * 
 */
package com.belle.infrastructure.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 
 * 
 */
public class TimeUtils extends org.apache.commons.lang.time.DateUtils {
	private static String defaultPattern = "yyyy-MM-dd";

	public final static SimpleDateFormat FORMAT_TIMESTAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public final static SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public final static SimpleDateFormat FORMAT_HOUR = new SimpleDateFormat("yyyy-MM-dd HH");

	public final static SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

	public final static SimpleDateFormat FORMAT_DIR = new SimpleDateFormat("yyyy/MM/dd/");

	/**
	 * 根据pattern判断字符串是否为合法日期
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static boolean isValidDate(String dateStr, String pattern) {
		boolean isValid = false;
		String patterns = "yyyy-MM-dd,MM/dd/yyyy";

		if (pattern == null || pattern.length() < 1) {
			pattern = "yyyy-MM-dd";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			// sdf.setLenient(false);
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
			patterns = "yyyy-MM-dd;dd/MM/yyyy;yyyy/MM/dd;yyyy/M/d h:mm";
		}
		boolean isValid = false;
		String[] patternArr = patterns.split(";");
		for (int i = 0; i < patternArr.length; i++) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(patternArr[i]);
				// sdf.setLenient(false);
				String date = sdf.format(sdf.parse(dateStr));
				if (date.equalsIgnoreCase(dateStr)) {
					isValid = true;
					TimeUtils.defaultPattern = patternArr[i];
					break;
				}
			} catch (Exception e) {
				isValid = false;
			}
		}
		return isValid;
	}

	public static String getFormatDate(String dateStr, String pattern) {
		if (pattern == null || pattern.length() < 1) {
			pattern = "yyyy-MM-dd";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(TimeUtils.defaultPattern);
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			String date = format.format(sdf.parse(dateStr));
			return date;
		} catch (Exception e) {
			System.out.println("日期格转换失败！");
		}
		return null;
	}

	public static Date formatDefaultDate(String dateStr) {
		try {
			Date date = FORMAT_DATE.parse(dateStr);
			return date;
		} catch (Exception e) {
		}
		return null;
	}

	public static String getFormatDate(Date date, SimpleDateFormat format) {
		if (format == null) {
			format = FORMAT_TIMESTAMP;
		}
		try {
			String strDate = format.format(date);
			return strDate;
		} catch (Exception e) {
			System.out.println("日期格转换失败！");
		}
		return null;
	}
	
	/**
	 * 将指定日期对象转为指定格式字符串
	 * @param date		
	 * @param pattern	默认为yyyy-MM-dd
	 * @return	
	 */
	public static String getFormatDate(Date date, String pattern) {
		if (pattern == null || pattern.length() < 1) {
			pattern = "yyyy-MM-dd";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			String strDate = sdf.format(date);
			return strDate;
		} catch (Exception e) {
			System.out.println("日期格转换失败！");
		}
		return null;
	}

	/**
	 * 将字符串转为日期对象
	 * @param s 
	 * @return
	 */
	public static Date parseDate(String s) {
		DateFormat df = DateFormat.getDateInstance();
		Date myDate = null;
		try {
			myDate = df.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return myDate;
	}

	/**
	 * 将字符串类型日期转为指定格式的日期对象  
	 * @param strDate  日期
	 * @param pattern  格式   默认为yyyy-MM-dd
	 * @return
	 */
	public static Date parseDate(String strDate, String pattern) {
		if (pattern == null || pattern.length() < 1) {
			pattern = "yyyy-MM-dd";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date nowDate = sdf.parse(strDate);
			return nowDate;
		} catch (Exception e) {
			System.out.println("无法获得当前日期！");
		}
		return null;
	}
	
	/**
	 * 日期格式化
	 * @param date
	 * @param formatStr
	 * @return
	 */
	 public static Date formatDate(Date date) {
		 String formatStr = "yyyy-MM-dd HH:mm:ss";
		return  parseDate(getFormatDate(date,formatStr),formatStr);
    }
	 
	 public static void main(String[] args) {
		 Date dt = formatDate(new Date());
		 System.out.println(dt);
	}
    

	/**
	 * 获取当前日期
	 * @param pattern  日期格式   默认为  yyyy-MM-dd
	 * @return
	 */
	public static Date getNowDate(String pattern) {
		if (pattern == null || pattern.length() < 1) {
			pattern = "yyyy-MM-dd";
		}
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
	 * 一天的开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static final Date dateBegin(Date date) {
		if (date == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		dateBegin(calendar);
		return calendar.getTime();
	}

	/**
	 * 一天的开始时间
	 * 
	 * @param calendar
	 * @return
	 */
	public static final Calendar dateBegin(Calendar calendar) {
		if (calendar == null)
			return null;
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	/**
	 * 返回两个时间相差的天数
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static final int dateSub(Date a, Date b) {
		int date = (int) (dateBegin(a).getTime() / (24 * 60 * 60 * 1000) - dateBegin(b).getTime()
				/ (24 * 60 * 60 * 1000));
		return date < 0 ? -1 : date;
	}
	
	/**
	 * 返回两个时间相差的分数
	 * @param a
	 * @param b
	 * @return
	 */
	public static final long dateSubMiddles(Date a, Date b){
//	    long middles = dateBegin(a).getTime() /(60 * 1000) - dateBegin(b).getTime() /(60 * 1000);
	    long middles = dateBegin(a).getTime()  - dateBegin(b).getTime();
	    return middles;
	}

	/**
	 * 在一个已知时间的基础上增加指定的时间
	 * 
	 * @param oleDate
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static final Date addDate(Date oldDate, int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(oldDate);
		calendar.add(Calendar.YEAR, year);
		calendar.add(Calendar.MONTH, month);
		calendar.add(Calendar.DATE, date);
		return calendar.getTime();
	}
}
