package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.PunishOrderAgentDao;
import com.belle.yitiansystem.merchant.model.pojo.PunishOrderAgent;

/**
 *  商家处罚订单中间表
 * 
 * @author he.wc
 * 
 */
@Repository
public class PunishOrderAgentDaoImpl extends HibernateEntityDao<PunishOrderAgent> implements PunishOrderAgentDao {

	public HibernateTemplate getTemplate() {
		return getHibernateTemplate();
	}

}
