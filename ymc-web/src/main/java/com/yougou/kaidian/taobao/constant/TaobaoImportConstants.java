package com.yougou.kaidian.taobao.constant;

/**
 * 淘宝导入 常量 和公用类
 * 
 * @author le.sm
 * 
 */
public interface TaobaoImportConstants {
	// 淘宝属性中目前已知的颜色属性的pid
	 String[] COLOR_PIDS = { "1627207", "1627975", "122276380", "1626984",
			"1627843", "6986510" };

	/**
	 * 单个线程执行的条数
	 */
	int ONE_THREAD_EXECTOR_NUM = 100;

	/**
	 * 线程最大数
	 */
	int THREAD_MAX = 5;
	/**
	 * 线程等待时间
	 */
	 int WAIT_TIME = 1;

	/**
	 * CPU个数
	 */
	 int cpuNums = Runtime.getRuntime().availableProcessors();

	/**
	 * 淘宝查询时候用的P_ID
	 */
	 long BRAND_P_ID = 20000L;
	/**
	 * contain p_id
	 */
	 String P_ID_SEARCH = "20000";

	/**
	 * CSV数据下标
	 */
	 int CSV_INDEX = 4;
	/**
	 *  针对淘宝靴子分类特除处理
	 */
	long CID_SP = 50012028L;
	
	
	// 单选name 前缀
	 String GOODS_PROP_SELECT = "goods_prop_select_";
	// 复选 name 前缀
	 String GOODS_PROP_CHECKBOX = "goods_prop_checkbox_";
	 String SIZE = "size_";
	 String GOODS_STOCK = "stock_";
	 String GOODS_THIRDPARTYCODE = "goods_thirdPartyCode_";
	
	
	 //商品状态
	 /**
	  * 校验未通过
	  */
	 String STATUS_NOT_ALLOW = "0" ; 
	 /**
	  * 校验通过 可以导入到优购
	 **
	 */
	 String STATUS_ALLOW = "1";
	 /**
	  * 
	       导入商品未处理图片
	  */
	 String STATUS_IMPORT_NOT_IMG = "2";
	
	 /**
	  * 款号
	  */
	 String TAOBAO_STYLE_NO = "13021751";
}
