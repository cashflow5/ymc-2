package com.belle.yitiansystem.systemmgmt.dao.impl;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.systemmgmt.dao.IsystemmgtOperaterLogDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUserOperateLog;

/**
 * 用户操作日志记录业务Dao实现类
 * @author zhubin
 * date：2012-2-17 下午3:11:05
 */
@Repository
public class systemmgtOperaterLogDaoImpl extends HibernateEntityDao<SystemmgtUserOperateLog> implements IsystemmgtOperaterLogDao {
	
}
