package com.belle.yitiansystem.systemmgmt.dao;

import java.util.List;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemConfig;

/**
 * 
 * 类IAuthorityMenuService.java的实现描述：TODO 系统配置
 * @author yinhongbiao 2011-4-21 下午02:08:08
 */
public interface ISystemConfigDao extends IHibernateEntityDao<SystemConfig> {

	public SystemConfig findSystemConfigByKey(String key);
	
	public List<SystemConfig>  findAllSystemConfigByKey(String key);
	
	public boolean updateSystemConfig(SystemConfig systemConfig);
	
}
