package com.yougou.api.service;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.mongodb.DBObject;
import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiLog;
import com.yougou.api.model.vo.AppKeySecretVo;

public interface IApiService {

	/**
	 * 删除API
	 * 
	 * @param id
	 * @throws Exception
	 */
	void deleteApiById(String id) throws Exception;

	/**
	 * 获取指定标示符API
	 * 
	 * @param id
	 * @return Api
	 * @throws Exception
	 */
	Api getApiById(String id) throws Exception;
	
	/**
	 * 获取所有API
	 * 
	 * @return List
	 * @throws Exception
	 */
	List<Api> getAllApi() throws Exception;
	
	/**
	 * 添加API
	 * 
	 * @param apiFilter
	 * @throws Exception
	 */
	void saveApi(Api apiFilter) throws Exception;

	/**
	 * 修改API
	 * 
	 * @param apiFilter
	 * @throws Exception
	 */
	void updateApi(Api apiFilter) throws Exception;
	
	/**
	 * 设置API是否启用（0：启用 1：禁用）
	 * 
	 * @param id
	 * @param isEnable
	 * @throws Exception
	 */
	void updateApiStatus(String id, String isEnable) throws Exception;

	/**
	 * 查询API列表
	 * 
	 * @param criteria
	 * @param query
	 * @return PageFinder
	 * @throws Exception
	 */
	PageFinder<Api> queryApi(DetachedCriteria criteria, Query query) throws Exception;
	
	/**
	 * 查询API日志信息
	 * 
	 * @param apiLog
	 * @param fromOperated
	 * @param toOperated
	 * @param apiInputParam
	 * @param apiInputParamValue
	 * @param query
	 * @return PageFinder
	 * @throws Exception
	 */
	PageFinder<DBObject> queryApiLogFromMongoDB(ApiLog apiLog, Date fromOperated, Date toOperated, String apiInputParam, String apiInputParamValue, Query query) throws Exception;
	
	/**
	 * 查询所有apiId
	 * @return
	 */
	public List<Api> findApiIdList() throws Exception;
	
	/**
	 * 查询api数据授权数量
	 * @return
	 */
	public int findApiAuthCount();
	
	/**
	 * 查询appkey验证数量
	 * @return
	 */
	public int findAppKeyAuthCount();
	
	/**
	 * 根据apiId查询所有appKey
	 * @param apiId
	 * @return
	 */
	public List<String> findAppKeyByApiId(String apiId);
	
	/**
	 * 查询appkey与secret列表
	 * @return
	 */
	public List<AppKeySecretVo> findAppKeySecret();
	
	public AppKeySecretVo findAppKeySecretByAppkey(String appkey) throws Exception;
	
	public String findEmailByAppKey(String appkey) throws Exception;
	
	/**
	 * 刷新缓存
	 * @throws Exception
	 */
	void refreshApiCache() throws Exception;
}
