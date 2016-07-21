/**
 * 
 */
package com.yougou.kaidian.user.model.pojo;

import java.io.Serializable;

/**
 * @author huang.tao
 *
 */
public class HelpMenu implements Serializable {
	
	private static final long serialVersionUID = 6472527887915412930L;
	
	/** 帮助菜单Id */
	private String id;
	
	/** 菜单名称 */
	private String menuName;
	
	/** 父节点Id */
	private String parentId;
	
	/** 节点Id */
	private String subId;
	
	/** 菜单级别 （从1开始级联 ）*/
	private Integer level;
	
	/** 是否为叶子节点（1:叶子节点 0：非叶子节点 ） */
	private Integer isLeaf;
	
	/** 菜单序号 */
	private Integer orderNo;
	
	/** 父菜单 */
	private String parentName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
