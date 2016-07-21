package com.belle.yitiansystem.systemmgmt.model.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validation.constraints.NotEmpty;

/**
 * TblAuthorityRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_authority_role")
public class AuthorityRole implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	// Fields
	private String id;
	@NotEmpty(message="validator.role.rolename.notempty")
	private String roleName;
	private String no;
	private Date roleCreatedate;
	private String remark;
	private Set<SystemmgtUser> systemmgtUsers = new HashSet<SystemmgtUser>(0);
	private Set<AuthorityResources> authorityResources = new HashSet<AuthorityResources>(0);

	// Constructors

	/** default constructor */
	public AuthorityRole() {
	}

	/** full constructor */
	public AuthorityRole(String roleName, String no,
			Date roleCreatedate, Set<SystemmgtUser> systemmgtUsers,
			Set<AuthorityResources> authorityResources) {
		this.roleName = roleName;
		this.no = no;
		this.roleCreatedate = roleCreatedate;
		this.systemmgtUsers = systemmgtUsers;
		this.authorityResources = authorityResources;
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

	@Column(name = "role_name", length = 150)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "no", length = 40)
	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(name = "role_createdate", length = 19)
	public Date getRoleCreatedate() {
		return this.roleCreatedate;
	}

	public void setRoleCreatedate(Date roleCreatedate) {
		this.roleCreatedate = roleCreatedate;
	}
	
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="tbl_systemmgt_user_role" , joinColumns={ @JoinColumn(name="role_id" , nullable=false,updatable=false)} , inverseJoinColumns = { @JoinColumn(name = "uid", nullable = false, updatable = false) })
	public Set<SystemmgtUser> getSystemmgtUsers() {
		return systemmgtUsers;
	}

	public void setSystemmgtUsers(Set<SystemmgtUser> systemmgtUsers) {
		this.systemmgtUsers = systemmgtUsers;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_authority_role_menu", joinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "menu_id", nullable = false, updatable = false) })
	public Set<AuthorityResources> getAuthorityResources() {
		return authorityResources;
	}

	public void setAuthorityResources(Set<AuthorityResources> authorityResources) {
		this.authorityResources = authorityResources;
	}
}