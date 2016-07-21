package com.belle.yitiansystem.mctnotice.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.yitiansystem.mctnotice.model.pojo.MctNotice;

public interface IMctNoticeDao extends IHibernateEntityDao<MctNotice>{

	HibernateTemplate getTemplate();
}
