package com.yougou.kaidian.bi.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.yougou.kaidian.bi.dao.MerchantsStatisticsMapper;
import com.yougou.kaidian.bi.service.IMerchantsStatisticsService;
import com.yougou.kaidian.bi.vo.AfterSaleStatisticsVo;
import com.yougou.kaidian.bi.vo.AfterSaleStatisticsVo.AfterSaleStatisticsVoDetail;
import com.yougou.kaidian.bi.vo.CommoditySalesDetailsVo;
import com.yougou.kaidian.bi.vo.CommoditySalesDetailsVo.CommoditySalesDetailsVoDetail;
import com.yougou.kaidian.bi.vo.SummaryOfOperationsVo;
import com.yougou.kaidian.bi.vo.SummaryOfOperationsVo.SummaryOfOperationsVoDetail;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.order.constant.OrderConstant.Paymentmethod;
import com.yougou.ordercenter.common.SaleStatusEnum;
import com.yougou.ordercenter.common.SaleTypeEnum;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.wms.wpi.warehouse.service.IWarehouseCacheService;

@Service
public class MerchantsStatisticsServiceImpl implements IMerchantsStatisticsService {

	private static final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm");

	@Resource
	private MerchantsStatisticsMapper merchantsStatisticsMapper;

	@Resource
    private ICommodityBaseApiService commodityBaseApi;
	
	@Resource
	private ICommodityMerchantApiService commodityApi;
	
	@Resource
	private IWarehouseCacheService warehouseCacheService;
	
	@Override
	public void injectSummaryOfOperationsInto(SummaryOfOperationsVo vo) throws Exception {
		injectSummaryOfOperationsOfOrderInto(vo);
		injectSummaryOfOperationsOfCommodityInto(vo);
	}

