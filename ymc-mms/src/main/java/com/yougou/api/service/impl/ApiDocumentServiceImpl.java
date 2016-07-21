package com.yougou.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.yougou.api.dao.IApiCategoryDao;
import com.yougou.api.dao.IApiDao;
import com.yougou.api.dao.IApiVersionDao;
import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiCategory;
import com.yougou.api.model.pojo.ApiVersion;
import com.yougou.api.service.IApiDocumentService;

@Service
public class ApiDocumentServiceImpl implements IApiDocumentService {

	@Resource
	private IApiCategoryDao apiCategoryDao;

	@Resource
	private IApiVersionDao apiVersionDao;

	@Resource
	private IApiDao apiDao;

	@Override
	public List<ApiVersion> selectAllApiVersion() throws Exception {
		return apiVersionDao.find("from ApiVersion");
	}

	@Override
	public List<ApiCategory> selectAllApiCategory() throws Exception {
		return apiCategoryDao.find("from ApiCategory");
	}

	@Override
	public List<Api> selectApiByCategory(String categoryCode) throws Exception {
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append(" from Api t1 ");
		hqlBuilder.append(" left join fetch t1.apiErrorMappers t2 ");
		hqlBuilder.append(" left join fetch t1.apiExamples t3 ");
		hqlBuilder.append(" left join fetch t1.apiInputParams t4 ");
		hqlBuilder.append(" left join fetch t1.apiOutputParams t5 ");
		hqlBuilder.append(" where t1.apiCategory.categoryCode = ? ");

		Session session = null;
		try {
			session = apiDao.getHibernateSession();
			Query query = session.createQuery(hqlBuilder.toString());
			query.setParameter(0, categoryCode);
			query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			return query.list();
		} finally {
			apiDao.releaseHibernateSession(session);
		}
	}

	@Override
	public Api selectApiByCode(String apiCode) throws Exception {
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append(" from Api t1 ");
		hqlBuilder.append(" left join fetch t1.apiErrorMappers t2 ");
		hqlBuilder.append(" left join fetch t1.apiExamples t3 ");
		hqlBuilder.append(" left join fetch t1.apiInputParams t4 ");
		hqlBuilder.append(" left join fetch t1.apiOutputParams t5 ");
		hqlBuilder.append(" left join fetch t1.apiVersion t6 ");
		hqlBuilder.append(" where t1.apiCode = ? ");
		
		Session session = null;
		try {
			session = apiDao.getHibernateSession();
			Query query = session.createQuery(hqlBuilder.toString());
			query.setParameter(0, apiCode);
			query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			return (Api) query.uniqueResult();
		} finally {
			apiDao.releaseHibernateSession(session);
		}
	}
}
