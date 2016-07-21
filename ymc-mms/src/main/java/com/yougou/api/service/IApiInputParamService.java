package com.yougou.api.service;

import java.util.List;

import com.yougou.api.model.pojo.InputParam;

/**
 * 
 * @author 杨梦清
 * 
 */
public interface IApiInputParamService {

	/**
	 *　获取API输入参数
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	InputParam getApiInputParamById(InputParam inputParam) throws Exception;
	
	/**
	 * 添加API输入参数
	 * 
	 * @param apiInputParam
	 * @throws Exception
	 */
	void saveApiInputParam(InputParam apiInputParam) throws Exception;
	
	/**
	 * 更新API输入参数
	 * 
	 * @param apiInputParam
	 * @throws Exception
	 */
	void updateApiInputParam(InputParam apiInputParam) throws Exception;
	
	/**
	 * 删除API输入参数
	 * 
	 * @param id
	 * @throws Exception
	 */
	void deleteApiInputParamById(InputParam inputParam) throws Exception;
	
	/**
	 * 获取API输入参数可用排序号
	 * 
	 * @param inputParam
	 * @param upperLimit
	 * @return List
	 * @throws Exception
	 */
	List<Integer> getUsableOrderNoList(InputParam inputParam, int upperLimit) throws Exception;
}

