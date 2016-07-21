package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiFaqDao;
import com.yougou.api.model.pojo.ApiFaq;

/**
 * 
 * @author yang.mq
 *
 */
@Repository
public class ApiFaqDaoImpl extends HibernateEntityDao<ApiFaq> implements IApiFaqDao {

}
