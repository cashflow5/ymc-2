package com.yougou.dto.input;

import com.yougou.ordercenter.vo.order.OrderEnum.DeliveryDate;



/**
 * 
 * @author 杨梦清
 * 
 */
public class QueryOrderInputDto extends PageableInputDto {

	private static final long serialVersionUID = -2933527863603185132L;

	/**
	 * 收货人姓名
	 */
	private String consignee_name;

	/**
	 * 创建时间开始
	 */
	private String start_created;

	/**
	 * 创建时间结束
	 */
	private String end_created;

	/**
	 * 发货时间开始
	 */
	private String start_posted;

	/**
	 * 发货时间结束
	 */
	private String end_posted;

	/**
	 * 物流公司代码
	 */
	private String logistics_code;

	/**
	 * 订单状态  1 待发货订单 2 已完成订单 3 客服取消订单
	 */
	private Integer order_status;

	/**
	 * 支付方式
	 */
	private String payment;

	/**
	 * 主订单号
	 */
	private String order_main_no;

	/**
	 * 子订单号
	 */
	private String order_sub_no;

	/**
	 * 商品代码
	 */
	private String commodity_no;

	/**
	 * 商品关键字
	 */
	private String commodity_keywords;

	/**
	 * 货品代码
	 */
	private String product_no;
	
	/**
	 * 订单最后更新时间戳开始
	 */
	private String start_modified;
	
	/**
	 * 订单最后更新时间戳结束
	 */
	private String end_modified;
	
	
	/**
	 * 送货日期
	 */
	private String deliveryDate;
	
	public String getConsignee_name() {
		return consignee_name;
	}

	public void setConsignee_name(String consignee_name) {
		this.consignee_name = consignee_name;
	}

	public String getStart_created() {
		return start_created;
	}

	public void setStart_created(String start_created) {
		this.start_created = start_created;
		// 校验下单时间范围不能超过3个月
		validateMonthRange(start_created, end_created, "start_created/end_created", 3);
	}

	public String getEnd_created() {
		return end_created;
	}

	public void setEnd_created(String end_created) {
		this.end_created = end_created;
		// 校验下单时间范围不能超过3个月
		validateMonthRange(start_created, end_created, "start_created/end_created", 3);
	}

	public String getStart_posted() {
		return start_posted;
	}

	public void setStart_posted(String start_posted) {
		this.start_posted = start_posted;
		// 校验发货时间范围不能超过3个月
		validateMonthRange(start_posted, end_posted, "start_posted/end_posted", 3);
	}

	public String getEnd_posted() {
		return end_posted;
	}

	public void setEnd_posted(String end_posted) {
		this.end_posted = end_posted;
		// 校验发货时间范围不能超过3个月
		validateMonthRange(start_posted, end_posted, "start_posted/end_posted", 3);
	}

	public String getLogistics_code() {
		return logistics_code;
	}

	public void setLogistics_code(String logistics_code) {
		this.logistics_code = logistics_code;
	}

	public Integer getOrder_status() {
		return order_status;
	}

	public void setOrder_status(Integer order_status) {
		this.order_status = order_status;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getOrder_main_no() {
		return order_main_no;
	}

	public void setOrder_main_no(String order_main_no) {
		this.order_main_no = order_main_no;
	}

	public String getOrder_sub_no() {
		return order_sub_no;
	}

	public void setOrder_sub_no(String order_sub_no) {
		this.order_sub_no = order_sub_no;
	}

	public String getCommodity_no() {
		return commodity_no;
	}

	public void setCommodity_no(String commodity_no) {
		this.commodity_no = commodity_no;
	}

	public String getCommodity_keywords() {
		return commodity_keywords;
	}

	public void setCommodity_keywords(String commodity_keywords) {
		this.commodity_keywords = commodity_keywords;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
		DeliveryDate temp = DeliveryDate.valueOf(deliveryDate);
		if(null == temp) {
			throw new IllegalArgumentException(deliveryDate + " 只能是WORKDAY_DELIVERY,ALL_CAN_DELIVER,HOLIDAY_DELIVERY," +
					"OTHER_TIME_DELIVERY中的一个");
		}
	}

	public String getProduct_no() {
		return product_no;
	}

	public void setProduct_no(String product_no) {
		this.product_no = product_no;
	}

	public String getStart_modified() {
		return start_modified;
	}

	public void setStart_modified(String start_modified) {
		this.start_modified = start_modified;
		// 校验订单最后修改时间戳范围不能超过1天
		validateTimestampRange(start_modified, end_modified, "start_modified/end_modified", 86400000L);
	}

	public String getEnd_modified() {
		return end_modified;
	}

	public void setEnd_modified(String end_modified) {
		this.end_modified = end_modified;
		// 校验订单最后修改时间戳范围不能超过1天
		validateTimestampRange(start_modified, end_modified, "start_modified/end_modified", 86400000L);
	}
	
}
