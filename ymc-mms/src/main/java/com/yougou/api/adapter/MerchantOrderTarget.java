package com.yougou.api.adapter;

import java.util.Map;

import com.yougou.api.Implementor;

/**
 * 招商系统与订单系统适配接口
 * 
 * @author 杨梦清
 *
 */
public interface MerchantOrderTarget extends Implementor {

	/**
	 * 查询订单列表信息 yougou.order.query
	 * 
	 * @param parameterMap
	 * @return Object
	 * @throws Exception
	 */
	Object queryOrder(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 查询增量订单列表信息 yougou.order.increment.query
	 * 
	 * @return Object
	 * @throws Exception
	 */
	Object queryIncrementOrder(Map<String, Object> parameterMap) throws Exception; 
	
	/**
	 * 查询订单详情信息 yougou.order.get
	 * 
	 * @param parameterMap
	 * @return Object
	 * @throws Exception
	 */
	Object getOrder(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 获取订单代码 yougou.order.barcode.get
	 * 
	 * @param parameterMap
	 * @return Object
	 * @throws Exception
	 */
	Object getBarcodeOrder(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 查询客服取消订单 yougou.order.canceled.query
	 * 
	 * @param parameterMap
	 * @return Object
	 * @throws Exception
	 */
	Object queryCanceledOrder(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 客服取消订单，商家成功拦截后更新订音状态为终止发货 yougou.order.nondelivery.update
	 * 
	 * @param parameterMap
	 * @return Object
	 * @throws Exception
	 */
	Object updateNondeliveryOrder(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 更新缺货异常、物流超区异常订单 yougou.order.stockout.update
	 * 
	 * @param parameterMap
	 * @return Object
	 * @throws Exception
	 */
	Object updateStockoutOrder(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 商家订单发货以后更新订单状态为已完成 yougou.order.completed.update
	 * 
	 * @param parameterMap
	 * @return Object
	 * @throws Exception
	 */
	Object updateCompletedOrder(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 商家同步订单后回调更新订单同步状态为已同步 yougou.order.synchronized.update
	 * 
	 * @param parameterMap
	 * @return Object
	 * @throws Exception
	 */
	Object updateSynchronizedOrder(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 查询客服申请拦截订单的申请拦截信息 yougou.order.intercept.query
	 * 
	 * @param parameterMap
	 * @return Object
	 * @throws Exception
	 */
	Object queryInterceptOrder(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 更新同意/不同意客服申请拦截订单信息 yougou.order.intercept.update
	 * 
	 * @param parameterMap
	 * @return Object
	 * @throws Exception
	 */
	Object updateInterceptOrder(Map<String, Object> parameterMap) throws Exception;
}
