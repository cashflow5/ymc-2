package com.yougou.dto.input;

public class QueryInventoryInputDto extends PageableInputDto {
	private static final long serialVersionUID = 2850957720615698810L;
	private String brand_no;
	private String cat_no;
	private String years;
	private String commodity_status;
	private String order_distribution_side;
	private String third_party_code;
	private String product_no;
	private String style_no;

	public String getBrand_no() {
		return brand_no;
	}

	public void setBrand_no(String brand_no) {
		this.brand_no = brand_no;
	}

	public String getCat_no() {
		return cat_no;
	}

	public void setCat_no(String cat_no) {
		this.cat_no = cat_no;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getCommodity_status() {
		return commodity_status;
	}

	public void setCommodity_status(String commodity_status) {
		this.commodity_status = commodity_status;
	}

	public String getOrder_distribution_side() {
		return order_distribution_side;
	}

	public void setOrder_distribution_side(String order_distribution_side) {
		this.order_distribution_side = order_distribution_side;
	}

	public String getThird_party_code() {
		return third_party_code;
	}

	public void setThird_party_code(String third_party_code) {
		this.third_party_code = third_party_code;
	}

	public String getProduct_no() {
		return product_no;
	}

	public void setProduct_no(String product_no) {
		this.product_no = product_no;
	}

	public String getStyle_no() {
		return style_no;
	}

	public void setStyle_no(String style_no) {
		this.style_no = style_no;
	}

}
