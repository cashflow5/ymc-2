package com.yougou.kaidian.bi.beans;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntelligenceCalendar {

	private static final Map<Integer, Integer> DAY_OF_WEEK_OFFSET = new HashMap<Integer, Integer>();
	
	private static final String WEEK_PATTERN = "{0,number,0000}年{1,number,00}周";
	
	private static final String MONTH_PATTERN = "{0,number,0000}-{1,number,00}";
	
	private static final String MIN_DATETIME_PATTERN = "{0,number,0000}-{1,number,00}-{2,number,00} 00:00:00";
	
	private static final String MAX_DATETIME_PATTERN = "{0,number,0000}-{1,number,00}-{2,number,00} 23:59:59";
	
	static {
		DAY_OF_WEEK_OFFSET.put(Calendar.SUNDAY, 1);
		DAY_OF_WEEK_OFFSET.put(Calendar.MONDAY, 7);
		DAY_OF_WEEK_OFFSET.put(Calendar.TUESDAY, 6);
		DAY_OF_WEEK_OFFSET.put(Calendar.WEDNESDAY, 5);
		DAY_OF_WEEK_OFFSET.put(Calendar.THURSDAY, 4);
		DAY_OF_WEEK_OFFSET.put(Calendar.FRIDAY, 3);
		DAY_OF_WEEK_OFFSET.put(Calendar.SATURDAY, 2);
	}
	
	private int thisYear;

	private List<Entry> weekEntrys = new ArrayList<Entry>();
	private List<Entry> monthEntrys = new ArrayList<Entry>();
	
	public IntelligenceCalendar() {
		initEntrys(null);
	}

	public IntelligenceCalendar(int thisYear) {
		initEntrys(thisYear);
	}
	
	private void initEntrys(Integer year) {
		// 当前日历
		Calendar now = Calendar.getInstance();
		// 当前年份
		int nowYear = now.get(Calendar.YEAR);
		// 当前月份
		int thisMonth = now.get(Calendar.MONTH);
		// 当前毫秒
		long nowMillis = now.getTimeInMillis();
		// 参数年份
		thisYear = year == null ? nowYear : year.intValue();
		// 参数日历
		Calendar calendar = new GregorianCalendar(this.thisYear, 0, 1);
		
		long before, after;
		boolean isloop;
		int month, dayOfMonth, offset;
		
		do {
			month = (calendar.get(Calendar.MONTH) + 1);
			dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			
			Entry weekEntry = new Entry();
			weekEntry.text = MessageFormat.format(WEEK_PATTERN, thisYear, weekEntrys.size() + 1);
			weekEntry.start = MessageFormat.format(MIN_DATETIME_PATTERN, thisYear, month, dayOfMonth);
			
			// 累加前计算偏移量信息
			offset = (month == 1 && dayOfMonth == 1) ? DAY_OF_WEEK_OFFSET.get(calendar.get(Calendar.DAY_OF_WEEK)) : 7;
			
			// 日历移至下周
			before = calendar.getTimeInMillis();
			calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth += offset);
			after = calendar.getTimeInMillis();
			
			// 累加后计算偏移量信息
			if (isloop = calendar.get(Calendar.YEAR) > thisYear) {
				offset = 0;
				calendar.set(thisYear, (month - 1), (dayOfMonth + (0 - calendar.get(Calendar.DAY_OF_MONTH))));
			} else {
				offset = 1;
			}
			
			weekEntry.checked = (nowMillis >= before && nowMillis <= after);
			weekEntry.end = MessageFormat.format(MAX_DATETIME_PATTERN, thisYear, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH) - offset); 
			weekEntrys.add(weekEntry);
			
		} while (!isloop && calendar.get(Calendar.YEAR) == thisYear);
		
		int[] monthDays = {31, ((thisYear % 400 == 0 || (thisYear % 4 == 0 && thisYear % 100 != 0)) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		for (int i = 0, j = 1; i < monthDays.length; i++, j++) {
			Entry monthEntry = new Entry();
			monthEntry.text = MessageFormat.format(MONTH_PATTERN, thisYear, j);
			monthEntry.start = MessageFormat.format(MIN_DATETIME_PATTERN, thisYear, j, 1);
			monthEntry.end = MessageFormat.format(MAX_DATETIME_PATTERN, thisYear, j, monthDays[i]);
			monthEntry.checked = (thisYear == nowYear && i == thisMonth);
			monthEntrys.add(monthEntry);
		}
		
		if (thisYear > nowYear) {
			weekEntrys.get(0).checked = true;
			monthEntrys.get(0).checked = true;
		} else if (thisYear < nowYear) {
			weekEntrys.get(weekEntrys.size() - 1).checked = true;
			monthEntrys.get(monthEntrys.size() - 1).checked = true;
		}
	}
	
	public static void main(String[] args) {
		IntelligenceCalendar ic = new IntelligenceCalendar(2014);
		System.out.println(ic.getThisYear());
		System.out.println(ic.getThisYearWeeks());
		System.out.println(ic.getThisYearMonths());
	}

	/**
	 * 当前年份
	 * 
	 * @return
	 */
	public int getThisYear() {
		return thisYear;
	}

	/**
	 * 获取今年的星期信息
	 * 
	 * @return List
	 */
	public List<Entry> getThisYearWeeks() {
		return weekEntrys;
	}

	/**
	 * 获取今年的月份信息
	 * 
	 * @return List
	 */
	public List<Entry> getThisYearMonths() {
		return monthEntrys;
	}

	public class Entry {
		private String text;
		private String start;
		private String end;
		private boolean checked;

		public String getText() {
			return text;
		}

		public String getStart() {
			return start;
		}

		public String getEnd() {
			return end;
		}

		public boolean isChecked() {
			return checked;
		}

		@Override
		public String toString() {
			return "\nEntry [text=" + text + ", start=" + start + ", end=" + end + ", checked=" + checked + "]";
		}
	}
}
