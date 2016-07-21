package com.belle.yitiansystem.mctnotice.dao.impl;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.mctnotice.dao.IMctNoticeDao;
import com.belle.yitiansystem.mctnotice.model.pojo.MctNotice;

@Repository
public class MctNoticeDao extends HibernateEntityDao<MctNotice> implements IMctNoticeDao{

	public HibernateTemplate getTemplate() {
		return getHibernateTemplate();
	}
}
