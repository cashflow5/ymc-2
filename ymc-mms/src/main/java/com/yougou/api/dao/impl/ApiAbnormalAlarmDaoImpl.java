package com.yougou.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.dao.IApiAbnormalAlarmDao;
import com.yougou.api.model.pojo.ApiAbnormalAlarm;

/**
 * 
 * @author 杨梦清
 * 
 */
@Repository
public class ApiAbnormalAlarmDaoImpl extends HibernateEntityDao<ApiAbnormalAlarm> implements IApiAbnormalAlarmDao {

}

