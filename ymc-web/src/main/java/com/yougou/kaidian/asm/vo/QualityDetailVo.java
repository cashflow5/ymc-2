package com.yougou.kaidian.asm.vo;

/**
 * 质检明细VO
 * @author mei.jf
 *
 */
public class QualityDetailVo {
	//质检明细的id
    private String id;
    //质检类型
    private String quality_type;
	// 当前质检日期
	private String curDate;
	// 错误信息
	private String errorMessage;
	//订单号
	private String orderSubNo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuality_type() {
		return quality_type;
	}
	public void setQuality_type(String quality_type) {
		this.quality_type = quality_type;
	}
	public String getCurDate() {
		return curDate;
	}
	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getOrderSubNo() {
		return orderSubNo;
	}
	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
	}
 
}
