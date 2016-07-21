package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiErrorDao;
import com.yougou.api.model.pojo.ApiError;

/**
 * 
 * @author 杨梦清
 * 
 */
@Repository
public class ApiErrorDaoImpl extends HibernateEntityDao<ApiError> implements IApiErrorDao {

}

