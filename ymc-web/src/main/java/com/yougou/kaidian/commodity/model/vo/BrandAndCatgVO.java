package com.yougou.kaidian.commodity.model.vo;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * 
 * @author liang.yx
 *
 */
public class BrandAndCatgVO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6541682272274110945L;
	 
	@NotEmpty
	private String brandNo;
	
	@NotEmpty
	private String brandName;
	
	@NotEmpty
	private String catName;
	
	@NotEmpty
	private String catStructname;
	
	@NotEmpty
	private String catNo;
	
	@NotEmpty
	private String catId;
	
	@NotEmpty
	private String selectedCatName;

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getCatStructname() {
		return catStructname;
	}

	public void setCatStructname(String catStructname) {
		this.catStructname = catStructname;
	}

	public String getCatNo() {
		return catNo;
	}

	public void setCatNo(String catNo) {
		this.catNo = catNo;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getSelectedCatName() {
		return selectedCatName;
	}

	public void setSelectedCatName(String selectedCatName) {
		this.selectedCatName = selectedCatName;
	}
	
	
}
