package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.IRoleAuthorityDao;
import com.belle.yitiansystem.merchant.model.pojo.RoleAuthority;

@Repository
public class RoleAuthorityDaoImpl  extends HibernateEntityDao< RoleAuthority>  implements IRoleAuthorityDao{

}
