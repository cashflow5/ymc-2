package com.belle.yitiansystem.systemmgmt.dao;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;

public interface ISystemmgtUserDao extends IHibernateEntityDao<SystemmgtUser> {
	
	/**
	 * 根据ID查询系统用户 级联查询用户角色
	 * @param id
	 * @return
	 */
	public SystemmgtUser getSystemmgtUserJoinRoleById(String id);
	
	/**
	 * 根据ID查询系统用户 级联权限小组
	 * @author wang.M
	 * @param id
	 * @return
	 */
	public SystemmgtUser getSystemmgtUserJoinPermissionById(String id);

}
