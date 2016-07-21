package com.yougou.api.adapter.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.yougou.api.adapter.ChainApiPriceTarget;
import com.yougou.dms.api.ApiPriceService;

@Component
public class ChainApiPriceAdapter implements ChainApiPriceTarget {

	@Resource
	private ApiPriceService chainApiPriceService;

	/**
	 * 获取多个商品供货价接口
	 */
	public Object queryPriceChain(Map<String, Object> parameterMap) throws Exception {
		int mapPage_index = MapUtils.getInteger(parameterMap, "page_index");
		int mapPage_Size  = MapUtils.getInteger(parameterMap, "page_size");
		
		int page_index = mapPage_index;
		parameterMap.put("page_index", page_index);
		parameterMap.put("page_size", mapPage_Size);
		return chainApiPriceService.queryPriceChain(parameterMap);
	}

	/**
	 * 增量同步供货价接口
	 */
	public Object changePriceChain(Map<String, Object> parameterMap) throws Exception {
		int mapPage_index = MapUtils.getInteger(parameterMap, "page_index");
		int mapPage_Size  = MapUtils.getInteger(parameterMap, "page_size");
		
		int page_index = mapPage_index;
		parameterMap.put("page_index", page_index);
		parameterMap.put("page_size", mapPage_Size);
		return chainApiPriceService.changePriceChain(parameterMap);
	}
}
