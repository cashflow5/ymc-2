package com.belle.yitiansystem.asm.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;
import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.yitiansystem.asm.model.pojo.WmsReturnQaProductDetail;

public interface IAsmDao extends IHibernateEntityDao<WmsReturnQaProductDetail>{

	HibernateTemplate getTemplate();
}
