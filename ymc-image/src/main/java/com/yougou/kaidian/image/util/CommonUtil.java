package com.yougou.kaidian.image.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

public class CommonUtil {

	//是否为数字
	public static boolean isNumeric(String str) {
		if (str.matches("\\d*")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isNumber(String str) {
		// 验证数字：^[0-9]*$
		return str.matches("^\\d+(\\,\\d+)*$");
	}

	public static String getRound(double d) {
		if (d == 0) {
			return "0";
		}
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(d);
	}

	private static String replace(String str, String replace, String replaceStr) {
		if (str != null) {
			int i = str.indexOf(replace);
			int j = 0;
			while (i != -1) {
				j++;
				str = str.substring(0, i) + replaceStr
						+ str.substring(i + replace.length());
				i = str.indexOf(replace, i + replaceStr.length());
			}
		}
		return str;
	}

	public static String htmlType(String s) {
		if (s == null || s.toLowerCase().equals("null")) {
			return "";
		}
		s = replace(s, " ", "&nbsp;");
		s = replace(s, "\r\n", "<br>");
		return s;
	}

	/**
	 * 判断该字符串是否为中文
	 * 
	 * @param str
	 *            String 输入字符
	 * @return boolean
	 */
	public static boolean IsChinese(String str) {
		if (str.matches("[\u4e00-\u9fa5]+")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isGB(String str) {
		if (null == str) {
			return false;
		}
		if (str.trim() == "") {
			return false;
		}
		byte[] bytes = str.getBytes();
		if (bytes.length < 2) {
			return false;
		}
		byte aa = (byte) 0xB0;
		byte bb = (byte) 0xF7;
		byte cc = (byte) 0xA1;
		byte dd = (byte) 0xFE;
		if (bytes[0] >= aa && bytes[0] <= bb) {
			if (bytes[1] < cc || bytes[1] > dd) {
				return false;
			}
			return true;
		}
		return false;
	}

	public static boolean isBig5(String str) {
		if (null == str) {
			return false;
		}
		if (str.trim() == "") {
			return false;
		}
		byte[] bytes = str.getBytes();
		if (bytes.length < 2) {
			return false;
		}
		byte aa = (byte) 0xB0;
		byte bb = (byte) 0xF7;
		byte cc = (byte) 0xA1;
		byte dd = (byte) 0xFE;
		if (bytes[0] >= aa && bytes[0] <= bb) {
			if (bytes[1] < cc || bytes[1] > dd) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * 对字符串进行MD5加密
	 * 
	 * @param plainText
	 *            String
	 * @return String
	 */
	public static String md5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

	/** 将输入字符串转化为utf8编码,并返回该编码的字符串 */
	public static String changeEncode(String in) {
		String s = null;
		byte temp[];
		if (in == null) {
			System.out.println("Warn:Chinese null founded!");
			return new String("");
		}
		try {
			temp = in.getBytes("utf8");
			s = new String(temp);
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.toString());
		}
		return s;
	}

	/** 根据时间生成唯一编号 */
	public static String buildNumber(int length) {
		long time = System.currentTimeMillis();

		Random random = new Random();
		StringBuffer buffer = new StringBuffer().append(time);

		String result = "";

		if (buffer.length() >= length) {
			buffer = new StringBuffer();
			for (int i = 0; i < length; i++) {
				buffer.append(random.nextInt(9));
			}
		} else {
			for (int i = 0; i < (length - buffer.length()); i++) {
				buffer.append(random.nextInt(9));
			}
		}

		result = buffer.toString();
		return result;
	}

	public static String html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script>]*?>[\s\S]*?<\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style>]*?>[\s\S]*?<\/style>
			// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;
	}

	/** The Constant hex. */
	private final static String[] hex = { "00", "01", "02", "03", "04", "05",
			"06", "07", "08", "09", "0A", "0B", "0C", "0D", "0E", "0F", "10",
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "1A", "1B",
			"1C", "1D", "1E", "1F", "20", "21", "22", "23", "24", "25", "26",
			"27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F", "30", "31",
			"32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C",
			"3D", "3E", "3F", "40", "41", "42", "43", "44", "45", "46", "47",
			"48", "49", "4A", "4B", "4C", "4D", "4E", "4F", "50", "51", "52",
			"53", "54", "55", "56", "57", "58", "59", "5A", "5B", "5C", "5D",
			"5E", "5F", "60", "61", "62", "63", "64", "65", "66", "67", "68",
			"69", "6A", "6B", "6C", "6D", "6E", "6F", "70", "71", "72", "73",
			"74", "75", "76", "77", "78", "79", "7A", "7B", "7C", "7D", "7E",
			"7F", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89",
			"8A", "8B", "8C", "8D", "8E", "8F", "90", "91", "92", "93", "94",
			"95", "96", "97", "98", "99", "9A", "9B", "9C", "9D", "9E", "9F",
			"A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "AA",
			"AB", "AC", "AD", "AE", "AF", "B0", "B1", "B2", "B3", "B4", "B5",
			"B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF", "C0",
			"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CA", "CB",
			"CC", "CD", "CE", "CF", "D0", "D1", "D2", "D3", "D4", "D5", "D6",
			"D7", "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF", "E0", "E1",
			"E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "EA", "EB", "EC",
			"ED", "EE", "EF", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7",
			"F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF" };

	/** The Constant val. */
	private final static byte[] val = { 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x00, 0x01,
			0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F };

	/**
	 * Escape.
	 * 
	 * @param s
	 *            the s
	 * 
	 * @return the string
	 */
	/**
	 * 编码
	 * 
	 * @param s
	 * @return
	 */
	public static String escape(String s) {
		if (s != null) {
			StringBuffer sbuf = new StringBuffer();
			int len = s.length();
			for (int i = 0; i < len; i++) {
				int ch = s.charAt(i);
				if ('A' <= ch && ch <= 'Z') {
					sbuf.append((char) ch);
				} else if ('a' <= ch && ch <= 'z') {
					sbuf.append((char) ch);
				} else if ('0' <= ch && ch <= '9') {
					sbuf.append((char) ch);
				} else if (ch == '-' || ch == '_' || ch == '.' || ch == '!'
						|| ch == '~' || ch == '*' || ch == '\'' || ch == '('
						|| ch == ')') {
					sbuf.append((char) ch);
				} else if (ch <= 0x007F) {
					sbuf.append('%');
					sbuf.append(hex[ch]);
				} else {
					sbuf.append('%');
					sbuf.append('u');
					sbuf.append(hex[(ch >>> 8)]);
					sbuf.append(hex[(0x00FF & ch)]);
				}
			}
			return sbuf.toString();
		}
		return null;
	}

	/**
	 * Unescape.
	 * 
	 * @param s
	 *            the s
	 * 
	 * @return the string
	 */
	/**
	 * 解码 说明：本方法保证 不论参数s是否经过escape()编码，均能得到正确的“解码”结果
	 * 
	 * @param s
	 * @return
	 */
	public static String unescape(String s) {
		if (s != null) {
			StringBuffer sbuf = new StringBuffer();
			int i = 0;
			int len = s.length();
			while (i < len) {
				int ch = s.charAt(i);
				if ('A' <= ch && ch <= 'Z') {
					sbuf.append((char) ch);
				} else if ('a' <= ch && ch <= 'z') {
					sbuf.append((char) ch);
				} else if ('0' <= ch && ch <= '9') {
					sbuf.append((char) ch);
				} else if (ch == '-' || ch == '_' || ch == '.' || ch == '!'
						|| ch == '~' || ch == '*' || ch == '\'' || ch == '('
						|| ch == ')') {
					sbuf.append((char) ch);
				} else if (ch == '%') {
					int cint = 0;
					if ('u' != s.charAt(i + 1)) {
						cint = (cint << 4) | val[s.charAt(i + 1)];
						cint = (cint << 4) | val[s.charAt(i + 2)];
						i += 2;
					} else {
						cint = (cint << 4) | val[s.charAt(i + 2)];
						cint = (cint << 4) | val[s.charAt(i + 3)];
						cint = (cint << 4) | val[s.charAt(i + 4)];
						cint = (cint << 4) | val[s.charAt(i + 5)];
						i += 5;
					}
					sbuf.append((char) cint);
				} else {
					sbuf.append((char) ch);
				}
				i++;
			}
			return sbuf.toString();
		}
		return null;
	}

	public static String getIpAddr(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("Proxy-Client-IP");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("WL-Proxy-Client-IP");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getRemoteAddr();

		}

		// updateby wuyang 获取ip如：172.20.1.54, 183.62.162.113 时，截取第二个
		if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			String[] strIps = StringUtils.stripToEmpty(ip).split(",");
			if (strIps != null && strIps.length > 1) {
				return StringUtils.stripToEmpty(strIps[1]);
			}
		}

		return ip;

	}

