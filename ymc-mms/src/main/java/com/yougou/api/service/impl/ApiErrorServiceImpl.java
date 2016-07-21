package com.yougou.api.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.yougou.api.dao.IApiDao;
import com.yougou.api.dao.IApiErrorDao;
import com.yougou.api.model.pojo.ApiError;
import com.yougou.api.service.IApiErrorService;

/**
 * 
 * @author 杨梦清
 * 
 */
@Service
public class ApiErrorServiceImpl implements IApiErrorService {

	@Resource
	private IApiDao apiDao;

	@Resource
	private IApiErrorDao apiErrorDao;

	@Override
	public ApiError getApiErrorById(String id) throws Exception {
		return apiErrorDao.getById(id);
	}

	@Override
	@Transactional
	public void deleteApiErrorById(String id) throws Exception {
		apiErrorDao.removeById(id);
	}

	@Override
	@Transactional
	public void saveApiError(ApiError apiError) throws Exception {
		Session session = null;
		try {
			session = apiErrorDao.getHibernateSession();
			Criteria criteria = session.createCriteria(ApiError.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("errorCode", apiError.getErrorCode()));
			if (((Number) criteria.uniqueResult()).intValue() >= 1) {
				throw new UnsupportedOperationException("API错误码[" + apiError.getErrorCode() + "]已经存在!");
			}
			session.save(apiError);
		} finally {
			apiErrorDao.releaseHibernateSession(session);
		}
	}

	@Override
	@Transactional
	public void updateApiError(ApiError apiError) throws Exception {
		Session session = null;
		try {
			session = apiErrorDao.getHibernateSession();
			Criteria criteria = session.createCriteria(ApiError.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("errorCode", apiError.getErrorCode()));
			criteria.add(Restrictions.ne("id", apiError.getId()));
			if (((Number) criteria.uniqueResult()).intValue() >= 1) {
				throw new UnsupportedOperationException("API错误码[" + apiError.getErrorCode() + "]已经存在!");
			}
			ApiError another = (ApiError) session.load(ApiError.class, apiError.getId(), LockMode.UPGRADE);
			another.setErrorCode(apiError.getErrorCode());
			another.setErrorDescription(apiError.getErrorDescription());
			another.setErrorSolution(apiError.getErrorSolution());
			another.setOrderNo(apiError.getOrderNo());
			another.setModifier(apiError.getModifier());
			another.setModified(apiError.getModified());
			session.update(another);
		} finally {
			apiErrorDao.releaseHibernateSession(session);
		}
	}

	@Override
	public List<Integer> getUsableOrderNoList(ApiError apiError, int upperLimit) throws Exception {
		Session session = null;
		try {
			List<Integer> usableOrderNoList = new ArrayList<Integer>();
			for (int i = 1; i <= upperLimit; i++) {
				usableOrderNoList.add(i);
			}
			session = apiErrorDao.getHibernateSession();
			Criteria criteria = session.createCriteria(ApiError.class);
			criteria.setProjection(Projections.property("orderNo"));
			usableOrderNoList.removeAll(criteria.list());
			return usableOrderNoList;
		} finally {
			apiErrorDao.releaseHibernateSession(session);
		}
	}

	@Override
	public PageFinder<ApiError> queryApiError(DetachedCriteria criteria, Query query) throws Exception {
		Session session = null;
		try {
			session = apiErrorDao.getHibernateSession();
			Criteria executableCriteria = criteria.getExecutableCriteria(session);
			return apiErrorDao.pagedByCriteria(executableCriteria, query.getPage(), query.getPageSize());
		} finally {
			apiErrorDao.releaseHibernateSession(session);
		}
	}

}
