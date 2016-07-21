package com.yougou.kaidian.user.model.vo;

import java.util.Date;

/**
 * 联系人实体类
 * @author wang.m
 */
public class ContractVo{

	private String id;
	/**
	 * 合同编号
	 */
	private String contractNo;
	/**
	 * 结算方式
	 */
	private Integer clearingForm;
	/**
	 * 生效时间
	 */
	private Date effectiveDate;
	/**
	 * 失效时间
	 */
	private Date failureDate;
	
	/**
	 * 附件名称
	 */
	private String attachment;
	
	private Integer status;//主表状态

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Integer getClearingForm() {
		return clearingForm;
	}

	public void setClearingForm(Integer clearingForm) {
		this.clearingForm = clearingForm;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getFailureDate() {
		return failureDate;
	}

	public void setFailureDate(Date failureDate) {
		this.failureDate = failureDate;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ContractVo(String id, String contractNo, Integer clearingForm,
			Date effectiveDate, Date failureDate, String attachment,
			Integer status) {
		super();
		this.id = id;
		this.contractNo = contractNo;
		this.clearingForm = clearingForm;
		this.effectiveDate = effectiveDate;
		this.failureDate = failureDate;
		this.attachment = attachment;
		this.status = status;
	}

	public ContractVo() {
		super();
	}
	

}
