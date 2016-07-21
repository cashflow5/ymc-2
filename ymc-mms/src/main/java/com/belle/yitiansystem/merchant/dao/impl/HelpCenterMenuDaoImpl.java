package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.IHelpCenterMenuDao;
import com.belle.yitiansystem.merchant.model.pojo.HelpCenterMenu;

@Repository
public class HelpCenterMenuDaoImpl extends HibernateEntityDao<HelpCenterMenu> implements IHelpCenterMenuDao {

}
