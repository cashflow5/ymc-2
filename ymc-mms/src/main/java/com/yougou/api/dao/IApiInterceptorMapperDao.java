package com.yougou.api.dao;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiCategory;
import com.yougou.api.model.pojo.ApiVersion;
import com.yougou.api.model.pojo.InterceptorMapper;

/**
 * 
 * @author 杨梦清
 * 
 */
public interface IApiInterceptorMapperDao extends IHibernateEntityDao<InterceptorMapper> {

	/**
	 * 根据API拦截器映射refId获取API
	 * 
	 * @param refId
	 * @return Api
	 */
	Api getApi(String refId);
	
	/**
	 * 根据API拦截器映射refId获取API版本
	 * 
	 * @param refId
	 * @return ApiVersion
	 */
	ApiVersion getApiVersion(String refId);
	
	/**
	 * 根据API拦截器映射refId获取API分类
	 * 
	 * @param refId
	 * @return ApiCategory
	 */
	ApiCategory getApiCategory(String refId);
}

