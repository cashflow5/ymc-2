package com.belle.yitiansystem.systemmgmt.dao;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.UserPermissionGroup;
/**
 * 用户权限小组dao接口层
 * @author wang.m
 *
 * @date 2011-12-21
 */
public interface IUserPermissionGroupDao extends IHibernateEntityDao<UserPermissionGroup>{
	

	/**
	 * 删除用户权限小组对象数据
	 * @author wang.m
	 * @throws Exception 
	 * @throws Exception 
	 * @Date 2011-12-20
	 */
	public void deleteUserPermissionGroupById(String id) throws Exception;

}
