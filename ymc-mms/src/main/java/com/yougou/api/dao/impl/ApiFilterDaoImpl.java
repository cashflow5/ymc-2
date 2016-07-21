package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiFilterDao;
import com.yougou.api.model.pojo.ApiFilter;

/**
 * 
 * @author 杨梦清
 * 
 */
@Repository
public class ApiFilterDaoImpl extends HibernateEntityDao<ApiFilter> implements IApiFilterDao {

}

