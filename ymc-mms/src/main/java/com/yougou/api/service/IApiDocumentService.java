package com.yougou.api.service;

import java.util.List;

import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiCategory;
import com.yougou.api.model.pojo.ApiVersion;

public interface IApiDocumentService {

	/**
	 * 查询所有 API 分类
	 * 
	 * @return List
	 */
	List<ApiCategory> selectAllApiCategory() throws Exception;
	
	/**
	 * 查询所有 API 版本
	 * 
	 * @return List
	 */
	List<ApiVersion> selectAllApiVersion() throws Exception;
	
	/**
	 * 按 API 分类代码查询 API
	 * 
	 * @param categoryCode
	 * @return List
	 */
	List<Api> selectApiByCategory(String categoryCode) throws Exception;

	/**
	 * 按代码查询 API
	 * 
	 * @param apiCode
	 * @return Api
	 */
	Api selectApiByCode(String apiCode) throws Exception;
}
