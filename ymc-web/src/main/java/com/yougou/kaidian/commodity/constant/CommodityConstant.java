package com.yougou.kaidian.commodity.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品常量类
 * @author huang.wq
 * 2012-11-13 14:08:44
 */
public class CommodityConstant {

	//----------------------------------------------------------------
	/**
	 * 审核状态，新建
	 */
	public static final String AUDIT_STATUS_DRAFT = "0";
	/**
	 * 审核状态，审核通过
	 */
	public static final String AUDIT_STATUS_PASS = "1";
	/**
	 * 审核状态，审核拒绝
	 */
	public static final String AUDIT_STATUS_REFUSE = "2";
	/**
	 * 审核状态，待审核
	 */
	public static final String AUDIT_STATUS_PENDING = "3";
	/**
	 * 审核状态map
	 */
	public static final Map<String, String> AUDIT_STATUS_NAMES = new HashMap<String, String>();
	static {
		AUDIT_STATUS_NAMES.put(AUDIT_STATUS_DRAFT, "待提交");
		AUDIT_STATUS_NAMES.put(AUDIT_STATUS_PASS, "审核通过");
		AUDIT_STATUS_NAMES.put(AUDIT_STATUS_REFUSE, "审核拒绝");
		AUDIT_STATUS_NAMES.put(AUDIT_STATUS_PENDING, "待审核");
	}
	//----------------------------------------------------------------

	//----------------------------------------------------------------
	/**
	 * 审核状态，已通过（用于DB）
	 */
	public static final int COMMODITY_IS_AUDIT_PASS = 2;
	//----------------------------------------------------------------
	
	//----------------------------------------------------------------
	/** 商品状态，停售 **/
	public static final int COMMODITY_STATUS_SALESTOP = 1;
	/**
	 * 商品状态，新建
	 */
	public static final int COMMODITY_STATUS_DRAFT = 11;
	/**
	 * 商品状态，提交审核
	 */
	public static final int COMMODITY_STATUS_PENDING = 12;
	/**
	 * 商品状态，提交拒绝
	 */
	public static final int COMMODITY_STATUS_REFUSE = 13;
	/**
	 * 待上架（无库存）
	 */
	public static final int COMMODITY_STATUS_WAITTOSTOCK = 4;
	/**
	 * 待上架
	 */
	public static final int COMMODITY_STATUS_WAITTOONSALE = 5;
	
	//----------------------------------------------------------------
	
	//----------------------------------------------------------------
	/**
	 * 更新商品操作类型，7:提交审核
	 */
	public static final String COMMODITY_UPDATE_LOG_TYPE_SUBMIT = "7";
	/**
	 * 更新商品操作类型，8:审核通过
	 */
	public static final String COMMODITY_UPDATE_LOG_TYPE_PASS = "8";
	/**
	 * 更新商品操作类型，9:审核拒绝
	 */
	public static final String COMMODITY_UPDATE_LOG_TYPE_REFUSE = "9";
	//----------------------------------------------------------------
	
