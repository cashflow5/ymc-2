package com.yougou.api.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.yougou.api.model.pojo.ApiError;

/**
 * 
 * @author 杨梦清
 * 
 */
public interface IApiErrorService {

	/**
	 * 获取API错误码数据
	 * 
	 * @param id
	 * @return ApiError
	 * @throws Exception
	 */
	ApiError getApiErrorById(String id) throws Exception;

	/**
	 * 删除API错误码数据
	 * 
	 * @param id
	 * @throws Exception
	 */
	void deleteApiErrorById(String id) throws Exception;

	/**
	 * 添加API错误码数据
	 * 
	 * @param apiError
	 * @throws Exception
	 */
	void saveApiError(ApiError apiError) throws Exception;

	/**
	 * 更新API错误码数据
	 * 
	 * @param apiError
	 * @throws Exception
	 */
	void updateApiError(ApiError apiError) throws Exception;
	
	/**
	 * 获取可用的排序号列表
	 * 
	 * @param apiError
	 * @param upperLimit
	 * @return List
	 * @throws Exception
	 */
	List<Integer> getUsableOrderNoList(ApiError apiError, int upperLimit) throws Exception;
	
	/**
	 * 分页查询API错误码数据
	 * 
	 * @param criteria
	 * @param query
	 * @return PageFinder
	 * @throws Exception
	 */
	PageFinder<ApiError> queryApiError(DetachedCriteria criteria, Query query) throws Exception;
}
