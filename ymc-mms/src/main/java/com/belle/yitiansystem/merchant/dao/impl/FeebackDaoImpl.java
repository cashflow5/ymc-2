package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.FeebackDao;
import com.belle.yitiansystem.merchant.model.pojo.Feeback;

/**
 * 意见反馈
 * 
 * @author he.wc
 * 
 */
@Repository
public class FeebackDaoImpl extends HibernateEntityDao<Feeback> implements FeebackDao {

	public HibernateTemplate getTemplate() {
		return getHibernateTemplate();
	}

}
