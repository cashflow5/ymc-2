package com.yougou.api.util;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author 杨梦清
 * 
 */
public class FieldNameChanger {

	/**
	 * 将字段名称以下划线劈开并置劈开位置字符首字母为大写<br>
	 * 如：commodity_name 处理完成后结果为 commodityName<br>
	 * 
	 * @param fieldName
	 * @return String
	 */
	public static String combineFieldName(String fieldName) {
		return combineFieldName(fieldName, "_");
	}
	
	/**
	 * 将字段名称以分隔符劈开并置劈开位置字符首字母为大写<br>
	 * 如：commodity_name 处理完成后结果为 commodityName<br>
	 * 
	 * @param fieldName
	 * @param delimiter
	 * @return String
	 */
	public static String combineFieldName(String fieldName, String delimiter) {
		StringTokenizer tokenizer = new StringTokenizer(fieldName, delimiter);
		fieldName = tokenizer.nextToken();
		while (tokenizer.hasMoreElements()) {
			fieldName += StringUtils.capitalize(tokenizer.nextToken());
		}
		return fieldName;
	}
	
	/**
	 * 将字段名称以<b>大写字母</b>位置劈开并以<b>下划线</b>组合<br>
	 * 如：commodityName 处理完成后结果为 commodity_name<br>
	 * 
	 * @param fieldName
	 * @return String
	 */
	public static String splitFieldName(String fieldName) {
		return splitFieldName(fieldName, "[A-Z]+");
	}
	
	/**
	 * 将字段名称以<b>正则表达式</b>匹配位置劈开并以<b>下划线</b>组合<br>
	 * 如：commodityName 以正则表达式 [A-Z]+ 处理完成后结果为 commodity_name<br>
	 * 
	 * @param fieldName
	 * @param regex
	 * @return String
	 */
	public static String splitFieldName(String fieldName, String regex) {
		return splitFieldName(fieldName, regex, "_");
	}
	
	/**
	 * 将字段名称以<b>正则表达式</b>匹配位置劈开并以<b>分隔符</b>组合<br>
	 * 如：commodityName 以正则表达式 [A-Z]+ 处理完成后结果为 commodity_name<br>
	 * 
	 * @param fieldName
	 * @param regex
	 * @param delimiter
	 * @return String
	 */
	public static String splitFieldName(String fieldName, String regex, String delimiter) {
		Matcher matcher = Pattern.compile(regex).matcher(fieldName);
		while (matcher.find()) {
			fieldName = fieldName.replaceFirst(matcher.group(), (matcher.start() == 0 ? "" : delimiter) + matcher.group().toLowerCase());
		}
		return fieldName;
	}
	
	public static void main(String[] args) {
		System.out.println(combineFieldName("commodity_namess_mxx"));
		System.out.println(combineFieldName("commodityMamessMxx"));
		System.out.println(splitFieldName("commodityNamessMxx"));
		System.out.println(splitFieldName("commodity_mamess_mxx"));
		System.out.println(splitFieldName("sqlDriverManage"));
		System.out.println(combineFieldName("sql_driver_manage"));
	}
}

