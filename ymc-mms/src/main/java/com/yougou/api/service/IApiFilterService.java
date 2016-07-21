package com.yougou.api.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.yougou.api.model.pojo.ApiFilter;

/**
 * 
 * @author 杨梦清
 * 
 */
public interface IApiFilterService {

	/**
	 * 删除过滤器
	 * 
	 * @param identifier
	 * @throws Exception
	 */
	void deleteApiFilterByIdentifier(String identifier) throws Exception;

	/**
	 * 获取指定标示符过滤器
	 * 
	 * @param identifier
	 * @return ApiFilter
	 * @throws Exception
	 */
	ApiFilter getApiFilterByIdentifier(String identifier) throws Exception;
	
	/**
	 * 获取可用的过滤器排序号列表
	 * 
	 * @param upperLimit
	 * @return List
	 */
	List<Integer> getUsableOrderNoList(int upperLimit) throws Exception;

	/**
	 * 添加过滤器
	 * 
	 * @param apiFilter
	 * @throws Exception
	 */
	void saveApiFilter(ApiFilter apiFilter) throws Exception;

	/**
	 * 修改过滤器
	 * 
	 * @param apiFilter
	 * @throws Exception
	 */
	void updateApiFilter(ApiFilter apiFilter) throws Exception;

	/**
	 * 查询过滤器列表
	 * 
	 * @param criteria
	 * @param query
	 * @return PageFinder
	 * @throws Exception
	 */
	PageFinder<ApiFilter> queryApiFilter(DetachedCriteria criteria, Query query) throws Exception;
}
