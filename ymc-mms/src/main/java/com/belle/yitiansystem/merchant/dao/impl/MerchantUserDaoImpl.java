package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.stereotype.Repository;
import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.IMerchantUserDao;
import com.belle.yitiansystem.merchant.model.pojo.MerchantUser;

/**
 * 商家登录信息Dao层
 * @author wang.m
 *
 */
@Repository
public class MerchantUserDaoImpl extends HibernateEntityDao< MerchantUser>  implements IMerchantUserDao{

}
