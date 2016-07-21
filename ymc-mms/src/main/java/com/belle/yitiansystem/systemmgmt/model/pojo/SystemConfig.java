package com.belle.yitiansystem.systemmgmt.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 */
@Entity
@Table(name = "tbl_systemmgt_config")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)   
public class SystemConfig implements java.io.Serializable {

	// Fields

	private String id;
	private String configName;  //名称
	private String key;			//键
	private String value;		//值
	private String remark;		//备注
	private String deleteFlag;	//删除标识
	
	/** default constructor */
	public SystemConfig() {
	}

	
	
	public SystemConfig(String id, String key, String value, String remark) {
		super();
		this.id = id;
		this.key = key;
		this.value = value;
		this.remark = remark;
	}





	// Property accessors
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


	@Column(name = "config_key")
	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	@Column(name = "config_value")
	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "delete_flag")
	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}


	@Column(name = "config_name")
	public String getConfigName() {
		return configName;
	}



	public void setConfigName(String configName) {
		this.configName = configName;
	}
	
	

	
}