package com.belle.yitiansystem.merchant.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 用户资源中间表
 * @author wang.M
 * @Date 2012-03-22
 *
 */
@Entity
@Table(name = "tbl_merchant_user_authority")
public class UserAuthority {

	private String id;//主键ID
	
	private String userId;//用户Id
	
	private String authorityId;//权限ID
	
	private String createDate;//创建时间
	
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
	
	@Column(name = "create_date", length = 30)
	public String getCreateDate() {
		return createDate;
	}
	@Column(name = "authority_id", length = 32)
	public String getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}

	public void setCreateDate(String createDate) {
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
