package com.yougou.kaidian.user.service;

import java.util.List;

import com.belle.finance.costsettlement.costsetofbooks.model.vo.CostSetofBooks;
import com.yougou.kaidian.user.model.pojo.SupplierContract;
import com.yougou.merchant.api.supplier.vo.MerchantUser;

/**
 * 商家的信息 ，合同等
 * @author le.sm
 *
 */
public interface IMerchantInfo {

	/**
	 * 获取当前 合同详情
	 * @param supplierId
	 * @return
	 */
	SupplierContract getSupplierContractBySupplierId(String supplierId);
	
	 MerchantUser getMerchantsBySuppliceCode(String supplierCode) ;
	 List<CostSetofBooks> getCostSetofBooksList() throws Exception;
}
