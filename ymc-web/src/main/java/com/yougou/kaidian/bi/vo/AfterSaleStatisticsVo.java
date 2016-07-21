package com.yougou.kaidian.bi.vo;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.ordercenter.common.SaleStatusEnum;
import com.yougou.ordercenter.common.SaleTypeEnum;

public class AfterSaleStatisticsVo extends StatisticsVo {

	// 处理状态
	private SaleStatusEnum saleStatus;
	// 售后类型
	private SaleTypeEnum saleType;
	// 退货数量
	private Long quitNums = NumberUtils.LONG_ZERO;
	// 退货金额
	private BigDecimal quitAmount = BigDecimal.ZERO;
	// 换货数量
	private Long tradeNums = NumberUtils.LONG_ZERO;
	// 换货金额
	private BigDecimal tradeAmount = BigDecimal.ZERO;

	// 数据集
	private List<String> labelList = Collections.emptyList();
	private List<Long> quitNumList = Collections.emptyList();
	private List<BigDecimal> quitAmountList = Collections.emptyList();
	private List<Long> tradeNumList = Collections.emptyList();
	private List<BigDecimal> tradeAmountList = Collections.emptyList();
	
	// 售后明细
	private PageFinder<AfterSaleStatisticsVoDetail> pageFinder;

	public SaleStatusEnum getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(SaleStatusEnum saleStatus) {
		this.saleStatus = saleStatus;
	}

	public SaleTypeEnum getSaleType() {
		return saleType;
	}

	public void setSaleType(SaleTypeEnum saleType) {
		this.saleType = saleType;
	}

	public Long getQuitNums() {
		return quitNums;
	}

	public void setQuitNums(Long quitNums) {
		this.quitNums = quitNums;
	}

	public BigDecimal getQuitAmount() {
		return quitAmount;
	}

	public void setQuitAmount(BigDecimal quitAmount) {
		this.quitAmount = quitAmount;
	}

	public Long getTradeNums() {
		return tradeNums;
	}

	public void setTradeNums(Long tradeNums) {
		this.tradeNums = tradeNums;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public List<String> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<String> labelList) {
		this.labelList = labelList;
	}

	public List<Long> getQuitNumList() {
		return quitNumList;
	}

	public void setQuitNumList(List<Long> quitNumList) {
		this.quitNumList = quitNumList;
	}

	public List<BigDecimal> getQuitAmountList() {
		return quitAmountList;
	}

	public void setQuitAmountList(List<BigDecimal> quitAmountList) {
		this.quitAmountList = quitAmountList;
	}

	public List<Long> getTradeNumList() {
		return tradeNumList;
	}

	public void setTradeNumList(List<Long> tradeNumList) {
		this.tradeNumList = tradeNumList;
	}

	public List<BigDecimal> getTradeAmountList() {
		return tradeAmountList;
	}

	public void setTradeAmountList(List<BigDecimal> tradeAmountList) {
		this.tradeAmountList = tradeAmountList;
	}

	public PageFinder<AfterSaleStatisticsVoDetail> getPageFinder() {
		return pageFinder;
	}

	public void setPageFinder(PageFinder<AfterSaleStatisticsVoDetail> pageFinder) {
		this.pageFinder = pageFinder;
	}

	public static class AfterSaleStatisticsVoDetail {
		// 订单号
		private String orderNo;
		// 商家货品条码
		private String thirdPartyCode;
		// 商品名称
		private String commodityName;
		// 仓库编码
		private String warehouseCode;
		// 仓库名称
		private String warehouseName;
		// 订单创建时间
		private Date createTime;
		// 订单支付金额
		private BigDecimal payTotalPrice;
		// 售后申请单号
		private String applyNo;
		// 售后申请时间
		private Date applyTime;
		// 售后申请单状态
		private SaleStatusEnum status;
		// 售后申请单类型
		private SaleTypeEnum saleType;
		// 退换货原因
		private String saleReason;
		// 快递公司名称
		private String logisticsName;
		// 快递单号
		private String expressNo;
		// 退换货返还金额
		private BigDecimal backedAmount;

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public String getThirdPartyCode() {
			return thirdPartyCode;
		}

		public void setThirdPartyCode(String thirdPartyCode) {
			this.thirdPartyCode = thirdPartyCode;
		}

		public String getCommodityName() {
			return commodityName;
		}

		public void setCommodityName(String commodityName) {
			this.commodityName = commodityName;
		}

		public String getWarehouseCode() {
			return warehouseCode;
		}

		public void setWarehouseCode(String warehouseCode) {
			this.warehouseCode = warehouseCode;
		}

		public String getWarehouseName() {
			return warehouseName;
		}

		public void setWarehouseName(String warehouseName) {
			this.warehouseName = warehouseName;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public BigDecimal getPayTotalPrice() {
			return payTotalPrice;
		}

		public void setPayTotalPrice(BigDecimal payTotalPrice) {
			this.payTotalPrice = payTotalPrice;
		}

		public String getApplyNo() {
			return applyNo;
		}

		public void setApplyNo(String applyNo) {
			this.applyNo = applyNo;
		}

		public Date getApplyTime() {
			return applyTime;
		}

		public void setApplyTime(Date applyTime) {
			this.applyTime = applyTime;
		}

		public SaleStatusEnum getStatus() {
			return status;
		}

		public void setStatus(SaleStatusEnum status) {
			this.status = status;
		}

		public SaleTypeEnum getSaleType() {
			return saleType;
		}

		public void setSaleType(SaleTypeEnum saleType) {
			this.saleType = saleType;
		}

		public String getSaleReason() {
			return saleReason;
		}

		public void setSaleReason(String saleReason) {
			this.saleReason = saleReason;
		}

		public String getLogisticsName() {
			return logisticsName;
		}

		public void setLogisticsName(String logisticsName) {
			this.logisticsName = logisticsName;
		}

		public String getExpressNo() {
			return expressNo;
		}

		public void setExpressNo(String expressNo) {
			this.expressNo = expressNo;
		}

		public BigDecimal getBackedAmount() {
			return backedAmount;
		}

		public void setBackedAmount(BigDecimal backedAmount) {
			this.backedAmount = backedAmount;
		}
	}
}
