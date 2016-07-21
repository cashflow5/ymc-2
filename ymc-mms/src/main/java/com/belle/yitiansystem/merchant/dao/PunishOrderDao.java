package com.belle.yitiansystem.merchant.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.yitiansystem.merchant.model.pojo.PunishOrder;

/**
 * 处罚规则
 * @author he.wc
 *
 */
public interface PunishOrderDao extends IHibernateEntityDao<PunishOrder> {
	
	/**
	 * 获取HibernateTemplate
	 * @return
	 */
	HibernateTemplate getTemplate();

}
