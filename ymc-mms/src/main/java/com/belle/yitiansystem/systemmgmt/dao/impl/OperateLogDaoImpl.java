package com.belle.yitiansystem.systemmgmt.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.systemmgmt.dao.IOperateLogDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.OperateLog;

@Repository
public class OperateLogDaoImpl  extends HibernateEntityDao<OperateLog> implements IOperateLogDao {

}