	/**
	 * 获取url,如：http://www.yougou.com
	 * @param request
	 * @return
	 */
	public static String getRequestUrl(HttpServletRequest request) {
		StringBuilder result = new StringBuilder(32);
		result.append(request.getScheme()).append("://").append(request.getServerName());
		int port = request.getServerPort();
		return port != 80?result.append(':').append(port).toString():result.toString();
	}
	
	/**
	 * 获取经过trim处理过的String
	 * @param str 待处理的String
	 * @return
	 */
	public static String getTrimString(String str) {
		return str == null ? "" : str.trim();
	}
	
	/**
	 * 获取文件路径的分隔符
	 * @param path 文件路径
	 * @return 返回分隔符"\\"或"/"
	 */
	public static String getPathSlashType(String path) {
		return path.lastIndexOf("\\") != -1 ? "\\" : "/";
	}
	
	/**
	 * 通过 文件全路径 获取 文件名
	 * @param path 文件全路径 
	 * @return 返回文件名
	 */
	public static String getFileNameByFullPath(String path) {
		return path.substring(path.lastIndexOf(getPathSlashType(path) + 1), path.length());
	}
	
	/**
	 * 获取文件名后缀名
	 * @param fileName 文件名
	 * @return 返回后缀名
	 */
	public static String getFileSuffixByFileName(String fileName) {
		return fileName.contains(".") ? 
				fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : "";
	}
	
