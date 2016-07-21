package com.yougou.kaidian.user.enums;

import java.util.HashMap;
import java.util.Map;

import com.yougou.kaidian.user.constant.UserConstant;

public enum LogMenuEnum {

	   MENU_DJDY("MENU_DJDY",UserConstant.MENU_DJDY),
	   MENU_KJFH("MENU_KJFH",UserConstant.MENU_KJFH),
	   MENU_DJDYX("MENU_DJDYX",UserConstant.MENU_DJDYX),
	   MENU_ZJCX("MENU_ZJCX",UserConstant.MENU_ZJCX),
	   MENU_ZJBTG("MENU_ZJBTG",UserConstant.MENU_ZJBTG),
	   MENU_SHDCX("MENU_SHDCX",UserConstant.MENU_SHDCX),
	   MENU_GDCL("MENU_GDCL",UserConstant.MENU_GDCL),
	   MENU_ZJDJ("MENU_ZJDJ",UserConstant.MENU_ZJDJ);
	   
	   private String key;
	   private String value;
	   
	   private LogMenuEnum(String key,String value){
		 this.key = key;   
		 this.value = value;   
	   }
	   
	   public static String getValue(String key){
		   return map.get(key);
	   }
	   
	   private static Map<String,String> map;
	   
	   static{
		   map = new HashMap<String,String>();
		   for (LogMenuEnum menu:LogMenuEnum.values()){
			   map.put(menu.key, menu.value);
		   }
	   }
}
