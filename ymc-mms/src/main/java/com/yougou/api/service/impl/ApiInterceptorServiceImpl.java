package com.yougou.api.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.yougou.api.dao.IApiInterceptorDao;
import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiInterceptor;
import com.yougou.api.model.pojo.ApiInterceptorMapper;
import com.yougou.api.model.pojo.ApiVersion;
import com.yougou.api.model.pojo.ApiVersionInterceptorMapper;
import com.yougou.api.model.pojo.InterceptorMapper;
import com.yougou.api.service.IApiInterceptorService;

/**
 * 
 * @author 杨梦清
 * 
 */
@Service
public class ApiInterceptorServiceImpl implements IApiInterceptorService {

	@Resource
	private IApiInterceptorDao apiInterceptorDao;
	
	@Override
	@Transactional
	public void deleteApiInterceptorByIdentifier(String identifier) throws Exception {
		// 检查拦截器是否有被其它实例引用(如：输入参数元数据)
		ApiInterceptor apiInterceptor = getApiInterceptorByIdentifier(identifier);
		Set<InterceptorMapper> interceptorMappers = apiInterceptor.getInterceptorMappers();
		for (InterceptorMapper interceptorMapper : interceptorMappers) {
			if (ApiVersionInterceptorMapper.class.isInstance(interceptorMapper)) {
				ApiVersion apiVersion = ((ApiVersionInterceptorMapper) interceptorMapper).getApiVersion();
				if (apiVersion != null) {
					throw new UnsupportedOperationException("API版本[" + apiVersion.getVersionNo() + "]引用该拦截器,请先解除引用再进行操作!");
				}
			}
			if (ApiInterceptorMapper.class.isInstance(interceptorMapper)) {
				Api api = ((ApiInterceptorMapper) interceptorMapper).getApi();
				if (api != null) {
					throw new UnsupportedOperationException("API[" + api.getApiMethod() + "]引用该拦截器,请先解除引用再进行操作!");
				}
			}
			throw new UnsupportedOperationException("未知[" + interceptorMapper.getId() + "]引用该拦截器,请先解除引用再进行操作!");
		}
		// 删除校验器
		apiInterceptorDao.remove(apiInterceptor);
	}

	@Override
	public ApiInterceptor getApiInterceptorByIdentifier(String identifier) throws Exception {
		return apiInterceptorDao.findUniqueBy("identifier", identifier);
	}

	@Override
	@Transactional
	public void saveApiInterceptor(ApiInterceptor apiInterceptor) throws Exception {
		if (getApiInterceptorByIdentifier(apiInterceptor.getIdentifier()) != null) {
			throw new UnsupportedOperationException("API拦截器标示符[" + apiInterceptor.getIdentifier() + "]已经存在!");
		}
		apiInterceptorDao.save(apiInterceptor);
	}

	@Override
	@Transactional
	public void updateApiInterceptor(ApiInterceptor apiInterceptor) throws Exception {
		Session session = null;
		try {
			session = apiInterceptorDao.getHibernateSession();
			Criteria criteria = session.createCriteria(ApiInterceptor.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("identifier", apiInterceptor.getIdentifier()));
			criteria.add(Restrictions.ne("id", apiInterceptor.getId()));
			if (((Number) criteria.uniqueResult()).intValue() >= 1) {
				throw new UnsupportedOperationException("API拦截器标示符[" + apiInterceptor.getIdentifier() + "]已经存在!");
			}
			ApiInterceptor another = (ApiInterceptor) session.load(ApiInterceptor.class, apiInterceptor.getId(), LockMode.UPGRADE);
			another.setInterceptorClass(apiInterceptor.getInterceptorClass());
			another.setDescription(apiInterceptor.getDescription());
			another.setModifier(apiInterceptor.getModifier());
			another.setModified(apiInterceptor.getModified());
			session.update(another);
		} finally {
			apiInterceptorDao.releaseHibernateSession(session);
		}
	}

	@Override
	public PageFinder<ApiInterceptor> queryApiInterceptor(DetachedCriteria criteria, Query query) throws Exception {
		Session session = null;
		try {
			session = apiInterceptorDao.getHibernateSession();
			Criteria executableCriteria = criteria.getExecutableCriteria(session);
			return apiInterceptorDao.pagedByCriteria(executableCriteria, query.getPage(), query.getPageSize());
		} finally {
			apiInterceptorDao.releaseHibernateSession(session);
		}
	}

}

