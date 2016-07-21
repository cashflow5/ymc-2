/*
 * com.belle.yitiansystem.merchant.enums.ContractStatusEnum
 * 
 *  Tue Jun 23 13:25:28 CST 2015
 * 
 * Copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */

package com.belle.yitiansystem.merchant.enums;

import java.util.HashMap;
import java.util.Map;

import com.belle.yitiansystem.merchant.constant.MerchantConstant;

/**
 * 合同状态枚举
 * @author li.j1
 * @history 2015-6-23 Created
 */
public enum ContractStatusEnum {
	CONTRACT_STATUS_NEW(MerchantConstant.CONTRACT_STATUS_NEW,MerchantConstant.CONTRACT_STATUS_NEW_ZH),
	CONTRACT_STATUS_BUSINESS_AUDITING(MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITING,MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITING_ZH),
	CONTRACT_STATUS_BUSINESS_AUDITED(MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITED,MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITED_ZH),
	CONTRACT_STATUS_BUSINESS_REFUEED(MerchantConstant.CONTRACT_STATUS_BUSINESS_REFUSED,MerchantConstant.CONTRACT_STATUS_BUSINESS_REFUSED_ZH),
	CONTRACT_STATUS_FINANCE_AUDITIED(MerchantConstant.CONTRACT_STATUS_FINANCE_AUDITED,MerchantConstant.CONTRACT_STATUS_FINANCE_AUDITED_ZH),
	CONTRACT_STATUS_FINANCE_REFUSED(MerchantConstant.CONTRACT_STATUS_FINANCE_REFUSED,MerchantConstant.CONTRACT_STATUS_FINANCE_REFUSED_ZH),
	CONTRACT_STATUS_EFFECTIVE(MerchantConstant.CONTRACT_STATUS_EFFECTIVE,MerchantConstant.CONTRACT_STATUS_EFFECTIVE_ZH),
	CONTRACT_STATUS_EXPIRED(MerchantConstant.CONTRACT_STATUS_EXPIRED,MerchantConstant.CONTRACT_STATUS_EXPIRED_ZH);
	   
	//键
    private String key;
    //值
    private String value;
   
    /**
     * 构造函数
     * @param key 键
     * @param value 值
     */
    private ContractStatusEnum(String key,String value){
	   this.key = key;   
	   this.value = value;   
    }
   
    /**
     * 通过键获取对应的值，如果没有这个键，则返回空字符串
     * @param key 键
     * @return 键对应的值
     */
    public static String getValue(String key){
		return map.get(key);
    }
   
    //存储枚举的键值数据Map
    private static Map<String,String> map;
   
    static{
    	map = new HashMap<String,String>();
    	for (ContractStatusEnum menu:ContractStatusEnum.values()){
    		map.put(menu.key, menu.value);
    	}
    }
}
