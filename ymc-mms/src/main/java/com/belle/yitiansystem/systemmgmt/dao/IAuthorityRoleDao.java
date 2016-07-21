package com.belle.yitiansystem.systemmgmt.dao;

import java.util.List;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.AuthorityRole;

public interface IAuthorityRoleDao extends IHibernateEntityDao<AuthorityRole> {
	
	/**
	 * 获取带级联关系的Role对象
	 * @param roleid
	 * @return
	 */
	public AuthorityRole getJoinRoleById(String roleid);
	
	/**
	 * 获取用户不拥有的角色
	 * @param userid
	 * @return
	 */
	public List<AuthorityRole> getnoAllotAuthorityRole(String userid);
}
