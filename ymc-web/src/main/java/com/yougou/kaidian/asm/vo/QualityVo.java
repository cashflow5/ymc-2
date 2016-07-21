/**
 * 
 */
package com.yougou.kaidian.asm.vo;

/**
 * 新质检登记Vo
 * 
 * @author huang.tao
 *
 */
public class QualityVo {
	
	/** 页面输入 */
	private String inputStr;
	
	/** 商家编码 */
	private String merchantCode;
	
	/** 快递单号 */
	private String expressNo;
	
	/** 订单号 */
	private String orderNo;
	
	/** 错误信息 */
	private String errorMessage;
	/**
	 * 查询关键字
	 */
	private String keyword;

	public String getInputStr() {
		return inputStr;
	}

	public void setInputStr(String inputStr) {
		this.inputStr = inputStr;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
