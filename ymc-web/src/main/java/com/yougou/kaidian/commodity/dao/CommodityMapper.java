package com.yougou.kaidian.commodity.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.commodity.pojo.Commodity;
import com.yougou.kaidian.commodity.model.vo.CommodityAndProductExportVo;
import com.yougou.kaidian.commodity.model.vo.CommodityQueryVo;
import com.yougou.kaidian.commodity.model.vo.ProductExportVo;
import com.yougou.pc.model.prop.PropValue;

public interface CommodityMapper {
	/**
	 * 通过商品货品条码查询货品信息
	 * creator liuwenjun
	 * create time 2012-5-3 上午10:37:57
	 * @param thirdPartyCode
	 * @return
	 */
	public Map<String,Object> getProductByPartyCode(String thirdPartyCode) ;
	
	public List<Commodity> queryCommodityList(@Param("param") CommodityQueryVo commodityQueryVo,@Param("query") Query query);
	
	public int queryCommodityCount(@Param("param") CommodityQueryVo commodityQueryVo);

	/**
	 * 根据货品条码查询相应的货品和商品信息
	 * @param productNo 货品条码
	 * @author wang.m
	 * @Date 2012-05-10
	 * @return
	 */
	public Map<String,Object> getProductByprouductNo(String productNo);
	
	/**
	 * 查询导出在销商品数据
	 * 
	 * @param commodityQueryVo
	 * @param list4 
	 * @param list3 
	 * @param list2 
	 * @param list 
	 * @return Commodity
	 * @throws Exception
	 */
	List<Commodity> queryExportCommodityList(@Param("vo") CommodityQueryVo commodityQueryVo,@Param("query") Query query) throws Exception;
	/**
	 * 查询待售商品
	 * 
	 * @param vo 查询Vo
	 * @param rowBounds 分页边界
	 * @return 返回待售商品列表
	 */
	List<Map<String, Object>> queryWaitSaleCommodity(@Param("param") CommodityQueryVo vo,@Param("query") Query query);
	
	/**
	 * 查询待售商品的总记录数
	 * 
	 * @param vo 查询Vo
	 * @return 待售查询总记录数
	 */
	Integer queryWaitSaleCommodityCount(@Param("param") CommodityQueryVo vo);
	
	/**
	 * 导出在售商品的货品条码信息
	 * 
	 * @param vo
	 * @return
	 */
	List<ProductExportVo> queryExportProductList(CommodityQueryVo vo);
	
	List<ProductExportVo> exportWaitSaleProductList(CommodityQueryVo vo);
	
	int querySaleCountByroductNo(String productNo) throws Exception;

	/** 
	 * exportWaitSaleCommodityList:
	 * @author li.n1 
	 * @param commodityQueryVo
	 * @param list3 
	 * @param list2 
	 * @param list 
	 * @param rowBounds
	 * @return 
	 * @since JDK 1.6 
	 */  
	public List<Commodity> exportWaitSaleCommodityList(
			@Param("param") CommodityQueryVo vo,@Param("query") Query query);

	/** 
	 * queryProductCountByExport:
	 * @author li.n1 
	 * @param commodityQueryVo
	 * @return 
	 * @since JDK 1.6 
	 */  
	public int queryProductCountByExport(@Param("vo") CommodityQueryVo commodityQueryVo);

	/** 
	 * queryWaitSaleCommodityCountByExport:(这里用一句话描述这个方法的作用) 
	 * @author li.n1 
	 * @param commodityQueryVo
	 * @param list3 
	 * @param list2 
	 * @param list 
	 * @return 
	 * @since JDK 1.6 
	 */  
	public int queryWaitSaleCommodityCountByExport(@Param("param") CommodityQueryVo vo);

