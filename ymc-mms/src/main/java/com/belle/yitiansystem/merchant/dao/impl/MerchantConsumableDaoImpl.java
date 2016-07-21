package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.IMerchantConsumableDao;
import com.belle.yitiansystem.merchant.model.pojo.MerchantConsumable;

/**
 * 耗材dao类
 * @author wang.M
 *
 */
@Repository
public class MerchantConsumableDaoImpl extends HibernateEntityDao<MerchantConsumable> implements IMerchantConsumableDao{

}
