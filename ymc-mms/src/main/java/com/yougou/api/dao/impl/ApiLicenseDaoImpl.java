package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiLicenseDao;
import com.yougou.api.model.pojo.ApiLicense;

/**
 * 
 * @author 杨梦清
 * 
 */
@Repository
public class ApiLicenseDaoImpl extends HibernateEntityDao<ApiLicense> implements IApiLicenseDao {

}

