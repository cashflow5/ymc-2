package com.belle.infrastructure.log.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.log.dao.ISystemLogDao;
import com.belle.infrastructure.log.model.pojo.Systemlog;
import com.belle.infrastructure.orm.basedao.HibernateEntityDao;

/**
 * 
 * 类SystemLogDaoImpl.java的实现描述：TODO 日志管理 
 * @author yinhongbiao 2011-4-19 下午03:17:18
 */
@Repository
public class SystemLogDaoImpl  extends HibernateEntityDao<Systemlog> implements ISystemLogDao {

}
