package com.belle.yitiansystem.asm.model.vo;


import java.util.Date;

/**
 * 创建商家工单，向招商发送消息Vo
 * @author cao.jz
 */

public class SaleTraceVo {
	
	/**
	 * 子订单号
	 */
	private String orderSubNo;
	/**
	 * 工单号
	 */
	private String orderTraceNo;
	/**
	 * 问题归属
	 */
	private String secondProblem;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 问题描述
	 */
	private String issueDescription;
	
	@Override
	public String toString() {
		return "SaleTraceVo [orderSubNo=" + orderSubNo + ", orderTraceNo="
				+ orderTraceNo + ", secondProblem=" + secondProblem
				+ ", createTime=" + createTime + ", issueDescription="
				+ issueDescription + "]";
	}
	
	public String getOrderSubNo() {
		return orderSubNo;
	}
	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
	}
	public String getOrderTraceNo() {
		return orderTraceNo;
	}
	public void setOrderTraceNo(String orderTraceNo) {
		this.orderTraceNo = orderTraceNo;
	}
	public String getSecondProblem() {
		return secondProblem;
	}
	public void setSecondProblem(String secondProblem) {
		this.secondProblem = secondProblem;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getIssueDescription() {
		return issueDescription;
	}
	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}
	
	

}
