package com.yougou.dto.output;


/**
 * 
 * @author 杨梦清
 * 
 */
public class QueryBrandOutputDto extends OutputDto {

	private static final long serialVersionUID = -6013018098175720318L;

	private String brand_name;
	private String brand_no;

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getBrand_no() {
		return brand_no;
	}

	public void setBrand_no(String brand_no) {
		this.brand_no = brand_no;
	}
}
