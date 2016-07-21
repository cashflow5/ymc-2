package com.belle.other.service;

import java.util.List;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.other.model.pojo.SupplierSp;
import com.belle.yitiansystem.merchant.model.vo.MerchantsVo;
import com.yougou.merchant.api.supplier.vo.SupplierQueryVo;
import com.yougou.merchant.api.supplier.vo.SupplierVo;
import com.yougou.merchant.api.common.Query;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-14 上午10:01:46
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public interface ISupplierSpLocalService {
	public PageFinder<SupplierSp> querySupplierListByPage(SupplierQueryVo vo,
			Query query, MerchantsVo merchantsVo);

	public PageFinder<SupplierVo> querySupplierListByPage(SupplierQueryVo vo,
			Query query);

	public String getSupplierNameById(String supplierId);
	/**为了导出，不分页查询 */
	public List<SupplierSp> querySupplierList(SupplierQueryVo vo,
			MerchantsVo merchantsVo);

	public int countTotalRemind(SupplierQueryVo vo);
}
