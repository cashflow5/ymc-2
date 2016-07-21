package com.yougou.kaidian.order.service;

import java.util.List;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.order.model.SalesVO;

/**
 * @directions:销售service
 * @author： daixiaowei
 * @create： 2012-3-14 下午07:42:01
 * @history：
 * @version:
 */
public interface ISalesService {

	/**
	 * 查询所有的销售列表
	 * @param SalesVO
	 * @param query
	 * @return
	 */
	public PageFinder<SalesVO> queryMerchantProductSales(SalesVO salesVO, Query query) throws Exception;

	/**
	 * 查询所有的销售列表 导出报表
	 * @param salesVO
	 * @param unionUser
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> queryMerchantSalesExport(SalesVO salesVO) throws Exception;
	/**
	 * queryMerchantSalesCount:查询所有的销售列表的数量count
	 * @author li.n1 
	 * @param salesVO
	 * @return 
	 * @since JDK 1.6
	 */
	public int queryMerchantSalesCount(SalesVO salesVO) ;

	/**
	 * 查询销售明细
	 * @param query
	 * @return
	 */
	public PageFinder<SalesVO> querySalesDetailByProductNo(SalesVO salesVO, Query query) throws Exception;

	/**
	 * 按货品导出销售明细
	 * @param salesVO
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> queryMerchantSalesDetailExport(SalesVO salesVO) throws Exception;
}
