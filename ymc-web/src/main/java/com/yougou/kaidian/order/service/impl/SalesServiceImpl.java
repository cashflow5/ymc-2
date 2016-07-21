package com.yougou.kaidian.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.order.dao.MerchantSalesMapper;
import com.yougou.kaidian.order.model.SalesVO;
import com.yougou.kaidian.order.service.ISalesService;
import com.yougou.wms.wpi.orderoutstore.service.IOrderOutStoreDomainService;

/**
 * @directions:销售service
 * @author： daixiaowei
 * @create： 2012-3-14 下午07:42:37
 * @history：
 * @version:
 */
@Service
public class SalesServiceImpl implements ISalesService {

	private static final Logger logger = LoggerFactory.getLogger(SalesServiceImpl.class);
	
	@Resource
	private MerchantSalesMapper merchantSalesMapper;
	@Resource
	private  IOrderOutStoreDomainService orderOutStoreDomainService;
	
	/**
	 * 查询销售明细
	 */
	@Override
	public PageFinder<SalesVO> queryMerchantProductSales(SalesVO salesVO, Query query) throws Exception{
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		List<SalesVO> lstSalesProduct = merchantSalesMapper.queryMerchantProductList(salesVO, rowBounds);
		for(SalesVO salesVo : lstSalesProduct) {
			if(salesVo.getOrderSubNo() != null) {
				String[] orderSubNos = salesVo.getOrderSubNo().split(",");
				String[] expressCodes = salesVo.getExpressCodes()==null?null:salesVo.getExpressCodes().split(",");
				//此处调用后续调用WMS的接口统计
				if (orderSubNos != null && orderSubNos.length > 0) {
					List<String> lstOrderSubNo = Arrays.asList(orderSubNos);
					//int outQuantity = merchantSalesMapper.getInOutStoreQuantity(lstOrderSubNo, salesVo.getProductNo());
					int outQuantity = orderOutStoreDomainService.getInOutStoreQuantity(lstOrderSubNo, salesVo.getProductNo());
					logger.info("调用WMS系统按照货品查询发货数量接口:输入参数- {}- {} -结果 {} " , 
										new Object[]{lstOrderSubNo,salesVo.getProductNo(),outQuantity});
                    int rejectionQuantity = 0;
                    if (null != expressCodes) {
                        rejectionQuantity = merchantSalesMapper.getInRejectionProductQuantity(Arrays.asList(expressCodes),
                                salesVo.getProductNo());
                    }
					int returnQuantity = merchantSalesMapper.getInReturnProductQuantity(lstOrderSubNo, salesVo.getProductNo());
					salesVo.setSalesCount(""+(outQuantity-rejectionQuantity-returnQuantity));
				}
			}
		}
		// 所有满足条件的金额汇总
		salesVO.setAllPageTotalAmount(merchantSalesMapper.queryMerchantAllProductTotalAmount(salesVO));
		// 所有满足条件的当前页码金额汇总
		BigDecimal onePageTotalAmount = new BigDecimal(0);
		List<Map<String, Object>> resultMaps = merchantSalesMapper.queryMerchantProductTotalAmount(salesVO, rowBounds);
		if (CollectionUtils.isNotEmpty(resultMaps)) {
			for (Map<String, Object> resultMap : resultMaps) {
				onePageTotalAmount = onePageTotalAmount.add(new BigDecimal(MapUtils.getDoubleValue(resultMap, "pay_total_price", 0)));
			}
		}
		
		salesVO.setOnePageTotalAmount(onePageTotalAmount);
		int count = merchantSalesMapper.queryMerchantProductCount(salesVO);
		PageFinder<SalesVO> pageFinder = new PageFinder<SalesVO>(query.getPage(), query.getPageSize(), count, lstSalesProduct);
		return pageFinder;
	}
	
	/**
	 * 查询所有的销售列表 导出报表
	 * @param salesVO
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> queryMerchantSalesExport(SalesVO salesVO) throws Exception{
		List<Object[]> lstDetailResult = new ArrayList<Object[]>(0);
		List<SalesVO> lstSalesProduct = merchantSalesMapper.queryMerchantSalesExport(salesVO);
		if(lstSalesProduct != null && !lstSalesProduct.isEmpty()) {
			lstDetailResult = new ArrayList<Object[]>(lstSalesProduct.size());
			for (SalesVO salesVo : lstSalesProduct) {
				if (salesVo.getOrderSubNo() != null) {
					Integer quantity = orderOutStoreDomainService.getOutStoreQuantity(salesVo.getOrderSubNo(), salesVo.getProductNo());
					logger.info("调用WMS系统根据订单号货品编码查询出库数量接口:输入参数- {}- {} -结果 {} " , 
							new Object[]{salesVo.getOrderSubNo(),salesVo.getProductNo(),quantity});
					salesVo.setOutQuantity(quantity);
					salesVo.setRejectionQuantity(merchantSalesMapper.getRejectionProductQuantity(salesVo.getExpressCodes(), salesVo.getProductNo()));
					salesVo.setReturnQuantity(merchantSalesMapper.getReturnProductQuantity(salesVo.getOrderSubNo(), salesVo.getProductNo()));
				}
				lstDetailResult.add(getSalesChangeToObjectArray(salesVo));
			}
		}
		return lstDetailResult;
	}

	/**
	 * 转换成对象数据
	 * @param salesVo
	 * @return
	 */
	private Object[] getSalesChangeToObjectArray(SalesVO salesVo) {
		Object[] result = {salesVo.getTimeEnd(),salesVo.getShipTimeEnd(),salesVo.getOrderSubNo(),salesVo.getNo(),salesVo.getCommodityName(),
				salesVo.getSupplierCode(),salesVo.getSalePrice(),salesVo.getActivePrice(),salesVo.getThirdPartyCode(),salesVo.getInsideCode(),
				salesVo.getCommoditySpecification(),salesVo.getOutQuantity(),salesVo.getRejectionQuantity(),salesVo.getReturnQuantity(),
				salesVo.getActiveName(),salesVo.getPrefTotalAmount(),(salesVo.getActivePrice()*salesVo.getOutQuantity()-salesVo.getPrefTotalAmount()),salesVo.getActivePrefAmount(),
				salesVo.getCouponPrefAmount(),salesVo.getCouponPrefAmount5(),salesVo.getBuyReductionPrefAmount()};
		return result;
	}

