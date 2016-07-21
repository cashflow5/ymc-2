package com.belle.yitiansystem.merchant.dao;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.yitiansystem.merchant.model.pojo.SupplierYgContact;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-11 上午9:53:48
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public interface ISupplierYgContactDao
		extends
			IHibernateEntityDao<SupplierYgContact> {

	public void insert(SupplierYgContact contact);
	public void update(SupplierYgContact contact);

}
