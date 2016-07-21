package com.belle.yitiansystem.systemmgmt.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.systemmgmt.model.pojo.OperateLog;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemConfig;
import com.belle.yitiansystem.systemmgmt.model.vo.OperateLogVo;

/**
 * 
 *@author yhb
 * 
 * @version 创建时间：2011-3-30 下午02:44:41
 */
public interface ISystemConfigService {
	
	/**
	 * 增加配置，已存的在键值 装将配置替换
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public SystemConfig addSystemConfig(SystemConfig config,HttpServletRequest request) throws Exception;
	
	/**
	 * 根据ID查询系统配置
	 * @param id
	 * @return
	 */
	public SystemConfig getSystemConfigById(String id);
	
	/**
	 * 获取所有系统正使用配置
	 * @return
	 */
	public List<SystemConfig> getAllUseSystemConfig();
	
	
	/**
	 * 获取所有系统配置
	 * @return
	 */
	public List<SystemConfig> getAllSystemConfig();
	
	/**
	 *根据键值获取配置
	 * @return
	 * @throws Exception 
	 */
	public SystemConfig getSystemConfigByKey(String key) throws Exception;
	
	/**
	 *读缓存根据键值获取配置
	 * @return
	 * @throws Exception 
	 */
	public SystemConfig getCacheSystemConfigByKey(String key);
	/**
	 * 修改系统配置
	 * @param Menu
	 * @return
	 * @throws Exception 
	 */
	public SystemConfig updateSystemConfig(SystemConfig SystemConfig,HttpServletRequest request) throws Exception;
	
	
	/**
	 * 删除节点
	 * @param funsetId
	 * @return
	 */
	public String removeSystemConfig(String resourceid,HttpServletRequest request)  throws Exception;
	
	/**
	 * 启用配置
	 * @return
	 */
	public String useConfig(String id,HttpServletRequest request) throws Exception;
	
	/**
	 * 查询没有删除的
	 * @return
	 */
	public List<SystemConfig> getAllUseSystemConfigNoCache();
	
	/**
	 * 无缓存查询配置项
	 * @param key
	 * @return
	 */
	public SystemConfig getConfigValueByKeyWithNoCache(String key);
	
	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void saveConfigValueByKey(String key ,String value);
	
	
	/**
	 * 使用本地内存中的Map缓存获取特定系统配置项
	 * 为了获取最高的性能，采用本地ConcurrentMap 来缓存各个仓库的配置。	 * 
	 * 该方法现在仅仅被WMS用来 获取各个仓库的授权码验证 
	 *  
	 * 当调用  一键更新缓存 是 该 本地Map会被清空
	 * @author hu.jp 2012-08-23
	 * @param key
	 * @return
	 */
	public SystemConfig getConfigByLocalCacheMap(String key);

	
	/**
	 * 添加日志信息
	 * @author zhao.my
	 * @param request 
	 * @param role  AuthorityRole 对象，可以传null  修改时不能为空
	 * @param type  操作状态
	 * @throws Exception
	 */
	public void saveOperateLog(HttpServletRequest request,String info_id,String operate_desc,Integer type) throws Exception;
	/**
	 *   查询配置项操作日志
	 * @param vo 操作日志查询条件vo
	 * @param query
	 * @return
	 */
	public PageFinder<OperateLog> pageQueryOperaterLog(OperateLogVo vo,
			Query query);
}
