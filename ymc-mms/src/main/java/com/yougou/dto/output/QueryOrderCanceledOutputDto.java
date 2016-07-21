package com.yougou.dto.output;

import java.util.Date;

/**
 * 查询客服取消订单输出DTO
 * 
 * @author 杨梦清
 * 
 */
public class QueryOrderCanceledOutputDto extends OutputDto {

	private static final long serialVersionUID = 2489527852088285170L;

	/** 子订单号 **/
	private String order_sub_no;

	/** 修改日期 */
	private Date modify_time;

	public String getOrder_sub_no() {
		return order_sub_no;
	}

	public void setOrder_sub_no(String order_sub_no) {
		this.order_sub_no = order_sub_no;
	}

	public Date getModify_time() {
		return modify_time;
	}

	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}

}
