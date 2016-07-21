package com.yougou.api.adapter;

import java.util.Map;

import com.yougou.api.Implementor;

/**
 * 招商系统与基础数据适配器接口
 * 
 * @author 杨梦清
 * 
 */
public interface MerchantCategoryTarget extends Implementor {
	
	/**
	 * 查询商家所有品牌信息 yougou.brand.query
	 * 
	 * @param parameterMap
	 * @return Object
	 * @throws Exception
	 */
	Object queryBrand(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 查询商家所有分类信息 yougou.cat.query
	 * 
	 * @param parameterMap
	 * @return Object
	 * @throws Exception
	 */
	Object queryCat(Map<String, Object> parameterMap) throws Exception;
}

