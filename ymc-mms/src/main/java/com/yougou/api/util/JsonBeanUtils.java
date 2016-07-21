package com.yougou.api.util;

import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.commons.lang.time.DateFormatUtils;

public class JsonBeanUtils {
	
	private static final JsonConfig JSON_CONFIG = new JsonConfig();
	
	static {
		JsonValueProcessor jsonValueProcessor = getJsonValueProcessor(DatePattern.ISO_DATETIME_PATTERN);
		JSON_CONFIG.registerJsonValueProcessor(java.sql.Date.class, jsonValueProcessor);
		JSON_CONFIG.registerJsonValueProcessor(java.sql.Timestamp.class, jsonValueProcessor);
		JSON_CONFIG.registerJsonValueProcessor(java.util.Date.class, jsonValueProcessor);
	}
	
	public static JsonConfig getDateTimeJsonValueProcessorConfig() {
		return JsonBeanUtils.JSON_CONFIG;
	}

	public static JsonValueProcessor getJsonValueProcessor(DatePattern datePattern) {
		return new DateJsonValueProcessor(datePattern);
	}
	
	public static enum DatePattern {
		
		ISO_DATE_PATTERN("yyyy-MM-dd"),
		ISO_DATETIME_PATTERN("yyyy-MM-dd HH:mm:ss")
		;
		
		private String value;

		private DatePattern(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	public static class DateJsonValueProcessor implements JsonValueProcessor {
		
		private DatePattern datePattern;

		protected DateJsonValueProcessor(DatePattern datePattern) {
			this.datePattern = datePattern;
		}

		@Override
		public Object processArrayValue(Object arg0, JsonConfig arg1) {
			return processValue(arg0);
		}

		@Override
		public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
			return processValue(arg1);
		}

		private Object processValue(Object value) {
			String result = "";
			if (value instanceof Date) {
				try {
					result = DateFormatUtils.format((Date) value, datePattern.getValue());
				} catch (Exception e) {
				}
			}
			return result;
		}
	}
}
