package com.yougou.api.adapter;

import java.util.Map;

import com.yougou.api.Implementor;

/**
 * 招商系统与库存系统适配接口
 * 
 * @author 杨梦清
 *
 */
public interface MerchantInventoryTarget extends Implementor {

	/**
	 * 更新商家库存信息 yougou.inventory.update
	 * 
	 * @param parameterMap
	 * @return Object
	 * @throws Exception
	 */
	Object updateInventory(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 查询商家库存信息 yougou.inventory.query
	 * 
	 * @param parameterMap
	 * @return Object
	 * @throws Exception
	 */
	Object queryInventory(Map<String, Object> parameterMap) throws Exception;
}
