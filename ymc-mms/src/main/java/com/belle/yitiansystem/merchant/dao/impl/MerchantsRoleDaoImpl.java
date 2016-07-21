package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.IMerchantsRoleDao;
import com.belle.yitiansystem.merchant.model.pojo.MerchantsRole;

/**
 * 商家角色Dao层
 * @author wang.m
 *
 */
@Repository
public class MerchantsRoleDaoImpl extends HibernateEntityDao< MerchantsRole>  implements IMerchantsRoleDao{

}
