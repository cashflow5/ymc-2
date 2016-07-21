package com.yougou.kaidian.order.service;

import java.util.Date;
import java.util.List;

import com.yougou.kaidian.order.model.AsmProduct;
import com.yougou.kaidian.order.model.MerchantOrderExpand;
import com.yougou.kaidian.order.model.MerchantOrderPrintInputDto;
import com.yougou.kaidian.order.model.MerchantQueryOrderPrintOutputDto;
import com.yougou.kaidian.order.model.MerchantQueryOrderStockOutputDto;
import com.yougou.ordercenter.common.PageFinder;
import com.yougou.ordercenter.vo.merchant.input.QueryStockInputDto;
import com.yougou.ordercenter.vo.merchant.output.QueryOrderPickOutputDto;

/**
 * 招商替代订单相关查询接口类
 * 
 * @author mei.jf
 * 
 */
public interface IOrderForMerchantService {
	
    public PageFinder<MerchantQueryOrderPrintOutputDto> queryPrintList(MerchantOrderPrintInputDto dto) throws Exception;

    public PageFinder<MerchantQueryOrderStockOutputDto> queryStockingList(QueryStockInputDto dto) throws Exception;

    public List<QueryOrderPickOutputDto> queryPcikingList(String merchantCode, String warehouseCode, List<String> orderSubNos) throws Exception;
    
    public AsmProduct getProductByLevelCode(String order_sub_no,String level_code);
    
    /**
	 * 保存商家备注
	 * @param vo
	 * @return
	 */
	public int insertOrUpdateMerchantRemark(MerchantOrderExpand vo)throws Exception;
	
//	public void resetPageFinderForMerchantRemark(List<String> orders,PageFinder pageFinder)throws Exception;

	public MerchantOrderExpand getMerchantOrderExpand(String orderSubNo)throws Exception;

	public List<MerchantOrderExpand> queryMerchantOrderExpandList(List<String> orderNOs)throws Exception;
	/**
	 * updateExportStatus:更新招商同步库的订单导出状态 
	 * @author li.n1 
	 * @param merchantCode
	 * @param lstOrderSubNo
	 * @param now 
	 * @since JDK 1.6 
	 * @date 2015-12-7 下午4:10:14
	 */
	public void updateExportStatus(String merchantCode,List<String> lstOrderSubNo, Date now);
	/**
	 * updatePrintExpressSize:修改该订单的快递单打印次数 
	 * @author li.n1 
	 * @param merchantCode
	 * @param orderSubNo 
	 * @since JDK 1.6 
	 * @date 2015-12-7 下午5:23:51
	 */
	public void updatePrintExpressSize(String merchantCode, String orderSubNo);
	/**
	 * updatePrintExpressSize:修改该订单的购物清单打印次数 
	 * @author li.n1 
	 * @param merchantCode
	 * @param orderSubNo 
	 * @since JDK 1.6 
	 * @date 2015-12-7 下午5:23:51
	 */
	public void updatePrintShopSize(String merchantCode, List<String> _orderNos);
	/**
	 * updateOutStoreStatus:修改订单的发货状态
	 * @author li.n1 
	 * @param merchantCode
	 * @param orderSubNo
	 * @param outShopDate 
	 * @since JDK 1.6 
	 * @date 2015-12-14 下午4:48:55
	 */
	public void updateOutStoreStatus(String merchantCode, String orderSubNo,
			Date outShopDate);
}
