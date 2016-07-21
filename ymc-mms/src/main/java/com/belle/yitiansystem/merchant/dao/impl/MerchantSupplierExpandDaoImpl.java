package com.belle.yitiansystem.merchant.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.IMerchantSupplierExpandDao;
import com.belle.yitiansystem.merchant.model.pojo.MerchantSupplierExpand;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-11 下午4:19:18
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@Repository
public class MerchantSupplierExpandDaoImpl
		extends
			HibernateEntityDao<MerchantSupplierExpand>
		implements
			IMerchantSupplierExpandDao {
	public void deleteByUserId(String userId) {
		Session session = null;
		try {
			session = this.getHibernateSession();
			session.beginTransaction();
			Query query = session
					.createQuery("delete MerchantSupplierExpand s where s.YgContactUserId=?");
			query.setString(0, userId);
			query.executeUpdate();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}
}
