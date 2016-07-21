package com.yougou.api.beans;

import java.util.TimeZone;

/**
 * 时间戳管理类
 * 
 * @author 杨梦清
 *
 */
public class TimestampHolder {

	// 每分毫秒数
	private static final long MINUTE_MILLISECONDS = 1000L * 60L;
	// 每小时毫秒数
	private static final long HOUR_MILLISECONDS = MINUTE_MILLISECONDS * 60L;
	// 每天毫秒数
	private static final long DAILY_MILLISECONDS = HOUR_MILLISECONDS * 24L;
	// 时间戳起始值年份(JDK默认为1970年)
	private static final int TIMESTAMP_ORIG_YEAR = 1970;

	// 中国时区毫秒原始偏移量
	private long timestampRawOffset = TimeZone.getTimeZone("GMT+8").getRawOffset();

	// 当前毫妙
	private long timestamp;

	private int year;
	private int month;
	private int date;
	private int hour;
	private int minute;
	private int second;
	
	public TimestampHolder() {
		this(System.currentTimeMillis());
	}

	public TimestampHolder(long timestamp) {
		this.timestamp = timestamp;

		// 计算1970年以后至今总天数
		int totalDays =  (int) (timestamp / DAILY_MILLISECONDS);
		int currentYearElapsedDays = totalDays;
		int yearDays = 365;
		
		// 计算当前年份
		for (int i = TIMESTAMP_ORIG_YEAR; currentYearElapsedDays >= 0; i++) {
			yearDays = isLeapYear(i) ? 366 : 365;
			currentYearElapsedDays -= yearDays;
			this.year = i;
		}

		// 计算当前年份已逝去天数
		currentYearElapsedDays = yearDays - Math.abs(currentYearElapsedDays) + 1;
		
		// 计算当前年份每月天数
		int[] monthDays = new int[] { 31, (isLeapYear(year) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		
		// 计算当前月份与当前日
		for (int i = 0, j = currentYearElapsedDays; i < monthDays.length; i++) {
			if ((j -= monthDays[i]) <= 0) {
				this.month = i + 1;
				this.date = j + monthDays[i];
				break;
			}
		}
		
		// 计算当前时
		this.hour = (int) (timestamp / HOUR_MILLISECONDS % 24L + timestampRawOffset / HOUR_MILLISECONDS);
		// 计算当前分
		this.minute = (int) (timestamp / MINUTE_MILLISECONDS % 60L);
		// 计算当前秒
		this.second = (int) (timestamp / 1000L % 60L);
	}

	public long getTimestamp() {
		return timestamp;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDate() {
		return date;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public int getSecond() {
		return second;
	}
	
	public String toString() {
		return new StringBuffer()
		.append(leftPad(Integer.toString(year), 4, "0"))
		.append("-")
		.append(leftPad(Integer.toString(month), 2, "0"))
		.append("-")
		.append(leftPad(Integer.toString(date), 2, "0"))
		.append(" ")
		.append(leftPad(Integer.toString(hour), 2, "0"))
		.append(":")
		.append(leftPad(Integer.toString(minute), 2, "0"))
		.append(":")
		.append(leftPad(Integer.toString(second), 2, "0"))
		.toString();
	}
	
	/**
	 * 字符串左拼接
	 * 
	 * @param str
	 * @param length
	 * @param character
	 * @return String
	 */
	private String leftPad(String str, int length, String character) {
		while (str.length() < length) {
			str = character + str;
		}
		return str.substring(str.length() - length);
	}

	/**
	 * 是否为润年
	 * 
	 * @param year
	 * @return boolean
	 */
	private boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}
	
	public static void main(String[] args) {
		System.out.println(new TimestampHolder());
	}
}
