package com.belle.other.constant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.belle.other.model.vo.LabelValue;
import com.yougou.ordercenter.constant.OrderSourceConstant;

public class BaseDataManagerConstant {

	/**
	 * WMS所有常量
	 */
	public static final Integer IS_COD = 1; // 支持货到付款常量
	public static final Integer NOT_IS_COD = 2; // 不支持货到付款常量

	public static final Integer IS_USE = 1; // 启用状态
	public static final Integer NOT_IS_USE = 2; // 未启用状态

	public static final Integer IS_YOUGOU_WAREHOUSE = 1; // 是优购仓库

	public static final Integer TABLE_INITIALIZATION_STATUS = 1; // 所有类新增时初始化属性
	public static final String CATB2C_LEVEL = "1"; // 所有类新增时初始化属性
	public static final Integer VIEW_QUERY_PAGESIZE = 10; // 所有页面显示的数据条数
	public static final String VIRTUALWAREHOUSE_SAVE_ERROR = "vw-save-error"; // 虚拟仓库对应的物理仓库已存在
	public static final String VIRTUALWAREHOUSE_SAVE_EXCEPTIONALVALUE = "物理仓库已存在";
	public static final String TABLE_INITIALLZATION_CD_CODE = "01"; // 仓库编号生成策略
	public static final String USERCODE = "123456";
	public static final String TABLE_VIRTUALWAREHOUSE_SAVE_NAME = "平台仓库-";
	public static final String TABLE_VIRTUALWAREHOUSE_SAVE_REAMKE = "自动创建";
	public static final String PREFIX_PACKAGE = "com.belle.wms.stocksmanager.model.pojo.";
	// 后台验证
	public static final String LOGISTICSCOMPANY_ENTITY_NAME_CK_ERROR = "物流公司名称过长";
	public static final String ENTITY_TELPHONE_CK_ERROR = "输入的电话号码格式错误";
	public static final String ENTITY_MOBILEPHONE_CK_ERROR = "输入的手机号码格式错误";
	public static final String ENTITY_QQ_CK_ERROR = "QQ号码格式输入错误";
	public static final String ENTITY_MSN_CK_ERROR = "MSN输入的长度错误";
	public static final String ENTITY_EMAIL_CK_ERROR = "电子邮箱格式输入错误";
	public static final String ENTITY_STATUS_CK_ERROR = "状态必须为数字";
	public static final String ENTITY_ADDRESS_CK_ERROR = "联系地址输入过长";
	// ajax验证
	public static final String LOGISTICSCOMPANY_NAME_EXIST = "物流公司名称已经存在";
	public static final String LOGISTICSCOMPANY_NAME_NOT_EXIST = "物流公司名称可以使用";
	// Areacounty类验证
	public static final String ENTITY_AREACOUNTY_CK_ERROR = "区县名称输入过长";
	// City类验证
	public static final String ENTITY_CITY_CK_ERROR = "城市名称输入过长";
	// CustomerDelivery类验证
	public static final String ENTITY_CUSTOMERDELIVERYCODE_CK_ERROR = "客户配送编号名称输入过程";
	public static final String ENTITY_CUSTOMERDELIVERYCODE_NULL_CK_ERROR = "客户配送编号不能为空";
	public static final String ENTITY_CUSTOMERDELIVERYNAME_CK_ERROR = "客户配送名称长度操作限制";
	public static final String ENTITY_CUSTOMERDELIVERYNAME_NULL_CK_ERROR = "客户配送名称不能为空字符";
	public static final String ENTITY_REMARK_CK_ERROR = "备注输入过长";
	// Distribution类 验证
	public static final String ENTITY_LOGISTICSCOMPANYID_NULL_CK_ERROR = "物流公司编号不能为空";
	public static final String ENTITY_WAREHOUSEID_NULL_CK_ERROR = "物理仓库编号不能为空";
	public static final String ENTITY_distribution_NULL_CK = "内部配送名称输入能为空";
	// DistributionDetail类
	public static final String ENTITY_DISTRIBUTIONAREA_CK = "配送地区名称输入过长";
	// Staff 类
	public static final String ENTITY_STAFFCODE_CK_ERROR = "人员编号必须在1-32个字符之间";
	public static final String ENTITY_STAFFNAME_CK_ERROR = "人员名称输入过长";
	public static final String ENTITY_STAFFNAME_CK_NUMBER_ERROR = "人员名称不能为空";
	public static final String ENTITY_SEX_CK_ERROR = "性别必须为数字";
	public static final String ENTITY_ISWORK_CK_ERROR = "是否在职必须为数字";
	public static final String ENTITY_MERCHANTCODE_CK_ERROR = "商家编号必须在1-32个字符(8个汉字)之间";
	public static final String ENTITY_CREATEDATE_CK_ERROR = "创建日期不能为空";
	public static final String ENTITY_MERCHANTNAME_CK_ERROR = "商家名称必须在1-32个字符(8个汉字)之间";
	// VirtualWarehouse类
	public static final String ENTITY_VIRTUALWAREHOUSECODE_CK_ERROR = "虚拟仓库编号必须在1-32个字符(8个汉字)之间";
	public static final String ENTITY_VIRTUALWAREHOUSENAME_CK_ERROR = "虚拟仓库名称必须在1-32个字符(8个汉字)之间";
	// Warehouse 类
	public static final String ENTITY_WAREHOUSECODE_CK_ERROR = "物理仓库编号必须在1-32个字符(8个汉字)之间";
	public static final String ENTITY_WAREHOUSENAME_CK_ERROR = "物理仓库名称必须在1-32个字符(8个汉字)之间";
	public static final String ENTITY_WAREHOUSEACREAGE_CK_ERROR = "物理仓库面积不能为空且必须为数字";
	public static final String ENTITY_CONTACT_CK_ERROR = "联系人必须在1-20个字符(5个汉字)之间";
	public static final String ENTITY_ISPDA_CK_ERROR = "是否PDA必须为整型";

