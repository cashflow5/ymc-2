package com.yougou.api.service.impl;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.yougou.api.dao.IApiLatestNewsDao;
import com.yougou.api.model.pojo.ApiLatestNews;
import com.yougou.api.service.IApiLatestNewsService;

@Service
public class ApiLatestNewsServiceImpl implements IApiLatestNewsService {

	@Resource
	private IApiLatestNewsDao apiLatestNewsDao;
	
	@Override
	public ApiLatestNews getApiLatestNews(String id) throws Exception {
		return apiLatestNewsDao.getById(id);
	}

	@Override
	@Transactional
	public void deleteApiLatestNews(String id) throws Exception {
		apiLatestNewsDao.removeById(id);
	}

	@Override
	@Transactional
	public void saveApiLatestNews(ApiLatestNews apiFaq) throws Exception {
		apiLatestNewsDao.saveObject(apiFaq);
	}

	@Override
	@Transactional
	public void updateApiLatestNews(ApiLatestNews apiFaq) throws Exception {
		Session session = null;
		try {
			session = apiLatestNewsDao.getHibernateSession();
			ApiLatestNews another = (ApiLatestNews) session.load(ApiLatestNews.class, apiFaq.getId(), LockMode.UPGRADE);
			another.setSubject(apiFaq.getSubject());
			another.setContent(apiFaq.getContent());
			another.setModifier(apiFaq.getModifier());
			another.setModified(apiFaq.getModified());
			session.update(another);
		} finally {
			apiLatestNewsDao.releaseHibernateSession(session);
		}
	}

	@Override
	public PageFinder<ApiLatestNews> queryApiLatestNews(DetachedCriteria criteria, Query query) throws Exception {
		Session session = null;
		try {
			session = apiLatestNewsDao.getHibernateSession();
			Criteria executableCriteria = criteria.getExecutableCriteria(session);
			return apiLatestNewsDao.pagedByCriteria(executableCriteria, query.getPage(), query.getPageSize());
		} finally {
			apiLatestNewsDao.releaseHibernateSession(session);
		}
	}
}
