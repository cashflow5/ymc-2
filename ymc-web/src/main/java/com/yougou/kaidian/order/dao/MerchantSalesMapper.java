package com.yougou.kaidian.order.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.order.model.SalesVO;

/**
 * 商家销售情况
 * 
 * @author zhuang.rb
 * 
 */
public interface MerchantSalesMapper {

	/**
	 * 按照货品编号查找销售明细
	 * @param salesVo
	 * @param rowBounds
	 * @return
	 */
	public List<SalesVO> queryMerchantSalesByProductNo(SalesVO salesVo, RowBounds rowBounds);
	
	/**
	 * 按照货品编号查找销售明细记录
	 * @param salesVo
	 * @param rowBounds
	 * @return
	 */
	public int queryMerchantSalesByProductNoCount(SalesVO salesVo);
	
	/**
	 * 导出商家销售明细
	 * @param salesVo
	 * @return
	 */
	public List<SalesVO> queryMerchantSalesExport(SalesVO salesVo);
	/**
	 * queryMerchantSalesExportCount:商家销售明细导出前的数量
	 * @author li.n1 
	 * @param salesVo
	 * @return 
	 * @since JDK 1.6
	 */
	public int queryMerchantSalesExportCount(SalesVO salesVo);
	
	/**
	 * 查询销售商家货品
	 * @param salesVo
	 * @return
	 */
	public List<SalesVO> queryMerchantProductList(SalesVO salesVo, RowBounds rowBounds);
	
	/**
	 * 统计总的货品数量
	 * @param salesVo
	 * @return
	 */
	public int queryMerchantProductCount(SalesVO salesVo);
	
	/**
	 * 统计总的货品总金额
	 * 
	 * @param salesVo
	 * @return BigDecimal
	 */
	BigDecimal queryMerchantAllProductTotalAmount(SalesVO salesVo);
	
	/**
	 * 统计总的货品总金额
	 * 
	 * @param salesVo
	 * @param rowBounds
	 * @return BigDecimal
	 */
	List<Map<String, Object>> queryMerchantProductTotalAmount(SalesVO salesVo, RowBounds rowBounds);
	
	/**
	 * 导出商家销售明细
	 * @param salesVo
	 * @return
	 */
	public List<SalesVO> queryMerchantSalesDetailExport(SalesVO salesVo);

	
	/**
	 * 统计货品拒收数量(出现异常拒收会找不到对应的订单号)
	 * @param lstExpressCode
	 * @param productNo
	 * @return
	 */
	public int getInRejectionProductQuantity(@Param("lstExpressCode")List<String> lstExpressCode, @Param("productNo")String productNo);
	
	/**
	 * 统计货品退换货数量
	 * @param orderSubNo
	 * @param productNo
	 * @return
	 */
	public int getInReturnProductQuantity(@Param("lstOrderSubNo")List<String> lstOrderSubNo, @Param("productNo")String productNo);
	
	/**
	 * 统计货品拒收数量(出现异常拒收会找不到对应的订单号)
	 * @param lstExpressCode
	 * @param productNo
	 * @return
	 */
	public int getRejectionProductQuantity(@Param("expressCode")String expressCode, @Param("productNo")String productNo);
	
	/**
	 * 统计货品退换货数量
	 * @param orderSubNo
	 * @param productNo
	 * @return
	 */
	public int getReturnProductQuantity(@Param("orderSubNo")String orderSubNo, @Param("productNo")String productNo);
}
