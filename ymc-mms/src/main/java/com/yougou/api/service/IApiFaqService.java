package com.yougou.api.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.yougou.api.model.pojo.ApiFaq;

public interface IApiFaqService {
	
	/**
	 * 获取API常见问题
	 * 
	 * @param id
	 * @return ApiFaq
	 * @throws Exception
	 */
	ApiFaq getApiFaq(String id) throws Exception;

	/**
	 * 删除API常见问题
	 * 
	 * @param id
	 * @throws Exception
	 */
	void deleteApiFaq(String id) throws Exception;
	
	/**
	 * 添加API常见问题
	 * 
	 * @param apiFaq
	 * @throws Exception
	 */
	void saveApiFaq(ApiFaq apiFaq) throws Exception;
	
	/**
	 * 更新API常见问题
	 * 
	 * @param apiFaq
	 * @throws Exception
	 */
	void updateApiFaq(ApiFaq apiFaq) throws Exception;
	
	/**
	 * 查询所有API常见问题
	 * 
	 * @return List
	 * @throws Exception
	 */
	List<ApiFaq> queryAllApiFaq() throws Exception;
	
	/**
	 * 分页查询API常见问题
	 * 
	 * @param criteria
	 * @param query
	 * @return PageFinder
	 * @throws Exception
	 */
	PageFinder<ApiFaq> queryApiFaq(DetachedCriteria criteria, Query query) throws Exception;
}
