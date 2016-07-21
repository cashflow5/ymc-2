package com.belle.infrastructure.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 生成UUID工具类
 * @author liyanbin
 *
 */
public class UUIDGenerator {
	public UUIDGenerator() {  
    }  
 
    public static String getUUID() {  
        UUID uuid = UUID.randomUUID();  
        String str = uuid.toString();  
        // 去掉"-"符号  
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);  
        return temp;  
    }  
    //获得指定数量的UUID  
    public static String[] getUUID(int number) {  
        if (number < 1) {  
            return null;  
        }  
        String[] ss = new String[number];  
        for (int i = 0; i < number; i++) {  
            ss[i] = getUUID();  
        }  
        return ss;  
    }  
 
    
    /**
     * 生成激活验证码,从时间戳取出
    * @Title: buildNumber 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param @param length
    * @param @return    设定文件 
    * @return String    返回类型
    * @author zhu.b 
    * @date 2011-5-23 下午09:48:29  
    * @throws
     */
    public static String generateNumber(int length){
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
    
    
    
    public static void main(String[] args) {  
        /*String[] ss = getUUID(10);  
        for (int i = 0; i < ss.length; i++) {  
            System.out.println("ss["+i+"]====="+ss[i]);  
        }  */
    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String dates =  format.format(new Date());
    	String second = " 23:59:00";
    	System.out.println(format.format(new Date()));
    	
	    System.out.println((dates+second).length());
	    
    	/*String date  = "2011-06-20 15:30:30"; //变成2011年06月20日
    	String year = date.substring(0,4);
    	String month = date.substring(5,7);
    	String day = date.substring(8, 10);
    	System.out.println(year+"年"+month+"月"+day+"日");
    	System.out.println(date.substring(11, 13));
    	System.out.println(date.substring(14, 16));
    	System.out.println(date.substring(17, 19));*/
    	
    }   
}
