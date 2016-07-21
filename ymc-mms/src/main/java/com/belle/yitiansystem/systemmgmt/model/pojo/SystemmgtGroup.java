package com.belle.yitiansystem.systemmgmt.model.pojo;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * TblSystemmgtGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_systemmgt_group")
public class SystemmgtGroup implements java.io.Serializable {

	// Fields

	private String id;
	private String groupName;
	private Integer supplierid;
	private String no;
	private Set<SystemmgtUserGroup> systemmgtUserGroups = new HashSet<SystemmgtUserGroup>(
			0);

	// Constructors

	/** default constructor */
	public SystemmgtGroup() {
	}

	/** full constructor */
	public SystemmgtGroup(String groupName, Integer supplierid, String no,
			Set<SystemmgtUserGroup> systemmgtUserGroups) {
		this.groupName = groupName;
		this.supplierid = supplierid;
		this.no = no;
		this.systemmgtUserGroups = systemmgtUserGroups;
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

	@Column(name = "group_name", length = 100)
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "supplierid")
	public Integer getSupplierid() {
		return this.supplierid;
	}

	public void setSupplierid(Integer supplierid) {
		this.supplierid = supplierid;
	}

	@Column(name = "no", length = 40)
	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "systemmgtGroup")
	public Set<SystemmgtUserGroup> getSystemmgtUserGroups() {
		return systemmgtUserGroups;
	}

	public void setSystemmgtUserGroups(Set<SystemmgtUserGroup> systemmgtUserGroups) {
		this.systemmgtUserGroups = systemmgtUserGroups;
	}
}