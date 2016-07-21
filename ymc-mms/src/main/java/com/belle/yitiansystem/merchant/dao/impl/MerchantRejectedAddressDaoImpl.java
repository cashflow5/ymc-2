package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.IMerchantRejectedAddressDao;
import com.belle.yitiansystem.merchant.model.pojo.MerchantRejectedAddress;


@Repository
public class MerchantRejectedAddressDaoImpl extends HibernateEntityDao<MerchantRejectedAddress> implements IMerchantRejectedAddressDao{

}