	//----------------------------------------------------------------
	/**
	 * 查询商品的页面tabId，未提交审核
	 */
	public static final String QUERY_COMMODITY_PAGE_TAB_ID_DRAFT = "query_goods_draft_commodity";
	/**
	 * 查询商品的页面tabId，待审核
	 */
	public static final String QUERY_COMMODITY_PAGE_TAB_ID_PENDING = "query_goods_pending_commodity";
	/**
	 * 查询商品的页面tabId，审核通过
	 */
	public static final String QUERY_COMMODITY_PAGE_TAB_ID_PASS = "query_goods_pass_commodity";
	/**
	 * 查询商品的页面tabId，审核拒绝
	 */
	public static final String QUERY_COMMODITY_PAGE_TAB_ID_REFUSE = "query_goods_refuse_commodity";
	/**
	 * 查询商品的页面tabId，全部商品
	 */
	public static final String QUERY_COMMODITY_PAGE_TAB_ID_ALL = "query_goods_all_commodity";
	/**
	 * 查询商品页面标签、审核状态 map
	 */
	public static final Map<String, String> QUERY_COMMODITY_PAGE_TAB_AUDIT_STATUS_MAP = new HashMap<String, String>();
	static {
		QUERY_COMMODITY_PAGE_TAB_AUDIT_STATUS_MAP.put(QUERY_COMMODITY_PAGE_TAB_ID_DRAFT, AUDIT_STATUS_DRAFT);
		QUERY_COMMODITY_PAGE_TAB_AUDIT_STATUS_MAP.put(QUERY_COMMODITY_PAGE_TAB_ID_PENDING, AUDIT_STATUS_PENDING);
		QUERY_COMMODITY_PAGE_TAB_AUDIT_STATUS_MAP.put(QUERY_COMMODITY_PAGE_TAB_ID_PASS, AUDIT_STATUS_PASS);
		QUERY_COMMODITY_PAGE_TAB_AUDIT_STATUS_MAP.put(QUERY_COMMODITY_PAGE_TAB_ID_REFUSE, AUDIT_STATUS_REFUSE);
	}
	//----------------------------------------------------------------
	
	
	public static final Map<String, String> COMMODITY_STATUS_MAP = new HashMap<String, String>();
	static {
		COMMODITY_STATUS_MAP.put(String.valueOf(COMMODITY_STATUS_DRAFT), "待提交");
		COMMODITY_STATUS_MAP.put(String.valueOf(COMMODITY_STATUS_PENDING), "待审核");
		COMMODITY_STATUS_MAP.put(String.valueOf(COMMODITY_STATUS_REFUSE), "审核拒绝");
		COMMODITY_STATUS_MAP.put(String.valueOf(COMMODITY_STATUS_SALESTOP), "下架");
		COMMODITY_STATUS_MAP.put(String.valueOf(COMMODITY_STATUS_WAITTOSTOCK), "待上架（无库存）");
		COMMODITY_STATUS_MAP.put(String.valueOf(COMMODITY_STATUS_WAITTOONSALE), "待上架");
	}
	
	
	//----------------------------------------------------------------
	/**
	 * 提交审批时间为NULL时，填上的默认时间
	 */
	public static final String SUBMIT_AUDIT_DATE_NULL_DEFAULT_TIME = "2000-1-1 00:00:00";
	//----------------------------------------------------------------

	//----------------------------------------------------------------
	/**
	 * 查询全部商品操作，是否显示预览
	 */
	public static final String QUERY_COMMODITY_OPT_IS_SHOW_DETAIL = "isShowDetail";
	/**
	 * 查询全部商品操作，是否显示修改
	 */
	public static final String QUERY_COMMODITY_OPT_IS_SHOW_UPDATE = "isShowUpdate";
	/**
	 * 查询全部商品操作，是否显示提交审核
	 */
	public static final String QUERY_COMMODITY_OPT_IS_SHOW_SUBMIT_AUDIT = "isShowSubmitAudit";
	//----------------------------------------------------------------
	
	//----------------------------------------------------------------
	/**
	 * 非全部商品的操作 配置
	 */
	public static final Map<String, Map<String, Boolean>> NOT_ALL_LIST_OPT_CONFIG = new HashMap<String, Map<String,Boolean>>();
	static {
		//预览 操作配置
		Map<String, Boolean> detailOptConfig = new HashMap<String, Boolean>();
		detailOptConfig.put(QUERY_COMMODITY_PAGE_TAB_ID_DRAFT, true);
		detailOptConfig.put(QUERY_COMMODITY_PAGE_TAB_ID_PENDING, true);
		detailOptConfig.put(QUERY_COMMODITY_PAGE_TAB_ID_PASS, true);
		detailOptConfig.put(QUERY_COMMODITY_PAGE_TAB_ID_REFUSE, true);
		detailOptConfig.put(QUERY_COMMODITY_PAGE_TAB_ID_ALL, true);
		NOT_ALL_LIST_OPT_CONFIG.put(QUERY_COMMODITY_OPT_IS_SHOW_DETAIL, detailOptConfig);
		
		//修改 操作配置 
		Map<String, Boolean> updateOptConfig = new HashMap<String, Boolean>();
		updateOptConfig.put(QUERY_COMMODITY_PAGE_TAB_ID_DRAFT, true);
		updateOptConfig.put(QUERY_COMMODITY_PAGE_TAB_ID_REFUSE, true);
		NOT_ALL_LIST_OPT_CONFIG.put(QUERY_COMMODITY_OPT_IS_SHOW_UPDATE, updateOptConfig);
		
		//提交审核操作配置
		Map<String, Boolean> submitAuditOptConfig = new HashMap<String, Boolean>();
		submitAuditOptConfig.put(QUERY_COMMODITY_PAGE_TAB_ID_DRAFT, true);
		NOT_ALL_LIST_OPT_CONFIG.put(QUERY_COMMODITY_OPT_IS_SHOW_SUBMIT_AUDIT, submitAuditOptConfig);
	}
	//----------------------------------------------------------------
	
