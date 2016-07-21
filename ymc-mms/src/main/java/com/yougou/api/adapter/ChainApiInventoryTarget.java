package com.yougou.api.adapter;

import java.util.Map;

import com.yougou.api.Implementor;

/**
 * 分销库存适配器接口
 * @author huang.wq
 * 2012-12-19
 */
public interface ChainApiInventoryTarget extends Implementor {

	/**
	 * 多个商品库存同步接口，查询商品库存
	 * 获取商品的库存，可以指定多个商品的货号查询，最多不超过50个货号
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object queryInventoryChain(Map<String, Object> parameterMap) throws Exception;

	
	/**
	 * 增量商品库存同步接口，查询增量商品库存
	 * 获取有改动的商品库存，需指定库存更改开始时间和结束时间，每页最多返回50个商品信息
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object changeInventoryChain(Map<String, Object> parameterMap) throws Exception;

}
