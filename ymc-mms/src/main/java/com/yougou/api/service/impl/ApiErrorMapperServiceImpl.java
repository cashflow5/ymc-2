package com.yougou.api.service.impl;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yougou.api.dao.IApiErrorMapperDao;
import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiError;
import com.yougou.api.model.pojo.ApiErrorMapper;
import com.yougou.api.service.IApiErrorMapperService;

@Service
public class ApiErrorMapperServiceImpl implements IApiErrorMapperService {

	@Resource
	private IApiErrorMapperDao apiErrorMapperDao;

	@Override
	@Transactional
	public void saveApiErrorMapper(ApiErrorMapper apiErrorMapper) throws Exception {
		Session session = null;
		try {
			session = apiErrorMapperDao.getHibernateSession();
			Api api = (Api) session.load(Api.class, apiErrorMapper.getApi().getId());
			ApiError apiError = (ApiError) session.load(ApiError.class, apiErrorMapper.getApiError().getId());
			Criteria criteria = session.createCriteria(ApiErrorMapper.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("api", apiErrorMapper.getApi()));
			criteria.add(Restrictions.eq("apiError", apiErrorMapper.getApiError()));
			if (((Number) criteria.uniqueResult()).intValue() > 0) {
				throw new UnsupportedOperationException("API[" + api.getApiCode() + "]已经添加[" + apiError.getErrorCode() + "]错误码!");
			}
			apiErrorMapper.setApi(api);
			apiErrorMapper.setApiError(apiError);
			session.save(apiErrorMapper);
		} finally {
			apiErrorMapperDao.releaseHibernateSession(session);
		}
	}

	@Override
	@Transactional
	public void deleteApiErrorMapper(String id) throws Exception {
		apiErrorMapperDao.removeById(id);
	}

}
