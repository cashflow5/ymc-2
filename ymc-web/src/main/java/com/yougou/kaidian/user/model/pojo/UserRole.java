package com.yougou.kaidian.user.model.pojo;


/**
 * 人员权限中间表
 * @author wang.M
 * @Date 2012-03-22
 *
 */
public class UserRole {

	private String id;//主键ID
	
	private String userId;//用户Id
	
	private String roleId;//权限ID
	
	private String createDate;//创建时间
	
	private String remark;//备注

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	public UserRole() {
		super();
	}
	
	
}
