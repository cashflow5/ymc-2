package com.yougou.api.adapter.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.yougou.api.adapter.ChainApiOrderTarget;
import com.yougou.api.exception.BusinessServiceException;
import com.yougou.api.service.BusinessLogger;
import com.yougou.dms.api.ApiOrderService;

/**
 * 分销订单适配器
 * @author huang.wq
 * 2012-12-27
 */
@Component
public class ChainApiOrderAdapter implements ChainApiOrderTarget {

	@Resource
	private BusinessLogger businessLogger;
	
	@Resource
	private ApiOrderService apiOrderService;
	
	/**
	 * 提交订单接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Object saveOrderChain(Map<String, Object> parameterMap) throws Exception{
		return apiOrderService.saveOrderChain(parameterMap);
	}
	/**
	 * 订单发货查询接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Object shipOrderChain(Map<String, Object> parameterMap) throws Exception{
		int mapPage_index = MapUtils.getInteger(parameterMap, "page_index");
		int mapPage_Size  = MapUtils.getInteger(parameterMap, "page_size");
		
		int page_index = mapPage_index;
		parameterMap.put("page_index", page_index);
		parameterMap.put("page_size", mapPage_Size);
		return apiOrderService.shipOrderChain(parameterMap);
	}
	/**
	 * 单个订单详情查询接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Object detailOrderChain(Map<String, Object> parameterMap) throws Exception{
		int mapPage_index = MapUtils.getInteger(parameterMap, "page_index");
		int mapPage_Size  = MapUtils.getInteger(parameterMap, "page_size");
		
		int page_index = mapPage_index;
		parameterMap.put("page_index", page_index);
		parameterMap.put("page_size", mapPage_Size);
		return apiOrderService.detailOrderChain(parameterMap);
	}
	/**
	 * 单个订单状态查询接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Object statusOrderChain(Map<String, Object> parameterMap) throws Exception{
		int mapPage_index = MapUtils.getInteger(parameterMap, "page_index");
		int mapPage_Size  = MapUtils.getInteger(parameterMap, "page_size");
		
		int page_index = mapPage_index;
		parameterMap.put("page_index", page_index);
		parameterMap.put("page_size", mapPage_Size);
		return apiOrderService.statusOrderChain(parameterMap);
	}
	/**
	 * 增量同步订单状态接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Object changeOrderChain(Map<String, Object> parameterMap) throws Exception{
		int mapPage_index = MapUtils.getInteger(parameterMap, "page_index");
		int mapPage_Size  = MapUtils.getInteger(parameterMap, "page_size");
		
		int page_index = mapPage_index;
		parameterMap.put("page_index", page_index);
		parameterMap.put("page_size", mapPage_Size);
		return apiOrderService.changeOrderChain(parameterMap);
	}
	
	/**
	 * 拆单查询接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Object querysplitOrderChain(Map<String, Object> parameterMap) throws Exception {
		int mapPage_index = MapUtils.getInteger(parameterMap, "page_index");
		int mapPage_Size  = MapUtils.getInteger(parameterMap, "page_size");
		String merchantCode = MapUtils.getString(parameterMap, "merchant_code");
		
		int page_index = mapPage_index;
		parameterMap.put("page_index", page_index);
		parameterMap.put("page_size", mapPage_Size);
		try {
			return apiOrderService.querysplitOrderChain(parameterMap);
		} catch (Exception e) {
			businessLogger.log("chain.order.querysplit", "501", e.getMessage(), merchantCode);
			return new BusinessServiceException(e.getMessage());
		}
		
	}
	
	/**
	 * 订单取消接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Object cancelOrderChain(Map<String, Object> parameterMap) throws Exception {
		return apiOrderService.cancelOrderChain(parameterMap);
	}
	
	/**
	 * 查询订单来源接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Object sourceOrderChain(Map<String, Object> parameterMap) throws Exception {
		return apiOrderService.sourceOrderChain(parameterMap);
	}
}
