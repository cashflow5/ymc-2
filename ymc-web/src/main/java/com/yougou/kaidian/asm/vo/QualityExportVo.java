/** 
 * Project Name:ymc-web 
 * File Name:QualityExportVo.java 
 * Package Name:com.yougou.kaidian.asm.vo 
 * Date:2014-9-5下午3:34:20 
 */   
  
package com.yougou.kaidian.asm.vo;  

import java.io.Serializable;

/** 
 * ClassName: QualityExportVo
 * Desc: 质检导出vo
 * date: 2014-9-5 下午3:34:20
 * @author li.n1 
 * @since JDK 1.6 
 */
public class QualityExportVo implements Serializable {
private static final long serialVersionUID = 1L;
	//订单号
	private String orderNo;
	//物流公司
	private String expressCode;
	private String expressName;
	//商品名称
	private String prodName;
	//商家货品条码
	private String insideCode;
	//商家款色编码
	private String supplierCode;
	//质检状态
	private String status;
	//收货人
	private String userName;
	//售后类型 （拒收|退货|换货）
	private String saleType;
	//退换货原因
	private String remark;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getExpressCode() {
		return expressCode;
	}
	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getInsideCode() {
		return insideCode;
	}
	public void setInsideCode(String insideCode) {
		this.insideCode = insideCode;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
