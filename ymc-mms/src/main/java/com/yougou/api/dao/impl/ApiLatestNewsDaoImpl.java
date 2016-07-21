package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiLatestNewsDao;
import com.yougou.api.model.pojo.ApiLatestNews;

/**
 * 
 * @author yang.mq
 *
 */
@Repository
public class ApiLatestNewsDaoImpl extends HibernateEntityDao<ApiLatestNews> implements IApiLatestNewsDao {

}