	//----------------------------------------------------------------
	/**
	 * 全部商品的 操作配置
	 */
	public static final Map<String, Map<String, Boolean>> ALL_LIST_OPT_CONFIG = new HashMap<String, Map<String,Boolean>>();
	static {
		//预览 操作配置
		Map<String, Boolean> detailOptConfig = new HashMap<String, Boolean>();
		detailOptConfig.put(AUDIT_STATUS_DRAFT, true);
		detailOptConfig.put(AUDIT_STATUS_PENDING, true);
		detailOptConfig.put(AUDIT_STATUS_PASS, true);
		detailOptConfig.put(AUDIT_STATUS_REFUSE, true);
		ALL_LIST_OPT_CONFIG.put(QUERY_COMMODITY_OPT_IS_SHOW_DETAIL, detailOptConfig);
		
		//修改 操作配置 
		Map<String, Boolean> updateOptConfig = new HashMap<String, Boolean>();
		updateOptConfig.put(AUDIT_STATUS_DRAFT, true);
		updateOptConfig.put(AUDIT_STATUS_REFUSE, true);
		ALL_LIST_OPT_CONFIG.put(QUERY_COMMODITY_OPT_IS_SHOW_UPDATE, updateOptConfig);
		
		//提交审核操作配置
		Map<String, Boolean> submitAuditOptConfig = new HashMap<String, Boolean>();
		submitAuditOptConfig.put(AUDIT_STATUS_DRAFT, true);
		ALL_LIST_OPT_CONFIG.put(QUERY_COMMODITY_OPT_IS_SHOW_SUBMIT_AUDIT, submitAuditOptConfig);
	}
	//----------------------------------------------------------------

	//----------------------------------------------------------------
	/**
	 * 添加或修改商品，最小年份和当前年份的差值
	 */
	public static final int ADD_OR_UPDATE_COMMODITY_YEAR_GROUND = 4;
	/**
	 * 添加或修改商品，年份列表长度
	 */
	public static final int ADD_OR_UPDATE_COMMODITY_YEAR_LENGTH = 7;
	//----------------------------------------------------------------
	
	//----------------------------------------------------------------
	/**
	 * 添加或修改商品，上传图片的数量
	 */
	public static final int ADD_OR_UPDATE_COMMODITY_IMAGE_COUNT = 7;
	//----------------------------------------------------------------

	//----------------------------------------------------------------
	/**
	 * 配送方，优购配送
	 */
	public static final String ORDER_DISTRIBUTION_SIDE_YOUGOU = "0";
	/**
	 * 配送方，卖家配送
	 */
	public static final String ORDER_DISTRIBUTION_SIDE_MERCHANT = "1";
	/**
	 * 配送方显示名称
	 */
	public static final Map<String, String> ORDER_DISTRIBUTION_SIDE_NAMES = new HashMap<String, String>();
	static {
		ORDER_DISTRIBUTION_SIDE_NAMES.put(ORDER_DISTRIBUTION_SIDE_YOUGOU, "优购配送");
		ORDER_DISTRIBUTION_SIDE_NAMES.put(ORDER_DISTRIBUTION_SIDE_MERCHANT, "卖家配送");
	}
	//----------------------------------------------------------------
	
	//----------------------------------------------------------------
	/**
	 * 添加或删除商品图片input name的前缀
	 */
	public static final String ADD_OR_UPDATE_COMMODITY_IMAGE_INPUT_NAME_PREFIX = "commodityImage_";
	/**
	 * 发布商品上传的图片大小，KB
	 */
	public static final long ADD_OR_UPDATE_COMMODITY_IMAGE_SIZE = 500;