	/**
	 * 注入经营概况订单信息
	 * 
	 * @param vo
	 */
	private void injectSummaryOfOperationsOfOrderInto(SummaryOfOperationsVo vo) throws Exception {
		List<Map<String, Object>> resultMaplist = merchantsStatisticsMapper.querySummaryOfOperationsOfOrder(vo.getStart(), vo.getEnd(), vo.getMerchantCode());
		List<SummaryOfOperationsVoDetail> details = new ArrayList<SummaryOfOperationsVoDetail>();

		// 初始变日期变量
		Date prevDate = vo.getStart(), nextDate = vo.getEnd();
		long orderCommodityNums = 0, allOrderNums = 0, allOrderCommodityNums = 0;
		BigDecimal orderPayTotalPrices = BigDecimal.ZERO, allOrderPayTotalPrices = BigDecimal.ZERO;
		Set<String> identifierSet = new HashSet<String>();

		// 明细统计
		switch (vo.getAnalyzePatttern()) {
		case BY_DAY: {
			for (int i = 0; i < 24; i++) {
				orderCommodityNums = 0;
				orderPayTotalPrices = BigDecimal.ZERO;
				identifierSet.clear();
				nextDate = DateUtils.addHours(prevDate, 1);
				for (int j = resultMaplist.size() - 1; j >= 0; j--) {
					Map<String, Object> resultMap = resultMaplist.get(j);
					Date another = (Date) resultMap.get("create_time");
					if (another.compareTo(prevDate) >= 0 && another.compareTo(nextDate) == -1) {
						orderCommodityNums += MapUtils.getLongValue(resultMap, "commodity_num");
						if (identifierSet.add(MapUtils.getString(resultMap, "order_sub_no"))) {
							orderPayTotalPrices = orderPayTotalPrices.add(new BigDecimal(MapUtils.getString(resultMap, "pay_total_price")));
						}
					}
				}
				SummaryOfOperationsVoDetail detail = new SummaryOfOperationsVoDetail();
				detail.setTimeRange(SDF.format(prevDate) + "-" + SDF.format(DateUtils.addSeconds(nextDate, -1)));
				detail.setOrderNums(new Long(identifierSet.size()));
				detail.setOrderCustomers(detail.getOrderNums());
				detail.setOrderCommodityNums(orderCommodityNums);
				detail.setOrderPayTotalPrices(orderPayTotalPrices);
				details.add(detail);
				prevDate = nextDate;
				allOrderNums += detail.getOrderNums();
				allOrderCommodityNums += orderCommodityNums;
				allOrderPayTotalPrices = allOrderPayTotalPrices.add(orderPayTotalPrices);
			}
		}
			break;
		case BY_USER_DEFINED: {
			// 如结束日期为空迭代集合从中找更大的日期
			if (vo.getEnd() == null) {
				nextDate = new Date();
				/*
				// 集合为空时默认与开始日期相同
				if (resultMaplist.isEmpty()) {
					nextDate = prevDate;
				} else {
					// 假设首个日期为最大
					nextDate = (Date) resultMaplist.get(0).get("create_time");
					for (int i = resultMaplist.size() - 1; i > 0; i--) {
						Date another = (Date) resultMaplist.get(i).get("create_time");
						if (another.compareTo(nextDate) == 1) {
							nextDate = another;
						}
					}
				}
				*/
			}
		}
		default: {
			nextDate = DateUtils.addDays(nextDate, 1);
			do {
				orderCommodityNums = 0;
				orderPayTotalPrices = BigDecimal.ZERO;
				identifierSet.clear();
				for (int j = resultMaplist.size() - 1; j >= 0; j--) {
					Map<String, Object> resultMap = resultMaplist.get(j);
					Date another = (Date) resultMap.get("create_time");
					if (DateUtils.isSameDay(prevDate, another)) {
						orderCommodityNums += MapUtils.getLongValue(resultMap, "commodity_num");
						if (identifierSet.add(MapUtils.getString(resultMap, "order_sub_no"))) {
							orderPayTotalPrices = orderPayTotalPrices.add(new BigDecimal(MapUtils.getString(resultMap, "pay_total_price")));
						}
					}
				}
				SummaryOfOperationsVoDetail detail = new SummaryOfOperationsVoDetail();
				detail.setTimeRange(DateFormatUtils.ISO_DATE_FORMAT.format(prevDate));
				detail.setOrderNums(new Long(identifierSet.size()));
				detail.setOrderCustomers(detail.getOrderNums());
				detail.setOrderCommodityNums(orderCommodityNums);
				detail.setOrderPayTotalPrices(orderPayTotalPrices);
				details.add(detail);
				prevDate = DateUtils.addDays(prevDate, 1);
				allOrderNums += detail.getOrderNums();
				allOrderCommodityNums += orderCommodityNums;
				allOrderPayTotalPrices = allOrderPayTotalPrices.add(orderPayTotalPrices);
			} while (!DateUtils.isSameDay(prevDate, nextDate));
		}
			break;
		}

		vo.setSummaryOfOperationsVoDetails(details);
		BigDecimal ratio = new BigDecimal(details.size());
		vo.setOrderNums(allOrderNums);
		vo.setOrderCustomers(allOrderNums);
		vo.setOrderCommodityNums(allOrderCommodityNums);
		vo.setOrderPayTotalPrices(allOrderPayTotalPrices);
		if (vo.getOrderNums().compareTo(NumberUtils.LONG_ZERO) == 1) {
			vo.setAvgOrderNums(new BigDecimal(vo.getOrderNums()).divide(ratio, 2, BigDecimal.ROUND_HALF_UP));
		}
		if (vo.getOrderCustomers().compareTo(NumberUtils.LONG_ZERO) == 1) {
			vo.setAvgOrderCustomers(new BigDecimal(vo.getOrderCustomers()).divide(ratio, 2, BigDecimal.ROUND_HALF_UP));
		}
		if (vo.getOrderCommodityNums().compareTo(NumberUtils.LONG_ZERO) == 1) {
			vo.setAvgOrderCommodityNums(new BigDecimal(vo.getOrderCommodityNums()).divide(ratio, 2, BigDecimal.ROUND_HALF_UP));
		}
		if (vo.getOrderPayTotalPrices().compareTo(BigDecimal.ZERO) == 1) {
			vo.setAvgOrderPayTotalPrices(vo.getOrderPayTotalPrices().divide(ratio, 2, BigDecimal.ROUND_HALF_UP));
		}
		if (allOrderPayTotalPrices.compareTo(BigDecimal.ZERO) == 1) {
			vo.setAvgOrderCustomerPrice(allOrderPayTotalPrices.divide(new BigDecimal(allOrderNums), 2, BigDecimal.ROUND_HALF_UP));
		}
	}

