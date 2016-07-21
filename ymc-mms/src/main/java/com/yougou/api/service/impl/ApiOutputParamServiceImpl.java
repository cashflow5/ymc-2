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

import com.yougou.api.dao.IApiOutputParamDao;
import com.yougou.api.model.pojo.ApiOutputParam;
import com.yougou.api.model.pojo.ApiVersionOutputParam;
import com.yougou.api.model.pojo.OutputParam;
import com.yougou.api.service.IApiOutputParamService;

/**
 * 
 * @author 杨梦清
 * 
 */
@Service
public class ApiOutputParamServiceImpl implements IApiOutputParamService {

	@Resource
	private IApiOutputParamDao apiOutputParamDao;
	
	@Override
	public OutputParam getApiOutputParamById(OutputParam outputParam) throws Exception {
		return apiOutputParamDao.getById(outputParam.getId());
	}

	@Override
	@Transactional
	public void saveApiOutputParam(OutputParam outputParam) throws Exception {
		apiOutputParamDao.save(outputParam);
	}

	@Override
	@Transactional
	public void updateApiOutputParam(OutputParam outputParam) throws Exception {
		Session session = null;
		try {
			session = apiOutputParamDao.getHibernateSession();
			OutputParam another = (OutputParam) session.load(OutputParam.class, outputParam.getId(), LockMode.UPGRADE);
			another.setParamName(outputParam.getParamName());
			another.setParamDataType(outputParam.getParamDataType());
			another.setIsRequired(outputParam.getIsRequired());
			another.setParamExampleData(outputParam.getParamExampleData());
			another.setParamDescription(outputParam.getParamDescription());
			another.setOrderNo(outputParam.getOrderNo());
			another.setModifier(outputParam.getModifier());
			another.setModified(outputParam.getModified());
			session.update(another);
		} finally {
			apiOutputParamDao.releaseHibernateSession(session);
		}
	}

	@Override
	@Transactional
	public void deleteApiOutputParamById(OutputParam outputParam) throws Exception {
		apiOutputParamDao.removeById(outputParam.getId());
	}

	@Override
	public List<Integer> getUsableOrderNoList(OutputParam outputParam, int upperLimit) throws Exception {
		Session session = null;
		try {
			List<Integer> usableOrderNoList = new ArrayList<Integer>();
			for (int i = 1; i <= upperLimit; i++) {
				usableOrderNoList.add(i);
			}
			session = apiOutputParamDao.getHibernateSession();
			Criteria criteria = session.createCriteria(OutputParam.class);
			criteria.setProjection(Projections.property("orderNo"));
			
			if (ApiOutputParam.class.isInstance(outputParam)) {
				criteria.add(Restrictions.eq("api.id", ((ApiOutputParam) outputParam).getApi().getId()));
			} else if (ApiVersionOutputParam.class.isInstance(outputParam)) {
				criteria.add(Restrictions.eq("apiVersion.id", ((ApiVersionOutputParam) outputParam).getApiVersion().getId()));
			}
			
			usableOrderNoList.removeAll(criteria.list());
			return usableOrderNoList;
		} finally {
			apiOutputParamDao.releaseHibernateSession(session);
		}
	}
}

