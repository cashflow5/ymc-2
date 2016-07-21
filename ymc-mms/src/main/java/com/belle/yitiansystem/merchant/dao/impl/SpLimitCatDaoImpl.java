package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.ISpLimitCatDao;
import com.belle.yitiansystem.merchant.model.pojo.SpLimitCat;

@Repository
public class SpLimitCatDaoImpl extends HibernateEntityDao<SpLimitCat>  implements ISpLimitCatDao{

}
