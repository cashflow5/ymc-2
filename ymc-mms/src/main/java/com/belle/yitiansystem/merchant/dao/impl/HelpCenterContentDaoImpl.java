package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.IHelpCenterContentDao;
import com.belle.yitiansystem.merchant.model.pojo.HelpCenterContent;

@Repository
public class HelpCenterContentDaoImpl extends HibernateEntityDao<HelpCenterContent> implements IHelpCenterContentDao {

}
