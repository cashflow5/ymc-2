package com.yougou.api.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiInterceptorMapperDao;
import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiCategory;
import com.yougou.api.model.pojo.ApiVersion;
import com.yougou.api.model.pojo.InterceptorMapper;

/**
 * 
 * @author 杨梦清
 * 
 */
@Repository
public class ApiInterceptorMapperDaoImpl extends HibernateEntityDao<InterceptorMapper> implements IApiInterceptorMapperDao {

	@Override
	public Api getApi(String refId) {
		return getRefObject(Api.class, refId);
	}

	@Override
	public ApiVersion getApiVersion(String refId) {
		return getRefObject(ApiVersion.class, refId);
	}

	@Override
	public ApiCategory getApiCategory(String refId) {
		return getRefObject(ApiCategory.class, refId);
	}

	/**
	 * 获取 refId 引用的对象
	 * 
	 * @param clazz
	 * @param refId
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	private <T> T getRefObject(Class<T> clazz, String refId) {
		Session session = null;
		try {
			session = getHibernateSession();
			return (T) session.get(clazz, refId);
		} finally {
			releaseHibernateSession(session);
		}
	}
}

