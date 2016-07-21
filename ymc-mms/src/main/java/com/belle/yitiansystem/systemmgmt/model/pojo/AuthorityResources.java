package com.belle.yitiansystem.systemmgmt.model.pojo;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * TblAuthorityMenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_authority_resources")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuthorityResources implements java.io.Serializable {

	// Fields

	private String id;
	private String menuName;
	private String memuUrl;
	private String no;
	private String structure;
	private String remark;
	private String type;   //资源类型 （菜单,功能点）
	private Integer sort;
	private String isleaf;
	private String flag;//管理后台菜单项分离标识

	private Set<AuthorityRole> authorityRoles = new HashSet<AuthorityRole>(0);

	// Constructors

	/** default constructor */
	public AuthorityResources() {
	}

	/** full constructor */
	public AuthorityResources(String menuName, String memuUrl, String no,
			String structure, String remark, Integer sort,
			Set<AuthorityRole> authorityRoles) {
		this.menuName = menuName;
		this.memuUrl = memuUrl;
		this.no = no;
		this.structure = structure;
		this.remark = remark;
		this.sort = sort;
		this.authorityRoles = authorityRoles;
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

	@Column(name = "menu_name", length = 50)
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "memu_url", length = 500)
	public String getMemuUrl() {
		return this.memuUrl;
	}

	public void setMemuUrl(String memuUrl) {
		this.memuUrl = memuUrl;
	}

	@Column(name = "no", length = 40)
	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(name = "structure", length = 20)
	public String getStructure() {
		return this.structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	@Column(name = "remark", length = 50)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Column(name = "isleaf" , length = 1)
	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	@Column(name = "flag" , length = 10)
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_authority_role_menu", joinColumns = { @JoinColumn(name = "menu_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
	public Set<AuthorityRole> getAuthorityRoles() {
		return authorityRoles;
	}

	public void setAuthorityRoles(Set<AuthorityRole> authorityRoles) {
		this.authorityRoles = authorityRoles;
	}
	
//	public boolean equals(Object obj) {
//		if (this == obj) {
//			return true;
//		}
//
//		AuthorityResources _r = null;
//		try {
//			_r = (AuthorityResources) obj;
//		} catch (ClassCastException ex) {
//		}
//
//		if (_r != null) {
//			if (id != _r.id) {
//				return false;
//			}
//			if (menuName != _r.menuName) {
//				return false;
//			}
//			if (no != _r.no) {
//				return false;
//			}
//			if (remark != _r.remark) {
//				return false;
//			}
//			if (memuUrl != _r.memuUrl && memuUrl != null
//					&& !memuUrl.equals(_r.memuUrl)) {
//				return false;
//			}
//
//			return true;
//		}
//		return false;
//	}
//	
//	public int hashCode() {
//		int __h = 143;
//		if (id != null) {
//			__h = 5 * __h + id.hashCode();
//		}
//		if (menuName != null) {
//			__h = 5 * __h + menuName.hashCode();
//		}
//		if (remark != null) {
//			__h = 5 * __h + remark.hashCode();
//		}
//		return __h;
//	}


}