	/**
	 * 注入经营概况商品信息
	 * 
	 * @param vo
	 */
	private void injectSummaryOfOperationsOfCommodityInto(SummaryOfOperationsVo vo) throws Exception {
		List<Map<String, Object>> resultMapList = merchantsStatisticsMapper.querySummaryOfOperationsOfCommodity(vo.getStart(), vo.getEnd(), vo.getMerchantCode());
		if (CollectionUtils.isNotEmpty(resultMapList)) {
			// 限定时间范围
			int SEL_COMMODITY_NUM_INDEX = 0;
			// 未限定时间范围
			int ALL_COMMODITY_NUM_INDEX = 1;
			vo.setSellCommodityNums(MapUtils.getLong(resultMapList.get(SEL_COMMODITY_NUM_INDEX), "commodity_num"));
			vo.setTotalCommodityNums(MapUtils.getLong(resultMapList.get(ALL_COMMODITY_NUM_INDEX), "commodity_num"));
		}
	}

	@Override
	public void injectCommoditySalesDetailsInto(CommoditySalesDetailsVo vo, Query query) throws Exception {
		injectCommoditySalesDetailsOfOrderInto(vo, query);
	}

	@Override
	public PageFinder<CommoditySalesDetailsVoDetail> queryCommoditySalesDetailsInfo(CommoditySalesDetailsVo vo, Query query) throws Exception {
		Integer rowCount = merchantsStatisticsMapper.queryCommoditySalesDetailsCount(vo);
		if (rowCount != null && rowCount > 0) {
			List<Map<String, Object>> resultMapList = merchantsStatisticsMapper.queryCommoditySalesDetails(vo, new RowBounds(query.getOffset(), query.getPageSize()));
			List<CommoditySalesDetailsVoDetail> details = new ArrayList<CommoditySalesDetailsVoDetail>();
			for (int j = 0; j < resultMapList.size(); j++) {
				Map<String, Object> resultMap = resultMapList.get(j);
				CommoditySalesDetailsVoDetail detail = new CommoditySalesDetailsVoDetail();
				detail.setCommodityImage(MapUtils.getString(resultMap, "default_pic"));
				detail.setCommodityName(MapUtils.getString(resultMap, "prod_name"));
				detail.setCommodityUrl(commodityBaseApi.getFullCommodityPageUrl(MapUtils.getString(resultMap, "no")));
				detail.setThirdPartyCode(MapUtils.getString(resultMap, "third_party_code"));
				//detail.setLastSalesTime((Date) MapUtils.getObject(resultMap, "last_sales_time"));
				detail.setLastSalesTime(commodityApi.getLastAuditTimeByCommodityNo(MapUtils.getString(resultMap, "no"), "3"));
				detail.setOrderCommodityNums(MapUtils.getLong(resultMap, "order_commodity_nums"));
				detail.setOrderNums(MapUtils.getLong(resultMap, "order_nums"));
				detail.setOrderPayTotalPrices(new BigDecimal(MapUtils.getString(resultMap, "order_pay_total_prices")));
				detail.setOrderTradedAmounts(new BigDecimal(MapUtils.getString(resultMap, "order_traded_amounts")));
				detail.setAvgOrderAmounts(new BigDecimal(MapUtils.getString(resultMap, "avg_order_amounts")));
				details.add(detail);
			}
			return new PageFinder<CommoditySalesDetailsVoDetail>(query.getPage(), query.getPageSize(), rowCount, details);
		}
		return null;
	}