	// 后台验证
	// 后台验证
	public static final String LOGISTICSCOMPANY_ENTITY_URL_CK_ERROR = "输入的物流公司网址字符长度过长";
	public static final String LOGISTICSCOMPANY_ENTITY_CONTACT_CK_ERROR = "	输入的联系人字符长度过长";

	// 生成流水号时精确到得时间类型yyyyMMddHHmmssSSS
	public static final String DOCUMENTS_NUMBERS_GENERATION_TIME = "yyyyMMdd";
	// 生成流水号时精确到得时间类型yyyyMMddHHmmssSSS
	// 后台验证

	// 异常信息常量
	public static final String VIRTUALWAREHOUSE_SELECT_NULL_ERROR = "没有查询到你需要的平台仓库";
	public static final String WAREHOUSE_SELECT_NULL_ERROR = "没有查询到你需要的PDA仓库";
	public static final String CUSTOMERDELIVERY_SELECT_NULL_ERROR = "没有查询到你需要的客户配送方式";
	public static final String LOGISTICSCOMPANY_SELECT_NULL_ERROR = "没有查询到你需要的物流公司";
	public static final String STAFF_SELECT_NULL_ERROR = "没有查询到你需要的人员信息";
	public static final String BRANDWMS_SELECT_NULL_ERROR = "没有查询到你需要的品牌";
	public static final String CATB2CWMS_SELECT_NULL_ERROR = "没有查询到你需要的类型";

	// 异常信息常量

	// 导出常量

	public static final String DOEXPORTINVENTORYERROR_FILENAME = "errorpos.xls";
	// 导出常量

	// 库存同步数据异常文件模板名称
	public static final String ERROR_FILE_NAME = "errorpos.xls";
	public static final String WAREHOUSEINVENTORYVO_PATH = "com.belle.wms.basedatamanager.model.vo.WarehouseInventoryVo";
	// Web Service 设定每次保存抓取数量
	public static final int JSON_FETCH_SIZE = 350;
	// Web Service 设定每次抓取订单数量
	public static final int ORDER_FETCH_SIZE = 100;
	// Web Service 设定每次抓取采购订单数量
	public static final int PURCHASE_FETCH_SIZE = 1;

	// 出入库单据的单据头
	// 客户订单
	public static final String CUSTOMER_ORDER_CODE_HEAD = "SO";
	// 采购订单
	public static final String PURCHASE_ORDER_CODE_HEAD = "PO";
	// 退残确认单
	public static final String RETURNDEFECT_ORDER_CODE_HEAD = "RO";
	// 供应商残品返回申请单
	public static final String RETURNDEFECT_SP_ORDER_CODE_HEAD = "RS";
	// 采购入库
	public static final String PURCHASE_STORAGE_CODE_HEAD = "PI";
	// 销售订单出库
	public static final String SALE_OUT_STORE_CODE_HEAD = "SD";
	// 销售退货
	public static final String ORDER_RETURN_CODE_HEAD = "SR";
	// 退货入库单
	public static final String ORDER_RETURN_STORAGE_CODE_HEAD = "RI";
	// 采购退货
	public static final String PURCHASE_RETURN_CODE_HEAD = "PB";
	// 采购退货出库
	public static final String PURCHASE_RETURN_OUT_STORE = "BO";
	// 其它入库
	public static final String OTHER_STORAGE_CODE_HEAD = "OI";
	// 其它出库
	public static final String OTHER_OUT_STORE_CODE_HEAD = "OO";
	// 盘点明细
	public static final String CHECK_STORAGE_CODE_HEAD = "CA";
	// 调拨申请
	public static final String ALLOCATION_REQUEST_CODE_HEAD = "AP";
	// 调拨入库
	public static final String ALLOCATION_STORAGE_CODE_HEAD = "AI";
	// 调拨出库
	public static final String ALLOCATION_OUT_STORE_CODE_HEAD = "AO";
	// 手工录入发票号
	public static final String INVOICE_HANDWORK_STORE_CODE_HEAD = "IH";
	// 订单录入发票号
	public static final String INVOICE_ORDER_STORE_CODE_HEAD = "I0";
	// 平台打印发票仓库warehouse_code
	public static final String INVOICE_WAREHOUSECODE_STORE_CODE_HEAD = "PT";
	// 样品及借出
	public static final String SAMPLE_LEND_CODE_HEAD = "SL";

	// 商品维修调出单号
	public static final String PRODUCT_MEND_OUT_CODE_HEAD = "DC";
	// 商品维修返回单号
	public static final String PRODUCT_MEND_BACK_CODE_HEAD = "FH";
	// 质检盘点单号
	public static final String STOCK_COUNT_CODE_HEAD = "SC";
	// 质检商品移交单号
	public static final String QA_TRANSFER_CODE_HEAD = "QT";

	// 淘宝仓库库存调整单号
	public static final String REVISION_INVENTORY_HEAD = "TI";

