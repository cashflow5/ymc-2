package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.IMerchantExpressTemplateDao;
import com.belle.yitiansystem.merchant.model.pojo.MerchantExpressTemplate;

/**
 * 发货模版dao类
 * @author wang.m
 * @DATE 2012-04-26
 */
@Repository
public class MerchantExpressTemplateDaoImpl  extends HibernateEntityDao<MerchantExpressTemplate> implements IMerchantExpressTemplateDao{

}