	@Override
	public PageFinder<CommoditySalesDetailsVoDetail> queryCommodityNoSalesDetailsInfo(CommoditySalesDetailsVo vo, Query query) throws Exception {
		Integer rowCount = merchantsStatisticsMapper.queryCommodityNoSalesDetailsCount(vo);
		if (rowCount != null && rowCount > 0) {
			List<Map<String, Object>> resultMapList = merchantsStatisticsMapper.queryCommodityNoSalesDetails(vo, new RowBounds(query.getOffset(), query.getPageSize()));
			List<CommoditySalesDetailsVoDetail> details = new ArrayList<CommoditySalesDetailsVoDetail>();
			for (int j = 0; j < resultMapList.size(); j++) {
				Map<String, Object> resultMap = resultMapList.get(j);
				CommoditySalesDetailsVoDetail detail = new CommoditySalesDetailsVoDetail();
				detail.setCommodityImage(MapUtils.getString(resultMap, "default_pic"));
				detail.setCommodityName(MapUtils.getString(resultMap, "commodity_name"));
				detail.setCommodityUrl(commodityBaseApi.getFullCommodityPageUrl(MapUtils.getString(resultMap, "no")));
				detail.setThirdPartyCode(MapUtils.getString(resultMap, "third_party_code"));
				detail.setLastSalesTime(commodityApi.getLastAuditTimeByCommodityNo(MapUtils.getString(resultMap, "no"), "3"));
				details.add(detail);
			}
			return new PageFinder<CommoditySalesDetailsVoDetail>(query.getPage(), query.getPageSize(), rowCount, details);
		}
		return null;
	}

	/**
	 * 注入商品销售明细订单信息
	 * 
	 * @param vo
	 */
	private void injectCommoditySalesDetailsOfOrderInto(CommoditySalesDetailsVo vo, Query query) throws Exception {
		List<Map<String, Object>> resultMapList = merchantsStatisticsMapper.querySummaryOfOperationsOfOrder(vo.getStart(), vo.getEnd(), vo.getMerchantCode());

		// 初始化变量
		long allOrderCommodityNums = 0;
		BigDecimal orderTradedAmount = BigDecimal.ZERO, allOrderPayTotalPrices = BigDecimal.ZERO;
		Set<String> identifierSet = new HashSet<String>();

		// 销售概况
		for (int j = resultMapList.size() - 1; j >= 0; j--) {
			Map<String, Object> resultMap = resultMapList.get(j);
			BigDecimal payTotalPrice = new BigDecimal(MapUtils.getString(resultMap, "pay_total_price"));
			allOrderCommodityNums += MapUtils.getLongValue(resultMap, "commodity_num");
			if (identifierSet.add(MapUtils.getString(resultMap, "order_sub_no"))) {
				allOrderPayTotalPrices = allOrderPayTotalPrices.add(payTotalPrice);
				if (Paymentmethod.ONLINE_PAYMENT.name().equals(MapUtils.getString(resultMap, "payment"))) {
					orderTradedAmount = orderTradedAmount.add(payTotalPrice);
				}
			}
		}
		vo.setOrderNums(new Long(identifierSet.size()));
		vo.setOrderCommodityNums(allOrderCommodityNums);
		vo.setOrderPayTotalPrices(allOrderPayTotalPrices);
		vo.setOrderTradedAmount(orderTradedAmount);
		
		// 商品销售排行TOP15
		resultMapList = merchantsStatisticsMapper.queryCommoditySalesDetails(vo, new RowBounds(0, 15));
		List<CommoditySalesDetailsVoDetail> top15 = new ArrayList<CommoditySalesDetailsVoDetail>();
		for (int j = 0; j < resultMapList.size(); j++) {
			Map<String, Object> resultMap = resultMapList.get(j);
			CommoditySalesDetailsVoDetail detail = new CommoditySalesDetailsVoDetail();
			detail.setCommodityImage(MapUtils.getString(resultMap, "commodity_image"));
			detail.setCommodityName(MapUtils.getString(resultMap, "prod_name"));
			detail.setCommodityUrl(commodityBaseApi.getFullCommodityPageUrl(MapUtils.getString(resultMap, "no")));
			detail.setThirdPartyCode(MapUtils.getString(resultMap, "third_party_code"));
			detail.setLastSalesTime(commodityApi.getLastAuditTimeByCommodityNo(MapUtils.getString(resultMap, "no"), "3"));
			detail.setOrderCommodityNums(MapUtils.getLong(resultMap, "order_commodity_nums"));
			detail.setOrderNums(MapUtils.getLong(resultMap, "order_nums"));
			detail.setOrderPayTotalPrices(new BigDecimal(MapUtils.getString(resultMap, "order_pay_total_prices")));
			detail.setOrderTradedAmounts(new BigDecimal(MapUtils.getString(resultMap, "order_traded_amounts")));
			detail.setAvgOrderAmounts(new BigDecimal(MapUtils.getString(resultMap, "avg_order_amounts")));
			top15.add(detail);
		}
		vo.setTop15(top15);
		
		// 销售明细(采用Ajax延迟加载)
		// vo.setPageFinder(this.queryCommoditySalesDetailsInfo(vo, query));
	}

