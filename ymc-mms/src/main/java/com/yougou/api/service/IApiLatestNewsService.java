package com.yougou.api.service;

import org.hibernate.criterion.DetachedCriteria;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.yougou.api.model.pojo.ApiLatestNews;

public interface IApiLatestNewsService {
	
	/**
	 * 获取API最新动态
	 * 
	 * @param id
	 * @return ApiLatestNews
	 * @throws Exception
	 */
	ApiLatestNews getApiLatestNews(String id) throws Exception;

	/**
	 * 删除API最新动态
	 * 
	 * @param id
	 * @throws Exception
	 */
	void deleteApiLatestNews(String id) throws Exception;
	
	/**
	 * 添加API最新动态
	 * 
	 * @param ApiLatestNews
	 * @throws Exception
	 */
	void saveApiLatestNews(ApiLatestNews apiLatestNews) throws Exception;
	
	/**
	 * 更新API最新动态
	 * 
	 * @param ApiLatestNews
	 * @throws Exception
	 */
	void updateApiLatestNews(ApiLatestNews apiLatestNews) throws Exception;
	
	/**
	 * 分页查询API最新动态
	 * 
	 * @param criteria
	 * @param query
	 * @return PageFinder
	 * @throws Exception
	 */
	PageFinder<ApiLatestNews> queryApiLatestNews(DetachedCriteria criteria, Query query) throws Exception;
}
