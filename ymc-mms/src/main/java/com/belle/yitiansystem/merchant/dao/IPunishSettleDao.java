package com.belle.yitiansystem.merchant.dao;  

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.yitiansystem.merchant.model.pojo.PunishSettle;

public interface IPunishSettleDao extends IHibernateEntityDao<PunishSettle> {
	/**
	 * 获取HibernateTemplate
	 * @return
	 */
	HibernateTemplate getTemplate();
}
