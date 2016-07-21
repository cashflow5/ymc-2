package com.yougou.api.service;

import org.hibernate.criterion.DetachedCriteria;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.yougou.api.model.pojo.ApiValidator;

/**
 * 
 * @author 杨梦清
 * 
 */
public interface IApiValidatorService {

	/**
	 * 获取下一个API符识符
	 * 
	 * @return String
	 * @throws Exception
	 */
	String getNextIdentifier() throws Exception;
	
	/**
	 * 按ID查询API验证器
	 * 
	 * @param id
	 * @return ApiValidator
	 * @throws Exception
	 */
	ApiValidator getApiValidatorById(String id) throws Exception;
	
	/**
	 * 按标识符查询API验证器
	 * 
	 * @param identifier
	 * @return ApiValidator
	 * @throws Exception
	 */
	ApiValidator getApiValidatorByIdentifier(String identifier) throws Exception;
	
	/**
	 * 添加API验证器
	 * 
	 * @param apiValidator
	 * @throws Exception
	 */
	void saveApiValidator(ApiValidator apiValidator) throws Exception;
	
	/**
	 * 更新API验证器
	 * 
	 * @param apiValidator
	 * @throws Exception
	 */
	void updateApiValidator(ApiValidator apiValidator) throws Exception;
	
	/**
	 * 按标识符删除API验证器
	 * 
	 * @param identifier
	 * @throws Exception
	 */
	void deleteApiValidatorByIdentifier(String identifier) throws Exception;
	
	/**
	 * 分页查询API验证器
	 * 
	 * @param criteria
	 * @param query
	 * @return PageFinder
	 * @throws Exception
	 */
	PageFinder<ApiValidator> queryApiValidator(DetachedCriteria criteria, Query query) throws Exception;
}

