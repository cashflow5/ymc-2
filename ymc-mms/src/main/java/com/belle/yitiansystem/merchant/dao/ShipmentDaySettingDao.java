package com.belle.yitiansystem.merchant.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.yitiansystem.merchant.model.pojo.ShipmentDaySetting;

/**
 *  招商商家发货日期设置表
 * @author he.wc
 *
 */
public interface ShipmentDaySettingDao extends IHibernateEntityDao<ShipmentDaySetting> {
	
	/**
	 * 获取HibernateTemplate
	 * @return
	 */
	HibernateTemplate getTemplate();
	

}
