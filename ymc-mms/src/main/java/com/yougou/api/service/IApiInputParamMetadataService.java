package com.yougou.api.service;

import java.util.List;

import com.yougou.api.model.pojo.ApiInputParamMetadata;

/**
 * 
 * @author 杨梦清
 * 
 */
public interface IApiInputParamMetadataService {

	/**
	 * 获取API输入参数元数据
	 * 
	 * @param id
	 * @return ApiInputParamMetadata
	 * @throws Exception
	 */
	ApiInputParamMetadata getApiInputParamMetadataById(String id) throws Exception;
	
	/**
	 * 删除API输入参数元数据
	 * 
	 * @param id
	 * @throws Exception
	 */
	void deleteApiInputParamMetadataById(String id) throws Exception;
	
	/**
	 * 添加API输入参数元数据
	 * 
	 * @param apiInputParamMetadata
	 * @throws Exception
	 */
	void saveApiInputParamMetadata(ApiInputParamMetadata apiInputParamMetadata) throws Exception;
	
	/**
	 * 修改API输入参数元数据
	 * 
	 * @param apiInputParamMetadata
	 * @throws Exception
	 */
	void updateApiInputParamMetadata(ApiInputParamMetadata apiInputParamMetadata) throws Exception;
	
	/**
	 * 获取API输入参数元数据排序号
	 * 
	 * @param refId
	 * @param upperLimit
	 * @return List
	 * @throws Exception
	 */
	List<Integer> getUsableOrderNoList(String refId, int upperLimit) throws Exception;
}

