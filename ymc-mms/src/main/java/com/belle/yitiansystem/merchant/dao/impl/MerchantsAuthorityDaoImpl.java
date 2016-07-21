package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.IMerchantsAuthorityDao;
import com.belle.yitiansystem.merchant.model.pojo.MerchantsAuthority;

/**
 * 商家权限资源dao层
 * @author wang.m
 *
 */
@Repository
public class MerchantsAuthorityDaoImpl extends HibernateEntityDao<MerchantsAuthority> implements IMerchantsAuthorityDao {

}
