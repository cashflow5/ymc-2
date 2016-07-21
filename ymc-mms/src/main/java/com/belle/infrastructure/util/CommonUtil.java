package com.belle.infrastructure.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

public class CommonUtil {

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
                str = str.substring(0, i) + replaceStr + str.substring(i + replace.length());
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
     * @param str String 输入字符
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
     * @param plainText String
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
//            System.out.println("Warn:Chinese null founded!");
            return new String("");
        }
        try {
            temp = in.getBytes("utf8");
            s = new String(temp);
        } catch (UnsupportedEncodingException e) {
//            System.out.println(e.toString());
        }
        return s;
    }
    
    
    /**根据时间生成唯一编号   */
    public static String buildNumber(int length){
    	long time = System.currentTimeMillis();
    	
    	Random random = new Random();
    	StringBuffer buffer = new StringBuffer().append(time);
    	
    	String result = "";

    	if(buffer.length() >= length){
    	    buffer = new StringBuffer();
    	    for (int i = 0; i < length; i++) {
                buffer.append(random.nextInt(9));
            }
    	}else{
    		for (int i = 0; i < (length - buffer.length()); i++) {
    			buffer.append(random.nextInt(9));
			}
    	}
    	
    	result = buffer.toString();
    	return result;
    }

    
    /**
	 * 获取国际化资源文件中的键所对应的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getLocaleBundleResourceValue(String key) {
		// 获取系统默认的国家/语言环境
		Locale myLocale = Locale.getDefault();
		// 根据指定的国家/语言环境加载资源文件
		ResourceBundle bundle = ResourceBundle.getBundle("yitianplatform", myLocale);
		// 获取资源文件中的key为hello的value值
		return bundle.getString(key);
		
		
	}
	
	/**
	 * 判断是否是正整数
	 * @param str
	 * @return
	 */
	public static boolean isPositiveInteger(String str){
		if(StringUtils.isBlank(str)){
			return false;
		}
		return str.trim().matches("[0-9]+");
	}
	
	/**
	 * 判断是否为数字（含小数）
	 * @param number
	 * @return
	 */
	public static boolean isNumeric(String number) {
		if(StringUtils.isBlank(number)){
			return false;
		}
		number=number.trim();
		return number.matches("[\\+-]?[0-9]+((.)[0-9])*[0-9]*");  
	}  

	public static void main(String[] args) {
		System.out.println(isNumeric("+a1123456.001"));
	}
}
