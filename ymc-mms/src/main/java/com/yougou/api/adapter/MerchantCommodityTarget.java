package com.yougou.api.adapter;

import java.util.Map;

import com.yougou.api.Implementor;

/**
 * 招商系统与商品系统适配接口
 * 
 * @author 杨梦清
 * 
 */
public interface MerchantCommodityTarget extends Implementor {

	/**
	 * 查询商家商品信息 yougou.commodity.query
	 * 
	 * @param parameterMap
	 * @return Object
	 * @throws Exception
	 */
	Object queryCommodity(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 商家对商品进行调价 yougou.price.commodity.update
	 * 
	 * @param parameterMap
	 * @return Object
	 * @throws Exception
	 */
	Object updateCommodityPrice(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 商家对商品进行上架操作 yougou.shelves.commodity
	 * 
	 * @param parameterMap
	 * @return Object
	 * @throws Exception
	 */
	Object commodityShelves(Map<String, Object> parameterMap) throws Exception;
}

