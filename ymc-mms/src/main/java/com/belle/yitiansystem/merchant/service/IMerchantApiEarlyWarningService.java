package com.belle.yitiansystem.merchant.service;

import java.util.Map;

public interface IMerchantApiEarlyWarningService {

	/**
	 * 招商 API 预警处理
	 * 
	 * @param ex
	 */
	void handleMerchantApiEarlyWarning(Exception ex);
	
	/**
	 * 招商 API 预警处理
	 * 
	 * @param ex
	 * @param parameterMap
	 */
	void handleMerchantApiEarlyWarning(Exception ex, Map<String, Object> parameterMap);
}
