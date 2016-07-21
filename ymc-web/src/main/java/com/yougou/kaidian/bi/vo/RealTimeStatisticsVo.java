package com.yougou.kaidian.bi.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class RealTimeStatisticsVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return "RealTimeStatisticsVo [brandNo=" + brandNo + ", rootCattegory="
				+ rootCattegory + ", secondCategory=" + secondCategory
				+ ", threeCategory=" + threeCategory + ", structName="
				+ structName + ", currentTime=" + currentTime + ", payedOrder="
				+ payedOrder + ", expectPayedOrder=" + expectPayedOrder
				+ ", compareYesterdayOrder=" + compareYesterdayOrder
				+ ", yesterdayOrder=" + yesterdayOrder
				+ ", expectCompareYesterdayOrder="
				+ expectCompareYesterdayOrder + ", oldCustomOrder="
				+ oldCustomOrder + ", newCustomOrder=" + newCustomOrder
				+ ", oldCustomOrderPercent=" + oldCustomOrderPercent
				+ ", newCustomOrderPercent=" + newCustomOrderPercent
				+ ", todayHourlyOrder=" + todayHourlyOrder
				+ ", yesterdayHourlyOrder=" + yesterdayHourlyOrder
				+ ", lastWeekSameDayHourlyOrder=" + lastWeekSameDayHourlyOrder
				+ ", payedAmount=" + payedAmount + ", expectPayedAmount="
				+ expectPayedAmount + ", compareYesterdayAmount="
				+ compareYesterdayAmount + ", yesterdayAmount="
				+ yesterdayAmount + ", expectCompareYesterdayAmount="
				+ expectCompareYesterdayAmount + ", oldCustomAmount="
				+ oldCustomAmount + ", newCustomAmount=" + newCustomAmount
				+ ", oldCustomAmountPercent=" + oldCustomAmountPercent
				+ ", newCustomAmountPercent=" + newCustomAmountPercent
				+ ", todayHourlyAmount=" + todayHourlyAmount
				+ ", yesterdayHourlyAmount=" + yesterdayHourlyAmount
				+ ", lastWeekSameDayHourlyAmount="
				+ lastWeekSameDayHourlyAmount + ", payedAveragePrice="
				+ payedAveragePrice + ", expectPayedAveragePrice="
				+ expectPayedAveragePrice + ", compareYesterdayAveragePrice="
				+ compareYesterdayAveragePrice + ", yesterdayAveragePrice="
				+ yesterdayAveragePrice
				+ ", expectCompareYesterdayAveragePrice="
				+ expectCompareYesterdayAveragePrice
				+ ", todayHourlyAveragePrice=" + todayHourlyAveragePrice
				+ ", yesterdayHourlyAveragePrice="
				+ yesterdayHourlyAveragePrice
				+ ", lastWeekSameDayHourlyAveragePrice="
				+ lastWeekSameDayHourlyAveragePrice + "]";
	}
	private String brandNo; //品牌编码
	private String rootCattegory;//一级分类
	private String secondCategory;//二级分类
	private String threeCategory;//三级分类
	private String structName;
	private String currentTime; //当前时间
	
	//订单数量
	private Integer payedOrder;  //支付订单数
	private Integer expectPayedOrder;  //预计今日支付订单数
	private BigDecimal compareYesterdayOrder;  //比昨日此时
	private Integer yesterdayOrder; //昨日订单数
	private BigDecimal expectCompareYesterdayOrder;  //预计比昨日
	private Integer oldCustomOrder; //老客户订单
	private Integer newCustomOrder; //新客户订单
	private BigDecimal oldCustomOrderPercent; //老客户比例
	private BigDecimal newCustomOrderPercent; //新客户比例
	private String todayHourlyOrder;  //今天每小时订单数，全天24小时，没数值的补null
	private String yesterdayHourlyOrder;  //昨天每小时订单数 
	private String lastWeekSameDayHourlyOrder; //上周同天订单数
	
	//支付金额
	private Integer payedAmount;  //支付订单金额
	private Integer expectPayedAmount;  //预计今日支付金额
	private BigDecimal compareYesterdayAmount;  //比昨日此时
	private Integer yesterdayAmount; //昨日订单金额
	private BigDecimal expectCompareYesterdayAmount;  //预计比昨日
	private Integer oldCustomAmount; //老客户金额
	private Integer newCustomAmount; //新客户金额
	private BigDecimal oldCustomAmountPercent; //老客户比例
	private BigDecimal newCustomAmountPercent; //新客户比例
	private String todayHourlyAmount; //今天每小时订单金额，全天24小时，没数值的补null
	private String yesterdayHourlyAmount;  //昨天每小时订单金额
	private String lastWeekSameDayHourlyAmount; //上周同天订单金额
	
	//支付均价
	private Integer payedAveragePrice;  //支付订单均价
	private Integer expectPayedAveragePrice;  //预计今日支付均价
	private BigDecimal compareYesterdayAveragePrice;  //比昨日此时
	private Integer yesterdayAveragePrice; //昨日订单均价
	private BigDecimal expectCompareYesterdayAveragePrice;  //预计比昨日
	private String todayHourlyAveragePrice;  //今天每小时订单均价，全天24小时，没数值的补null
	private String yesterdayHourlyAveragePrice; //昨天每小时订单均价
	private String lastWeekSameDayHourlyAveragePrice; //上周同天订单均价
	public String getRootCattegory() {
		return rootCattegory;
	}
	public void setRootCattegory(String rootCattegory) {
		this.rootCattegory = rootCattegory;
	}
	public String getSecondCategory() {
		return secondCategory;
	}
	public void setSecondCategory(String secondCategory) {
		this.secondCategory = secondCategory;
	}
	public String getThreeCategory() {
		return threeCategory;
	}
	public void setThreeCategory(String threeCategory) {
		this.threeCategory = threeCategory;
	}
	public String getStructName() {
		return structName;
	}
	public void setStructName(String structName) {
		this.structName = structName;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public Integer getPayedOrder() {
		return payedOrder;
	}
	public void setPayedOrder(Integer payedOrder) {
		this.payedOrder = payedOrder;
	}
	public Integer getExpectPayedOrder() {
		return expectPayedOrder;
	}
	public void setExpectPayedOrder(Integer expectPayedOrder) {
		this.expectPayedOrder = expectPayedOrder;
	}
	public BigDecimal getCompareYesterdayOrder() {
		return compareYesterdayOrder;
	}
	public void setCompareYesterdayOrder(BigDecimal compareYesterdayOrder) {
		this.compareYesterdayOrder = compareYesterdayOrder;
	}
	public Integer getYesterdayOrder() {
		return yesterdayOrder;
	}
	public void setYesterdayOrder(Integer yesterdayOrder) {
		this.yesterdayOrder = yesterdayOrder;
	}
	public BigDecimal getExpectCompareYesterdayOrder() {
		return expectCompareYesterdayOrder;
	}
	public void setExpectCompareYesterdayOrder(
			BigDecimal expectCompareYesterdayOrder) {
		this.expectCompareYesterdayOrder = expectCompareYesterdayOrder;
	}
	public Integer getOldCustomOrder() {
		return oldCustomOrder;
	}
	public void setOldCustomOrder(Integer oldCustomOrder) {
		this.oldCustomOrder = oldCustomOrder;
	}
	public Integer getNewCustomOrder() {
		return newCustomOrder;
	}
	public void setNewCustomOrder(Integer newCustomOrder) {
		this.newCustomOrder = newCustomOrder;
	}
	public BigDecimal getOldCustomOrderPercent() {
		return oldCustomOrderPercent;
	}
	public void setOldCustomOrderPercent(BigDecimal oldCustomOrderPercent) {
		this.oldCustomOrderPercent = oldCustomOrderPercent;
	}
	public BigDecimal getNewCustomOrderPercent() {
		return newCustomOrderPercent;
	}
	public void setNewCustomOrderPercent(BigDecimal newCustomOrderPercent) {
		this.newCustomOrderPercent = newCustomOrderPercent;
	}
	public String getTodayHourlyOrder() {
		return todayHourlyOrder;
	}
	public void setTodayHourlyOrder(String todayHourlyOrder) {
		this.todayHourlyOrder = todayHourlyOrder;
	}
	public String getYesterdayHourlyOrder() {
		return yesterdayHourlyOrder;
	}
	public void setYesterdayHourlyOrder(String yesterdayHourlyOrder) {
		this.yesterdayHourlyOrder = yesterdayHourlyOrder;
	}
	public String getLastWeekSameDayHourlyOrder() {
		return lastWeekSameDayHourlyOrder;
	}
	public void setLastWeekSameDayHourlyOrder(String lastWeekSameDayHourlyOrder) {
		this.lastWeekSameDayHourlyOrder = lastWeekSameDayHourlyOrder;
	}
	public Integer getPayedAmount() {
		return payedAmount;
	}
	public void setPayedAmount(Integer payedAmount) {
		this.payedAmount = payedAmount;
	}
	public Integer getExpectPayedAmount() {
		return expectPayedAmount;
	}
	public void setExpectPayedAmount(Integer expectPayedAmount) {
		this.expectPayedAmount = expectPayedAmount;
	}
	public BigDecimal getCompareYesterdayAmount() {
		return compareYesterdayAmount;
	}
	public void setCompareYesterdayAmount(BigDecimal compareYesterdayAmount) {
		this.compareYesterdayAmount = compareYesterdayAmount;
	}
	public Integer getYesterdayAmount() {
		return yesterdayAmount;
	}
	public void setYesterdayAmount(Integer yesterdayAmount) {
		this.yesterdayAmount = yesterdayAmount;
	}
	public BigDecimal getExpectCompareYesterdayAmount() {
		return expectCompareYesterdayAmount;
	}
	public void setExpectCompareYesterdayAmount(
			BigDecimal expectCompareYesterdayAmount) {
		this.expectCompareYesterdayAmount = expectCompareYesterdayAmount;
	}
	public Integer getOldCustomAmount() {
		return oldCustomAmount;
	}
	public void setOldCustomAmount(Integer oldCustomAmount) {
		this.oldCustomAmount = oldCustomAmount;
	}
	public Integer getNewCustomAmount() {
		return newCustomAmount;
	}
	public void setNewCustomAmount(Integer newCustomAmount) {
		this.newCustomAmount = newCustomAmount;
	}
	public BigDecimal getOldCustomAmountPercent() {
		return oldCustomAmountPercent;
	}
	public void setOldCustomAmountPercent(BigDecimal oldCustomAmountPercent) {
		this.oldCustomAmountPercent = oldCustomAmountPercent;
	}
	public BigDecimal getNewCustomAmountPercent() {
		return newCustomAmountPercent;
	}
	public void setNewCustomAmountPercent(BigDecimal newCustomAmountPercent) {
		this.newCustomAmountPercent = newCustomAmountPercent;
	}
	public String getTodayHourlyAmount() {
		return todayHourlyAmount;
	}
	public void setTodayHourlyAmount(String todayHourlyAmount) {
		this.todayHourlyAmount = todayHourlyAmount;
	}
	public String getYesterdayHourlyAmount() {
		return yesterdayHourlyAmount;
	}
	public void setYesterdayHourlyAmount(String yesterdayHourlyAmount) {
		this.yesterdayHourlyAmount = yesterdayHourlyAmount;
	}
	public String getLastWeekSameDayHourlyAmount() {
		return lastWeekSameDayHourlyAmount;
	}
	public void setLastWeekSameDayHourlyAmount(String lastWeekSameDayHourlyAmount) {
		this.lastWeekSameDayHourlyAmount = lastWeekSameDayHourlyAmount;
	}
	public Integer getPayedAveragePrice() {
		return payedAveragePrice;
	}
	public void setPayedAveragePrice(Integer payedAveragePrice) {
		this.payedAveragePrice = payedAveragePrice;
	}
	public Integer getExpectPayedAveragePrice() {
		return expectPayedAveragePrice;
	}
	public void setExpectPayedAveragePrice(Integer expectPayedAveragePrice) {
		this.expectPayedAveragePrice = expectPayedAveragePrice;
	}
	public BigDecimal getCompareYesterdayAveragePrice() {
		return compareYesterdayAveragePrice;
	}
	public void setCompareYesterdayAveragePrice(
			BigDecimal compareYesterdayAveragePrice) {
		this.compareYesterdayAveragePrice = compareYesterdayAveragePrice;
	}
	public Integer getYesterdayAveragePrice() {
		return yesterdayAveragePrice;
	}
	public void setYesterdayAveragePrice(Integer yesterdayAveragePrice) {
		this.yesterdayAveragePrice = yesterdayAveragePrice;
	}
	public BigDecimal getExpectCompareYesterdayAveragePrice() {
		return expectCompareYesterdayAveragePrice;
	}
	public void setExpectCompareYesterdayAveragePrice(
			BigDecimal expectCompareYesterdayAveragePrice) {
		this.expectCompareYesterdayAveragePrice = expectCompareYesterdayAveragePrice;
	}
	public String getTodayHourlyAveragePrice() {
		return todayHourlyAveragePrice;
	}
	public void setTodayHourlyAveragePrice(String todayHourlyAveragePrice) {
		this.todayHourlyAveragePrice = todayHourlyAveragePrice;
	}
	public String getYesterdayHourlyAveragePrice() {
		return yesterdayHourlyAveragePrice;
	}
	public void setYesterdayHourlyAveragePrice(String yesterdayHourlyAveragePrice) {
		this.yesterdayHourlyAveragePrice = yesterdayHourlyAveragePrice;
	}
	public String getLastWeekSameDayHourlyAveragePrice() {
		return lastWeekSameDayHourlyAveragePrice;
	}
	public void setLastWeekSameDayHourlyAveragePrice(
			String lastWeekSameDayHourlyAveragePrice) {
		this.lastWeekSameDayHourlyAveragePrice = lastWeekSameDayHourlyAveragePrice;
	}
	public String getBrandNo() {
		return brandNo;
	}
	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}
	
}
