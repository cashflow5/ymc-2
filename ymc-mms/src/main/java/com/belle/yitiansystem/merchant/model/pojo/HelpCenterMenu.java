package com.belle.yitiansystem.merchant.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 商家帮助中心菜单实体
 * 
 * @author huang.tao
 *
 */
@Entity
@Table(name = "tbl_merchant_help_menu")
public class HelpCenterMenu {

	private String id;
	
	/** 菜单名称 */
	private String menuName;
	
	/** 父节点id */
	private String parentId;
	
	/** 节点id */
	private String subId;
	
	/** 菜单级别，从1开始级联 */
	private Integer level;
	
	/** 是否为叶子节点 1:叶子节点 0：非叶子节点 */
	private Integer isLeaf;
	
	/** 排序号 */
	private Integer orderNo;
	
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
	
	@Column(name = "menu_name",  length = 32)
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	@Column(name = "parent_id",  length = 10)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "sub_id",  length = 10)
	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	@Column(name = "level",  length = 3)
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "is_leaf",  length = 1)
	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Column(name = "order_no",  length = 10)
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public boolean equals(Object obj) {
		if(this==obj) return true;
		if (!(obj instanceof HelpCenterMenu)) return false;
		final HelpCenterMenu tempMenu=(HelpCenterMenu)obj;
		if (!this.getMenuName().equals(tempMenu.getMenuName())) return false;
		if (!this.getParentId().equals(tempMenu.getParentId())) return false;
		if (!this.getSubId().equals(tempMenu.getSubId())) return false;
		if (!this.getLevel().equals(tempMenu.getLevel())) return false;
		if (!this.getIsLeaf().equals(tempMenu.getIsLeaf())) return false;
		if (!this.getOrderNo().equals(tempMenu.getOrderNo())) return false;
		return true;
	}
	
	
}
