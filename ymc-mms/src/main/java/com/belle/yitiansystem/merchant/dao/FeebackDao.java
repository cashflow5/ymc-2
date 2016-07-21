package com.belle.yitiansystem.merchant.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.yitiansystem.merchant.model.pojo.Feeback;

/**
 * 意见反馈
 * @author he.wc
 *
 */
public interface FeebackDao extends IHibernateEntityDao<Feeback> {
	
	/**
	 * 获取HibernateTemplate
	 * @return
	 */
	HibernateTemplate getTemplate();
	

}
