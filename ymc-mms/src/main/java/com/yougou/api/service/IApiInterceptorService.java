package com.yougou.api.service;

import org.hibernate.criterion.DetachedCriteria;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.yougou.api.model.pojo.ApiInterceptor;

/**
 * 
 * @author 杨梦清
 * 
 */
public interface IApiInterceptorService {

	/**
	 * 删除拦截器
	 * 
	 * @param identifier
	 * @throws Exception
	 */
	void deleteApiInterceptorByIdentifier(String identifier) throws Exception;

	/**
	 * 获取拦截器
	 * 
	 * @param identifier
	 * @return ApiInterceptor
	 * @throws Exception
	 */
	ApiInterceptor getApiInterceptorByIdentifier(String identifier) throws Exception;

	/**
	 * 保存拦截器
	 * 
	 * @param apiInterceptor
	 * @throws Exception
	 */
	void saveApiInterceptor(ApiInterceptor apiInterceptor) throws Exception;

	/**
	 * 更新拦截器
	 * 
	 * @param apiInterceptor
	 * @throws Exception
	 */
	void updateApiInterceptor(ApiInterceptor apiInterceptor) throws Exception;

	/**
	 * 分页查询拦截器
	 * 
	 * @param criteria
	 * @param query
	 * @return PageFinder
	 * @throws Exception
	 */
	PageFinder<ApiInterceptor> queryApiInterceptor(DetachedCriteria criteria, Query query) throws Exception;
	
}

