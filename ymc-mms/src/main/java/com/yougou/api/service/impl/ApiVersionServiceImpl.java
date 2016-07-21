package com.yougou.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yougou.api.dao.IApiVersionDao;
import com.yougou.api.model.pojo.ApiVersion;
import com.yougou.api.service.IApiVersionService;

/**
 * 
 * @author 杨梦清
 * 
 */
@Service
public class ApiVersionServiceImpl implements IApiVersionService {

	@Resource
	private IApiVersionDao apiVersionDao;

	@Override
	public ApiVersion getApiVersionByNo(String versionNo) throws Exception {
		return apiVersionDao.findUniqueBy("versionNo", versionNo);
	}

	@Override
	public ApiVersion getApiVersionById(String versionId) throws Exception {
		return apiVersionDao.getById(versionId);
	}

	@Override
	@Transactional
	public void saveApiVersion(ApiVersion apiVersion) throws Exception {
		if (getApiVersionByNo(apiVersion.getVersionNo()) != null) {
			throw new UnsupportedOperationException("API版本[" + apiVersion.getVersionNo() + "]已经存在!");
		}
		apiVersionDao.save(apiVersion);
	}

	@Override
	@Transactional
	public void updateApiVersion(ApiVersion apiVersion) throws Exception {
		ApiVersion another = getApiVersionByNo(apiVersion.getVersionNo());
		if (another != null && !another.getId().equals(apiVersion.getId())) {
			throw new UnsupportedOperationException("API版本[" + apiVersion.getVersionNo() + "]已经存在!");
		}
		another.setVersionNo(apiVersion.getVersionNo());
		another.setDescription(apiVersion.getDescription());
		another.setModifier(apiVersion.getModifier());
		another.setModified(apiVersion.getModified());
		apiVersionDao.save(another);
	}

	@Override
	@Transactional
	public void deleteApiVersionById(String versionId) throws Exception {
		ApiVersion apiVersion = getApiVersionById(versionId);
		if (CollectionUtils.isNotEmpty(apiVersion.getApis())) {
			throw new UnsupportedOperationException("API版本包含API,请先删除API!"); 
		}
		apiVersionDao.remove(apiVersion);
	}

	@Override
	public List<ApiVersion> queryAllApiVersion() throws Exception {
		return apiVersionDao.getAll("created", false);
	}

}
