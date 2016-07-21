package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiCategoryDao;
import com.yougou.api.model.pojo.ApiCategory;

@Repository
public class ApiCategoryDaoImpl extends HibernateEntityDao<ApiCategory> implements IApiCategoryDao {

}