	/**
	 * 发布商品描述图上传的图片大小，KB
	 */
	public static final long ADD_OR_UPDATE_COMMODITY_IMAGE_SIZE_DESC = 1024;
	/**
	 * 发布商品图片宽度，px
	 */
	public static final int ADD_OR_UPDATE_COMMODITY_IMAGE_WIDTH = 1000;
	/**
	 * 发布商品图片高度，px
	 */
	public static final int ADD_OR_UPDATE_COMMODITY_IMAGE_HEIGHT = 1000;
	/**
	 * 商品描述图片大小，KB
	 */
	public static final long DESC_IMG_SIZE = 60;
	/**
	 * 商品描述图片宽度，px
	 */
	public static final int DESC_IMG_WIDTH = 740;
	/**
	 * 商品描述图片最小高度，px
	 */
	public static final int DESC_IMG_MIN_HEIGHT = 100;
	//----------------------------------------------------------------	
	
	//----------------------------------------------------------------
	/**
	 * 列表页图片（1个角度）size
	 */
	public static final String IMG_SIZE_LIST = "imgSizeList"; 
	/**
	 * 商品详细页左边大图（7个角度）size
	 */
	public static final String IMG_SIZE_DETAIL_LEFT = "imgSizeDetailLeft"; 
	/**
	 * 商品缩略小图（7个角度）size
	 */
	public static final String IMG_SIZE_SMALL = "imgSizeSmall"; 
	/**
	 * 商品颜色选择小图（1个角度）size
	 */
	public static final String IMG_SIZE_SMALL_COLOR = "imgSizeSmallColor";
	/**
	 * 放大镜 大图（7个角度）size
	 */
	public static final String IMG_SIZE_BIG_ZOOM = "imgSizeBigZoom";
	/**
	 * 手机 版小图（7个角度）size
	 */
	public static final String IMG_SIZE_MOBILE_SMALL = "imgSizeMobileSmall";
	/**
	 * 手机版小图（7个角度）size
	 */
	public static final String IMG_SIZE_MOBILE_SMALLER = "imgSizeMobileSmaller";
	/**
	 * 后台程序（1个角度）size
	 */
	public static final String IMG_SIZE_BACK = "imgSizeBack";
	/**
	 * 图片size map
	 */
	public static final Map<String, Integer> IMG_SIZE_MAP = new HashMap<String, Integer>();
	static {
		IMG_SIZE_MAP.put(IMG_SIZE_LIST, 160);
		IMG_SIZE_MAP.put(IMG_SIZE_DETAIL_LEFT, 480);
		IMG_SIZE_MAP.put(IMG_SIZE_SMALL, 60);
		IMG_SIZE_MAP.put(IMG_SIZE_SMALL_COLOR, 40);
		IMG_SIZE_MAP.put(IMG_SIZE_BIG_ZOOM, 1000);
		IMG_SIZE_MAP.put(IMG_SIZE_MOBILE_SMALL, 240);
		IMG_SIZE_MAP.put(IMG_SIZE_MOBILE_SMALLER, 160);
		IMG_SIZE_MAP.put(IMG_SIZE_BACK, 100);
	}
	//----------------------------------------------------------------
	
	//----------------------------------------------------------------
	/**
	 * 图片质量
	 */
	public static final float IMG_QUALITY = 0.955f;
	/**
	 * 图片扩展名
	 */
	public static final String IMG_NAME_EXT_TYPE = "jpg";
	/**
	 * 放大镜 大图（7个角度），1000 * 1000 图片的后缀
	 */
	public static final String IMG_NAME_SIZE_1000_1000_SUFFIX = "_l.";
	/**
	 * 商品描述图片后缀
	 */
	public static final String DESC_IMG_NAME_SUFFIX = "_b.";
	/**
	 * 商品描述图片最大数量
	 */
	public static final int DESC_IMG_MAX_COUNT = 999;
	/**
	 * 图片尺寸 / 图片后缀 map
	 */
	public static final Map<String, String> IMG_NAME_SIZE_SUFFIX_MAP = new HashMap<String, String>();
	static {
		IMG_NAME_SIZE_SUFFIX_MAP.put(IMG_SIZE_LIST, "_s.");
		IMG_NAME_SIZE_SUFFIX_MAP.put(IMG_SIZE_DETAIL_LEFT, "_m.");
		IMG_NAME_SIZE_SUFFIX_MAP.put(IMG_SIZE_SMALL, "_t.");
		IMG_NAME_SIZE_SUFFIX_MAP.put(IMG_SIZE_SMALL_COLOR, "_c.");
		IMG_NAME_SIZE_SUFFIX_MAP.put(IMG_SIZE_BIG_ZOOM, "_l.");
		IMG_NAME_SIZE_SUFFIX_MAP.put(IMG_SIZE_MOBILE_SMALL, "_mb.");
		IMG_NAME_SIZE_SUFFIX_MAP.put(IMG_SIZE_MOBILE_SMALLER, "_ms.");
		IMG_NAME_SIZE_SUFFIX_MAP.put(IMG_SIZE_BACK, "_u.");
	}
	
