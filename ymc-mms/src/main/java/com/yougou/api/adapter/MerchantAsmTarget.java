package com.yougou.api.adapter;

import java.util.Map;

import com.yougou.api.Implementor;

/**
 * 招商售后API
 * 
 * @author huang.tao
 *
 */
public interface MerchantAsmTarget extends Implementor {
	
	/**
	 * 查询退换货明细yougou.return.quality.query
	 * 
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object queryQualityReturn(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 退换货入库yougou.return.quality.add 
	 * 
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object addQualityReturn(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 拒收入库yougou.rejection.quality.add 
	 * 
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object addQualityRejection(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 获取退货地址yougou.warehouse.return.get
	 * 
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object getReturnWarehouse(Map<String, Object> parameterMap) throws Exception;
}
