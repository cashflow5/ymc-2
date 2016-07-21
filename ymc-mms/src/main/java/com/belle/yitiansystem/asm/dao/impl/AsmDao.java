package com.belle.yitiansystem.asm.dao.impl;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.asm.dao.IAsmDao;
import com.belle.yitiansystem.asm.model.pojo.WmsReturnQaProductDetail;

@Repository
public class AsmDao extends HibernateEntityDao<WmsReturnQaProductDetail> implements IAsmDao{

	public HibernateTemplate getTemplate() {
		return getHibernateTemplate();
	}
}
