package com.belle.yitiansystem.merchant.dao;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.yitiansystem.merchant.model.pojo.MerchantSupplierExpand;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-11 下午4:18:31
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public interface IMerchantSupplierExpandDao
		extends
			IHibernateEntityDao<MerchantSupplierExpand> {
	public void deleteByUserId(String userId);
}
