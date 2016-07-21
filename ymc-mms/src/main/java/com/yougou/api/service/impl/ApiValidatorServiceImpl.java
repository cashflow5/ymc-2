package com.yougou.api.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.yougou.api.dao.IApiValidatorDao;
import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiInputParam;
import com.yougou.api.model.pojo.ApiInputParamMetadata;
import com.yougou.api.model.pojo.ApiValidator;
import com.yougou.api.model.pojo.ApiVersion;
import com.yougou.api.model.pojo.ApiVersionInputParam;
import com.yougou.api.model.pojo.InputParam;
import com.yougou.api.service.IApiValidatorService;
import com.yougou.api.util.HibernateUtils;

/**
 * 
 * @author 杨梦清
 * 
 */
@Service
public class ApiValidatorServiceImpl implements IApiValidatorService {

	private static final String NEXT_IDENTIFIER_SQL = "select max(cast(identifier as decimal)) from tbl_merchant_api_validator";
	
	@Resource
	private IApiValidatorDao apiValidatorDao;
	
	@Override
	public String getNextIdentifier() throws Exception {
		Session session = null;
		try {
			session = apiValidatorDao.getHibernateSession();
			SQLQuery query = session.createSQLQuery(NEXT_IDENTIFIER_SQL);
			long identifier = ((Number) ObjectUtils.defaultIfNull(query.uniqueResult(), 0L)).longValue();
			return Long.toString(Math.min(Long.MAX_VALUE, identifier << 1));
		} finally {
			apiValidatorDao.releaseHibernateSession(session);
		}
	}

	@Override
	public ApiValidator getApiValidatorById(String id) throws Exception {
		return apiValidatorDao.getById(id);
	}

	@Override
	public ApiValidator getApiValidatorByIdentifier(String identifier) throws Exception {
		return apiValidatorDao.findUniqueBy("identifier", identifier);
	}

	@Override
	@Transactional
	public void deleteApiValidatorByIdentifier(String identifier) throws Exception {
		// 检查校验器是否有被其它实例引用(如：输入参数元数据)
		ApiValidator apiValidator = getApiValidatorByIdentifier(identifier);
		Set<ApiInputParamMetadata> apiInputParamMetadatas = apiValidator.getApiInputParamMetadatas();
		for (ApiInputParamMetadata apiInputParamMetadata : apiInputParamMetadatas) {
			InputParam inputParam = HibernateUtils.unwarp(apiInputParamMetadata.getInputParam());
			if (ApiVersionInputParam.class.isInstance(inputParam)) {
				ApiVersion apiVersion = ((ApiVersionInputParam) inputParam).getApiVersion();
				if (apiVersion != null) {
					throw new UnsupportedOperationException("API系统级参数[" + apiVersion.getVersionNo() + "@" + inputParam.getParamName() + "]引用该验证器,请先解除引用再进行操作!");
				}
			}
			if (ApiInputParam.class.isInstance(inputParam)) {
				Api api = ((ApiInputParam) inputParam).getApi();
				if (api != null) {
					throw new UnsupportedOperationException("API应用级参数[" + api.getApiMethod() + "@" + inputParam.getParamName() + "]引用该验证器,请先解除引用再进行操作!");
				}
			}
			throw new UnsupportedOperationException("未知[" + inputParam.getId() + "]引用该验证器,请先解除引用再进行操作!");
		}
		// 删除校验器
		apiValidatorDao.remove(apiValidator);
	}

	@Override
	@Transactional
	public void saveApiValidator(ApiValidator apiValidator) throws Exception {
		if (getApiValidatorByIdentifier(apiValidator.getIdentifier()) != null) {
			throw new UnsupportedOperationException("API校验器标示符[" + apiValidator.getIdentifier() +  "]已经存在!");
		}
		apiValidatorDao.save(apiValidator);

	}

	@Override
	@Transactional
	public void updateApiValidator(ApiValidator apiValidator) throws Exception {
		Session session = null;
		try {
			session = apiValidatorDao.getHibernateSession();
			Criteria criteria = session.createCriteria(ApiValidator.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("identifier", apiValidator.getIdentifier()));
			criteria.add(Restrictions.ne("id", apiValidator.getId()));
			if (((Number) criteria.uniqueResult()).intValue() >= 1) {
				throw new UnsupportedOperationException("API校验器标示符[" + apiValidator.getIdentifier() +  "]已经存在!");
			}
			
			ApiValidator another = (ApiValidator) session.load(ApiValidator.class, apiValidator.getId(), LockMode.UPGRADE);
			another.setMessageKey(apiValidator.getMessageKey());
			another.setMessagePattern(apiValidator.getMessagePattern());
			another.setValidatorClass(apiValidator.getValidatorClass());
			another.setDescription(apiValidator.getDescription());
			another.setModifier(apiValidator.getModifier());
			another.setModified(apiValidator.getModified());
			session.update(another);
		} finally {
			apiValidatorDao.releaseHibernateSession(session);
		}
	}

	@Override
	public PageFinder<ApiValidator> queryApiValidator(DetachedCriteria criteria, Query query) throws Exception {
		Session session = null;
		try {
			session = apiValidatorDao.getHibernateSession();
			Criteria executableCriteria = criteria.getExecutableCriteria(session);
			return apiValidatorDao.pagedByCriteria(executableCriteria, query.getPage(), query.getPageSize());
		} finally {
			apiValidatorDao.releaseHibernateSession(session);
		}
	}
}

