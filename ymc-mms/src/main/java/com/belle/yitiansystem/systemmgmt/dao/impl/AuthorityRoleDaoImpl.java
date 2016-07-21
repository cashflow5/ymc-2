package com.belle.yitiansystem.systemmgmt.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.CriteriaAdapter;
import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.systemmgmt.dao.IAuthorityRoleDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.AuthorityRole;

@Repository
public class AuthorityRoleDaoImpl extends HibernateEntityDao<AuthorityRole> implements IAuthorityRoleDao {

	@Override
	public AuthorityRole getJoinRoleById(String roleid) {
		CriteriaAdapter criteriaAdapter = createCriteriaAdapter();
		Criteria criteria = criteriaAdapter.getCriteria();
		criteria.add(Restrictions.eq("id", roleid));
		criteria.setFetchMode("systemmgtUsers", FetchMode.JOIN);
		criteria.setFetchMode("authorityResources", FetchMode.JOIN);
		AuthorityRole role = (AuthorityRole)criteria.uniqueResult();
		super.releaseHibernateSession(criteriaAdapter.getSession());
		return role;
	}

	@Override
	public List<AuthorityRole> getnoAllotAuthorityRole(String userid) {
		CriteriaAdapter criteriaAdapter = createCriteriaAdapter();
		Criteria criteria = criteriaAdapter.getCriteria();
		ProjectionList proList = Projections.projectionList();//设置投影集合 
		proList.add(Projections.groupProperty("id"));
		criteria.setProjection(proList);
		criteria.createAlias("systemmgtUsers", "sysuser");
		criteria.setFetchMode("sysuser", FetchMode.JOIN);
		criteria.add(Restrictions.eq("sysuser.id",userid));
		List<Object> list = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		super.releaseHibernateSession(criteriaAdapter.getSession());
		
		CriteriaAdapter critAdapter = createCriteriaAdapter();
		Criteria crit = critAdapter.getCriteria();
		if(list.size() > 0){
			crit.add(Restrictions.not(Restrictions.in("id", list)));
		}
		crit.addOrder(Order.asc("roleName"));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AuthorityRole> rolelist = crit.list();
		super.releaseHibernateSession(critAdapter.getSession());
		
		return rolelist;
	}

}
