package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiVisitCounterDao;
import com.yougou.api.model.pojo.ApiVisitCounter;

/**
 * 
 * @author yang.mq
 *
 */
@Repository
public class ApiVisitCounterDaoImpl extends HibernateEntityDao<ApiVisitCounter> implements IApiVisitCounterDao {

}
