package com.belle.infrastructure.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
     *
     * @param c 需要判断的字符
     * @return 返回true,Ascill字符
     */
    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @param s 需要得到长度的字符串
     * @return i得到的字符串长度
     */
    public static int length(String s) {
        if (s == null) {
            return 0;
        }
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    /**
     * 截取一段字符的长度,不区分中英文,如果数字不正好，则少取一个字符位
     *
     *
     * @param  origin 原始字符串
     * @param len 截取长度(一个汉字长度按2算的)
     * @param c 后缀
     * @return 返回的字符串
     */
    public static String substring(String origin, int len, String c) {
        if (origin == null || origin.equals("") || len < 1) {
            return "";
        }
        if (length(origin) <= len) {
            return origin;
        }
        byte[] strByte = new byte[len];
        if (len > length(origin)) {
            return origin + c;
        }
        try {
            System.arraycopy(origin.getBytes("GBK"), 0, strByte, 0, len);
            int count = 0;
            for (int i = 0; i < len; i++) {
                int value = (int) strByte[i];
                if (value < 0) {
                    count++;
                }
            }
            if (count % 2 != 0) {
                len = (len == 1) ? ++len : --len;
            }
            return new String(strByte, 0, len, "GBK") + c;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 判断字符串是否存在
     * @param str
     * @return
     */
    public static boolean isExist(String str) {
        if (str != null && str.trim().length() > 0) { 
            return true;
        } 
        return false;
    }
    
    public static boolean isValidDate(String dateStr) {
        boolean isValid = false;
        if (dateStr == null || dateStr.length() <= 0) {
            return false;
        }
        String pattern = "yyyy-MM-dd";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String date = sdf.format(sdf.parse(dateStr));
            if (date.equalsIgnoreCase(dateStr)) {
                isValid = true;
            }
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }
    
    /**
     * 验证日期格式是否是 yyyy-MM-dd HH:mm:ss
     * @param dateStr
     * @return
     */
    public static boolean isValidDate2(String dateStr) {
        boolean isValid = false;
        if (dateStr == null || dateStr.length() <= 0) {
            return false;
        }
        String pattern = "yyyy-MM-dd HH:mm:ss";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String date = sdf.format(sdf.parse(dateStr));
            if (date.equalsIgnoreCase(dateStr)) {
                isValid = true;
            }
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }
    /**
     * 验证日期格式是否是 yyyy-MM-dd HH:mm:ss
     * @param dateStr
     * @return
     */
    public static boolean isValidDate3(String dateStr) {
        boolean isValid = false;
        if (dateStr == null || dateStr.length() <= 0) {
            return false;
        }
        String pattern = "yyyy-MM-dd HH:mm";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String date = sdf.format(sdf.parse(dateStr));
            if (date.equalsIgnoreCase(dateStr)) {
                isValid = true;
            }
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }
    
       
    public static boolean isNumber(String str) {
        //验证数字：^[0-9]*$
        return str.matches("^\\d+(\\,\\d+)*$");
    }
    
    // 转码iso-8859-1
    public static String ecodeStr(String str) {
        try {
            str = new String(str.getBytes("UTF-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
    
    // 转码utf-8
    public static String decodeStr(String str) {
        try {
            str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
    
    public static String joinStr(String[] array,String joinChar){
    	if(array==null)
    	return "";
    	if(joinChar==null)joinChar=",";
    	String str="";
    	for(int i=0;i<array.length;i++){
    		str=str+array[i];
    		if(i<array.length-1)str=str+joinChar;
    	}
    	return str;
    }
    
    
    /*
	 * 按指定长度和编码拆分中英混合字符串
	 * 
	 * @param text 被拆分字符串
	 * @param length 指定长度，即子字符串的最大长度
	 * @param encoding 字符编码
	 * @return 拆分后的子字符串列表
	 * @throws Exception
	 */
	public static ArrayList<String> split(String text, int length, String encoding) throws Exception {
		ArrayList<String> texts = new ArrayList<String>();
		int pos = 0;
		int startInd = 0;
		for (int i = 0; text != null && i < text.length();) {
			byte[] b = String.valueOf(text.charAt(i)).getBytes(encoding);
			if (b.length > length) {
				i++;
				startInd = i;
				continue;
			}
			pos += b.length;
			if (pos >= length) {
				int endInd;
				if (pos == length) {
					endInd = ++i;
				} else {
					endInd = i;
				}
				texts.add(text.substring(startInd, endInd));
				pos = 0;
				startInd = i;
			} else {
				i++;
			}
		}
		if (startInd < text.length()) {
			texts.add(text.substring(startInd));
		}
		return texts;
	}
	
	/**
	 * 指定长度按UTF-8编码拆分中英混合字符串，即一个非ASCII码长度为3
	 * @param text 被拆分字符串
	 * @param length 指定长度，即子字符串的最大长度
	 * @return
	 */
	public static ArrayList<String> split(String text, int len) {
		try {
			return split(text, len, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 
	 * @Title: testParttern 
	 * @Description: TODO(校验是否为非0的正整数) 
	 * @param @param numStr
	 * @param @return    设定文件 
	 * @return boolean    返回类型 
	 * @throws
	 */
	public static boolean isPositiveNum(String numStr){  
         //表达式的功能：验证必须为数字（整数或小数）  
		String pattern = "^\\+?[1-9][0-9]*$";  
		//对()的用法总结：将()中的表达式作为一个整体进行处理，必须满足他的整体结构才可以。  
		//(.[0-9]+)? ：表示()中的整体出现一次或一次也不出现  
		Pattern p = Pattern.compile(pattern);  
		Matcher m = p.matcher(numStr);  
		return m.matches();  
	}  
	
	/***
	 * 
	 * @Description: 校验是否为正数（包括正整数，正浮点数）
	 * @param numStr
	 * @return
	 */
    public static boolean isZum(String numStr){  
		String pattern = "^(0|([1-9]\\d*))(\\.\\d+)?$";  
		Pattern p = Pattern.compile(pattern);  
		Matcher m = p.matcher(numStr);  
		return m.matches();  
	}  
	
    /**
     * 针对url乱码  <b>escape(escape('str'))</b> 进行解码
     * 
     * @param src
     * @return
     */
	public static String unescape(String src) {
	    StringBuffer tmp = new StringBuffer();
	    tmp.ensureCapacity(src.length());
	    int lastPos=0,pos=0;
	    char ch;
	    while (lastPos<src.length()) {
	    	pos = src.indexOf("%",lastPos);
	    	if (pos == lastPos) {
	    		if (src.charAt(pos+1)=='u') {
	    			ch = (char)Integer.parseInt(src.substring(pos+2,pos+6),16);
	    			tmp.append(ch);
	    			lastPos = pos+6;
	    		} else {
	    			ch = (char)Integer.parseInt(src.substring(pos+1,pos+3),16);
	    			tmp.append(ch);
	    			lastPos = pos+3;
	    		}
	    	} else {
	    		if (pos == -1) {
	    			tmp.append(src.substring(lastPos));
	    			lastPos=src.length();
	    		} else {
	    			tmp.append(src.substring(lastPos,pos));
	    			lastPos=pos;
	    		}
	    	}
	    }
	    return tmp.toString();
	 }
}
