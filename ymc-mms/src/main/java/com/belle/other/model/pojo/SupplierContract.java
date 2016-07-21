package com.belle.other.model.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 合同信息实体类
 * @author zhuangruibo
 *
 */
@Entity
@Table(name = "tbl_sp_supplier_contract")
public class SupplierContract implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	/**
	 * 供应商
	 */
	private SupplierSp supplier;
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
	 * 更新时间
	 */
	private String updateTime;
	/**
	 * 操作员
	 */
	private String updateUser;
	/**
	 * 附件名称
	 */
	private String attachment;
	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "supplier_id")
	public SupplierSp getSupplier() {
		return this.supplier;
	}
	
	public void setSupplier(SupplierSp supplier) {
		this.supplier = supplier;
	}

	@Column(name = "contract_no", length = 50)
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "clearing_form")
	public Integer getClearingForm() {
		return clearingForm;
	}

	public void setClearingForm(Integer clearingForm) {
		this.clearingForm = clearingForm;
	}

	@Column(name = "effective_date")
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Column(name = "failure_date")
	public Date getFailureDate() {
		return failureDate;
	}

	public void setFailureDate(Date failureDate) {
		this.failureDate = failureDate;
	}

	@Column(name = "update_time")
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "update_user", length = 50)
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Column(name = "attachment", length = 100)
	public String getAttachment() {
		return attachment;
	}
	
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
}
