package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiInterceptorDao;
import com.yougou.api.model.pojo.ApiInterceptor;

/**
 * 
 * @author 杨梦清
 * 
 */
@Repository
public class ApiInterceptorDaoImpl extends HibernateEntityDao<ApiInterceptor> implements IApiInterceptorDao {

}
