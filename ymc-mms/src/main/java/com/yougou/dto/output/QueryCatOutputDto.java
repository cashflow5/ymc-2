package com.yougou.dto.output;


/**
 * 
 * @author 杨梦清
 * 
 */
public class QueryCatOutputDto extends OutputDto {

	private static final long serialVersionUID = -6426223150686086083L;

	private String cat_id;
	private String cat_name;
	private String cat_no;
	private String struct_name;

	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public String getCat_name() {
		return cat_name;
	}

	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}

	public String getCat_no() {
		return cat_no;
	}

	public void setCat_no(String cat_no) {
		this.cat_no = cat_no;
	}

	public String getStruct_name() {
		return struct_name;
	}

	public void setStruct_name(String struct_name) {
		this.struct_name = struct_name;
	}
}