	/**
	 * 按照货品查询销售明细
	 */
	@Override
	public PageFinder<SalesVO> querySalesDetailByProductNo(SalesVO salesVO, Query query) throws Exception{
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		List<SalesVO> lstSalesDetail = merchantSalesMapper.queryMerchantSalesByProductNo(salesVO, rowBounds);
		for(SalesVO salesVo : lstSalesDetail) {
			if(salesVo.getOrderSubNo() != null) {
				Integer quantity = orderOutStoreDomainService.getOutStoreQuantity(salesVo.getOrderSubNo(), salesVo.getProductNo());
				logger.info("调用WMS系统根据订单号货品编码查询出库数量接口:输入参数- {}- {} -结果 {} " , 
						new Object[]{salesVo.getOrderSubNo(),salesVo.getProductNo(),quantity});
				salesVo.setOutQuantity(quantity);
				salesVo.setRejectionQuantity(merchantSalesMapper.getRejectionProductQuantity(salesVo.getExpressCodes(), salesVo.getProductNo()));
				salesVo.setReturnQuantity(merchantSalesMapper.getReturnProductQuantity(salesVo.getOrderSubNo(), salesVo.getProductNo()));
			}
		}
		int count = merchantSalesMapper.queryMerchantSalesByProductNoCount(salesVO);
		PageFinder<SalesVO> pageFinder = new PageFinder<SalesVO>(query.getPage(), query.getPageSize(), count, lstSalesDetail);
		return pageFinder;
	} 

	/**
	 * 按货品导出销售明细
	 * @param salesVO
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> queryMerchantSalesDetailExport(SalesVO salesVO) throws Exception{
		List<Object[]> lstDetailResult = new ArrayList<Object[]>(0);
		List<SalesVO> lstSalesProduct = merchantSalesMapper.queryMerchantSalesDetailExport(salesVO);
		if(lstSalesProduct != null && !lstSalesProduct.isEmpty()) {
			lstDetailResult = new ArrayList<Object[]>(lstSalesProduct.size());
			for (SalesVO salesVo : lstSalesProduct) {
				if (salesVo.getOrderSubNo() != null) {
					Integer quantity = orderOutStoreDomainService.getOutStoreQuantity(salesVo.getOrderSubNo(), salesVo.getProductNo());
					logger.info("调用WMS系统根据订单号货品编码查询出库数量接口:输入参数- {}- {} -结果 {} " , 
							new Object[]{salesVo.getOrderSubNo(),salesVo.getProductNo(),quantity});
					salesVo.setOutQuantity(quantity);
					salesVo.setRejectionQuantity(merchantSalesMapper.getRejectionProductQuantity(salesVo.getExpressCodes(), salesVo.getProductNo()));
					salesVo.setReturnQuantity(merchantSalesMapper.getReturnProductQuantity(salesVo.getOrderSubNo(), salesVo.getProductNo()));
				}
				lstDetailResult.add(getDetailChangeToObjectArray(salesVo));
			}
		}
		return lstDetailResult;
	}

	/**
	 * 转换成对象数据
	 * @param salesVo
	 * @return
	 */
	private Object[] getDetailChangeToObjectArray(SalesVO salesVo) {
		Object[] result = {salesVo.getTimeEnd(),salesVo.getShipTimeEnd(),salesVo.getOrderSubNo(),
				salesVo.getSupplierCode(),salesVo.getThirdPartyCode(),salesVo.getInsideCode(),salesVo.getCommodityName(),
				salesVo.getCommoditySpecification(),salesVo.getOutQuantity(),salesVo.getRejectionQuantity(),salesVo.getReturnQuantity()};
		return result;
	}

	/** 
	 *  商家销售明细导出前的数量
	 * @see com.yougou.kaidian.order.service.ISalesService#queryMerchantSalesCount(com.yougou.kaidian.order.model.SalesVO) 
	 */
	@Override
	public int queryMerchantSalesCount(SalesVO salesVO) {
		return merchantSalesMapper.queryMerchantSalesExportCount(salesVO);
	}


	
}
