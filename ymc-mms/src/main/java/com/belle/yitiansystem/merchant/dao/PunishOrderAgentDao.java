package com.belle.yitiansystem.merchant.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.yitiansystem.merchant.model.pojo.PunishOrderAgent;

/**
 * 商家处罚订单中间表
 * @author he.wc
 *
 */
public interface PunishOrderAgentDao extends IHibernateEntityDao<PunishOrderAgent> {
	
	/**
	 * 获取HibernateTemplate
	 * @return
	 */
	HibernateTemplate getTemplate();
	

}