	@Override
	public void injectAfterSaleStatisticsDetailsInfo(AfterSaleStatisticsVo vo) throws Exception {
		//如果vo为空，那么直接返回
		if(vo == null) {
			return;
		} else if (null == vo.getAnalyzePatttern()) {
			return;
		}
		
		List<Map<String, Object>> resultMapList = merchantsStatisticsMapper.queryAfterSaleStatistics(vo);
		long quitNums = 0, sumQuitNums = 0;
		long tradeNums = 0, sumTradeNums = 0;
		BigDecimal quitAmount = BigDecimal.ZERO;
		BigDecimal tradeAmount = BigDecimal.ZERO;
		BigDecimal sumQuitAmount = BigDecimal.ZERO;
		BigDecimal sumTradeAmount = BigDecimal.ZERO;
		
		Date prevDate = vo.getStart(), nextDate = vo.getEnd();
		List<String> labelList = new ArrayList<String>();
		List<Long> quitNumList = new ArrayList<Long>();
		List<BigDecimal> quitAmountList = new ArrayList<BigDecimal>();
		List<Long> tradeNumList = new ArrayList<Long>();
		List<BigDecimal> tradeAmountList = new ArrayList<BigDecimal>();
		
		// 明细统计
		switch (vo.getAnalyzePatttern()) {
		case BY_DAY: {
			for (int i = 0; i < 24; i++) {
				quitNums = tradeNums = 0;
				quitAmount = tradeAmount = BigDecimal.ZERO;
				nextDate = DateUtils.addHours(prevDate, 1);
				for (int j = resultMapList.size() - 1; j >= 0; j--) {
					Map<String, Object> resultMap = resultMapList.get(j);
					Date another = (Date) resultMap.get("apply_time");
					if (another.compareTo(prevDate) >= 0 && another.compareTo(nextDate) == -1) {
						BigDecimal backedAmount = new BigDecimal(MapUtils.getString(resultMap, "pay_total_price"));
						SaleTypeEnum saleType = SaleTypeEnum.valueOf(MapUtils.getString(resultMap, "sale_type"));
						switch (saleType) {
						case QUIT_GOODS:
							quitNums++;
							quitAmount = quitAmount.add(backedAmount);
							break;
						case TRADE_GOODS:
							tradeNums++;
							tradeAmount = tradeAmount.add(backedAmount);
							break;
						}
					}
				}
				labelList.add(SDF.format(prevDate) + "-" + SDF.format(DateUtils.addSeconds(nextDate, -1)));
				prevDate = nextDate;
				quitNumList.add(quitNums);
				quitAmountList.add(quitAmount);
				tradeNumList.add(tradeNums);
				tradeAmountList.add(tradeAmount);
				sumQuitNums += quitNums;
				sumQuitAmount = sumQuitAmount.add(quitAmount);
				sumTradeNums += tradeNums;
				sumTradeAmount = sumTradeAmount.add(tradeAmount);
			}
		}
			break;
		case BY_USER_DEFINED: {
			// 如结束日期为空迭代集合从中找更大的日期
			if (vo.getEnd() == null) {
				nextDate = new Date();
			}
		}
		default: {
			nextDate = DateUtils.addDays(nextDate, 1);
			do {
				quitNums = tradeNums = 0;
				quitAmount = tradeAmount = BigDecimal.ZERO;
				for (int j = resultMapList.size() - 1; j >= 0; j--) {
					Map<String, Object> resultMap = resultMapList.get(j);
					Date another = (Date) resultMap.get("apply_time");
					if (DateUtils.isSameDay(prevDate, another)) {
						BigDecimal backedAmount = new BigDecimal(MapUtils.getString(resultMap, "pay_total_price"));
						SaleTypeEnum saleType = SaleTypeEnum.valueOf(MapUtils.getString(resultMap, "sale_type"));
						switch (saleType) {
						case QUIT_GOODS:
							quitNums++;
							quitAmount = quitAmount.add(backedAmount);
							break;
						case TRADE_GOODS:
							tradeNums++;
							tradeAmount = tradeAmount.add(backedAmount);
							break;
						}
					}
				}
				labelList.add(DateFormatUtils.ISO_DATE_FORMAT.format(prevDate));
				quitNumList.add(quitNums);
				quitAmountList.add(quitAmount);
				tradeNumList.add(tradeNums);
				tradeAmountList.add(tradeAmount);
				sumQuitNums += quitNums;
				sumQuitAmount = sumQuitAmount.add(quitAmount);
				sumTradeNums += tradeNums;
				sumTradeAmount = sumTradeAmount.add(tradeAmount);
				prevDate = DateUtils.addDays(prevDate, 1);
			} while (!DateUtils.isSameDay(prevDate, nextDate));
		}
			break;
		}
		
		vo.setQuitNums(sumQuitNums);
		vo.setQuitAmount(sumQuitAmount);
		vo.setTradeNums(sumTradeNums);
		vo.setTradeAmount(sumTradeAmount);
		vo.setLabelList(labelList);
		vo.setQuitNumList(quitNumList);
		vo.setQuitAmountList(quitAmountList);
		vo.setTradeNumList(tradeNumList);
		vo.setTradeAmountList(tradeAmountList);
	}

