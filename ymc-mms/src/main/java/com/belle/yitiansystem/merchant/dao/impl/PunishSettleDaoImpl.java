package com.belle.yitiansystem.merchant.dao.impl;  

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.IPunishSettleDao;
import com.belle.yitiansystem.merchant.model.pojo.PunishSettle;

@Repository
public class PunishSettleDaoImpl extends HibernateEntityDao<PunishSettle> implements IPunishSettleDao {
	/** 
	 * @see com.belle.yitiansystem.merchant.dao.IPunishSettleDao#getTemplate() 
	 */
	@Override
	public HibernateTemplate getTemplate() {
		return getHibernateTemplate();
	}

}
