package com.belle.yitiansystem.systemmgmt.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.systemmgmt.dao.IPermissionDataObjectDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.PermissionDataObject;

@Repository
public class PermissionDataObjectDaoImpl extends HibernateEntityDao<PermissionDataObject> implements IPermissionDataObjectDao{

}
