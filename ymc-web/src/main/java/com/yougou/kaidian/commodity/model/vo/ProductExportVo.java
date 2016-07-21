/**
 * 
 */
package com.yougou.kaidian.commodity.model.vo;

/**
 * 货品Vo
 * 
 * @author huang.tao
 *
 */
public class ProductExportVo {
	
	/** 款色编码 */
	private String supplierCode;
	
	/** 尺码 */
	private String sizeName;
	
	/** 第三方条码 *//**商家款色编码*/
	private String thirdPartyCode;

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	public String getThirdPartyCode() {
		return thirdPartyCode;
	}

	public void setThirdPartyCode(String thirdPartyCode) {
		this.thirdPartyCode = thirdPartyCode;
	}
	
}
