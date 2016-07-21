package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.IMerchantOperationLogDao;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog;

@Repository
public class MerchantOperationLogDaoImpl extends HibernateEntityDao<MerchantOperationLog> implements IMerchantOperationLogDao {

}
