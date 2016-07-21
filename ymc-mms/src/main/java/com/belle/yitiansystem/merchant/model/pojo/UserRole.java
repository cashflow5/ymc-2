package com.belle.yitiansystem.merchant.model.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 人员角色中间表
 * @author wang.M
 * @Date 2012-03-22
 *
 */
@Entity
@Table(name = "tbl_merchant_user_role")
public class UserRole {

	private String id;//主键ID
	
	private String userId;//用户Id
	
	private String roleId;//权限ID
	
	private Date createDate;//创建时间
	
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
	@Column(name = "user_id", length = 32)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}
	@Column(name = "role_id", length = 32)
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name = "remark", length = 50)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