	/**
     * 获取工程上下文的物理路径
     * @return 工程上下文的物理路径
     */
    public static String getContextRealPath() {
		return getRealPath("/");
	}
    
    /**
     * 获取物理路径
     * @param path 相对路径
     * @return 
     */
    public static String getRealPath(String path) {
    	return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(path);
    }
    
    /**
	 * 格式化数字，进行在前面补0的操作
	 * @param num 索引
	 * @param len 长度 len至少为2
	 * @return 
	 */
	public static String addZeroBeforeNumber(int num, int len) {
		StringBuffer sb = new StringBuffer("");
		for (int i = 1; i < len; i++) {
			int max = (int) Math.pow(10, i);
			if (num < max) {
				sb.append(0);
			}
		}
		sb.append(num);
		return sb.toString();
	}
	
	/**
	 * 把上传上来的文件保存到本地
	 * @param uploadFile 上传上来的文件
	 * @param filePath 文件路径
	 * @return 返回保存好的文件
	 */
	public static File saveUploadFile(MultipartFile uploadFile, String filePath) {
		File file = null;
		OutputStream os = null;
		try {
			byte[] bytes = uploadFile.getBytes();
			file = new File(filePath);
			if (file.exists()) file.delete();
			os = new FileOutputStream(file);
			os.write(bytes);
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