	/** 
	 * queryExportProductList:在售商品分货品条码分批导出 
	 * @author li.n1 
	 * @param commodityQueryVo
	 * @param rowBounds
	 * @return 
	 * @since JDK 1.6 
	 */  
	public List<ProductExportVo> queryExportProductList(
			@Param("vo") CommodityQueryVo commodityQueryVo,@Param("query") Query query);
	/** 
	 * queryWaitSaleProductCountByExport:(这里用一句话描述这个方法的作用) 
	 * @author li.n1 
	 * @param commodityQueryVo
	 * @return 
	 * @since JDK 1.6 
	 */  
	public int queryWaitSaleProductCountByExport(
			CommodityQueryVo commodityQueryVo);
	/** 
	 * exportWaitSaleProductList:
	 * @author li.n1 
	 * @param commodityQueryVo
	 * @param rowBounds
	 * @return 
	 * @since JDK 1.6 
	 */  
	public List<ProductExportVo> exportWaitSaleProductList(
			CommodityQueryVo commodityQueryVo, Query query);
	/** 
	 * queryCommodityCountByExport:
	 * @author li.n1 
	 * @param commodityQueryVo
	 * @param list4 
	 * @param list3 
	 * @param list2 
	 * @param list 
	 * @return 
	 * @since JDK 1.6 
	 */  
	public int queryCommodityCountByExport(@Param("vo") CommodityQueryVo commodityQueryVo);
	/** 
	 * queryWaitSaleCommodityAndProductCountByExport:
	 * @author li.n1 
	 * @param commodityQueryVo
	 * @param list3 
	 * @param list2 
	 * @param list 
	 * @return 
	 * @since JDK 1.6 
	 */  
	public int queryWaitSaleCommodityAndProductCountByExport(
			@Param("param") CommodityQueryVo vo,@Param("commodityNoList")List<String> commodityNoList,
			@Param("supplierCodeList")List<String> supplierCodeList,@Param("styleNoList")List<String> styleNoList,
			@Param("thirdPartyCodeList")List<String> thirdPartyCodeList);
	/** 
	 * exportWaitSaleCommodityAndProductList: 
	 * @author li.n1 
	 * @param commodityQueryVo
	 * @param list3 
	 * @param list2 
	 * @param list 
	 * @param rowBounds
	 * @return 
	 * @since JDK 1.6 
	 */  
	public List<CommodityAndProductExportVo> exportWaitSaleCommodityAndProductList(
			@Param("param") CommodityQueryVo vo,@Param("commodityNoList")List<String> commodityNoList,
			@Param("supplierCodeList")List<String> supplierCodeList,
			@Param("styleNoList")List<String> styleNoList,
			@Param("thirdPartyCodeList")List<String> thirdPartyCodeList,
			Query query);
	
	/**
	 * 系统自动下架，自动删除商品
	 * @return
	 */
	public int select30DaysNoUpdateCommodityCount();
	
	public List<Commodity>select30DaysNoUpdateCommodity(RowBounds rowBounds);
	
	public int select60DaysNoUpdateCommodityCount4WaitSale();
	
	public List<Commodity>select60DaysNoUpdateCommodity4WaitSale(RowBounds rowBounds);

	public String getSupplierCodeByStyleNoAndMerchantCode(@Param("brandNo") String brandNo,
			@Param("styleNo") String styleNo, @Param("merchantCode") String merchantCode);

	public Map<String,Object> getCountByStyleNoAndMerchantCodeAndColor(@Param("brandNo") String brandNo,
			@Param("styleNo") String styleNo, 
			@Param("merchantCode") String merchantCode, 
			@Param("color") String color);

	public int getCommoditySize(@Param("merchantCode") String merchantCode, @Param("styleNo") String styleNo,
			@Param("color") String colorName,
			@Param("years") String years,
			@Param("sizeNo") String sizeNo,
			@Param("propList") List<PropValue> propList);
	public List<Map<String, Object>> queryCommodityByKeyword(@Param("keyword") String keyword,
			@Param("merchantCode") String merchantCode);
	/**
	 * getCommodityStatusInfoByMerchantCode:根据商家编码，查询商品的各种状态下的数量
	 * @author li.n1 
	 * @param merchantCode
	 * @return 
	 * @since JDK 1.6
	 */
	public List<Map<String, Object>> getCommodityStatusInfoByMerchantCode(
			String merchantCode);
}
