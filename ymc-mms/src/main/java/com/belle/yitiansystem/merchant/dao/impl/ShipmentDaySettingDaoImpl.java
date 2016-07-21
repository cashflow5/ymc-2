package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.ShipmentDaySettingDao;
import com.belle.yitiansystem.merchant.model.pojo.ShipmentDaySetting;

@Repository
public class ShipmentDaySettingDaoImpl extends HibernateEntityDao<ShipmentDaySetting> implements ShipmentDaySettingDao {


	public HibernateTemplate getTemplate() {
		return getHibernateTemplate();
	}

}
