package com.belle.yitiansystem.merchant.enums;

import java.util.HashMap;
import java.util.Map;

import com.belle.yitiansystem.merchant.constant.MerchantConstant;

public enum MerchantStatusEnum {

	MERCHANT_STATUS_NEW(MerchantConstant.MERCHANT_STATUS_NEW,MerchantConstant.MERCHANT_STATUS_NEW_ZH),
	MERCHANT_STATUS_BUSINESS_AUDITING(MerchantConstant.MERCHANT_STATUS_BUSINESS_AUDITING,MerchantConstant.MERCHANT_STATUS_BUSINESS_AUDITING_ZH),
	MERCHANT_STATUS_BUSINESS_AUDITED(MerchantConstant.MERCHANT_STATUS_BUSINESS_AUDITED,MerchantConstant.MERCHANT_STATUS_BUSINESS_AUDITED_ZH),
	MERCHANT_STATUS_BUSINESS_REFUEED(MerchantConstant.MERCHANT_STATUS_BUSINESS_REFUSED,MerchantConstant.MERCHANT_STATUS_BUSINESS_REFUSED_ZH),
	MERCHANT_STATUS_FINANCE_AUDITIED(MerchantConstant.MERCHANT_STATUS_FINANCE_AUDITED,MerchantConstant.MERCHANT_STATUS_FINANCE_AUDITED_ZH),
	MERCHANT_STATUS_FINANCE_REFUSED(MerchantConstant.MERCHANT_STATUS_FINANCE_REFUSED,MerchantConstant.MERCHANT_STATUS_FINANCE_REFUSED_ZH),
	MERCHANT_STATUS_ENABLE(MerchantConstant.MERCHANT_STATUS_ENABLE,MerchantConstant.MERCHANT_STATUS_ENABLE_ZH),
	MERCHANT_STATUS_DISABLE(MerchantConstant.MERCHANT_STATUS_DISABLE,MerchantConstant.MERCHANT_STATUS_DISABLE_ZH);
	   
	//键
    private int key;
    //值
    private String value;
   
    /**
     * 构造函数
     * @param key 键
     * @param value 值
     */
    private MerchantStatusEnum(int key,String value){
	   this.key = key;   
	   this.value = value;   
    }
   
    /**
     * 通过键获取对应的值，如果没有这个键，则返回空字符串
     * @param key 键
     * @return 键对应的值
     */
    public static String getValue(int key){
		return map.get(key);
    }
   
    //存储枚举的键值数据Map
    private static Map<Integer,String> map;
   
    static{
    	map = new HashMap<Integer,String>();
    	for (MerchantStatusEnum merchant:MerchantStatusEnum.values()){
    		map.put(merchant.key, merchant.value);
    	}
    }
}
