/**
 * 
 */
package com.yougou.kaidian.asm.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 售后质检查询（新_tmall风格）
 * 
 * @author huang.tao
 * @date 2013-12-19 14:56:00 
 */
public class AsmQcVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//订单号
	private String orderNo;
	//收货人
	private String userName;
	//收货人联系方式
	private String mobilePhone;
	
	//质检明细
	private List<AsmQcDetailVo> details;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public List<AsmQcDetailVo> getDetails() {
		return details;
	}

	public void setDetails(List<AsmQcDetailVo> details) {
		this.details = details;
	}
	
	
}
