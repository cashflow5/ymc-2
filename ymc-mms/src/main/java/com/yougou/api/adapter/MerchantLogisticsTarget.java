package com.yougou.api.adapter;

import java.util.Map;

import com.yougou.api.Implementor;

/**
 * 
 * @author 杨梦清
 * 
 */
public interface MerchantLogisticsTarget extends Implementor {

	/**
	 * 获取所有商家物流公司信息 yougou.logisticscompany.get
	 * 
	 * <b>List<QueryLogisticsCompanyOuputDto></b>
	 * @return List
	 */
	Object getLogisticscompany() throws Exception;
	
	/**
	 * 获取优购收货快递公司信息yougou.expresscompany.get
	 * 
	 * <b>List<QueryExpressCompanyOuputDto></b>
	 * @return List
	 */
	Object getExpresscompany() throws Exception;
	
	/**
	 * 调用第三方平台接口导唯品会JIT入出仓单明细
	 * @return
	 * @throws Exception
	 */
	Object importDeliveryDetail(Map<String, Object> parameterMap) throws Exception;
	
	
	/**
	 * 调用第三方平台接口获取唯品会JIT入库编号
	 * @return
	 * @throws Exception
	 */
	Object getStorageNo(Map<String, Object> parameterMap) throws Exception;
}

