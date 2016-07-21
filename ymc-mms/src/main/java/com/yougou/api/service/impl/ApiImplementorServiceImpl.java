package com.yougou.api.service.impl;

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
import com.yougou.api.dao.IApiImplementorDao;
import com.yougou.api.model.pojo.ApiImplementor;
import com.yougou.api.service.IApiImplementorService;

/**
 * 
 * @author 杨梦清
 * 
 */
@Service
public class ApiImplementorServiceImpl implements IApiImplementorService {

	@Resource
	private IApiImplementorDao apiImplementorDao;
	
	@Override
	public List<ApiImplementor> queryAllApiImplementor() throws Exception {
		return apiImplementorDao.getAll("created", false);
	}

	@Override
	@Transactional
	public void deleteApiImplementorByIdentifier(String identifier) throws Exception {
		apiImplementorDao.remove(getApiImplementorByIdentifier(identifier));
	}

	@Override
	public ApiImplementor getApiImplementorByIdentifier(String identifier) throws Exception {
		return apiImplementorDao.findUniqueBy("identifier", identifier);
	}

	@Override
	@Transactional
	public void saveApiImplementor(ApiImplementor apiImplementor) throws Exception {
		if (getApiImplementorByIdentifier(apiImplementor.getIdentifier()) != null) {
			throw new UnsupportedOperationException("API实现者标示符[" + apiImplementor.getIdentifier() + "]已经存在!");
		}
		apiImplementorDao.save(apiImplementor);
	}

	@Override
	@Transactional
	public void updateApiImplementor(ApiImplementor apiImplementor) throws Exception {
		Session session = null;
		try {
			session = apiImplementorDao.getHibernateSession();
			Criteria criteria = session.createCriteria(ApiImplementor.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("identifier", apiImplementor.getIdentifier()));
			criteria.add(Restrictions.ne("id", apiImplementor.getId()));
			if (((Number) criteria.uniqueResult()).intValue() >= 1) {
				throw new UnsupportedOperationException("API实现者标示符[" + apiImplementor.getIdentifier() + "]已经存在!");
			}
			ApiImplementor another = (ApiImplementor) session.load(ApiImplementor.class, apiImplementor.getId(), LockMode.UPGRADE);
			another.setImplementorClass(apiImplementor.getImplementorClass());
			another.setIsSpringManagedInstance(apiImplementor.getIsSpringManagedInstance());
			another.setDescription(apiImplementor.getDescription());
			another.setModifier(apiImplementor.getModifier());
			another.setModified(apiImplementor.getModified());
			session.update(another);
		} finally {
			apiImplementorDao.releaseHibernateSession(session);
		}
	}

	@Override
	public PageFinder<ApiImplementor> queryApiImplementor(DetachedCriteria criteria, Query query) throws Exception {
		Session session = null;
		try {
			session = apiImplementorDao.getHibernateSession();
			Criteria executableCriteria = criteria.getExecutableCriteria(session);
			return apiImplementorDao.pagedByCriteria(executableCriteria, query.getPage(), query.getPageSize());
		} finally {
			apiImplementorDao.releaseHibernateSession(session);
		}
	}
}