	// 退换货质检单号
	public static final String RETURN_CHANGE_CODE_HEAD = "RC";

	// 拒收质检单号
	public static final String RETURN_REFUSE_CODE_HEAD = "RF";

	// 退回发货仓单号
	public static final String RETURN_WAREHHOURSE_CODE_HEAD = "BS";

	// 索赔出库单号
	public static final String OUT_PAY_CODE_HEAD = "OP";

	// 残品报废出库单号
	public static final String BAD_OVER_OUT_CODE_HEAD = "BD";

	// 采购退货申请单号
	public static final String PU_RETURN_CODE_HEAD = "PR";

	// 召回申请单号
	public static final String PU_RETURN_RE_CALL_CODE_HEAD = "PRC";

	// ============================== 出入库类型,用于更新库存
	// 入库
	public static final int PUT_IN_STORAGE = 1;
	// 出库
	public static final int PUT_OUT_STORAGE = 2;
	// 订单出库<>
	public static final int ORDER_OUT_STORAGE = 3;
	// 不用预占库存
	public static final int NOT_HAVE_OWN = 0;
	// 要预占库存
	public static final int HAVE_OWN = 1;

	// 出库时,库存中没有改条记录的信息
	public static final String INVENTORY_NON_COMMODITY_RECORD = "库存表中没有此记录,但已出库";

	// 库存预警
	public static final String EARLYWARIN_QUANTITY = "0";
	// 库存小于库存预警
	public static final String EARLYWARIN_MIN = "1";
	// 库存大于库存预警
	public static final String EARLYWARIN_MAX = "2";

	// 出库单分摊状态
	/**
	 * 未分摊
	 */
	public static final Integer DIS_APPORTION = 100;
	/**
	 * 未分摊
	 */
	public static final String DIS_APPORTION_NAME = "未分摊";
	/**
	 * 已分摊
	 */
	public static final Integer APPORTION = 200;
	/**
	 * 已分摊
	 */
	public static final String APPORTION_NAME = "已分摊";
	/**
	 * 数量不足-分摊异常
	 */
	public static final Integer APPORTION_ERROR_LESS = 300;
	/**
	 * 数量不足-分摊异常
	 */
	public static final String APPORTION_ERROR_LESS_NAME = "数量不足-分摊异常";
	/**
	 * 货品不存在-分摊异常
	 */
	public static final Integer APPORTION_ERROR_NULL = 310;
	/**
	 * 货品不存在-分摊异常
	 */
	public static final String APPORTION_ERROR_NULL_NAME = "货品不存在-分摊异常";
	/**
	 * 货品不存在和货品不足-分摊异常
	 */
	public static final Integer APPORTION_ERROR_NULL_LESS = 320;
	/**
	 * 货品不存在和货品不足-分摊异常
	 */
	public static final String APPORTION_ERROR_NULL_LESS_NAME = "货品不存在和货品不足-分摊异常";

	/* 此处用单据新增，审核，退回，初始状态常态变量 */
	/**
	 * 新增
	 */
	public static final int CHECK_NEW = 4;
	/**
	 * 新增
	 */
	public static final String CHECK_NEW_NAME = "待提交";
	/**
	 * 待审核常量
	 */
	public static final int CHECK_INIT = 0;
	/**
	 * 待审核
	 */
	public static final String CHECK_INIT_NAME = "待审核";
	/**
	 * 审核常量
	 */
	public static final int CHECK_APPLY = 1;
	/**
	 * 已审核
	 */
	public static final String CHECK_APPLY_NAME = "已审核";
	/**
	 * 退回常量
	 */
	public static final int CHECK_BACK = 2;
	/**
	 * 已退回
	 */
	public static final String CHECK_BACK_NAME = "已退回";
	/**
	 * 已修改常量
	 */
	public static final int CHECK_UPDATE = 3;
	/**
	 * 已修改
	 */
	public static final String CHECK_UPDATE_NAME = "已修改";
	/**
	 * 已关闭常量
	 */
	public static final int CHECK_CLOSE = 6;
	/**
	 * 已修改
	 */
	public static final String CHECK_CLOSE_NAME = "已关闭";

	/* 入库类型 */
	// ===================(一级分类)==============================
	// ----------->(正常入库)
	/**
	 * 200 INTO_PURCHASE(采购入库)
	 */
	public static final int INTO_PURCHASE = 200;
	/**
	 * 采购入库
	 */
	public static final String INTO_PURCHASE_NAME = "采购入库";

	/**
	 * 260 INTO_SP_RETURN_DEFECT(供应商残品返回入库)
	 */
	public static final int INTO_SP_RETURN_DEFECT = 260;
	/**
	 * 供应商残品返回入库
	 */
	public static final String INTO_SP_RETURN_DEFECT_NAME = "供应商残品返回入库";

	/**
	 * 召回返回入库
	 */
	public static final int INTO_RE_CALL = 270;
	/**
	 * 召回返回入库
	 */
	public static final String INTO_RE_CALL_NAME = "召回返回入库";

