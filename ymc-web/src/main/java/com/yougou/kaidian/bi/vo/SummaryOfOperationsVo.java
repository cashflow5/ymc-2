package com.yougou.kaidian.bi.vo;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

public class SummaryOfOperationsVo extends StatisticsVo {

	// 下单量
	private Long orderNums = NumberUtils.LONG_ZERO;
	// 下单商品件数
	private Long orderCommodityNums = NumberUtils.LONG_ZERO;
	// 下单金额
	private BigDecimal orderPayTotalPrices = BigDecimal.ZERO;
	// 下单客户数
	private Long orderCustomers = NumberUtils.LONG_ZERO;
	
	// 平均下单量
	private BigDecimal avgOrderNums = BigDecimal.ZERO;
	// 平均下单商品件数
	private BigDecimal avgOrderCommodityNums = BigDecimal.ZERO;
	// 平均下单金额
	private BigDecimal avgOrderPayTotalPrices = BigDecimal.ZERO;
	// 平均下单客户数
	private BigDecimal avgOrderCustomers = BigDecimal.ZERO;
	
	// 客单价
	private BigDecimal avgOrderCustomerPrice = BigDecimal.ZERO;
	
	// 上架商品数量
	private Long sellCommodityNums = NumberUtils.LONG_ZERO;
	// 总上架商品数量
	private Long totalCommodityNums = NumberUtils.LONG_ZERO;
	
	// 经营明细
	private List<SummaryOfOperationsVoDetail> summaryOfOperationsVoDetails = Collections.emptyList();

	public Long getOrderNums() {
		return orderNums;
	}

	public void setOrderNums(Long orderNums) {
		this.orderNums = orderNums;
	}
	
	public Long getOrderCommodityNums() {
		return orderCommodityNums;
	}

	public void setOrderCommodityNums(Long orderCommodityNums) {
		this.orderCommodityNums = orderCommodityNums;
	}

	public BigDecimal getOrderPayTotalPrices() {
		return orderPayTotalPrices;
	}

	public void setOrderPayTotalPrices(BigDecimal orderPayTotalPrices) {
		this.orderPayTotalPrices = orderPayTotalPrices;
	}

	public Long getOrderCustomers() {
		return orderCustomers;
	}

	public void setOrderCustomers(Long orderCustomers) {
		this.orderCustomers = orderCustomers;
	}

	public BigDecimal getAvgOrderNums() {
		return avgOrderNums;
	}

	public void setAvgOrderNums(BigDecimal avgOrderNums) {
		this.avgOrderNums = avgOrderNums;
	}

	public BigDecimal getAvgOrderCommodityNums() {
		return avgOrderCommodityNums;
	}

	public void setAvgOrderCommodityNums(BigDecimal avgOrderCommodityNums) {
		this.avgOrderCommodityNums = avgOrderCommodityNums;
	}

	public BigDecimal getAvgOrderPayTotalPrices() {
		return avgOrderPayTotalPrices;
	}

	public void setAvgOrderPayTotalPrices(BigDecimal avgOrderPayTotalPrices) {
		this.avgOrderPayTotalPrices = avgOrderPayTotalPrices;
	}

	public BigDecimal getAvgOrderCustomers() {
		return avgOrderCustomers;
	}

	public void setAvgOrderCustomers(BigDecimal avgOrderCustomers) {
		this.avgOrderCustomers = avgOrderCustomers;
	}

	public BigDecimal getAvgOrderCustomerPrice() {
		return avgOrderCustomerPrice;
	}

	public void setAvgOrderCustomerPrice(BigDecimal avgOrderCustomerPrice) {
		this.avgOrderCustomerPrice = avgOrderCustomerPrice;
	}

	public Long getSellCommodityNums() {
		return sellCommodityNums;
	}

	public void setSellCommodityNums(Long sellCommodityNums) {
		this.sellCommodityNums = sellCommodityNums;
	}

	public Long getTotalCommodityNums() {
		return totalCommodityNums;
	}

	public void setTotalCommodityNums(Long totalCommodityNums) {
		this.totalCommodityNums = totalCommodityNums;
	}

	public List<SummaryOfOperationsVoDetail> getSummaryOfOperationsVoDetails() {
		return summaryOfOperationsVoDetails;
	}

	public void setSummaryOfOperationsVoDetails(List<SummaryOfOperationsVoDetail> summaryOfOperationsVoDetails) {
		this.summaryOfOperationsVoDetails = summaryOfOperationsVoDetails;
	}

	public static class SummaryOfOperationsVoDetail {
		// 时间段
		private String timeRange;
		// 下单量
		private Long orderNums = NumberUtils.LONG_ZERO;
		// 下单客户数
		private Long orderCustomers = NumberUtils.LONG_ZERO;
		// 下单商品件数
		private Long orderCommodityNums = NumberUtils.LONG_ZERO;
		// 下单金额
		private BigDecimal orderPayTotalPrices = BigDecimal.ZERO;

		public String getTimeRange() {
			return timeRange;
		}

		public void setTimeRange(String timeRange) {
			this.timeRange = timeRange;
		}

		public Long getOrderNums() {
			return orderNums;
		}

		public void setOrderNums(Long orderNums) {
			this.orderNums = orderNums;
		}

		public Long getOrderCustomers() {
			return orderCustomers;
		}

		public void setOrderCustomers(Long orderCustomers) {
			this.orderCustomers = orderCustomers;
		}

		public Long getOrderCommodityNums() {
			return orderCommodityNums;
		}

		public void setOrderCommodityNums(Long orderCommodityNums) {
			this.orderCommodityNums = orderCommodityNums;
		}

		public BigDecimal getOrderPayTotalPrices() {
			return orderPayTotalPrices;
		}

		public void setOrderPayTotalPrices(BigDecimal orderPayTotalPrices) {
			this.orderPayTotalPrices = orderPayTotalPrices;
		}
	}
}
