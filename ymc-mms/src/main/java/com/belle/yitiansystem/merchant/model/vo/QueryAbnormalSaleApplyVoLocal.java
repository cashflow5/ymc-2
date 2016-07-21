package com.belle.yitiansystem.merchant.model.vo;

import com.yougou.merchant.api.order.vo.QueryAbnormalSaleApplyVo;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-14 下午4:49:52
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class QueryAbnormalSaleApplyVoLocal extends QueryAbnormalSaleApplyVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String supplierYgContacts;

	public String getSupplierYgContacts() {
		return supplierYgContacts;
	}

	public void setSupplierYgContacts(String supplierYgContacts) {
		this.supplierYgContacts = supplierYgContacts;
	}

}