	/**
	 * 210 INTO_ORDER_RETURN(销售退货入库)
	 */
	public static final int INTO_ORDER_RETURN = 210;
	/**
	 * 销售退货入库
	 */
	public static final String INTO_ORDER_RETURN_NAME = "销售退货入库";
	// ----------->(其他入库)
	/**
	 * 其他入库(一级)
	 */
	public static final int INTO_OTHER = 1000;
	/**
	 * 310 INTO_CHECK(盘盈入库) (其他入库-二级分类)
	 */
	public static final int INTO_CHECK = 310;
	/**
	 * 盘盈入库
	 */
	public static final String INTO_CHECK_NAME = "其他入库-盘盈入库";
	/**
	 * 320 INTO_GIFT(赠品入库) (其他入库-二级分类)
	 */
	public static final int INTO_GIFT = 320;
	/**
	 * 赠品入库
	 */
	public static final String INTO_GIFT_NAME = "其他入库-赠品入库";
	/**
	 * 340 INTO_SEND_BACK(暂借返还)(其他入库-二级分类)
	 */
	public static final int INTO_SEND_BACK = 340;
	/**
	 * 暂借返还
	 */
	public static final String INTO_SEND_BACK_NAME = "其他入库-暂借返还";
	/**
	 * 330 INTO_CUSTOMER_REFUSED(拒收入库)
	 */
	public static final int INTO_CUSTOMER_REFUSED = 330;
	/**
	 * 拒收入库
	 */
	public static final String INTO_CUSTOMER_REFUSED_NAME = "拒收入库";
	/**
	 * 110 INTO_AREA_WAREHOUSE(地区仓商品入库)
	 */
	public static final int INTO_AREA_WAREHOUSE = 110;
	/**
	 * 地区仓商品入库
	 */
	public static final String INTO_AREA_WAREHOUSE_NAME = "地区仓商品入库";

	/**
	 * 350 INTO_OTHER_OTHER(其他入库的子级其他入库)(其他入库-二级分类)
	 */
	public static final int INTO_OTHER_OTHER = 350;

	/**
	 * 其他入库的子级其他入库
	 */
	public static final String INTO_OTHER_OTHER_NAME = "其他入库-其他入库";

	// ----------->(调拨入库)
	/**
	 * 400 INTO_ALLOCATION(调拨入库)
	 */
	public static final int INTO_ALLOCATION = 400;
	/**
	 * 调拨入库
	 */
	public static final String INTO_ALLOCATION_NAME = "调拨入库";

	// ----------->(样品入库)
	/**
	 * 500 INTO_SAMPLE(样品入库)
	 */
	public static final int INTO_SAMPLE = 500;
	/**
	 * 样品入库
	 */
	public static final String INTO_SAMPLE_NAME = "样品入库";

	// ----------->(借出入库)
	/**
	 * 550 INTO_BORROW(借出入库)
	 */
	public static final int INTO_BORROW = 550;
	/**
	 * 借出入库
	 */
	public static final String INTO_BORROW_NAME = "借出入库";

	// ===================(二级分类)==============================
	/**
	 * 102 INTO_BUYER(自采)
	 */
	public static final int INTO_BUYER = 102;
	/**
	 * 自采
	 */
	public static final String INTO_BUYER_NAME = "自采";
	/**
	 * 106 INTO_PROXY_BUYER(比例代销)
	 */
	public static final int INTO_PROXY_BUYER = 106;
	/**
	 * 比例代销
	 */
	public static final String INTO_PROXY_BUYER_NAME = "比例代销";

	/**
	 * 107 INTO_AGREEMENT_PROXY_BUYER(协议代销)
	 */
	public static final int INTO_AGREEMENT_PROXY_BUYER = 107;
	/**
	 * 协议代销
	 */
	public static final String INTO_AGREEMENT_PROXY_BUYER_NAME = "协议代销";
	// =====================出库类型=========================
	/**
	 * 订单出库（一级分类）
	 */
	public static final int OUT_ORDER = 210;
	/**
	 * 订单出库
	 */
	public static final String OUT_ORDER_NAME = "订单出库";
	/**
	 * 其他出库（一级分类）
	 */
	public static final int OUT_OTHER = 510;
	/**
	 * 调拨出库（一级分类）
	 */
	public static final int OUT_ALLOCATION = 414;
	/**
	 * 调拨出库
	 */
	public static final String OUT_ALLOCATION_NAME = "调拨出库";
	/**
	 * 采购退货出库（一级分类）
	 */
	public static final int OUT_PURCHASE = 310;
	/**
	 * 采购退货出库
	 */
	public static final String OUT_PURCHASE_NAME = "采购退货出库";

	/**
	 * 盘亏出库(其他出库-二级分类)
	 */
	public static final int OUT_OTHER_CHECK = 104;
	/**
	 * 盘亏出库
	 */
	public static final String OUT_OTHER_CHECK_NAME = "其他出库-盘亏出库";
	/**
	 * 报损出库(其他出库-二级分类)
	 */
	public static final int OUT_OTHER_GIFT = 105;
	/**
	 * 报损出库
	 */
	public static final String OUT_OTHER_GIFT_NAME = "其他出库-报损出库";

	/**
	 * 赠送出库(其他出库-二级分类)
	 */
	public static final int OUT_OTHER_PRESENT = 116;
	/**
	 * 赠送出库
	 */
	public static final String OUT_OTHER_PRESENT_NAME = "其他出库-赠送出库";

	/**
	 * 暂借出库(其他出库-二级分类)
	 */
	public static final int OUT_OTHER_BORROW = 117;
	/**
	 * 暂借出库
	 */
	public static final String OUT_OTHER_BORROW_NAME = "其他出库-暂借出库";
	/**
	 * 地区仓商品出库(其他出库-二级分类)
	 */
	public static final int OUT_AREA_WAREHOUSE = 118;
	/**
	 * 地区仓商品出库
	 */
	public static final String OUT_AREA_WAREHOUSE_NAME = "其他出库-地区仓商品出库";

