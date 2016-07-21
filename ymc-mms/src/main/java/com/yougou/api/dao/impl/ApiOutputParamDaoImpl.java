package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiOutputParamDao;
import com.yougou.api.model.pojo.OutputParam;

/**
 * 
 * @author 杨梦清
 * 
 */
@Repository
public class ApiOutputParamDaoImpl extends HibernateEntityDao<OutputParam> implements IApiOutputParamDao {

}

