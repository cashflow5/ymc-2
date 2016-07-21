package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiErrorMapperDao;
import com.yougou.api.model.pojo.ApiErrorMapper;

/**
 * 
 * @author yang.mq
 *
 */
@Repository
public class ApiErrorMapperDaoImpl extends HibernateEntityDao<ApiErrorMapper> implements IApiErrorMapperDao {

}
