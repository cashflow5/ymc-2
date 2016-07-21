package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.IHelpCenterLogDao;
import com.belle.yitiansystem.merchant.model.pojo.HelpCenterLog;

@Repository
public class HelpCenterLogDaoImpl extends HibernateEntityDao<HelpCenterLog> implements IHelpCenterLogDao {

}
