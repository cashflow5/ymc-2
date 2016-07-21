package com.yougou.api.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.yougou.api.model.pojo.ApiImplementor;

/**
 * 
 * @author 杨梦清
 * 
 */
public interface IApiImplementorService {

	/**
	 * 查询所有实现者
	 * 
	 * @return List
	 * @throws Exception
	 */
	List<ApiImplementor> queryAllApiImplementor() throws Exception;
	
	/**
	 * 删除实现者
	 * 
	 * @param identifier
	 * @throws Exception
	 */
	void deleteApiImplementorByIdentifier(String identifier) throws Exception;

	/**
	 * 获取指定标示符实现者
	 * 
	 * @param identifier
	 * @return ApiImplementor
	 * @throws Exception
	 */
	ApiImplementor getApiImplementorByIdentifier(String identifier) throws Exception;
	
	/**
	 * 添加实现者
	 * 
	 * @param ApiImplementor
	 * @throws Exception
	 */
	void saveApiImplementor(ApiImplementor apiImplementor) throws Exception;

	/**
	 * 修改实现者
	 * 
	 * @param ApiImplementor
	 * @throws Exception
	 */
	void updateApiImplementor(ApiImplementor apiImplementor) throws Exception;

	/**
	 * 查询实现者列表
	 * 
	 * @param criteria
	 * @param query
	 * @return PageFinder
	 * @throws Exception
	 */
	PageFinder<ApiImplementor> queryApiImplementor(DetachedCriteria criteria, Query query) throws Exception;
}

