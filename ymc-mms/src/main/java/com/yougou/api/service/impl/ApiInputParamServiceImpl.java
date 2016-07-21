package com.yougou.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yougou.api.dao.IApiDao;
import com.yougou.api.dao.IApiInputParamDao;
import com.yougou.api.dao.IApiVersionDao;
import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiInputParam;
import com.yougou.api.model.pojo.ApiVersion;
import com.yougou.api.model.pojo.ApiVersionInputParam;
import com.yougou.api.model.pojo.InputParam;
import com.yougou.api.service.IApiInputParamService;

/**
 * 
 * @author 杨梦清
 * 
 */
@Service
public class ApiInputParamServiceImpl implements IApiInputParamService {

	@Resource
	private IApiDao apiDao;
	
	@Resource
	private IApiVersionDao apiVersionDao;
	
	@Resource
	private IApiInputParamDao apiInputParamDao;
	
	@Override
	public InputParam getApiInputParamById(InputParam inputParam) throws Exception {
		return apiInputParamDao.getById(inputParam.getId());
	}

	@Override
	@Transactional
	public void saveApiInputParam(InputParam inputParam) throws Exception {
		if (ApiInputParam.class.isInstance(inputParam)) {
			Api api = inputParam.getReferAs(Api.class);
			if (api == null || (api = apiDao.getById(api.getId())) == null) {
				throw new IllegalArgumentException("无法匹配API输入参数引用的API对象!");
			}
		} else if (ApiVersionInputParam.class.isInstance(inputParam)) {
			ApiVersion apiVersion = inputParam.getReferAs(ApiVersion.class);
			if (apiVersion == null || (apiVersion = apiVersionDao.getById(apiVersion.getId())) == null) {
				throw new IllegalArgumentException("无法匹配API输入参数引用的API版本对象!");
			}
		}
		apiInputParamDao.save(inputParam);
	}

	@Override
	@Transactional
	public void updateApiInputParam(InputParam inputParam) throws Exception {
		Session session = null;
		try {
			session = apiInputParamDao.getHibernateSession();
			InputParam another = (InputParam) session.load(InputParam.class, inputParam.getId(), LockMode.UPGRADE);
			another.setParamName(inputParam.getParamName());
			another.setParamDataType(inputParam.getParamDataType());
			another.setIsRequired(inputParam.getIsRequired());
			another.setParamExampleData(inputParam.getParamExampleData());
			another.setParamDefaultData(inputParam.getParamDefaultData());
			another.setParamDescription(inputParam.getParamDescription());
			another.setModifier(inputParam.getModifier());
			another.setModified(inputParam.getModified());
			session.update(another);
		} finally {
			apiInputParamDao.releaseHibernateSession(session);
		}
	}

	@Override
	@Transactional
	public void deleteApiInputParamById(InputParam inputParam) throws Exception {
		apiInputParamDao.removeById(inputParam.getId());
	}

	@Override
	public List<Integer> getUsableOrderNoList(InputParam inputParam, int upperLimit) throws Exception {
		Session session = null;
		try {
			List<Integer> usableOrderNoList = new ArrayList<Integer>();
			for (int i = 1; i <= upperLimit; i++) {
				usableOrderNoList.add(i);
			}
			session = apiInputParamDao.getHibernateSession();
			Criteria criteria = session.createCriteria(InputParam.class);
			criteria.setProjection(Projections.property("orderNo"));
			
			if (ApiInputParam.class.isInstance(inputParam)) {
				criteria.add(Restrictions.eq("api.id", inputParam.getReferAs(Api.class).getId()));
			} else if (ApiVersionInputParam.class.isInstance(inputParam)) {
				criteria.add(Restrictions.eq("apiVersion.id", inputParam.getReferAs(ApiVersion.class).getId()));
			}
			
			usableOrderNoList.removeAll(criteria.list());
			return usableOrderNoList;
		} finally {
			apiInputParamDao.releaseHibernateSession(session);
		}
	}

}

