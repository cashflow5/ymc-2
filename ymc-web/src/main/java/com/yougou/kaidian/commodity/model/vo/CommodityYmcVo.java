package com.yougou.kaidian.commodity.model.vo;
/**
 * 
 * @author liang.yx
 *
 */
public class CommodityYmcVo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**商品发布改版增加的属性 begin**/
	/**三级分类 id*/
	private String catId;
	
	/**三级分类 no*/
	private String catNo;
	
	/**三级分类 名称*/
	private String catName;
	
	/**三级分类 结构*/
	private String catStructName;
	
	/**多个商品的优购价*/
	private String[] salePriceArr;
	
	/**多个商品的市场价*/
	private String[] publicPriceArr;
	
	/**品牌id*/
	private String brandId;
	
	/**品牌编号*/
	private String brandNo;
	
	/**品牌名称*/
	private String brandName;
	 
	/**二级分类名称*/
	private String secondCatName;
	
	/**一级分类名称*/
	private String rootCatName;
	
	/**商品名称*/
	private String commodityName;
	
	/**商品卖点*/
	private String sellingPoint;
	
	/**商品款号*/
	private String styleNo;
	
	/**上市年份*/
	private String years;
	
	
	

}
