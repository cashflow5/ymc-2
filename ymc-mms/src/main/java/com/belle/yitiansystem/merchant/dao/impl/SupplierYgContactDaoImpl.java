package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.ISupplierYgContactDao;
import com.belle.yitiansystem.merchant.model.pojo.SupplierYgContact;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-11 上午9:54:04
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@Repository
public class SupplierYgContactDaoImpl
		extends
			HibernateEntityDao<SupplierYgContact>
		implements
			ISupplierYgContactDao {

	@Override
	public void insert(SupplierYgContact contact) {
		this.getHibernateTemplate().save(contact);
	}

	@Override
	public void update(SupplierYgContact contact) {
		this.getHibernateTemplate().update(contact);
	}

}
