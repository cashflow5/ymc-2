package com.yougou.api.service.impl;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yougou.api.dao.IApiDao;
import com.yougou.api.dao.IApiExampleDao;
import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiExample;
import com.yougou.api.service.IApiExampleService;

/**
 * 
 * @author 杨梦清
 * 
 */
@Service
public class ApiExampleServiceImpl implements IApiExampleService {

	@Resource
	private IApiDao apiDao;

	@Resource
	private IApiExampleDao apiExampleDao;
	
	@Override
	public ApiExample getApiExampleById(String id) throws Exception {
		return apiExampleDao.getById(id);
	}

	@Override
	@Transactional
	public void deleteApiExampleById(String id) throws Exception {
		apiExampleDao.removeById(id);
	}

	@Override
	@Transactional
	public void saveApiExample(ApiExample apiExample) throws Exception {
		Session session = null;
		try {
			Api api = apiExample.getApi();
			if (api == null || (api = apiDao.getById(api.getId())) == null) {
				throw new IllegalArgumentException("无法匹配API错误码引用的API对象!");
			}
			session = apiExampleDao.getHibernateSession();
			Criteria criteria = session.createCriteria(ApiExample.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("exampleCategory", apiExample.getExampleCategory()));
			criteria.add(Restrictions.eq("api.id", api.getId()));
			if (((Number) criteria.uniqueResult()).intValue() >= 1) {
				throw new UnsupportedOperationException("API输出示例数据分类[" + apiExample.getExampleCategory() + "]已经存在!");
			}
			apiExample.setApi(api);
			session.save(apiExample);
		} finally {
			apiExampleDao.releaseHibernateSession(session);
		}
	}

	@Override
	@Transactional
	public void updateApiExample(ApiExample apiExample) throws Exception {
		Session session = null;
		try {
			Api api = apiExample.getApi();
			if (api == null || (api = apiDao.getById(api.getId())) == null) {
				throw new IllegalArgumentException("无法匹配API错误码引用的API对象!");
			}
			session = apiExampleDao.getHibernateSession();
			Criteria criteria = session.createCriteria(ApiExample.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("exampleCategory", apiExample.getExampleCategory()));
			criteria.add(Restrictions.eq("api.id", api.getId()));
			criteria.add(Restrictions.ne("id", apiExample.getId()));
			if (((Number) criteria.uniqueResult()).intValue() >= 1) {
				throw new UnsupportedOperationException("API输出示例数据分类[" + apiExample.getExampleCategory() + "]已经存在!");
			}
			ApiExample another = (ApiExample) session.load(ApiExample.class, apiExample.getId(), LockMode.UPGRADE);
			another.setExampleCategory(apiExample.getExampleCategory());
			another.setExampleData(apiExample.getExampleData());
			another.setModifier(apiExample.getModifier());
			another.setModified(apiExample.getModified());
			session.update(another);
		} finally {
			apiExampleDao.releaseHibernateSession(session);
		}
	}

}
