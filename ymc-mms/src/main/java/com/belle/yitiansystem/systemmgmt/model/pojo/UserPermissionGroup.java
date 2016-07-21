package com.belle.yitiansystem.systemmgmt.model.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


/**
 * 用户权限小组
 * @author zhubin
 * date：2011-12-20 下午3:54:51
 */
@Entity
@Table(name="tbl_user_group")
public class UserPermissionGroup implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String groupName;
	
	private String groupRemark;
	
	private Date createDate;
	
	private Date updateDate;
	
	private Integer groupState;
	
	private Integer deleteFlag;

	private Set<PermissionDataObject> permissionDataObjects = new HashSet<PermissionDataObject>();
	
	private Set<SystemmgtUser> systemmgtUsers = new HashSet<SystemmgtUser>();
	
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
	@Column(name = "group_name", length = 20)
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@Column(name = "group_remark", length = 200)
	public String getGroupRemark() {
		return groupRemark;
	}

	public void setGroupRemark(String groupRemark) {
		this.groupRemark = groupRemark;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	@Column(name = "group_state")
	public Integer getGroupState() {
		return groupState;
	}

	public void setGroupState(Integer groupState) {
		this.groupState = groupState;
	}
	@Column(name = "delete_flag")
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "userPermissionGroup")
	public Set<PermissionDataObject> getPermissionDataObjects() {
		return permissionDataObjects;
	}

	public void setPermissionDataObjects(
			Set<PermissionDataObject> permissionDataObjects) {
		this.permissionDataObjects = permissionDataObjects;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "permissionGroup")
	public Set<SystemmgtUser> getSystemmgtUsers() {
		return systemmgtUsers;
	}

	public void setSystemmgtUsers(Set<SystemmgtUser> systemmgtUsers) {
		this.systemmgtUsers = systemmgtUsers;
	}
	
	
	
}
