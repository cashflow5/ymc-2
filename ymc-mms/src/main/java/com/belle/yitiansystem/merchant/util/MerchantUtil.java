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

package com.belle.yitiansystem.merchant.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;

import com.belle.infrastructure.util.DateUtil;
import com.belle.yitiansystem.merchant.constant.MerchantConstant;
import com.belle.yitiansystem.merchant.model.vo.MerchantsVo;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.merchant.api.supplier.vo.SupplierVo;
import com.yougou.purchase.model.Supplier;



/**
 * 供应商工具类
 * @author li.j1
 * @history 2015-06-23 Created
 */
public class MerchantUtil {
	
	private static Logger logger = Logger.getLogger(MerchantUtil.class);
	
	/**
	 * 生成唯一的合同编号 "HT"+年份+月份+日期+4位数字随机码
	 * @return
	 */
	public static String generateUniqueContractNo(){
		Random random = new Random();
		String randomValue = String.valueOf(random.nextInt(8999) + 1000);
		String date = DateUtil.format(new Date(System.currentTimeMillis()),"yyyyMMdd");
		return "HT"+date + randomValue;
	}
	
	 /**
     * 初始化合同编号
     * @return 合同编号
     */
    public static String initContractNo( RedisTemplate<String, String> redisTemplate){
    	String contractNo = "";
    	while(true){
	    	contractNo = MerchantUtil.generateUniqueContractNo();
			String usedContractNo = redisTemplate.opsForValue().get(CacheConstant.C_MERCHANT_CONTRACT_NO_KEY+":"+contractNo);
			if(StringUtils.isBlank(usedContractNo)){
				redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_CONTRACT_NO_KEY+":"+contractNo, contractNo);
				redisTemplate.expire(CacheConstant.C_MERCHANT_CONTRACT_NO_KEY+":"+contractNo, 1, TimeUnit.DAYS);
				break;
			}
		}
		return contractNo;
    }
    
    /**
     * 保存提交审核，根据状态进阶
     * @param isValid
     * @return
     */
    public static Integer getNextStatus(Integer isValid){
    	if( null== isValid || isValid <-1 || isValid> 7 ){// 隔离出异常情况
    		return MerchantConstant.MERCHANT_STATUS_NEW;
    	}
    	switch(isValid){
    		case MerchantConstant.MERCHANT_STATUS_NEW:
    			return MerchantConstant.MERCHANT_STATUS_BUSINESS_AUDITING;
    		case MerchantConstant.MERCHANT_STATUS_BUSINESS_REFUSED:
    			return MerchantConstant.MERCHANT_STATUS_BUSINESS_AUDITING;
    		case MerchantConstant.MERCHANT_STATUS_FINANCE_REFUSED:
    			return MerchantConstant.MERCHANT_STATUS_BUSINESS_AUDITING;
    		case MerchantConstant.MERCHANT_STATUS_DISABLE:
    			return MerchantConstant.MERCHANT_STATUS_DISABLE;
    		default:
    			return isValid;// 其他状态，不处理
    	}
    }
    
    /**
     * 保存提交审核，根据状态进阶
     * @param status
     * @return
     */
    public static String getNextStatus(String status){
    	
    	if( StringUtils.isBlank(status) ){// 隔离出异常情况
    		return MerchantConstant.CONTRACT_STATUS_NEW;
    	}
    	if( status.equalsIgnoreCase( MerchantConstant.CONTRACT_STATUS_NEW ) ){
    		return MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITING;
    	}
    	if( status.equalsIgnoreCase( MerchantConstant.CONTRACT_STATUS_BUSINESS_REFUSED ) ){
    		return MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITING;
    	}
    	if( status.equalsIgnoreCase( MerchantConstant.CONTRACT_STATUS_FINANCE_REFUSED ) ){
    		return MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITED;
    	}
    	if( status.equalsIgnoreCase( MerchantConstant.CONTRACT_STATUS_EFFECTIVE ) ){
    		return MerchantConstant.CONTRACT_STATUS_EFFECTIVE;
    	}
    	return status;
    }
    
    /**
     * 商家创建-商家草稿缓存
     * @param key
     * @param redisTemplate
     * @return
     */
    public static HashMap<String,Object> getSupplierSegmentCache(String key,String hashKey,RedisTemplate<String, Object> redisTemplate){
    	
    	return (HashMap<String,Object>)redisTemplate.opsForHash().get(key,hashKey);
    	
    }
    
    /**
     * 商家创建-商家草稿缓存
     * @param key
     * @param redisTemplate
     * @return
     */
    public static List<SupplierVo> queryAllSupplierSegmentCache(String key,MerchantsVo merchantsVo,RedisTemplate<String, Object> redisTemplate){
    	List<SupplierVo> resultList = null;
    	Set<Object> keysSet = redisTemplate.opsForHash().keys(key );
    	String supplierNameOnPage = merchantsVo.getSupplier();
    	String updateUserOnPage = merchantsVo.getUpdateUser();
    	String startDateOnPage = merchantsVo.getEffectiveDate();
    	String endDateOnPage = merchantsVo.getFailureDate();
    	if(null!=keysSet && keysSet.size()>0){
    		resultList = new ArrayList<SupplierVo>();
    		HashMap<String, Object> map;
    		for(Object hashKey :keysSet){
    			map = null;
				try {
					map = (HashMap<String, Object>)redisTemplate.opsForHash().get(key, (String)hashKey);
				} catch (Exception e1) {
					logger.error("queryAllSupplierSegmentCache报错", e1);
				}
    			if(null != map && map.size() > 0){
    				SupplierVo supplierVo = new SupplierVo();
    				supplierVo.setId((String)hashKey);
    				String supplierName = (String)map.get(CacheConstant.KEY_SUPPLIER_NAME);
    		    	String updateUser = (String)map.get(CacheConstant.KEY_UPDATE_USER);
    		    	Date updateTime = (Date)map.get(CacheConstant.KEY_UPDATE_TIME);
    				supplierVo.setSupplier( supplierName );
    				supplierVo.setUpdateDate( updateTime );
    				supplierVo.setUpdateUser( updateUser );
    				
    				// 过滤结果
    				if( StringUtils.isNotEmpty(supplierNameOnPage) ){
    					supplierNameOnPage = supplierNameOnPage.trim();
    					if( supplierName.indexOf(supplierNameOnPage)==-1 ){
    						continue;
    					}
    				}
					if( StringUtils.isNotEmpty(updateUserOnPage) ){
						updateUserOnPage = updateUserOnPage.trim();
						if( updateUser.indexOf(updateUserOnPage)==-1 ){
    						continue;
    					}
    				}
					if( null!=updateTime ){
						try {
							if( StringUtils.isNotEmpty(startDateOnPage) ){
								Date dateStart = DateUtil2.getdate1(startDateOnPage);
								if( dateStart.after(updateTime ) ){
		    						continue;
		    					}
							}
							if( StringUtils.isNotEmpty(endDateOnPage) ){
								Date dateEnd = DateUtil2.getdate1(endDateOnPage);
								if( dateEnd.before(updateTime ) ){
		    						continue;
		    					}
							}
						} catch (Exception e) {
							logger.error("error happend when parse string to date....");
						}
					}
    				resultList.add(supplierVo);
    			}
    		}
    	}
    	return resultList;
    	
    }
    
    /**
     * 商家创建-商家草稿保存
     * @param key
     * @param redisTemplate
     * @param resourceMap
     * @return
     */
    public static boolean saveSupplierSegmentCache(String key,String hashKey,RedisTemplate<String, Object> redisTemplate,
    												HashMap<String,Object> resourceMap){
   	 
    	redisTemplate.opsForHash().put(key,hashKey,resourceMap);
    	return true;
    }
    /**
     * 商家创建-删除商家草稿缓存 
     * @param key
     * @param redisTemplate
     * @return
     */
    public static boolean removeFromSupplierSegmentCache(String key,String hashKey,RedisTemplate<String, Object> redisTemplate){
      	 
    	Object object = getSupplierSegmentCache(key,hashKey,redisTemplate);
    	if(null!=object){
    		redisTemplate.opsForHash().delete(key, hashKey);
    	}
    	return true;
    }
    
    public static Map<String,Object> buildMapFromSupplierVO(Supplier obj) throws Exception{
    	Map<String,Object> map = null;
    	if( null!=obj){
    		map = new HashMap<String,Object>();
    	    try {
    	    	// 获取目标类的属性信息
    	    	PropertyDescriptor[] propertyDescriptors = getBeanPropertyDescriptors(obj);
				 // 对每个目标类的属性查找set方法，并进行处理
				 for (int i = 0; i < propertyDescriptors.length; i++) {
				     PropertyDescriptor pro = propertyDescriptors[i];
				     String key = pro.getName();
				     Method wm = pro.getReadMethod();
				     Object value = wm.invoke(obj);
				     if( null!= value ){
				    	map.put(key, value);
				     }
				    
				 }
			} catch (Exception e) {
				logger.error("商家工具类进行(supplier)VO的转换出错 (supplier->map) ", e);
				throw new Exception("商家工具类进行(supplier)VO的转换出错(supplier->map) ",e);
			}
    		
    	}
    	return map;
    }
    
    // 获取目标类的属性信息	
    private static PropertyDescriptor[] getBeanPropertyDescriptors (Supplier obj) throws IntrospectionException{
    	 BeanInfo targetbean = Introspector.getBeanInfo(obj.getClass());
		 PropertyDescriptor[] propertyDescriptors = targetbean.getPropertyDescriptors();
		 return propertyDescriptors;
    }
    
    
    public static Supplier rebuildSupplierVoFromMap(Map<String,Object> map) throws Exception{
    	Supplier obj = null;
    	if( null!=map){
    		 obj = new Supplier();
             try {
            	 // 获取目标类的属性信息
				 PropertyDescriptor[] propertyDescriptors = getBeanPropertyDescriptors(obj);
				 // 对每个目标类的属性查找set方法，并进行处理
				 for (int i = 0; i < propertyDescriptors.length; i++) {
				     PropertyDescriptor pro = propertyDescriptors[i];
				     String key = pro.getName();
				     Method wm = pro.getWriteMethod();
				     Object value = map.get(key);
				     
				     if( null!= value && !(value instanceof Class) ){
				    	 wm.invoke(obj, value);
				     }
				    
				 }
			} catch (Exception e) {
				logger.error("商家工具类进行supplier VO的转换出错 ", e);
 				throw new Exception("商家工具类进行supplier VO的转换出错",e);
			}
    		
    	}
    	return obj;
    }
    
    /**
     * 生成商家仓名称
     * @param supplierName 商家名称
     * @return 仓库名称
     */
    public static String generateWarehouseName(String supplierName){
    	
    	if( StringUtils.isNotEmpty(supplierName) ){
    		supplierName = supplierName.trim();
    		StringBuffer warehouseName = new StringBuffer();
    		warehouseName.append( MerchantConstant.WAREHOUSE_NAME_PRE )
    					 .append( supplierName )
    					 .append( MerchantConstant.WAREHOUSE_NAME_END );
    		return warehouseName.toString();
    	}else{
    		return "";
    	}
    	
    }
    
    /* 两个VO有同名但不同类型的属性：supplierType 暂时不能使用该方法*/
    public static SupplierVo buildRedisObjectFromSupplier(Supplier supplier){
    	SupplierVo target = null;
    	if( null!=supplier){
    		target = new SupplierVo();
    		BeanUtils.copyProperties(supplier, target);
    	}
    	return target;
    }
    /* 两个VO有同名但不同类型的属性：supplierType 暂时不能使用该方法*/
    public static Supplier rebuildSupplierFromRedisObject(SupplierVo vo){
    	Supplier supplier = null;
    	if( null!=vo){
    		 supplier = new Supplier();
    		 BeanUtils.copyProperties(vo, supplier);
    	}
    	return supplier;
    }
    
//    public static void main(String[] args) throws Exception {
//    	Supplier supplier = new Supplier();
//    	supplier.setId("1");
//    	supplier.setAccount("111");
//    	System.out.println(supplier.toString());
//    	Map map = new HashMap();
//    	map = buildMapFromSupplierVO(supplier);
//    	System.out.println("buildMapFromSupplierVO -> "+ map.toString());
//    	supplier = rebuildSupplierVoFromMap( map );
//    	System.out.println("rebuildSupplierVoFromMap -> "+ supplier.toString());
// 	
//	}
    
}
