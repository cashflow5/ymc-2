package com.belle.yitiansystem.systemmgmt.model.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 数据权限类型
 * @author zhubin
 * date：2011-12-20 下午3:55:29
 */
@Entity
@Table(name="tbl_data_object")
public class PermissionDataObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String dateType;
	
	private String dataName;
	
	private String dataValue;
	
	private String remark;
	
	private Date  createDate;
	
	private int deleteFlag;
	
	private UserPermissionGroup userPermissionGroup;

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
	@Column(name = "data_type", length = 20)
	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	@Column(name = "data_name", length = 50)
	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	@Column(name = "data_value", length = 200)
	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	@Column(name = "data_remark", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_group_id")
	public UserPermissionGroup getUserPermissionGroup() {
		return userPermissionGroup;
	}

	public void setUserPermissionGroup(UserPermissionGroup userPermissionGroup) {
		this.userPermissionGroup = userPermissionGroup;
	}

	@Column(name = "delete_flag")
	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	
	
}
