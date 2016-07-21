package com.belle.yitiansystem.merchant.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.yitiansystem.merchant.model.pojo.PunishRule;

/**
 * 处罚规则
 * @author he.wc
 *
 */
public interface PunishRuleDao extends IHibernateEntityDao<PunishRule> {
	
	/**
	 * 获取HibernateTemplate
	 * @return
	 */
	HibernateTemplate getTemplate();
	

}
