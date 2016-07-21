package com.yougou.api.adapter;

import java.util.Map;

import com.yougou.api.Implementor;

/**
 * 分销系统供货价适配接口
 * 
 * @author haung.qm
 *
 */
public interface ChainApiPriceTarget extends Implementor {

	/**
	 * 获取多个商品供货价接口
	 * 
	 * @param UpdateInventoryInputDto
	 * @return UpdateInventoryOutputDto
	 * @throws Exception
	 */
	Object queryPriceChain(Map<String, Object> parameterMap) throws Exception;

	/**
	 * 增量同步供货价接口
	 * 
	 * @param QueryInventoryInputDto
	 * @return QueryInventoryOutputDto
	 * @throws Exception
	 */
	Object changePriceChain(Map<String, Object> parameterMap) throws Exception;

}
