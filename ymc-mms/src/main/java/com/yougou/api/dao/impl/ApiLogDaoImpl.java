package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiLogDao;
import com.yougou.api.model.pojo.ApiLog;

@Repository
public class ApiLogDaoImpl extends HibernateEntityDao<ApiLog> implements IApiLogDao {

}
