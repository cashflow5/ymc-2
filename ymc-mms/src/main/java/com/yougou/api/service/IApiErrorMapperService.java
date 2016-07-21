package com.yougou.api.service;

import com.yougou.api.model.pojo.ApiErrorMapper;

/**
 * 
 * @author yang.mq
 *
 */
public interface IApiErrorMapperService {


	/**
	 * 添加API错误码映射
	 * 
	 * @param apiErrorMapper
	 * @throws Exception
	 */
	void saveApiErrorMapper(ApiErrorMapper apiErrorMapper) throws Exception;
	
	/**
	 * 删除API错误码映射
	 * 
	 * @param id
	 * @throws Exception
	 */
	void deleteApiErrorMapper(String id) throws Exception;
}
