package com.yougou.kaidian.commodity.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;

import com.yougou.kaidian.commodity.model.vo.CommodityAndProductExportVo;
import com.yougou.kaidian.commodity.model.vo.CommodityQueryVo;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitVo;
import com.yougou.kaidian.commodity.model.vo.ProductExportVo;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.commodity.pojo.Brand;
import com.yougou.kaidian.common.commodity.pojo.Cat;
import com.yougou.kaidian.common.commodity.pojo.Commodity;
import com.yougou.kaidian.taobao.exception.BusinessException;
import com.yougou.pc.model.category.Category;
import com.yougou.pc.model.prop.PropValue;

public interface ICommodityService {
	
	/**
	 * <p>查询商家已授权品牌列表</p>
	 * 
	 * @param merchantCode
	 * @return <code>List</code>
	 */
	public List<Brand> queryBrandList(String merchantCode);
	
	public List<Cat> queryCatList(Integer structLength, String merchantCode, String structName);
	
	/**
	 * <p>按分类查询下一级已授权分类列表</p>
	 * 
	 * @param merchantCode
	 * @param structName <code>structName: 10</code>
	 * @return <code>结果：[10-11,10-12,10-13]</code
	 */
	List<Cat> queryCatList(String merchantCode, String structName);
	
	/**
	 * 查询一级分类
	 * @param merchantCode
	 * @param brandNo
	 * @return
	 */
	List<Cat> querySupplierFirstCatListByBrandNo(String merchantCode, String brandNo);
	
	/**
	 * <p>查询品牌关联的分类列表</p>
	 * 
	 * @param merchantCode 商家编码
	 * @param brandId  品牌ID
	 * @return category list
	 */
	List<Category> getAllCategoryByBrandId(String merchantCode, String brandId);
	
	public PageFinder<Commodity> queryCommodityList(Query query, CommodityQueryVo commodityQueryVo, ModelMap map);
	
	/**
	 * 根据供应商编码查询相应的货品和商品信息
	 * @param supplyCode 供应商编码
	 * @author wang.m
	 * @Date 2012-05-10
	 * @return
	 */
	public Map<String,Object> getProductBySupplyCode(String supplyCode);
	
	/**
	 * 查询导出在销商品数据（分批导出）
	 * 
	 * @param commodityQueryVo
	 * @return Commodity
	 * @throws Exception
	 */
	List<Commodity> queryExportCommodityList(CommodityQueryVo commodityQueryVo,int size) throws Exception;
	
	/**
	 * 导出货品条码等信息
	 * 
	 * @param commodityQueryVo
	 * @return
	 * @throws Exception
	 */
	List<ProductExportVo> queryExportProductList(CommodityQueryVo commodityQueryVo) throws Exception;
	
	/**
	 * 查询待售商品
	 * @param query 分页类
	 * @param vo 查询商品vo
	 */
	PageFinder<Map<String, Object>> queryWaitSaleCommodityList(Query query, CommodityQueryVo vo, ModelMap modelMap);
	
	/**
	 * 导出待售货品条码等信息
	 * 
	 * @param commodityQueryVo
	 * @return
	 * @throws Exception
	 */
	List<ProductExportVo> exportWaitSaleProductList(CommodityQueryVo commodityQueryVo) throws Exception;
	
	/**
	 * 发送Jms消息(角度图片逻辑处理)
	 * 
	 * @param submitVo
	 * @param oldLength 旧描述字符串的长度
	 */
	void sendJmsForMaster(CommoditySubmitVo submitVo, int oldLength);
	
	/**
	 * 发送Jms消息(批量上传图片逻辑处理)
	 * 
	 * @param submitVo
	 * @param imgType 'bb':批量描述图  'bl':批量角度图
	 */
	void sendJmsForBatch(CommoditySubmitVo submitVo, String imgType,String picUrl);
	
	/**
	 * 发送JSM消息，单个商品上传图片 描述图，角度图
	 * 
	 * @param submitVo
	 */
	void sendJms4SingleCommodityImg(CommoditySubmitVo submitVo)
			throws Exception;
	
