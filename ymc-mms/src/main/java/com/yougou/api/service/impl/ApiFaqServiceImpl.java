package com.yougou.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.yougou.api.dao.IApiCategoryDao;
import com.yougou.api.dao.IApiFaqDao;
import com.yougou.api.model.pojo.ApiCategory;
import com.yougou.api.model.pojo.ApiFaq;
import com.yougou.api.service.IApiFaqService;

@Service
public class ApiFaqServiceImpl implements IApiFaqService {

	@Resource
	private IApiFaqDao apiFaqDao;
	
	@Resource
	private IApiCategoryDao apiCategoryDao;
	
	@Override
	public ApiFaq getApiFaq(String id) throws Exception {
		return apiFaqDao.getById(id);
	}

	@Override
	@Transactional
	public void deleteApiFaq(String id) throws Exception {
		apiFaqDao.removeById(id);
	}

	@Override
	@Transactional
	public void saveApiFaq(ApiFaq apiFaq) throws Exception {
		apiFaq.setApiCategory(apiCategoryDao.getById(apiFaq.getApiCategory().getId()));
		apiFaqDao.saveObject(apiFaq);
	}

	@Override
	@Transactional
	public void updateApiFaq(ApiFaq apiFaq) throws Exception {
		Session session = null;
		try {
			session = apiFaqDao.getHibernateSession();
			ApiFaq another = (ApiFaq) session.load(ApiFaq.class, apiFaq.getId(), LockMode.UPGRADE);
			ApiCategory apiCategory = (ApiCategory) session.get(ApiCategory.class, apiFaq.getApiCategory().getId());
			another.setSubject(apiFaq.getSubject());
			another.setContent(apiFaq.getContent());
			another.setModifier(apiFaq.getModifier());
			another.setModified(apiFaq.getModified());
			another.setApiCategory(apiCategory);
			session.update(another);
		} finally {
			apiFaqDao.releaseHibernateSession(session);
		}
	}

	@Override
	public List<ApiFaq> queryAllApiFaq() throws Exception {
		return apiFaqDao.getAll();
	}

	@Override
	public PageFinder<ApiFaq> queryApiFaq(DetachedCriteria criteria, Query query) throws Exception {
		Session session = null;
		try {
			session = apiFaqDao.getHibernateSession();
			Criteria executableCriteria = criteria.getExecutableCriteria(session);
			return apiFaqDao.pagedByCriteria(executableCriteria, query.getPage(), query.getPageSize());
		} finally {
			apiFaqDao.releaseHibernateSession(session);
		}
	}
}
