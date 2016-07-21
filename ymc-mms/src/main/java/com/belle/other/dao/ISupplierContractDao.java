package com.belle.other.dao;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.other.model.pojo.SupplierContract;
import com.belle.other.model.vo.SupplierContractVo;
/**
 * 合同信息dao接口
 * @author zhuangruibo
 *
 */
public interface ISupplierContractDao  extends IHibernateEntityDao<SupplierContract>{

	public PageFinder<SupplierContractVo> querySupplierContract(SupplierContractVo vo, Query query) throws Exception;
}
