package com.yougou.api.service;


public interface IApiVisitCounterService {

	/**
	 * 获取 API 访问计算器
	 * 
	 * @param visitorIp
	 * @return ApiVisitCounter
	 */
	void createOrUpdateApiVisitCounter(String visitorIp) throws Exception;
	
	/**
	 * 是否超出访问计算器限制
	 * 
	 * @param visitorIp
	 * @param visitCount
	 * @return boolean
	 * @throws Exception
	 */
	boolean isVisitsLimitExceeded(String visitorIp, int visitCount) throws Exception;
	
	/**
	 * 删除 API 访问计算器
	 * 
	 * @param visitorIp
	 * @param visitTimestamp
	 */
	void deleteApiVisitCounter(String visitorIp, long visitTimestamp) throws Exception;
}
