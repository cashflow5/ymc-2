package com.yougou.api.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yougou.api.dao.IApiVisitCounterDao;
import com.yougou.api.model.pojo.ApiVisitCounter;
import com.yougou.api.service.IApiVisitCounterService;

@Service
public class ApiVisitCounterServiceImpl implements IApiVisitCounterService {

	@Resource
	private IApiVisitCounterDao apiVisitCounterDao;
	
	@Override
	@Transactional
	public void createOrUpdateApiVisitCounter(String visitorIp) throws Exception {
		Session session = null;
		try {
			session = apiVisitCounterDao.getHibernateSession();
			Criteria criteria = session.createCriteria(ApiVisitCounter.class);
			criteria.setLockMode(LockMode.UPGRADE);
			criteria.add(Restrictions.eq("visitorIp", visitorIp));
			ApiVisitCounter apiVisitCounter = (ApiVisitCounter) criteria.uniqueResult();
			if (apiVisitCounter == null) {
				apiVisitCounter = new ApiVisitCounter();
				apiVisitCounter.setVisitor(visitorIp);
				apiVisitCounter.setVisitorIp(visitorIp);
				apiVisitCounter.setVisitCount(NumberUtils.INTEGER_ONE);
				apiVisitCounter.setVisitTimestamp(System.currentTimeMillis());
			} else {
				apiVisitCounter.setVisitCount(apiVisitCounter.getVisitCount() + 1);
			}
			apiVisitCounterDao.saveObject(apiVisitCounter);
		} finally {
			apiVisitCounterDao.releaseHibernateSession(session);
		}
	}

	@Override
	public boolean isVisitsLimitExceeded(String visitorIp, int visitCount) throws Exception {
		Session session = null;
		try {
			session = apiVisitCounterDao.getHibernateSession();
			Criteria criteria = session.createCriteria(ApiVisitCounter.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("visitorIp", visitorIp));
			criteria.add(Restrictions.gt("visitCount", visitCount));
			return ((Number) criteria.uniqueResult()).intValue() != 0;
		} finally {
			apiVisitCounterDao.releaseHibernateSession(session);
		}
	}

	@Override
	@Transactional
	public void deleteApiVisitCounter(String visitorIp, long visitTimestamp) throws Exception {
		Session session = null;
		try {
			session = apiVisitCounterDao.getHibernateSession();
			Criteria criteria = session.createCriteria(ApiVisitCounter.class);
			criteria.setProjection(Projections.property("id"));
			criteria.add(Restrictions.eq("visitorIp", visitorIp));
			criteria.add(Restrictions.lt("visitTimestamp", visitTimestamp));
			Object id = criteria.uniqueResult();
			if (id != null) {
				apiVisitCounterDao.removeById(id.toString());
			}
		} finally {
			apiVisitCounterDao.releaseHibernateSession(session);
		}
	}
}