	/**
	 * 119 其他出库子级其他出库
	 */
	public static final int OUT_OTHER_OHER = 119;
	/**
	 * 其他出库
	 */
	public static final String OUT_OTHER_OHER_NAME = "其他出库-其他出库";
	/**
	 * 报废出库(其他出库-二级分类)
	 */
	public static final int OUT_OTHER_BAD = 120;
	/**
	 * 报废出库
	 */
	public static final String OUT_OTHER_BAD_NAME = "其他出库-报废出库";
	/**
	 * 残品销售出库(其他出库-二级分类)
	 */
	public static final int OUT_OTHER_BAD_SALE = 121;
	/**
	 * 残品销售出库
	 */
	public static final String OUT_OTHER_BAD_SALE_NAME = "其他出库-残品销售出库";

	// =====================PDA传过来的单据类型<其它入库>====================
	/**
	 * 赠送入库（赠品）
	 */
	public static final String PRESENT_STORAGE = "01";
	/**
	 * 暂借返还
	 */
	public static final String BORROW_STORAGE = "02";
	/**
	 * 盘盈入库
	 */
	public static final String CHECK_SURPLUS = "03";
	/**
	 * 其它入库
	 */
	public static final String OTHER_INTO = "04";

	// =====================PDA传过来的单据类型<其它出库>====================
	/**
	 * 赠送出库（赠品）
	 */
	public static final String PRESENT_OUT_STORAGE = "01";
	/**
	 * 暂借出库
	 */
	public static final String BORROW_OUT_STORAGE = "02";
	/**
	 * 盘亏出库
	 */
	public static final String CHECK_LOSS = "03";
	/**
	 * 其它出库
	 */
	public static final String OTHER_OUTER = "04";
	/**
	 * 报废出库
	 */
	public static final String BAD_OUT_STORAGE = "05";
	/**
	 * 残品销售出库
	 */
	public static final String BAD_SALE_OUT_STORAGE = "07";
	/**
	 * 地区库出库
	 */
	public static final String OUT_AREA_WAREHOUSE_STORAGE = "4";

	/* 用于物流公司是否存在接口常量，测试用 */
	/**
	 * 物流公司是否有接口 １ 代表有接口，其它均为无接口
	 */
	public static final int LOGISYICS_IS_INTERFACE = 1;

	/**
	 * 实体包路径
	 */
	public static final String CHECK_STOCKS_POJO = "com.belle.wms.stocksmanager.model.pojo.";

	public static final String AREWAREHOUSE_FILE_PATH = File.separator + "tmp" + File.separator + "soft" + File.separator + "wms" + File.separator;
	public static final String AREWAREHOUSE_FILE_PATH_BAK = File.separator + "tmp" + File.separator + "soft" + File.separator + "wmsbak" + File.separator;
	public static final String AREWAREHOUSE_FILE_PATH_ERROR = File.separator + "tmp" + File.separator + "soft" + File.separator + "wmserror" + File.separator;
	public static final String AREWAREHOUSE_FILE_PATH_ERROR_TEMPLATE = "inventoryerrorpostemplate" + File.separator + "errorpos.xls";
	public static final String AREWAREHOUSE_Folder_PATH_ERROR_TEMPLATE = "inventoryerrorpostemplate" + File.separator;
	public static final String FTP_IP = "202.104.147.141";
	public static final int FTP_PORT = 21;
	public static final String FTP_USERNAME = "csv_download";
	public static final String FTP_PASSWORD = "+)s0pP,E.YQiYVF(UAQS=CNy3NZp%N";
	public static final String FTP_FILEPATH = File.separator + "yougou_test";

	public static final String AREA_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	// public static final String
	// AREA_JDBC_URL="jdbc:mysql://172.20.0.121:3306/yitian_b2c_db?useUnicode=true&characterEncoding=utf-8";
	// public static final String AREA_JDBC_USERNAME="belle";
	// public static final String AREA_JDBC_PASSWORD="belle@belle";
	// public static final String AREA_JDBC_BATCH_SIZE="200000";
	public static final String AREA_JDBC_URL = "com.belle.wms.jdbc.url";
	public static final String AREA_JDBC_USERNAME = "com.belle.wms.jdbc.username";
	public static final String AREA_JDBC_PASSWORD = "com.belle.wms.jdbc.password";
	public static final String AREA_JDBC_BATCH_SIZE = "200";

	public static final String DATABASE_CONNECTION_EXCEPTION = "数据库连接异常";
	public static final String DATA_FORMAT_ERROR = "数据格式化错误";

	/**
	 * 地区仓(warehouseDeliveryStatus)出货状态
	 */

	/**
	 * 未出货
	 */
	public static final int WAREHOUSE_DELIVERY_STATUS_NO = 1;
	/**
	 * 出货
	 */
	public static final int WAREHOUSE_DELIVERY_STATUS_YES = 2;
	/**
	 * 生成出库单
	 */
	public static final int WAREHOUSE_DELIVERY_STATUS_CREATE = 3;
	/**
	 * 
	 */
	public static final String NOT_DATA = "没有数据";
	/**
	 * 
	 */
	public static final String WAREHOUSE_TUNE_OUT = "地区仓调出";

	public static final String OUT_ENCODING = "GBK";

