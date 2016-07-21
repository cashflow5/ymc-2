package com.belle.yitiansystem.systemmgmt.model.pojo;

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
 * TblSystemmgtUserGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_systemmgt_user_group")
public class SystemmgtUserGroup implements java.io.Serializable {

	// Fields

	private String id;
	private SystemmgtGroup systemmgtGroup;
	private SystemmgtUser systemmgtUser;

	// Constructors

	/** default constructor */
	public SystemmgtUserGroup() {
	}

	/** full constructor */
	public SystemmgtUserGroup(SystemmgtGroup systemmgtGroup,
			SystemmgtUser systemmgtUser) {
		this.systemmgtGroup = systemmgtGroup;
		this.systemmgtUser = systemmgtUser;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gid")
	public SystemmgtGroup getSystemmgtGroup() {
		return systemmgtGroup;
	}

	public void setSystemmgtGroup(SystemmgtGroup systemmgtGroup) {
		this.systemmgtGroup = systemmgtGroup;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public SystemmgtUser getSystemmgtUser() {
		return systemmgtUser;
	}

	public void setSystemmgtUser(SystemmgtUser systemmgtUser) {
		this.systemmgtUser = systemmgtUser;
	}
}