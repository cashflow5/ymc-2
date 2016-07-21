package com.yougou.kaidian.user.util;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.yougou.kaidian.common.util.DateUtil2;

public class UserUtil {

    public static boolean checkEncryptOrNot(String date){
    	Date originalTime = null;
    	try {
			originalTime = DateUtil2.getdate1(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	Date compareTime = DateUtil2.addDate( DateUtil2.getCurrentDateTime(),-90 );
    	if( compareTime.after(originalTime) ){
    		return true;
    	}else{
    		return false;
    	}
    	
    }
    
    public static boolean checkEncryptOrNot(Date originalTime){
    	
    	Date compareTime = DateUtil2.addDate( DateUtil2.getCurrentDateTime(),-90 );
    	if( compareTime.after(originalTime) ){
    		return true;
    	}else{
    		return false;
    	}
    	
    }
    
    public static String encriptPhone(String phone){
    	if( !StringUtils.isEmpty(phone) && phone.trim().length()>7 ){
    		phone = phone.trim();
       	  	phone = phone.substring(0,3)+"****"+ phone.substring(7);
    	}
    	return phone;
    }
    
    public static String encriptAddress(String consigneeAddr ){
    	if( !StringUtils.isEmpty(consigneeAddr)){
    		consigneeAddr =  consigneeAddr.trim();
    		consigneeAddr =  consigneeAddr.replaceAll(".", "*");  	
    	}
    	return consigneeAddr;
    }
	    
//	public static void main(String[] args) {
//		String date1 = "2014-01-03 11:37:03";
//		Date date2 = new Date();
//		String phone = "111111111111";
//		String addr = "我的我的我的  我的我的我的";
//		System.out.println( checkEncryptOrNot(date1));
//		System.out.println( checkEncryptOrNot(date2));
//		System.out.println(encriptPhone(phone));
//		System.out.println(encriptAddress(addr));
//
//	}

}