	/** 通过抓取类型从数据库中获得抓取数量 */
	public static final String PRODUCT_INFO = "prodcuctInfo"; // 商品档案
	public static final String LOGISTICS_COMPANY = "logisticsCompany"; // 物流公司
	public static final String PRODUCT_TYPE = "productType"; // 商品分类
	public static final String PRODUCT_BRAND = "productBrand"; // 商品品牌
	public static final String SALE_ORDER = "saleOrder"; // 销售订单
	public static final String STAFF_INFO = "staffInfo"; // 人员档案
	public static final String SUPPLIER_INFO = "supplierInfo"; // 供应商
	public static final String TMS_INFO = "tmsInfo"; // 区域配送
	public static final String PURCHASE_ORDERE = "purchaseOrder"; // 采购订单
	public static final String SALE_RETURN = "saleReturn"; // 销售退货
	public static final String SALE_ORDER_CANCEL = "saleOrderCancel"; // 客服取消订单
	public static final String CUSTOMER_REFUSE = "customerRefuse"; // 客户拒收
	public static final String PURCHASE_RETURN = "purchaseReturn"; // 采购退货
	public static final String ALLOCATION_APPLY = "allocationApply"; // 调拨申请
	public static final String WAREHOUSE_INFO = "warehouseInfo"; // 物理仓库
	public static final String AREA_OTHER_OUT_STORE_APPLY = "areaOutStoreApply"; // 地区仓订单商品调入
	public static final String SAMPLE_OUT_APPLY = "sampleOutApply"; // 样品出库申请
	public static final String AREA_DATA_APPLY = "areaDataApply"; // 区域数据
	public static final String ORDER_SOURCE = "orderSource"; // 订单来源
	public static final String ORDER_SOURCE_SELLER = "orderSourceSeller"; // 订单来源店铺
	public static final String PRE_SAMPLE = "preSample"; // 待抽样货品
	public static final String WS_PACKAGE = "wspackage"; // 西街打包数据

	/**
	 * 区域配送和货到付款设置模板位置
	 */
	public static final String TMS_TEMPLATE = "inventoryerrorpostemplate" + File.separator + "3PL.xls";
	public static final String TMS_COD_TEMPLATE = "inventoryerrorpostemplate" + File.separator + "3PL_S.xls";

	/**
	 * 月结提示信息
	 */
	public static final String INVENTORYMONTHKNOT_NO_WAREHOUSE = "没有仓库";
	public static final String INVENTORYMONTHKNOT_NO_INVENTORY = "仓库中没有库存，不能结算";
	public static final String INVENTORYMONTHKNOT_THISMONTH_OK = "本月已经月结过";
	public static final String INVENTORYMONTHKNOT_THISMONTH_SUCCESS = "本月月结成功";
	public static final String INVENTORYMONTHKNOT_THISMONTH_NOTO = "本月未到月结日，不能结算";
	public static final String INVENTORYMONTHKNOT_LASTMONTH_SUCCESS = "上月月结成功";
	public static final String INVENTORYMONTHKNOT_ERROR = "系统故障，请联系管理员或稍候重试";

	/**
	 * 查询库存信息失败
	 */
	public static final String INVENTORY_QUERY_ERROR = "查询库存信息失败";

	/**
	 * 查询库存成本失败
	 */
	public static final String INVENTORY_COST_QUERY_ERROR = "查询库存成本失败";
	public static final String INVENTORY_COST_EXPORT_ERROR = "导出库存成本失败";

	/** <<BEGIN>> compose by ching * */

	/**
	 * 911 INTO_SALE_ORDER_RETURN_IN 销售退货入库(新流程)
	 */
	public static final int INTO_SALE_ORDER_RETURN_IN = 911;

	/**
	 * pda_wms_b2c_wms_key WSTP_KEY PDA&WMS与B2C&WMS通信密匙
	 */
	public static final String WSTP_KEY = "pda_wms_b2c_wms_key";

	/** << END >> compose by ching * */

	/** 出入库类型列表 */
	public static final List<LabelValue> stockTypeList() {
		List<LabelValue> storageTypes = new ArrayList<LabelValue>();

		storageTypes.add(new LabelValue("调拨出库", "S01"));
		storageTypes.add(new LabelValue("销售出库", "S02"));
		storageTypes.add(new LabelValue("其他出库", "S03"));
		storageTypes.add(new LabelValue("采购退货出库", "S04"));
		storageTypes.add(new LabelValue("样品出库", "S05"));
		storageTypes.add(new LabelValue("残转正", "P01"));
		storageTypes.add(new LabelValue("正转残", "P02"));
		storageTypes.add(new LabelValue("地区仓入库", "110"));
		storageTypes.add(new LabelValue("采购入库", "200"));
		storageTypes.add(new LabelValue("销售退货入库", "210"));
		storageTypes.add(new LabelValue("盘盈入库", "310"));
		storageTypes.add(new LabelValue("赠品入库", "320"));
		storageTypes.add(new LabelValue("拒收入库", "330"));
		storageTypes.add(new LabelValue("暂借返还", "340"));
		storageTypes.add(new LabelValue("其他入库", "350"));
		storageTypes.add(new LabelValue("调拨入库", "400"));
		storageTypes.add(new LabelValue("样品入库", "500"));
		return storageTypes;
	}

	/* 质检仓盘点 */
	// 盘点类型
	/**
	 * 全盘
	 */
	public static final String SC_TYPE_ALL = "ALL";
	public static final String SC_TYPE_ALL_NAME = "全盘";
	/**
	 * 非全盘
	 */
	public static final String SC_TYPE_NOT_ALL = "NOT_ALL";
	public static final String SC_TYPE_NOT_ALL_NAME = "非全盘";

