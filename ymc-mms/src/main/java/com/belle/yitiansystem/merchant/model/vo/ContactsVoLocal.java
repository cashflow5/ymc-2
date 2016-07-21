package com.belle.yitiansystem.merchant.model.vo;

import com.yougou.merchant.api.supplier.vo.ContactsVo;


/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-15 下午2:54:57
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class ContactsVoLocal extends ContactsVo {
	private String supplierYgContacts;

	public String getSupplierYgContacts() {
		return supplierYgContacts;
	}

	public void setSupplierYgContacts(String supplierYgContacts) {
		this.supplierYgContacts = supplierYgContacts;
	}
}