	/**
	 * 店铺提醒（商品的数量）
	 * <p>
	 * 1. onSaleCount 在线商品数量(对应上架的商品) <br/>
     * 2. approveCount 审核通过商品数量(is_audit = 2 并且未上架的商品)<br/>
     * 3. refusedCount 审核拒绝商品数量(commodity_status = 13)<br/>
     * 4. newCount 新建商品数量(commodity_status = 11)<br/>
     * 5. waitAuditCount 待审核商品数量(commodity_status = 12)<br/>
     * </p>
	 * @param merchantCode
	 * @return
	 */
	Map<String, Integer> queryShopCommodityTips(String merchantCode);
	
	/**
	 * 根据货品号查询销售数
	 * @param productNo
	 * @return
	 * @throws Exception
	 */
	int querySaleCountByroductNo(String productNo) throws Exception;

	/** 
	 * queryExportProductList:(这里用一句话描述这个方法的作用) 
	 * @author li.n1 
	 * @param commodityQueryVo
	 * @param size 每次查询的数量
	 * @return 
	 * @since JDK 1.6 
	 */  
	public List<ProductExportVo> queryExportProductList(
			CommodityQueryVo commodityQueryVo, int size);

	/** 
	 * exportWaitSaleCommodityList:(这里用一句话描述这个方法的作用) 
	 * @author li.n1 
	 * @param commodityQueryVo
	 * @param i 分批取出的数量
	 * @return 
	 * @since JDK 1.6 
	 */  
	public List<Commodity> exportWaitSaleCommodityList(
			CommodityQueryVo commodityQueryVo, Query query);

	/** 
	 * exportWaitSaleProductList:
	 * @author li.n1 
	 * @param commodityQueryVo
	 * @param i
	 * @return 
	 * @since JDK 1.6 
	 */  
	public List<ProductExportVo> exportWaitSaleProductList(
			CommodityQueryVo commodityQueryVo, Query query);

	/** 
	 * exportWaitSaleCommodityAndProductList:
	 * @author li.n1 
	 * @param commodityQueryVo
	 * @param i
	 * @return 
	 * @since JDK 1.6 
	 */  
	public List<CommodityAndProductExportVo> exportWaitSaleCommodityAndProductList(
			CommodityQueryVo commodityQueryVo, Query query);
	
	public int commodityAutoOffShelves();
	
	public int deleteCommodity2RecycleAuto();
	
	public void updateOnsalePrice(String commodityNo,String merchantCode,String price,String type) throws BusinessException;
	
	/**
	 * getCountByStyleNoAndMerchantCode:
	 * 根据款号与商家编码与颜色查询该款色下有多少个尺码
	 * 根据款号与商家编码与颜色查询该款色下最大的款色编码
	 * @author li.n1  
	 * @param merchantCode 商家编码
	 * @param styleNo 款号
	 * @param color 颜色
	 * @param brandNo 品牌编码
	 * @return 
	 * @since JDK 1.6
	 */
	public Map<String,Object> getCountByStyleNoAndMerchantCodeAndColor(String brandNo, String styleNo, String merchantCode, String color);
	/**
	 * getCountByStyleNoAndMerchantCode:
	 * 根据款号与商家编码查询该款下最大的款色编码
	 * @author li.n1  
	 * @param merchantCode 商家编码
	 * @param styleNo 款号
	 * @param brandNo 品牌编码
	 * @return 
	 * @since JDK 1.6
	 */
	public String getSupplierCodeByStyleNoAndMerchantCode(String brandNo, String styleNo,
			String merchantCode);
	
	/**
	 * check:判断该款在该商家下同色同年份下是否存在商品编码，存在则提示不允许修改 
	 * @author li.n1 
	 * @param styleNo
	 * @param colorName
	 * @param years
	 * @param sizeNo 尺码
	 * @param propList 是否按尺码设置价格
	 * @return true 不存在允许修改，false 存在不允许修改
	 * @since JDK 1.6
	 */
	public boolean check(String merchantCode,String styleNo, String colorName, 
			String years, String[] sizeNo, List<PropValue> propList);
	/**
	 * queryAndVerify:
	 * keyword可能是商品名称、商品款号、商品编号；
	 * 根据keyword查询出的品牌、分类，供应商没有权限，提示；
	 * 根据keyword查询空内容，不存在，提示
	 * @author li.n1 
	 * @param keyword
	 * @param merchantCode 
	 * @return -1  查询为空  0未授权  其他字符串就是授权的品牌
	 * @since JDK 1.6
	 */
	public String queryAndVerify(String keyword, String merchantCode);

}