	@Override
	public PageFinder<AfterSaleStatisticsVoDetail> queryAfterSaleStatisticsDetailsInfo(AfterSaleStatisticsVo vo, Query query) throws Exception {
		Integer rowCount = merchantsStatisticsMapper.queryAfterSaleStatisticsDetailsCount(vo);
		if (rowCount != null && rowCount > 0) {
			List<Map<String, Object>> resultMapList = merchantsStatisticsMapper.queryAfterSaleStatisticsDetails(vo, new RowBounds(query.getOffset(), query.getPageSize()));
			List<AfterSaleStatisticsVoDetail> details = new ArrayList<AfterSaleStatisticsVoDetail>();
			for (int j = 0; j < resultMapList.size(); j++) {
				Map<String, Object> resultMap = resultMapList.get(j);
				AfterSaleStatisticsVoDetail detail = new AfterSaleStatisticsVoDetail();
				detail.setOrderNo(MapUtils.getString(resultMap, "order_no"));
				detail.setThirdPartyCode(MapUtils.getString(resultMap, "third_party_code"));
				detail.setCommodityName(MapUtils.getString(resultMap, "commodity_name"));
				detail.setWarehouseCode(MapUtils.getString(resultMap, "warehouse_code"));
				Map<String, Object> warehouse=warehouseCacheService.getwarehouseByWareCode(MapUtils.getString(resultMap, "warehouse_code"));
				//detail.setWarehouseName(MapUtils.getString(resultMap, "warehouse_name"));
				detail.setWarehouseName(MapUtils.getString(warehouse, "warehouseName"));
				detail.setCreateTime((Date) MapUtils.getObject(resultMap, "create_time"));
				detail.setPayTotalPrice(new BigDecimal(MapUtils.getString(resultMap, "pay_total_price")));
				detail.setApplyNo(MapUtils.getString(resultMap, "apply_no"));
				detail.setApplyTime((Date) MapUtils.getObject(resultMap, "apply_time"));
				detail.setStatus(SaleStatusEnum.valueOf(MapUtils.getString(resultMap, "status")));
				detail.setSaleType(SaleTypeEnum.valueOf(MapUtils.getString(resultMap, "sale_type")));
				detail.setSaleReason(MapUtils.getString(resultMap, "sale_reason"));
				detail.setLogisticsName(MapUtils.getString(resultMap, "logistics_name"));
				detail.setExpressNo(MapUtils.getString(resultMap, "express_no"));
				detail.setBackedAmount(new BigDecimal(MapUtils.getString(resultMap, "backed_amount")));
				details.add(detail);
			}
			return new PageFinder<AfterSaleStatisticsVoDetail>(query.getPage(), query.getPageSize(), rowCount, details);
		}
		return null;
	}
}
