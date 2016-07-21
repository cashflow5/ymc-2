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
import com.yougou.api.dao.IApiFilterDao;
import com.yougou.api.model.pojo.ApiFilter;
import com.yougou.api.service.IApiFilterService;

/**
 * 
 * @author 杨梦清
 * 
 */
@Service
public class ApiFilterServiceImpl implements IApiFilterService {

	@Resource
	private IApiFilterDao apiFilterDao;

	@Override
	public ApiFilter getApiFilterByIdentifier(String identifier) throws Exception {
		return apiFilterDao.findUniqueBy("identifier", identifier);
	}

	@Override
	@Transactional
	public void deleteApiFilterByIdentifier(String identifier) throws Exception {
		apiFilterDao.remove(getApiFilterByIdentifier(identifier));
	}
	
	@Override
	@Transactional
	public void saveApiFilter(ApiFilter apiFilter) throws Exception {
		if (getApiFilterByIdentifier(apiFilter.getIdentifier()) != null) {
			throw new UnsupportedOperationException("API过滤器标示符[" + apiFilter.getIdentifier() + "]已经存在!");
		}
		apiFilterDao.save(apiFilter);
	}

	@Override
	@Transactional
	public void updateApiFilter(ApiFilter apiFilter) throws Exception {
		Session session = null;
		try {
			session = apiFilterDao.getHibernateSession();
			Criteria criteria = session.createCriteria(ApiFilter.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("identifier", apiFilter.getIdentifier()));
			criteria.add(Restrictions.ne("id", apiFilter.getId()));
			if (((Number) criteria.uniqueResult()).intValue() >= 1) {
				throw new UnsupportedOperationException("API过滤器标示符[" + apiFilter.getIdentifier() + "]已经存在!");
			}
			ApiFilter another = (ApiFilter) session.load(ApiFilter.class, apiFilter.getId(), LockMode.UPGRADE);
			another.setFilterClass(apiFilter.getFilterClass());
			another.setFilterRule(apiFilter.getFilterRule());
			another.setOrderNo(apiFilter.getOrderNo());
			another.setModifier(apiFilter.getModifier());
			another.setModified(apiFilter.getModified());
			another.setDescription(apiFilter.getDescription());
			session.update(another);
		} finally {
			apiFilterDao.releaseHibernateSession(session);
		}
	}

	@Override
	public PageFinder<ApiFilter> queryApiFilter(DetachedCriteria criteria, Query query) throws Exception {
		Session session = null;
		try {
			session = apiFilterDao.getHibernateSession();
			Criteria executableCriteria = criteria.getExecutableCriteria(session);
			return apiFilterDao.pagedByCriteria(executableCriteria, query.getPage(), query.getPageSize());
		} finally {
			apiFilterDao.releaseHibernateSession(session);
		}
	}

	@Override
	public List<Integer> getUsableOrderNoList(int upperLimit) throws Exception {
		Session session = null;
		try {
			List<Integer> usableOrderNoList = new ArrayList<Integer>();
			for (int i = 1; i <= upperLimit; i++) {
				usableOrderNoList.add(i);
			}
			session = apiFilterDao.getHibernateSession();
			Criteria criteria = session.createCriteria(ApiFilter.class);
			criteria.setProjection(Projections.property("orderNo"));
			usableOrderNoList.removeAll(criteria.list());
			return usableOrderNoList;
		} finally {
			apiFilterDao.releaseHibernateSession(session);
		}
	}
}

