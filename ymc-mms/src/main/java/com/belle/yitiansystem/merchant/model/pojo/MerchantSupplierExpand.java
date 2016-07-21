package com.belle.yitiansystem.merchant.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-10 下午4:51:17
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@Entity
@Table(name = "tbl_merchant_supplier_expand")
public class MerchantSupplierExpand {
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;

	@Column(name = "merchant_code")
	private String merchantCode;

	@Column(name = "yg_contact_user_id")
	private String YgContactUserId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getYgContactUserId() {
		return YgContactUserId;
	}

	public void setYgContactUserId(String ygContactUserId) {
		YgContactUserId = ygContactUserId;
	}

}
