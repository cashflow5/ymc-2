package com.yougou.dto.input;

/**
 * 查询分销商账户信息
 * @author song.nh
 *
 */
public class DistributorsAccountInputDto extends InputDto {
	
	public String getPage_index() {
		return page_index;
	}

	public void setPage_index(String page_index) {
		this.page_index = page_index;
	}

	public String getPage_size() {
		return page_size;
	}

	public void setPage_size(String page_size) {
		this.page_size = page_size;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	/**
	 * 交易号
	 */
	private String tradeNo;
	
	/**
	 * 金额
	 */
	private String account;
	
	/**
	 * 开始时间
	 */
	private String startTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;
	
	/**
	 * 第几页
	 */
	private String page_index;
	
	/**
	 * 页面行数
	 */
	private String page_size;
}
