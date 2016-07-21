package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiInputParamMetadataDao;
import com.yougou.api.model.pojo.ApiInputParamMetadata;

/**
 * 
 * @author 杨梦清
 * 
 */
@Repository
public class ApiInputParamMetadataDaoImpl extends HibernateEntityDao<ApiInputParamMetadata> implements IApiInputParamMetadataDao {

}

