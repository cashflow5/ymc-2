/**
 * 
 */
package com.yougou.kaidian.asm.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 售后质检明细对象(新_tmall风格)
 * 
 * @author huang.tao
 * @date 2013-12-19 14:56:00 
 */
public class AsmQcDetailVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	//订单号
	private String orderNo;
	//商品名称
	private String prodName;
	//商品图片url
	private String picUrl;
	//商品优购url
	private String commodityUrl;
	//货品编号
	private String prodNo;
	//商家货品条码
	private String insideCode;
	//商家款色编码
	private String supplierCode;
	//售后申请单号
	private String applyNo;
	//售后申请时间
	private Date applyDate;
	
	//物流公司
	private String expressCode;
	private String expressName;
	
	//质检人
	private String qaPerson;
	//质检时间
	private Date qaDate;
	//是否异常收货
	private String isException;
	//质检状态
	private String status;
	
	//质检类型 （拒收|退货|换货）
	private String qualityType;
	
	//是否质检通过
	private String isPass;
	//快递费用
	private String expressCharges;
	//快递是否到付
	private String cashOnDelivery;
	//质检描述
	private String qaDescription;
	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getProdNo() {
		return prodNo;
	}

	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
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

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
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

	public String getQaPerson() {
		return qaPerson;
	}

	public void setQaPerson(String qaPerson) {
		this.qaPerson = qaPerson;
	}

	public Date getQaDate() {
		return qaDate;
	}

	public void setQaDate(Date qaDate) {
		this.qaDate = qaDate;
	}

	public String getIsException() {
		return isException;
	}

	public void setIsException(String isException) {
		this.isException = isException;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getQualityType() {
		return qualityType;
	}

	public void setQualityType(String qualityType) {
		this.qualityType = qualityType;
	}

	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}

    public String getCommodityUrl() {
        return commodityUrl;
    }

    public void setCommodityUrl(String commodityUrl) {
        this.commodityUrl = commodityUrl;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getExpressCharges() {
        return expressCharges;
    }

    public void setExpressCharges(String expressCharges) {
        this.expressCharges = expressCharges;
    }

    public String getCashOnDelivery() {
        return cashOnDelivery;
    }

    public void setCashOnDelivery(String cashOnDelivery) {
        this.cashOnDelivery = cashOnDelivery;
    }

    public String getQaDescription() {
        return qaDescription;
    }

    public void setQaDescription(String qaDescription) {
        this.qaDescription = qaDescription;
    }
	
	
}
