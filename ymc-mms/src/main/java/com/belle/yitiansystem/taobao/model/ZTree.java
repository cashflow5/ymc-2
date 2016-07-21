package com.belle.yitiansystem.taobao.model;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-22 下午1:24:09
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class ZTree {
	private String id;
	private String pId;
	private String name;
	private String itemId;
	private Long sortOrder;
	private String structName;
	private boolean isParent;
	private boolean hasChild;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public Long getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}
	public boolean getIsParent() {
		return isParent;
	}
	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
	public String getStructName() {
		return structName;
	}
	public void setStructName(String structName) {
		this.structName = structName;
	}
	public boolean isHasChild() {
		return hasChild;
	}
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

}
