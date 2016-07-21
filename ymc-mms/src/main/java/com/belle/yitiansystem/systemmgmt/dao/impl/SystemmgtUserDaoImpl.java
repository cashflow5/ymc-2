package com.belle.yitiansystem.systemmgmt.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.CritMap;
import com.belle.infrastructure.orm.basedao.CriteriaAdapter;
import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.systemmgmt.dao.ISystemmgtUserDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;

@Repository
public class SystemmgtUserDaoImpl extends HibernateEntityDao<SystemmgtUser> implements ISystemmgtUserDao {

	@Override
	public SystemmgtUser getSystemmgtUserJoinRoleById(String id) {
		CriteriaAdapter criteriaAdapter = createCriteriaAdapter();
		Criteria criteria = criteriaAdapter.getCriteria();
		criteria.add(Restrictions.eq("id", id));
		criteria.setFetchMode("authorityRoles", FetchMode.JOIN);
		SystemmgtUser user = (SystemmgtUser)criteria.uniqueResult();
		super.releaseHibernateSession(criteriaAdapter.getSession());
		return user;
	}
	
	/**
	 * 根据ID查询系统用户 级联权限小组
	 * @author wang.M
	 * @param id
	 * @return
	 */
	public SystemmgtUser getSystemmgtUserJoinPermissionById(String id){
		CritMap critMap=new CritMap();
		critMap.addEqual("id", id);
		critMap.addFech("permissionGroup");
		SystemmgtUser user = (SystemmgtUser)this.getObjectByCritMap(critMap);
		return user;
	}

}
