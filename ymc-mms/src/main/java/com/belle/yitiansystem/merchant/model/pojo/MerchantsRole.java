package com.belle.yitiansystem.merchant.model.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * 商家列表菜单名称实体类
 * @author wang.m
 *
 */
@Entity
@Table(name = "tbl_merchant_Role")
public class MerchantsRole {

	private String id;
	
	private String roleName;//角色名称
	
	/**
	 * 操作人
	 */
	@Column(name = "operator")
	private String operator;
	
	/**
	 * 状态
	 */
	@Column(name = "status")
	private String status;
	
	private Date createTime;//创建时间
	
	private String remark;//备注

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "role_name",  length = 50)
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	@Column(name = "remark",  length = 50)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
