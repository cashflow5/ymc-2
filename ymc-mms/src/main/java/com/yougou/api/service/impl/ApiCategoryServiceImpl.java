package com.yougou.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
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
import com.yougou.api.dao.IApiCategoryDao;
import com.yougou.api.model.pojo.ApiCategory;
import com.yougou.api.service.IApiCategoryService;

/**
 * 
 * @author 杨梦清
 * 
 */
@Service
public class ApiCategoryServiceImpl implements IApiCategoryService {

	@Resource
	private IApiCategoryDao apiCategoryDao;
	
	@Override
	public List<ApiCategory> queryAllApiCategory() throws Exception {
		return apiCategoryDao.getAll("created", false);
	}

	@Override
	public ApiCategory getApiCategoryById(String id) throws Exception {
		return apiCategoryDao.findUniqueBy("id", id);
	}

	@Override
	public ApiCategory getApiCategoryByCode(String categoryCode) throws Exception {
		return apiCategoryDao.findUniqueBy("categoryCode", categoryCode);
	}
	
	@Override
	@Transactional
	public void saveApiCategory(ApiCategory apiCategory) throws Exception {
		if (getApiCategoryByCode(apiCategory.getCategoryCode()) != null) {
			throw new UnsupportedOperationException("API分类[" + apiCategory.getCategoryCode() + "]已经存在!");
		}
		apiCategoryDao.save(apiCategory);
	}

	@Override
	@Transactional
	public void updateApiCategory(ApiCategory apiCategory) throws Exception {
		Session session = null;
		try {
			session = apiCategoryDao.getHibernateSession();
			Criteria criteria = session.createCriteria(ApiCategory.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("categoryCode", apiCategory.getCategoryCode()));
			criteria.add(Restrictions.ne("id", apiCategory.getId()));
			if (((Number) criteria.uniqueResult()).intValue() >= 1) {
				throw new UnsupportedOperationException("API分类[" + apiCategory.getCategoryCode() + "]已经存在!");
			}
			ApiCategory persistence = (ApiCategory) session.load(ApiCategory.class, apiCategory.getId(), LockMode.UPGRADE);
			persistence.setCategoryName(apiCategory.getCategoryName());
			persistence.setCategoryDescription(apiCategory.getCategoryDescription());
			persistence.setOwnership(apiCategory.getOwnership());
			persistence.setModifier(apiCategory.getModifier());
			persistence.setModified(apiCategory.getModified());
			session.update(persistence);
		} finally {
			apiCategoryDao.releaseHibernateSession(session);
		}
	}

	@Override
	@Transactional
	public void deleteApiCategory(String id) throws Exception {
		ApiCategory apiCategory = getApiCategoryById(id);
		if (CollectionUtils.isNotEmpty(apiCategory.getApis())) {
			throw new UnsupportedOperationException("API分类包含API,请先删除API!"); 
		}
		apiCategoryDao.remove(apiCategory);
	}

	@Override
	public PageFinder<ApiCategory> queryApiCategory(DetachedCriteria criteria, Query query) throws Exception {
		Session session = null;
		try {
			session = apiCategoryDao.getHibernateSession();
			Criteria executableCriteria = criteria.getExecutableCriteria(session);
			return apiCategoryDao.pagedByCriteria(executableCriteria, query.getPage(), query.getPageSize());
		} finally {
			apiCategoryDao.releaseHibernateSession(session);
		}
	}

}

