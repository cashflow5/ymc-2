package com.yougou.kaidian.bi.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.yougou.kaidian.bi.beans.CommoditySalesQueryState;
import com.yougou.kaidian.common.base.PageFinder;

public class CommoditySalesDetailsVo extends StatisticsVo {
	
	// 商品名称
	private String commodityName;
	// 货号(商家货品条码)
	private String thirdPartyCode;
	// 商售明细查询类型
	private CommoditySalesQueryState salesQueryState;
	// 下单量
	private Long orderNums = NumberUtils.LONG_ZERO;
	// 下单商品件数
	private Long orderCommodityNums = NumberUtils.LONG_ZERO;
	// 下单金额
	private BigDecimal orderPayTotalPrices = BigDecimal.ZERO;
	// 下单成交金额
	private BigDecimal orderTradedAmount = BigDecimal.ZERO;

	// 商品销售排行TOP15
	private List<CommoditySalesDetailsVoDetail> top15;
	
	// 销售明细
	private PageFinder<CommoditySalesDetailsVoDetail> pageFinder;

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getThirdPartyCode() {
		return thirdPartyCode;
	}

	public void setThirdPartyCode(String thirdPartyCode) {
		this.thirdPartyCode = thirdPartyCode;
	}

	public CommoditySalesQueryState getSalesQueryState() {
		return salesQueryState;
	}

	public void setSalesQueryState(CommoditySalesQueryState salesQueryState) {
		this.salesQueryState = salesQueryState;
	}

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

	public BigDecimal getOrderTradedAmount() {
		return orderTradedAmount;
	}

	public void setOrderTradedAmount(BigDecimal orderTradedAmount) {
		this.orderTradedAmount = orderTradedAmount;
	}

	public List<CommoditySalesDetailsVoDetail> getTop15() {
		return top15;
	}

	public void setTop15(List<CommoditySalesDetailsVoDetail> top15) {
		this.top15 = top15;
	}

	public PageFinder<CommoditySalesDetailsVoDetail> getPageFinder() {
		return pageFinder;
	}

	public void setPageFinder(PageFinder<CommoditySalesDetailsVoDetail> pageFinder) {
		this.pageFinder = pageFinder;
	}

	public static class CommoditySalesDetailsVoDetail {
		// 商品图片
		private String commodityImage;
		// 商品单品页URL
		private String commodityUrl;
		// 商品名称
		private String commodityName;
		// 货号
		private String thirdPartyCode;
		// 最近上架时间
		private Date lastSalesTime;
		// 下单商品件数
		private Long orderCommodityNums = NumberUtils.LONG_ZERO;
		// 下单数量
		private Long orderNums = NumberUtils.LONG_ZERO;
		// 下单金额
		private BigDecimal orderPayTotalPrices = BigDecimal.ZERO;
		// 成交金额
		private BigDecimal orderTradedAmounts = BigDecimal.ZERO;
		// 销售均价
		private BigDecimal avgOrderAmounts = BigDecimal.ZERO;

		public String getCommodityImage() {
			return commodityImage;
		}

		public void setCommodityImage(String commodityImage) {
			this.commodityImage = commodityImage;
		}

		public String getCommodityUrl() {
			return commodityUrl;
		}

		public void setCommodityUrl(String commodityUrl) {
			this.commodityUrl = commodityUrl;
		}

		public String getCommodityName() {
			return commodityName;
		}

		public void setCommodityName(String commodityName) {
			this.commodityName = commodityName;
		}

		public String getThirdPartyCode() {
			return thirdPartyCode;
		}

		public void setThirdPartyCode(String thirdPartyCode) {
			this.thirdPartyCode = thirdPartyCode;
		}

		public Date getLastSalesTime() {
			return lastSalesTime;
		}

		public void setLastSalesTime(Date lastSalesTime) {
			this.lastSalesTime = lastSalesTime;
		}

		public Long getOrderCommodityNums() {
			return orderCommodityNums;
		}

		public void setOrderCommodityNums(Long orderCommodityNums) {
			this.orderCommodityNums = orderCommodityNums;
		}

		public Long getOrderNums() {
			return orderNums;
		}

		public void setOrderNums(Long orderNums) {
			this.orderNums = orderNums;
		}

		public BigDecimal getOrderPayTotalPrices() {
			return orderPayTotalPrices;
		}

		public void setOrderPayTotalPrices(BigDecimal orderPayTotalPrices) {
			this.orderPayTotalPrices = orderPayTotalPrices;
		}

		public BigDecimal getOrderTradedAmounts() {
			return orderTradedAmounts;
		}

		public void setOrderTradedAmounts(BigDecimal orderTradedAmounts) {
			this.orderTradedAmounts = orderTradedAmounts;
		}

		public BigDecimal getAvgOrderAmounts() {
			return avgOrderAmounts;
		}

		public void setAvgOrderAmounts(BigDecimal avgOrderAmounts) {
			this.avgOrderAmounts = avgOrderAmounts;
		}
	}
}
