package com.belle.yitiansystem.taobao.model;

/**
 * 分类实体类
 *
 * @author zhuangruibo
 * @create 2012-3-30 上午10:08:01 
 * @history
 */
public class YougouCat implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String catName;
	private String structName;
	private String no;
	private Integer deleteFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getStructName() {
		return structName;
	}

	public void setStructName(String structName) {
		this.structName = structName;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