	// 盘点状态
	/**
	 * 盘点中
	 */
	public static final String SC_STATUS_COUNTING = "COUNTING";
	public static final String SC_STATUS_COUNTING_NAME = "盘点中";
	/**
	 * 待审核
	 */
	public static final String SC_STATUS_WAIT_CHECK = "WAIT_CHECK";
	public static final String SC_STATUS_WAIT_CHECK_NAME = "提交待审核";
	/**
	 * 已审核
	 */
	public static final String SC_STATUS_PASS_CHECK = "PASS_CHECK";
	public static final String SC_STATUS_PASS_CHECK_NAME = "已审核";

	/* 质检仓盘点 */

	/* 结算分摊 */

	// 出库类型
	public static final String AP_OUT_SALE = "SALE";
	public static final String AP_OUT_NAME = "销售出库";

	public static final String AP_OUT_QA_RETURN = "QA_RETURN";
	public static final String AP_OUT_QA_RETURN_NAME = "销售退换货质检";

	public static final String AP_OUT_QA_REFUSE = "QA_REFUSE";
	public static final String AP_OUT_QA_REFUSE_NAME = "销售拒收质检";

	public static final String AP_OUT_LESS = "LESS";
	public static final String AP_OUT_LESS_NAME = "盘亏";

	public static final String AP_OUT_PS_RETURN = "PS_RETURN";
	public static final String AP_OUT_PS_RETURN_NAME = "采购退货";
	// 类型

	/* 结算分摊 */
	/* 销售类型 */

	// 品牌特卖
	public static final String SALE_TYPE_PPTM = "PPTM";

	public static final String SALE_TYPE_MERCHANT = "MERCHANT";

	/* 调拨单据状态 */
	/**
	 * 新增
	 */
	public static final int AL_STATUS_NEW = 4;
	public static final String AL_STATUS_NEW_NAME = "新增";

	/**
	 * 待审核
	 */
	public static final int AL_STATUS_INIT = 0;
	public static final String AL_STATUS_INIT_NAME = "待审核";

	/**
	 * 已审核
	 */
	public static final int AL_STATUS_APPLY = 1;
	public static final String AL_STATUS_APPLY_NAME = "已审核";

	/**
	 * 已回退
	 */
	public static final int AL_STATUS_BACK = 2;
	public static final String AL_STATUS_BACK_NAME = "已退回";

	/**
	 * 已出库
	 */
	public static final int AL_STATUS_OUT_BACK = 5;
	public static final String AL_STATUS_OUT_NAME = "已出库";

	/**
	 * 已关闭
	 */
	public static final int AL_STATUS_CLOSE = 6;
	public static final String AL_STATUS_CLOSE_NAME = "已关闭";

	/* 退回仓库 */
	// 状态
	public static final String BACKW_STATUS_ALL = "ALL";
	public static final String BACKW_STATUS_ALL_NAME = "全部";

	public static final String BACKW_STATUS_CONFIRM = "CONFIRM";
	public static final String BACKW_STATUS_CONFIRM_NAME = "已确认";

	public static final String BACKW_STATUS_UNCONFIRM = "UNCONFIRM";
	public static final String BACKW_STATUS_UNCONFIRM_NAME = "待确认";
	// 类别
	public static final String BACKW_TYPE_CERTIFIED = "SDARD_GOODS";
	public static final String BACKW_TYPE_CERTIFIED_NAME = "正品";

	public static final String BACKW_TYPE_SUBSTANDARD = "UNSDARD_GOODS";
	public static final String BACKW_TYPE_SUBSTANDARD_NAME = "残品";

	// 抽样类型
	/**
	 * 首次抽样
	 */
	public static final int SAMPLE_FIRST = 1;
	/**
	 * 二次抽样
	 */
	public static final int SAMPLE_SECOND = 2;

	/**
	 * 未同步
	 */
	public static final int SS_NO_SYNC = 0;
	/**
	 * 已同步
	 */
	public static final int SS_SYNC = 1;

	// 3:RETURN_WARE_NEW 待归还仓库,RETURN_WARE_STORAGE 仓库已入库
	/**
	 * 待仓库抽样
	 */
	public static final int SAMPLE_WAREHOUSE_SAMPL_NEW = 0;
	/**
	 * 待摄影收样
	 */
	public static final int SAMPLE_PHOTO_SAMPL_NEW = 1;
	/**
	 * 待摄影拍摄
	 */
	public static final int SAMPLE_PHOTO_PHOTO_NEW = 2;
	/**
	 * 待归还仓库
	 */
	public static final int SAMPLE_RETURN_WARE_NEW = 3;
	/**
	 * 仓库已入库
	 */
	public static final int SAMPLE_RETURN_WARE_STORAGE = 4;
	/**
	 * 已作废
	 */
	public static final int QC_CANCEL = 1;
	/**
	 * 未作废
	 */
	public static final int QC_NO_CANCEL = 0;
	/**
	 * 对冲单
	 */
	public static final int QC_OFFSET = 1;
	/**
	 * 非对冲单
	 */
	public static final int QC_NO_OFFSET = 0;
	/**
	 * 二次审核提交
	 */
	public static final int QC_SEC_WAIT = 1;
	/**
	 * 二次审核打回
	 */
	public static final int QC_SEC_FAIL = 2;
	/**
	 * 二次抽样（借出申请）
	 */
	public static final String LEND_SEC_SAMPLE = "sample";

