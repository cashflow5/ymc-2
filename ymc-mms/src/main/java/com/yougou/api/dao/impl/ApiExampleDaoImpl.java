package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiExampleDao;
import com.yougou.api.model.pojo.ApiExample;

/**
 * 
 * @author 杨梦清
 * 
 */
@Repository
public class ApiExampleDaoImpl extends HibernateEntityDao<ApiExample> implements IApiExampleDao {

}

