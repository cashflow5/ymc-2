package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiVersionDao;
import com.yougou.api.model.pojo.ApiVersion;

@Repository
public class ApiVersionDaoImpl extends HibernateEntityDao<ApiVersion> implements IApiVersionDao {

}
