package com.yougou.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yougou.api.dao.IApiInputParamDao;
import com.yougou.api.dao.IApiInputParamMetadataDao;
import com.yougou.api.dao.IApiValidatorDao;
import com.yougou.api.model.pojo.ApiInputParamMetadata;
import com.yougou.api.model.pojo.ApiValidator;
import com.yougou.api.model.pojo.InputParam;
import com.yougou.api.service.IApiInputParamMetadataService;

/**
 * 
 * @author 杨梦清
 * 
 */
@Service
public class ApiInputParamMetadataServiceImpl implements IApiInputParamMetadataService {

	@Resource
	private IApiInputParamMetadataDao apiInputParamMetadataDao;
	
	@Resource
	private IApiValidatorDao apiValidatorDao;
	
	@Resource
	private IApiInputParamDao apiInputParamDao;
	
	@Override
	public ApiInputParamMetadata getApiInputParamMetadataById(String id) throws Exception {
		return apiInputParamMetadataDao.getById(id);
	}

	@Override
	@Transactional
	public void deleteApiInputParamMetadataById(String id) throws Exception {
		apiInputParamMetadataDao.removeById(id);
	}

	@Override
	@Transactional
	public void saveApiInputParamMetadata(ApiInputParamMetadata apiInputParamMetadata) throws Exception {
		checkAndPrepare(apiInputParamMetadata);
		apiInputParamMetadataDao.save(apiInputParamMetadata);
	}

	@Override
	@Transactional
	public void updateApiInputParamMetadata(ApiInputParamMetadata apiInputParamMetadata) throws Exception {
		Session session = null;
		try {
			checkAndPrepare(apiInputParamMetadata);
			session = apiInputParamMetadataDao.getHibernateSession();
			ApiInputParamMetadata another = (ApiInputParamMetadata) session.load(ApiInputParamMetadata.class, apiInputParamMetadata.getId(), LockMode.UPGRADE);
			another.setExpression(apiInputParamMetadata.getExpression());
			another.setCaseSensitive(apiInputParamMetadata.getCaseSensitive());
			another.setTrim(apiInputParamMetadata.getTrim());
			another.setMinValue(apiInputParamMetadata.getMinValue());
			another.setMaxValue(apiInputParamMetadata.getMaxValue());
			another.setMinLength(apiInputParamMetadata.getMinLength());
			another.setMaxLength(apiInputParamMetadata.getMaxLength());
			another.setModifier(apiInputParamMetadata.getModifier());
			another.setModified(apiInputParamMetadata.getModified());
			session.update(another);
		} finally {
			apiInputParamMetadataDao.releaseHibernateSession(session);
		}
	}
	
	private void checkAndPrepare(ApiInputParamMetadata apiInputParamMetadata) throws Exception {
		InputParam inputParam = apiInputParamMetadata.getInputParam();
		if (inputParam == null || (inputParam = apiInputParamDao.getById(inputParam.getId())) == null) {
			throw new IllegalArgumentException("无法匹配API验证器链条引用的API输入参数对象!");
		}
		ApiValidator apiValidator = apiInputParamMetadata.getApiValidator();
		if (apiValidator == null || (apiValidator = apiValidatorDao.findUniqueBy("validatorClass", apiValidator.getValidatorClass())) == null) {
			throw new IllegalArgumentException("无法匹配API验证器链条引用的API验证器对象!");
		}
		Session session = null;
		try {
			session = apiInputParamDao.getHibernateSession();
			Criteria criteria = session.createCriteria(ApiInputParamMetadata.class);
			criteria.add(Restrictions.eq("apiValidator", apiValidator));
			criteria.add(Restrictions.eq("inputParam", inputParam));
			criteria.setProjection(Projections.rowCount());
			if (((Number) criteria.uniqueResult()).intValue() == 1) {
				throw new IllegalArgumentException("已为该输入参数注册引用的API验证器对象!");
			}
		} finally {
			apiInputParamDao.releaseHibernateSession(session);
		}
		apiInputParamMetadata.setInputParam(inputParam);
		apiInputParamMetadata.setApiValidator(apiValidator);
	}

	@Override
	public List<Integer> getUsableOrderNoList(String refId, int upperLimit) throws Exception {
		Session session = null;
		try {
			List<Integer> usableOrderNoList = new ArrayList<Integer>();
			for (int i = 1; i <= upperLimit; i++) {
				usableOrderNoList.add(i);
			}
			
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append(" select ");
			sqlBuilder.append(" t2.order_no ");
			sqlBuilder.append(" from tbl_merchant_api_input_param t1 ");
			sqlBuilder.append(" inner join tbl_merchant_api_input_param_metadata t2 on(t1.id = t2.input_param_id) ");
			sqlBuilder.append(" where ");
			sqlBuilder.append(" t1.ref_id = ? ");
			session = apiInputParamDao.getHibernateSession();
			SQLQuery sqlQuery = session.createSQLQuery(sqlBuilder.toString());
			sqlQuery.setParameter(0, refId);
			usableOrderNoList.removeAll(sqlQuery.list());
			return usableOrderNoList;
		} finally {
			apiInputParamDao.releaseHibernateSession(session);
		}
	}
}

