package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiValidatorDao;
import com.yougou.api.model.pojo.ApiValidator;

/**
 * 
 * @author 杨梦清
 * 
 */
@Repository
public class ApiValidatorDaoImpl extends HibernateEntityDao<ApiValidator> implements IApiValidatorDao {

}

