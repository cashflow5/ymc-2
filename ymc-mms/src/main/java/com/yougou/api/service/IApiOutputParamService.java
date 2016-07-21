package com.yougou.api.service;

import java.util.List;

import com.yougou.api.model.pojo.OutputParam;

/**
 * 
 * @author 杨梦清
 * 
 */
public interface IApiOutputParamService {

	/**
	 *　获取API输入参数
	 * 
	 * @param outputParam
	 * @return OutputParam
	 * @throws Exception
	 */
	OutputParam getApiOutputParamById(OutputParam outputParam) throws Exception;
	
	/**
	 * 添加API输入参数
	 * 
	 * @param outputParam
	 * @throws Exception
	 */
	void saveApiOutputParam(OutputParam outputParam) throws Exception;
	
	/**
	 * 更新API输入参数
	 * 
	 * @param outputParam
	 * @throws Exception
	 */
	void updateApiOutputParam(OutputParam outputParam) throws Exception;
	
	/**
	 * 删除API输入参数
	 * 
	 * @param outputParam
	 * @throws Exception
	 */
	void deleteApiOutputParamById(OutputParam outputParam) throws Exception;
	
	/**
	 * 获取可用排序号
	 * 
	 * @param outputParam
	 * @param upperLimit
	 * @return List
	 * @throws Exception
	 */
	List<Integer> getUsableOrderNoList(OutputParam outputParam, int upperLimit) throws Exception;
}

