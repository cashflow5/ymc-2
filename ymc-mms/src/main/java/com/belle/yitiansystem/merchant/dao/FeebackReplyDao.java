package com.belle.yitiansystem.merchant.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.yitiansystem.merchant.model.pojo.FeebackReply;

/**
 * 意见反馈
 * @author he.wc
 *
 */
public interface FeebackReplyDao extends IHibernateEntityDao<FeebackReply> {
	
	/**
	 * 获取HibernateTemplate
	 * @return
	 */
	HibernateTemplate getTemplate();
	

}
