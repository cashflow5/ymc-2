package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.IMerchantGrantConsumableDao;
import com.belle.yitiansystem.merchant.model.pojo.MerchantGrantConsumable;

/**
 * 给商家耗材dao类
 * @author wang.M
 *
 */
@Repository
public class MerchantGrantConsumableDaoImpl extends HibernateEntityDao<MerchantGrantConsumable> implements IMerchantGrantConsumableDao{

}
