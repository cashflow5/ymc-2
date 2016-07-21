package com.yougou.kaidian.taobao.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.yougou.kaidian.taobao.constant.TaobaoImportConstants;

public class TaobaoImportUtils {

	public static String subStr(String title) {
		if (StringUtils.isEmpty(title)) {
			return "";
		}
		if (title.length() > 10) {
			return "<a  href='##' title='" + title + "'>【" + title.substring(0, 10) + " ... 】</a>";
		}
		return "<a  href='##' 【" + title + " ... 】</a>";
	}

	/**
	 * 获取统一的格式
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static SimpleDateFormat dateFormat(String dateStr) throws Exception {
		SimpleDateFormat dateFormat = null;
		if (dateStr.indexOf("-") != -1) {
			dateFormat = getDateFormat("-", dateStr);

		} else {
			// 空格截取
			dateFormat = getDateFormat("/", dateStr);
		}
		if (dateFormat == null) {
			return null;
		}
		return dateFormat;
	}

	private static SimpleDateFormat getDateFormat(String type, String dateStr) {
		SimpleDateFormat dateFormat = null;
		String[] strArr = dateStr.split(" ");
		if (strArr.length == 1) {
			dateFormat = new SimpleDateFormat("yyyy" + type + "MM" + type + "dd");
		} else {
			if (strArr[1].split(":").length == 3) {
				dateFormat = new SimpleDateFormat("yyyy" + type + "MM" + type + "dd HH:mm:ss");
			} else if (strArr[1].split(":").length == 2) {
				dateFormat = new SimpleDateFormat("yyyy" + type + "MM" + type + "dd HH:mm");
			} else {
				dateFormat = new SimpleDateFormat("yyyy" + type + "MM" + type + "dd HH");
			}
		}
		return dateFormat;
	}

	/**
	 * 进入线程前 将dateFormat 统一准备，因为csv文件格式都一样，不用每条数据都去用一个SimpleDateFormat
	 * 
	 * @param dateStr
	 * @param dateFormat
	 * @return
	 * @throws Exception
	 */
	public static Date formatStrToDate(String dateStr) throws Exception {
		// 入口处判断空值
		if (StringUtils.isEmpty(dateStr)) {
			return null;
		}

		SimpleDateFormat dateFormat;
		if (dateStr.indexOf("-") != -1) {
			dateFormat = getDateFormat("-", dateStr);

		} else {
			// 空格截取
			dateFormat = getDateFormat("/", dateStr);
		}
		if (dateFormat == null) {
			return null;
		}
		return dateFormat.parse(dateStr);
	}

	/**
	 * 获取数字中文名称
	 * 
	 * @param d
	 * @return
	 */
	public static String getChinese(Long d) {
		String[] str = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		String s = String.valueOf(d);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			String index = String.valueOf(s.charAt(i));
			sb = sb.append(str[Integer.parseInt(index)]);
		}

		return sb.toString();
	}

	/**
	 * 判断是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
	/**
	 * 计算需要开辟多少个线程
	 */
	public static int getThreadSum(int length) {
		if (length <= TaobaoImportConstants.ONE_THREAD_EXECTOR_NUM) {
			return 1;
		}
		if (length <= TaobaoImportConstants.ONE_THREAD_EXECTOR_NUM * TaobaoImportConstants.THREAD_MAX) {
			return length / TaobaoImportConstants.ONE_THREAD_EXECTOR_NUM;
		}
		return TaobaoImportConstants.THREAD_MAX;
	}
	
	/**
	 * 校验款色编码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkYougouSupplierCode(String str) {
		String regex = "^[0-9a-zA-Z_-]+$";
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(str);
		return matcher.find();
	}

	/**
	 * 校验小数
	 */
	public static boolean checkYougouPrice(String str) {
		String regex = "^([0-9]+)(\\.\\d{1,})?$";
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(str);
		return matcher.find();
	}
}
