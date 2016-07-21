package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.IUserAuthorityDao;
import com.belle.yitiansystem.merchant.model.pojo.UserRole;

/**
 * 用户权限dao层
 * @author wang.m
 *
 */
@Repository
public class UserAuthorityDaoImpl  extends HibernateEntityDao<UserRole>  implements IUserAuthorityDao{

}
