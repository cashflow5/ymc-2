package com.yougou.api.adapter;

import java.util.Map;

import com.yougou.api.Implementor;

/**
 * 分销商品适配器
 * @author huang.wq
 * 2012-12-27
 */
public interface ChainApiCommodityTarget extends Implementor {

	/**
	 * 单个商品详情查询接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object detailCommodityChain(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 增量同步商品接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object changeCommodityChain(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 商品图片链接查询接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object picCommodityChain(Map<String, Object> parameterMap) throws Exception;
	
	
	
	
	/**
	 * 所有品牌查询接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object queryBrandChain(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 所有分类查询接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object queryCategoryChain(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 商品上架查询接口
	 * @param paraterMap
	 * @return
	 * @throws Exception
	 */
	Object upCommodityChain(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 商品下架查询接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object downCommodityChain(Map<String, Object> parameterMap) throws Exception;

}
