package com.yougou.api.adapter;

import java.util.Map;

import com.yougou.api.Implementor;

/**
 * 分销订单适配器
 * @author huang.qm
 * 2012-1-05
 */
public interface ChainApiOrderTarget extends Implementor {

	/**
	 * 提交订单接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object saveOrderChain(Map<String, Object> parameterMap) throws Exception;
	/**
	 * 订单发货查询接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object shipOrderChain(Map<String, Object> parameterMap) throws Exception;
	/**
	 * 单个订单详情查询接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object detailOrderChain(Map<String, Object> parameterMap) throws Exception;
	/**
	 * 单个订单状态查询接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object statusOrderChain(Map<String, Object> parameterMap) throws Exception;
	/**
	 * 增量同步订单状态接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object changeOrderChain(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 拆单查询接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object querysplitOrderChain(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 订单取消接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object cancelOrderChain(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 查询订单来源接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object sourceOrderChain(Map<String, Object> parameterMap) throws Exception;
}
