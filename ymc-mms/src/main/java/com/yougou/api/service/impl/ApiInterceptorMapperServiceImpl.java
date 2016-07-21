package com.yougou.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yougou.api.dao.IApiDao;
import com.yougou.api.dao.IApiInterceptorDao;
import com.yougou.api.dao.IApiInterceptorMapperDao;
import com.yougou.api.dao.IApiVersionDao;
import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiInterceptor;
import com.yougou.api.model.pojo.ApiInterceptorMapper;
import com.yougou.api.model.pojo.ApiVersion;
import com.yougou.api.model.pojo.ApiVersionInterceptorMapper;
import com.yougou.api.model.pojo.InterceptorMapper;
import com.yougou.api.service.IApiInterceptorMapperService;

/**
 * 
 * @author 杨梦清
 * 
 */
@Service
public class ApiInterceptorMapperServiceImpl implements IApiInterceptorMapperService {
	
	@Resource
	private IApiDao apiDao;
	
	@Resource
	private IApiVersionDao apiVersionDao;
	
	@Resource
	private IApiInterceptorDao apiInterceptorDao;
	
	@Resource
	private IApiInterceptorMapperDao apiInterceptorMapperDao;

	@Override
	public List<Integer> getUsableOrderNoList(InterceptorMapper interceptorMapper, int upperLimit) throws Exception {
		Session session = null;
		try {
			List<Integer> usableOrderNoList = new ArrayList<Integer>();
			for (int i = 1; i <= upperLimit; i++) {
				usableOrderNoList.add(i);
			}
			session = apiInterceptorMapperDao.getHibernateSession();
			Criteria criteria = session.createCriteria(InterceptorMapper.class);
			criteria.setProjection(Projections.property("orderNo"));

			if (ApiInterceptorMapper.class.isInstance(interceptorMapper)) {
				criteria.add(Restrictions.eq("api.id", interceptorMapper.getReferAs(Api.class).getId()));
			} else if (ApiVersionInterceptorMapper.class.isInstance(interceptorMapper)) {
				criteria.add(Restrictions.eq("apiVersion.id", interceptorMapper.getReferAs(ApiVersion.class).getId()));
			}
			
			usableOrderNoList.removeAll(criteria.list());
			return usableOrderNoList;
		} finally {
			apiInterceptorMapperDao.releaseHibernateSession(session);
		}
	}

	@Override
	public InterceptorMapper getInterceptorMapperById(String id) throws Exception {
		return apiInterceptorMapperDao.getById(id);
	}

	@Override
	@Transactional
	public void deleteInterceptorMapperById(String id) throws Exception {
		apiInterceptorMapperDao.removeById(id);
	}

	@Override
	@Transactional
	public void saveInterceptorMapper(InterceptorMapper interceptorMapper) throws Exception {
		String referId = resolveReferId(interceptorMapper);
		Session session = null;
		try {
			session = apiInterceptorMapperDao.getHibernateSession();
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append(" select ");
			sqlBuilder.append(" count(1) ");
			sqlBuilder.append(" from ");
			sqlBuilder.append(" tbl_merchant_api_interceptor_mapper ");
			sqlBuilder.append(" where ");
			sqlBuilder.append(" interceptor_id = ? and ref_id = ? ");
			SQLQuery sqlQuery = session.createSQLQuery(sqlBuilder.toString());
			sqlQuery.setParameter(0, interceptorMapper.getApiInterceptor().getId());
			sqlQuery.setParameter(1, referId);
			if (Number.class.cast(sqlQuery.uniqueResult()).intValue() >= 1) {
				throw new UnsupportedOperationException("拦截器链条已经引用!");
			}
			session.save(interceptorMapper);
		} finally {
			apiInterceptorMapperDao.releaseHibernateSession(session);
		}
	}

	@Override
	@Transactional
	public void updateInterceptorMapper(InterceptorMapper interceptorMapper) throws Exception {
		String referId = resolveReferId(interceptorMapper);
		Session session = null;
		try {
			session = apiInterceptorMapperDao.getHibernateSession();
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append(" select ");
			sqlBuilder.append(" count(1) ");
			sqlBuilder.append(" from ");
			sqlBuilder.append(" tbl_merchant_api_interceptor_mapper ");
			sqlBuilder.append(" where ");
			sqlBuilder.append(" interceptor_id = ? and ref_id = ? and id <> ? ");
			SQLQuery sqlQuery = session.createSQLQuery(sqlBuilder.toString());
			sqlQuery.setParameter(0, interceptorMapper.getApiInterceptor().getId());
			sqlQuery.setParameter(1, referId);
			sqlQuery.setParameter(2, interceptorMapper.getId());
			if (Number.class.cast(sqlQuery.uniqueResult()).intValue() >= 1) {
				throw new UnsupportedOperationException("拦截器链条已经引用!");
			}
			InterceptorMapper another = (InterceptorMapper) session.load(InterceptorMapper.class, interceptorMapper.getId(), LockMode.UPGRADE);
			another.setOrderNo(interceptorMapper.getOrderNo());
			session.update(another);
		} finally {
			apiInterceptorMapperDao.releaseHibernateSession(session);
		}
	}

	private String resolveReferId(InterceptorMapper interceptorMapper) throws Exception {
		ApiInterceptor apiInterceptor = interceptorMapper.getApiInterceptor();
		if (apiInterceptor == null || (apiInterceptor = apiInterceptorDao.findUniqueBy("interceptorClass", apiInterceptor.getInterceptorClass())) == null) {
			throw new IllegalArgumentException("无法匹配API拦截器链条引用的API拦截器对象!");
		}
		interceptorMapper.setApiInterceptor(apiInterceptor);
		if (ApiInterceptorMapper.class.isInstance(interceptorMapper)) {
			Api api = interceptorMapper.getReferAs(Api.class);
			if (api == null || (api = apiDao.getById(api.getId())) == null) {
				throw new IllegalArgumentException("无法匹配API输入参数引用的API对象!");
			}
			((ApiInterceptorMapper) interceptorMapper).setApi(api);
			return api.getId();
		} else if (ApiVersionInterceptorMapper.class.isInstance(interceptorMapper)) {
			ApiVersion apiVersion = interceptorMapper.getReferAs(ApiVersion.class);
			if (apiVersion == null || (apiVersion = apiVersionDao.getById(apiVersion.getId())) == null) {
				throw new IllegalArgumentException("无法匹配API输入参数引用的API版本对象!");
			}
			((ApiVersionInterceptorMapper) interceptorMapper).setApiVersion(apiVersion);
			return apiVersion.getId();
		} else {
			throw new IllegalArgumentException("无法匹配API输入参数引用的对象!");
		}
	}
}

