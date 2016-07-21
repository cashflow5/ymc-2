package com.yougou.dto.input;


public class OrderOutStoreInputDto extends InputDto {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6746978959712316216L;

	/**
	 * 子订单号
	 */
	private String order_sub_no;

	/**
	 * 快递单号
	 */
	private String express_code;

	/**
	 * 快递公司代码
	 */
	private String logistics_company_code;

	/**
	 * 快递公司名称
	 */
	private String logistics_company_name;
	
	/**
	 * 发货时间
	 */
	private String ship_time;

	public String getOrder_sub_no() {
		return order_sub_no;
	}

	public void setOrder_sub_no(String order_sub_no) {
		this.order_sub_no = order_sub_no;
	}

	public String getExpress_code() {
		return express_code;
	}

	public void setExpress_code(String express_code) {
		this.express_code = express_code;
	}

	public String getLogistics_company_code() {
		return logistics_company_code;
	}

	public void setLogistics_company_code(String logistics_company_code) {
		this.logistics_company_code = logistics_company_code;
	}

	public String getLogistics_company_name() {
		return logistics_company_name;
	}

	public void setLogistics_company_name(String logistics_company_name) {
		this.logistics_company_name = logistics_company_name;
	}

	public String getShip_time() {
		return ship_time;
	}

	public void setShip_time(String ship_time) {
		this.ship_time = ship_time;
	}

}
