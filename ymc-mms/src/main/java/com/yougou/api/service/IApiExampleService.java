package com.yougou.api.service;

import com.yougou.api.model.pojo.ApiExample;

/**
 * 
 * @author 杨梦清
 * 
 */
public interface IApiExampleService {

	/**
	 * 获取API输出示例数据
	 * 
	 * @param id
	 * @return ApiExample
	 * @throws Exception
	 */
	ApiExample getApiExampleById(String id) throws Exception;

	/**
	 * 删除API输出示例数据
	 * 
	 * @param id
	 * @throws Exception
	 */
	void deleteApiExampleById(String id) throws Exception;

	/**
	 * 添加API输出示例数据
	 * 
	 * @param apiExample
	 * @throws Exception
	 */
	void saveApiExample(ApiExample apiExample) throws Exception;

	/**
	 * 更新API输出示例数据
	 * 
	 * @param apiExample
	 * @throws Exception
	 */
	void updateApiExample(ApiExample apiExample) throws Exception;
}