	/* 供应商样品登记 */
	/**
	 * 待确认
	 */
	public static final int SS_STATUS_WAIT = 0;
	/**
	 * 确认
	 */
	public static final int SS_STATUS_PASS = 1;
	/**
	 * 是(是否提供相片)
	 */
	public static final int SS_PHOTO_SUPPORT = 1;
	/**
	 * 否(是否提供相片)
	 */
	public static final int SS_PHOTO_NO_SUPPORT = 0;
	/**
	 * 是(文案)
	 */
	public static final int SS_DOC = 1;
	/**
	 * 否(文案)
	 */
	public static final int SS_NO_DOC = 0;
	/**
	 * 静物
	 */
	public static final int SS_GOODS = 0;
	/**
	 * 模特
	 */
	public static final int SS_GIRL = 1;
	/**
	 * 静物和模特
	 */
	public static final int SS_GOODS_GIRL = 2;
	/**
	 * 常规
	 */
	public static final int SS_NORMAL = 0;
	/**
	 * 精编
	 */
	public static final int SS_MORE = 1;
	/* 供应商样品登记 */
	/* 借出和领用 */
	/**
	 * 借出
	 */
	public static final String RESERVE = "reserve";
	/**
	 * 领用
	 */
	public static final String USED = "used";
	/**
	 * 样品
	 */
	public static final String SAMPLE = "sample";

	/* 借出和领用 */
	/**
	 * 删除类型-采购单
	 */
	public static final String DEL_TYPE_PS = "01";
	/**
	 * 删除类型-首次抽样商品
	 */
	public static final String DEL_TYPE_FIRST = "02";
	/**
	 * 删除类型-发货单
	 */
	public static final String DEL_TYPE_PSS = "03";

	/**
	 * 借出和领用的申请
	 */
	public static final String SAMPLELEND_TYPE_APPLY = "apply";

	/**
	 * 借出和领用的审核
	 */
	public static final String SAMPLELEND_TYPE_CHECK = "check";

	public static final String SEND_BY_OTHER_BRAND = "sendByOtherBrand";

	// 入库
	public static final String OPERATION_IN_STORAGE = "IN";

	// 出库
	public static final String OPERATION_OUT_STORAGE = "OUT";

	// 打标判断
	public static final String WPI_VALIDATE_DO_MARK = "wpi_validate_do_mark";

	/**
	 * 区域默认选项
	 */
	public static final String AREA_DEFAULT = "-1";

	/**
	 * 调拨出库 - 是否入库 1：已入库
	 */
	public static final int ALLOCATION_OUT_STORE_IS_INSTORE = 1;

	/**
	 * 查询问题原因列表的默认值
	 */
	public final static String STRUTS_CODE = "10";
	/**
	 * 新建
	 */
	public static final int PR_NEW = 0;
	/**
	 * 待审核
	 */
	public static final int PR_SUBMIT = 1;
	/**
	 * 已审核
	 */
	public static final int PR_PASS = 2;
	/**
	 * 已回退
	 */
	public static final int PR_FAIL = 3;

	/**
	 * 质检商品移交状态 NEW：新建
	 */
	public static final String TRANSFER_STATUS_NEW = "NEW";

	/**
	 * 优购
	 */
	public static final String ORDER_SOURCE_YOUGOU = OrderSourceConstant.YG.getNo();

	/**
	 * 淘宝
	 */
	public static final String ORDER_SOURCE_TAOBAO = OrderSourceConstant.TB.getNo();

	/**
	 * 当当客户订单
	 */
	public static final String ORDER_SOUECE_DANGDANG = OrderSourceConstant.WBPT_DD.getNo();

	/**
	 * 西街
	 * 
	 */
	public static final String ORDER_SOUECE_XIJIE = OrderSourceConstant.FXPT_XJ.getNo();

	/**
	 * V+
	 */
	public static final String ORDER_SOUECE_VJIA = OrderSourceConstant.WBPT_FKVjia.getNo();

	/**
	 * 移动商城网站
	 */
	public static final String ORDER_SOUECE_YDSC = OrderSourceConstant.WBPT_YDSC_YDSC.getNo();
	/**
	 * 优购时尚商城
	 */
	public static final String ORDER_SOURCE_YOUGOU_YGWSSC = OrderSourceConstant.YG_YGWSSC_YGWSSC.getNo();
	/**
	 * 优购客服单
	 */
	public static final String ORDER_SOURCE_YOUGOU_YGKF = OrderSourceConstant.YG_YGKF_YGKF.getNo();
	/**
	 * 优购特卖商城
	 */
	public static final String ORDER_SOURCE_YOUGOU_YGAL = OrderSourceConstant.YG_YGAL_YGAL.getNo();
	/**
	 * 手机优购时尚商城-iOS
	 */
	public static final String ORDER_SOURCE_YOUGOU_IOS = OrderSourceConstant.YG_SJYGSSSC_iOS.getNo();
	/**
	 * 手机优购时尚商城-Android
	 */
	public static final String ORDER_SOURCE_YOUGOU_ANDROID = OrderSourceConstant.YG_SJYGSSSC_Android.getNo();
	/**
	 * 手机WAP
	 */
	public static final String ORDER_SOURCE_YOUGOU_WAP = OrderSourceConstant.YG_SJWAP_SJWAP.getNo();

}
