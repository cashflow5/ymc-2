package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.PunishRuleDao;
import com.belle.yitiansystem.merchant.model.pojo.PunishRule;

/**
 * 处罚规则
 * 
 * @author he.wc
 * 
 */
@Repository
public class PunishRuleDaoImpl extends HibernateEntityDao<PunishRule> implements PunishRuleDao {

	public HibernateTemplate getTemplate() {
		return getHibernateTemplate();
	}

}
