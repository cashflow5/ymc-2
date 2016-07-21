package com.yougou.dto.output;


/**
 * 更新订单状态输出DTO
 * 
 * @author 杨梦清
 * 
 */
public class UpdateOrderStatusOutputDto extends OutputDto {

	private static final long serialVersionUID = 2489527852088285170L;

	/** 子订单号 **/
	private String orderSubNo;

	/** 操作是否成功 */
	private boolean isSuccess = false;
	/** 操作结果说明 */
	private String message;

	public UpdateOrderStatusOutputDto() {
		super();
	}

	public UpdateOrderStatusOutputDto(String orderSubNo) {
		super();
		this.orderSubNo = orderSubNo;
	}

	public String getOrderSubNo() {
		return orderSubNo;
	}

	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
