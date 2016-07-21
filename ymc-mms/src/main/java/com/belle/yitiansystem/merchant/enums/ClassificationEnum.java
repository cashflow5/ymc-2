package com.belle.yitiansystem.merchant.enums;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author luo.q1
 * 培训中心课程分类枚举类型
 */
public enum ClassificationEnum {	
	
	新手报到("1"), 
	商品管理("2"), 
	店铺管理("3"),
	促销引流("4"),
	客户服务("5"),
	规则解读("6");
	
	private String type;
	public String getType(){
		return type;
	}
	
	ClassificationEnum(String type){
		this.type = type;
	}
	
	public static Map<String, String> typeMap;
	
	static{
		typeMap = new HashMap<String,String>();
		for(ClassificationEnum type:ClassificationEnum.values() ){
			
			typeMap.put(type.getType(), type.name());
		}
	}
	
//	public static void main(String [] args){
//		System.out.println(ClassificationEnum.typeMap.get("2"));
//	}
	
}