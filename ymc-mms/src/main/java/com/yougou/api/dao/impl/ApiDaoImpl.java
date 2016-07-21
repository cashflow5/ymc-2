package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiDao;
import com.yougou.api.model.pojo.Api;

@Repository
public class ApiDaoImpl extends HibernateEntityDao<Api> implements IApiDao {

}
