package com.belle.yitiansystem.asm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.other.service.ISqlService;
import com.belle.yitiansystem.asm.dao.IAsmDao;
import com.belle.yitiansystem.asm.model.pojo.WmsReturnQaProductDetail;
import com.belle.yitiansystem.asm.service.IAsmService;

@Service
public class AsmServiceImpl implements IAsmService {

	@Resource
	private IAsmDao asmDao;
	@Resource
	private ISqlService sqlService;
	
	@Transactional
	public int getApplyCount(String apply_no) throws Exception {
/*		Criteria criteria=asmDao.getHibernateSession().createCriteria(WmsReturnQaProductDetail.class);
		criteria.add(Restrictions.eq("apply_no", apply_no));
		return asmDao.getRowCount(criteria);*/
		String sql="select count(*) from tbl_wms_return_qa_product_detail d RIGHT JOIN tbl_wms_return_qa_product t on (t.id=d.return_qa_product_id and t.is_cancel is null) where d.apply_no=?";
		List<Object> params=new ArrayList<Object>();
		params.add(apply_no);
		return sqlService.getCountBySql(sql, null, params).intValue();
	}
}
