package com.yougou.api.service;

import java.util.List;

import com.yougou.api.model.pojo.ApiVersion;

/**
 * 
 * @author 杨梦清
 * 
 */
public interface IApiVersionService {

	/**
	 * 获取API版本
	 * 
	 * @param versionNo
	 * @return ApiVersion
	 * @throws Exception
	 */
	ApiVersion getApiVersionByNo(String versionNo) throws Exception;
	
	/**
	 * 获取API版本
	 * 
	 * @param versionId
	 * @return ApiVersion
	 * @throws Exception
	 */
	ApiVersion getApiVersionById(String versionId) throws Exception;

	/**
	 * 添加API版本
	 * 
	 * @param apiVersion
	 * @throws Exception
	 */
	void saveApiVersion(ApiVersion apiVersion) throws Exception;
	
	/**
	 * 更新API版本
	 * 
	 * @param apiVersion
	 * @throws Exception
	 */
	void updateApiVersion(ApiVersion apiVersion) throws Exception;
	
	/**
	 * 删除API版本
	 * 
	 * @param versionId
	 * @throws Exception
	 */
	void deleteApiVersionById(String versionId) throws Exception;

	/**
	 * 获取所有API版本
	 * 
	 * @return List
	 * @throws Exception
	 */
	List<ApiVersion> queryAllApiVersion() throws Exception;
}
