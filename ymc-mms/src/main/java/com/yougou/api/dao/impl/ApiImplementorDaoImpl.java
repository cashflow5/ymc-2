package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiImplementorDao;
import com.yougou.api.model.pojo.ApiImplementor;

/**
 * 
 * @author 杨梦清
 * 
 */
@Repository
public class ApiImplementorDaoImpl extends HibernateEntityDao<ApiImplementor> implements IApiImplementorDao {

}

