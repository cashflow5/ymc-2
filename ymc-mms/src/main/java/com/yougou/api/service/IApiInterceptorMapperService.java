package com.yougou.api.service;

import java.util.List;

import com.yougou.api.model.pojo.InterceptorMapper;

/**
 * 
 * @author 杨梦清
 * 
 */
public interface IApiInterceptorMapperService {

	/**
	 * 获取API拦截器映射排序号
	 * 
	 * @param interceptorMapper
	 * @param upperLimit
	 * @return List
	 * @throws Exception
	 */
	List<Integer> getUsableOrderNoList(InterceptorMapper interceptorMapper, int upperLimit) throws Exception;
	
	/**
	 * 获取API拦截器映射
	 * 
	 * @param id
	 * @return InterceptorMapper
	 * @throws Exception
	 */
	InterceptorMapper getInterceptorMapperById(String id) throws Exception;

	/**
	 * 删除API拦截器映射
	 * 
	 * @param id
	 * @throws Exception
	 */
	void deleteInterceptorMapperById(String id) throws Exception;
	
	/**
	 * 添加API拦截器映射
	 * 
	 * @param interceptorMapper
	 * @throws Exception
	 */
	void saveInterceptorMapper(InterceptorMapper interceptorMapper) throws Exception;
	
	/**
	 * 更新API拦截器映射
	 * 
	 * @param interceptorMapper
	 * @throws Exception
	 */
	void updateInterceptorMapper(InterceptorMapper interceptorMapper) throws Exception;
}

