package com.yougou.dto.input;


public class OrderExceptionInputDto extends InputDto {

	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1284200332014618383L;

	/**
	 * 子订单号
	 */
	private String order_sub_no;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 1 缺货  5 物流公司超区
	 */
	private Integer is_exception;

	public String getOrder_sub_no() {
		return order_sub_no;
	}

	public void setOrder_sub_no(String order_sub_no) {
		this.order_sub_no = order_sub_no;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIs_exception() {
		return is_exception;
	}

	public void setIs_exception(Integer is_exception) {
		this.is_exception = is_exception;
	}



}
