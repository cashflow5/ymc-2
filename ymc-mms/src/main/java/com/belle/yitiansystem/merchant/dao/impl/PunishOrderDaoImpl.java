package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.PunishOrderDao;
import com.belle.yitiansystem.merchant.model.pojo.PunishOrder;

/**
 * 处罚规则
 * 
 * @author he.wc
 * 
 */
@Repository
public class PunishOrderDaoImpl extends HibernateEntityDao<PunishOrder> implements PunishOrderDao {

	public HibernateTemplate getTemplate() {
		return getHibernateTemplate();
	}

}