	/**
	 * 生成7个图片的尺寸(除放大镜 大图)
	 */
	public static final String[] IMG_GENERATE_NUMBER_7 = {
		IMG_SIZE_DETAIL_LEFT, IMG_SIZE_SMALL, IMG_SIZE_MOBILE_SMALL, IMG_SIZE_MOBILE_SMALLER
	};
	
	/**
	 * 生成1个图片的尺寸
	 */
	public static final String[] IMG_GENERATE_NUMBER_1 = {
		IMG_SIZE_LIST, IMG_SIZE_SMALL_COLOR, IMG_SIZE_BACK
	};
	//----------------------------------------------------------------
	
	//----------------------------------------------------------------
	/**
	 * 处理图片时的临时文件存放路径
	 */
	public static final String TEMP_FILE_PATH = "/manage/commodity/tmpFile";
	//----------------------------------------------------------------
	
	//----------------------------------------------------------------
	/**
	 * 是否入优购仓库，入优购仓
	 */
	public static final int SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_IN = 1;
	/**
	 * 是否入优购仓库，不入优购仓
	 */
	public static final int SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_NOT_IN = 0;
	
	//----------------------------------------------------------------
	
	//----------------------------------------------------------------
	/**
	 * 商品预览CommodityVO的key
	 */
	public static final String COMMODITY_PREVIEW_COMMODITYVO_KEY = "commodityVo";
	/**
	 * 商品预览commoditySubmitVo的key
	 */
	public static final String COMMODITY_PREVIEW_COMMODITY_SUBMIT_VO_KEY = "commoditySubmitVo";
	//----------------------------------------------------------------
	
	//----------------------------------------------------------------
	/**
	 * 提交商品是否预览，是
	 */
	public static final String SUBMIT_COMMODITY_IS_PREVIEW_TRUE = "1";
	/**
	 * 提交商品，是否为保存并提交审核，是
	 */
	public static final String SUBMIT_COMMODITY_IS_SAVE_SUBMIT_TRUE = "1";
	public static final String SUBMIT_COMMODITY_IS_SAVE_SUBMIT_CONTINUE = "2";
	public static final String SUBMIT_COMMODITY_IS_SAVE_SUBMIT_YOUGOU = "3";
	public static final String SUBMIT_COMMODITY_IS_SAVE_YOUGOU = "0";
	//----------------------------------------------------------------
	
	/** redis分类缓存key */
	//public static final String CATEGORY_CACHE = "categorycache";
	
	/** redis存储商品描述字符串更新key */
	//public static final String COMMODITY_PIC_DESC_KEY_CACHE = "commoditypicdesckeycache";
	/** Session中是否入优购仓对应的key */
	public static final String IS_INPUT_YOUGOU_WAREHOUSE_KEY = "is_input_yougou_warehouse";
	
	public static final String SUCCESS = "success";
	
	public static final String ITEMNAME1 = "所在区域";
	public static final String ITEMNAME2 = "名厂直销品牌";
	public static final String ITEMNAME3 = "名厂直销分类";
	public static final String ITEMNAME4 = "货品来源";
	public static final String SIZE = "size";
	public static final String SIZENAME = "尺码";
	
	/** 工单类型-全部 */
	public static final String WORK_ORDER_TYPE_ALL = "2";
	/** 工单类型-待客服处理*/
	public static final String WORK_ORDER_TYPE_KF = "1";
	/**工单类型-待商家处理 */
	public static final String WORK_ORDER_TYPE_SJ = "0";
}
