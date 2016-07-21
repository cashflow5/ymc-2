package com.yougou.dto.input;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.time.DateUtils;
import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.yougou.dto.BaseDto;

/**
 * 
 * @author 杨梦清
 * 
 */
public class InputDto extends BaseDto {

	private static final long serialVersionUID = 7978719981956480110L;

	/** 商家编码 **/
	private String merchant_code;

	public String getMerchant_code() {
		return merchant_code;
	}

	public void setMerchant_code(String merchant_code) {
		this.merchant_code = merchant_code;
	}

	@IgnoreProperty
	protected void validateTimestampRange(String startDateStr, String endDateStr, String fields, long timestamp) {
		validateTimestampRange(parseDate(startDateStr), parseDate(endDateStr), fields, timestamp);
	}
	
	@IgnoreProperty
	protected void validateTimestampRange(Date start, Date end, String fields, long timestamp) {
		if (start != null && end != null && Math.abs(start.getTime() - end.getTime()) > timestamp) {
			throw new IllegalArgumentException(fields + " range overflow, legal range is " + timestamp + " timestamp.");
		}
	}
	
	@IgnoreProperty
	protected void validateMonthRange(String startDateStr, String endDateStr, String fields, int months) {
		validateMonthRange(parseDate(startDateStr), parseDate(endDateStr), fields, months);
	}
	
	@IgnoreProperty
	protected void validateMonthRange(Date start, Date end, String fields, int months) {
		if (start != null && end != null ) {
			//&& Math.abs(getTimeField(start, Calendar.MONTH) - getTimeField(end, Calendar.MONTH)) > months
					GregorianCalendar grc=new GregorianCalendar();
					grc.setTime(start);


					grc.add(GregorianCalendar.MONTH,months);
					Date maxDate = grc.getTime(); 
					if(maxDate.before(end)) {
						throw new IllegalArgumentException(fields + " range overflow, legal range is " + months + " months.");
					}


		}
	}
	
	@IgnoreProperty
	private int getTimeField(Date time, int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		return calendar.get(Calendar.MONTH);
	}
	
	@IgnoreProperty
	private Date parseDate(String dateStr) {
		try {
			if (dateStr == null || dateStr.trim().equals("")) {
				return null;
			} else {
				return DateUtils.parseDate(dateStr, new String[]{ "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS" });
			}
		} catch (Exception e) {
			return null;
		}
	}
}
