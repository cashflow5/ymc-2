package com.yougou.kaidian.user.model.pojo;

/**
 * 用户资源中间表
 * @author wang.M
 * @Date 2012-03-22
 *
 */
public class UserAuthority {

	private String id;//主键ID
	
	private String userId;//用户Id
	
	private String authorityId;//权限ID
	
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

	public String getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
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

	public UserAuthority(String id, String userId, String authorityId,
			String createDate, String remark) {
		super();
		this.id = id;
		this.userId = userId;
		this.authorityId = authorityId;
		this.createDate = createDate;
		this.remark = remark;
	}

	public UserAuthority() {
		super();
	}
	
	
}
