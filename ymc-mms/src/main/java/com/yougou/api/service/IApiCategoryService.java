package com.yougou.api.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.yougou.api.model.pojo.ApiCategory;

/**
 * 
 * @author 杨梦清
 * 
 */
public interface IApiCategoryService {
	
	/**
	 * 获取所有API分类
	 * 
	 * @return List
	 * @throws Exception
	 */
	List<ApiCategory> queryAllApiCategory() throws Exception;

	/**
	 * 获取API分类
	 * 
	 * @param id
	 * @return ApiCategory
	 * @throws Exception
	 */
	ApiCategory getApiCategoryById(String id) throws Exception;
	
	/**
	 * 获取API分类
	 * 
	 * @param categoryCode
	 * @return ApiCategory
	 * @throws Exception
	 */
	ApiCategory getApiCategoryByCode(String categoryCode) throws Exception;
	
	/**
	 * 添加API分类
	 * 
	 * @param apiCategory
	 * @throws Exception
	 */
	void saveApiCategory(ApiCategory apiCategory) throws Exception;
	
	/**
	 * 更新API分类
	 * 
	 * @param apiCategory
	 * @throws Exception
	 */
	void updateApiCategory(ApiCategory apiCategory) throws Exception;
	
	/**
	 * 删除API分类
	 * 
	 * @param id
	 * @throws Exception
	 */
	void deleteApiCategory(String id) throws Exception;
	
	/**
	 * 分页查询API分类
	 * 
	 * @param criteria
	 * @param query
	 * @return PageFinder
	 * @throws Exception
	 */
	PageFinder<ApiCategory> queryApiCategory(DetachedCriteria criteria, Query query) throws Exception;
}

