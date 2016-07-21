package com.belle.yitiansystem.merchant.dao.impl;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.FeebackReplyDao;
import com.belle.yitiansystem.merchant.model.pojo.FeebackReply;

/**
 * 意见反馈
 * 
 * @author he.wc
 * 
 */
@Repository
public class FeebackReplyDaoImpl extends HibernateEntityDao<FeebackReply> implements FeebackReplyDao {

	public HibernateTemplate getTemplate() {
		return getHibernateTemplate();
	}

}
