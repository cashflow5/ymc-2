package com.belle.yitiansystem.constant;

import com.yougou.ordercenter.constant.OrderSourceConstant;


public class OrderConstant {

	/** 订单日志是否显示+订单是否逻辑删除 */
	public static final String ORDER_SHOW_TRUE = "1";// 显示
	public static final String ORDER_SHOW_FALSE = "0";// 不显示
	/**
	 *商家店铺编号
	 */
	public static final String ORDER_SELLER_MERCHANT_CODE = "10000";
	/**
	 *【系统配置】订单号使用新规则配置项
	 */
	public static final String USE_NEW_ORDERNO_RULE = "USE_NEW_ORDERNO_RULE";
	/**
	 * 订单过滤开关
	 */
	public static boolean switchFlag = true;
	/**
	 * 生成订单成功
	 */
	public static final String CREAT_ORDER_SUCCES = "生成订单成功";
	/**
	 * 生成订单失败
	 */
	public static final String CREAT_ORDER_ERROR = "生成订单失败";
	/******************************** 支付方式 ****************************/
	/**
	 * 在线支付
	 */
	public static final int ONLINE_PAYMENT = 1;
	/**
	 * 货到付款
	 */
	public static final int CASH_AGAINST_DELIVERY = 2;
	/**
	 * 银行转帐
	 */
	public static final int BANK_TRANSFER = 3;
	/**
	 * 邮局汇款
	 */
	public static final int WANG_POS = 4;
	/**
	 * 线下转账
	 */
	public static final int OFFLINE_TRANSFER=5;
	
	/******************************** 支付方式 名称 ****************************/
	public static final String ONLINE_PAYMENT_VALUE = "在线支付";
	public static final String CASH_AGAINST_DELIVERY_VALUE = "货到付款";
	public static final String BANK_TRANSFER_VALUE = "银行转帐";
	public static final String WANG_POS_VALUE = "邮局汇款";
	/******************************** 快递公司 ****************************/
	/**
	 * 加急快递
	 */
	public static final int SHIP_SPEED = 0; // 已废弃
	/**
	 * 普通快递
	 */
	public static final int SHIP_COMMON = 1;
	/**
	 * 顺丰
	 */
	public static final int SHIP_SF = 2;
	/**
	 * EMS
	 */
	public static final int SHIP_EMS = 3;
	/******************************** 快递公司 ****************************/
	public static final String SHIP_COMMON_DISPLAY = "普通快递";
	public static final String SHIP_SPEED_DISPLAY = "加急快递"; // 已废弃
	public static final String SHIP_SF_DISPLAY = "顺丰专递";
	public static final String SHIP_EMS_DISPLAY = "EMS专递";
	/*** 快递标识No **/
	/* 普通快递 */
	public static final String SHIP_COMMON_DISPLAY_NO = "PT";
	/* 顺丰专递 */
	public static final String SHIP_SF_DISPLAY_NO = "SF";
	/* EMS专递 */
	public static final String SHIP_EMS_DISPLAY_NO = "EMS";
	/**
	 * 订单在线支付过滤限制最低价
	 */
	public static final Double ONLINE_ORDER_LOW_PRICE = 85.0;
	
	// 【系统配置】订单最低金额限制
	public static final String MININUM_ORDER_AMOUNT = "MININUM_ORDER_AMOUNT";
	
	// 【系统配置】订单最高金额限制
	public static final String MAXIMUM_ORDER_AMOUNT = "MAXIMUM_ORDER_AMOUNT";
	
	/**************************** 订单过滤 ***********************************************************/
	/** 订单过滤成功 key */
	public static final String ORDER_FILTER_SUCCESS = "success";

	/** 订单过滤失败 key */
	public static final String ORDER_FILTER_FAIL = "fail";

	/** 订单过滤数据 key **/
	public static final String ORDER_DATA = "order";

	public static final String ZERO_DAYS = "0";
	/**
	 * 过去三十天的订单
	 */
	public static final String THREE_DAYS = "1";
	/**
	 * 过去半年的订单
	 */
	public static final String HALF_YEARS = "2";
	/**
	 * 过去第一年的的订单
	 */
	public static final String PATH_ONE_YEARS = "3";

	/*************************************** 订单基本状态 *********************************/
	// 未处理(默认值)
	public static final int BASE_STAY_CHECK = 1;
	// 已确认
	public static final int BASE_CONFIRM = 2;
	// 已挂起 (订单表加入挂起类型 类型有缺货挂起、预售挂起)
	public static final int BASE_SUSPEND = 3;
	// 已完成
	public static final int BASE_FINISH = 4;
	// 已作废
	public static final int BASE_CANCEL = 5;
	// 客服取消（传给PDA做拦截）
	public static final int BASE_SPECIAL_CANCEL = 6;
	// 客服修改（传给PDA做拦截）
	public static final int BASE_UPDATE = 7;
	// 已打包
	public static final int BASE_PACK = 25;
	// 訂單已被刪除
	public static final Short DEL_FLAG_TRUE = 0;
	// 訂單正常
	public static final Short DEL_FLAG_FALSE = 1;
	/*************************************** 交易状态 *********************************/
	/**
	 * 等待完成
	 */
	public static final int PENDING_COMPLETION = 1;
	/**
	 * 已成功
	 */
	public static final int ALREADY_COMPLETION = 2;
	/**
	 * 待退款
	 */
	public static final int PENDING_REFUND = 3;
	/**
	 * 已退款
	 */
	public static final int ALREADY_REFUND = 4;
	/**
	 * 已取消
	 */
	public static final int ALREADY_CANCEL = 5;

	/************************************* 订单付款状态 *********************************/
	// 未支付
	public static final int PAY_STAY_PAID = 7;
	// 部分支付 暂时没用到
	public static final int PAY_PART = 8;
	// 已支付
	public static final int PAY_PAID = 9;
	// 退款申请中
	public static final int PAY_REFUND_APPLY = 10;
	// 部分退款
	public static final int PAY_REFUND_PART = 11;
	// 全额退款
	public static final int PAY_REFUND_ALL = 12;
	// 退款失败
	public static final int PAY_REFUND_FAIL = 13;
	// 顾客退款申请
	public static final int PAY_REFUND_APPLY_CUS = 23;
	// 已同意退款
	public static final int PAY_REFUND_AGREE = 24;
	// 拒绝退款
	public static final int PAY_REFUND_REFUSE = 26;
	/*************************************** 配送状态 ********************************/
	// 备货中 ( 已做新建使用)
	public static final int DELIVERY_PREPARE = 14;
	// 部分发货
	public static final int DELIVERY_PART = 15;
	// 已发货
	public static final int DELIVERY_SEND = 16;
	// 已收货
	public static final int DELIVERY_RECEIVE = 17;
	// 拒收
	public static final int DELIVERY_REFUSE = 18;
	// 部分退货
	public static final int DELIVERY_PART_RETURN = 19;
	// 已取消发货
	public static final int DELIVERY_ALL_RETURN = 20;
	// 已终止发货
	public static final int DELIVERY_STOP_SEND = 21;
	// 真正备货
	public static final int DELIVERY_PREPARE_REAl = 22;
	/*************************** 挂起类型 ************************************/
	/* 没有挂起 */
	public static final int SUSPEND_TYPE_NOT = 0;
	/* 缺货挂起 */
	public static final int SUSPEND_TYPE_LACK = 1;
	/* 预售挂起 */
	public static final int SUSPEND_TYPE_PRESELL = 2;
	/* 退款挂起 */
	public static final int SUSPEND_TYPE_REFUND = 3;
	/* 换货挂起 */
	public static final int SUSPEND_TYPE_TRADE = 4;
	/* 物流超区挂起 */
	public static final int SUSPEND_TYPE_LOGICTIS = 5;
	/************************* 异常订单标示 *************************************/
	// 正常
	public static final int NO_EXCEPTION = 0;
	// 全部异常
	public static final int IS_EXCEPTION = 1;
	// 留言异常
	public static final int EXCEPTION_MESSAGE = 2;
	// 价格异常
	public static final int EXCEPTION_PRICE = 3;
	// 字段完整性异常
	public static final int EXCEPTION_VALID = 4;
	// 获取物流公司异常
	public static final int EXCEPTION_LOGICTIS = 5;
	// 客服异常(可疑订单)
	public static final int EXCEPTION_DUBIOUS = 6;
	// 灰名单异常 (已经废弃)
	public static final int EXCEPTION_BLACK = 7;
	// 物流公司超区异常
	public static final int EXCEPTION_LOGICTIS_BEYOND = 8;
	// 商品条码错误 
	public static final int EXCEPTION_PRODUCTCODE_ERROR = 9;
	// 仓库匹配异常
	public static final int EXCEPTION_WAREHOUSE_ERROR = 10;
	// 会员身份黑名单异常
	public static final int EXCEPTION_MEMBER_BLACK = 11;
	// 订单货品真实库存不足
	public static final int EXCEPTION_LACK_STOCK = 12;
	// 可疑代销商
	public static final int EXCEPTION_SUSPECTED_DEALERS = 13;
	// 超卖异常
	public static final int EXCEPTION_OVER_SELL = 14;
	// 活动异常
	public static final int EXCEPTION_ACTIVITY = 15;
	/*************************************** 基本状态说明 **************************/
	// 未处理(默认值)
	public static final String BASE_STAY_CHECK_DISPLAY = "未处理";
	// 已确认
	public static final String BASE_CONFIRM_DISPLAY = "已确认";
	// 已挂起 (订单表加入挂起类型 类型有缺货挂起、预售挂起)
	public static final String BASE_SUSPEND_DISPLAY = "已挂起";
	// 已完成
	public static final String BASE_FINISH_DISPLAY = "已完成";
	// 已作废
	public static final String BASE_CANCEL_DISPLAY = "已作废";
	// 客服取消（传给PDA做拦截）
	public static final String BASE_SPECIAL_CANCEL_DISPLAY = "客服取消";
	// 已打包
	public static final String BASE_PACK_DISPLAY = "已打包";
	// 已打包
	public static final String BASE_UPDATE_DISPLAY = "申请拦截";
	/**
	 * WMS订单类型<供pda根据不同类型来获取>
	 */
	// 获取正常订单
	public static final int FETCH_NORMAL_ORDER = 1;
	// 获取已取消订单
	public static final int FETCH_CANCLE_ORDER = 2;
	// 异常订单
	public static final int FETCH_EXCEPTION_ORDER = 3;
	// 已终止发货订单
	public static final int FETCH_TERMINATE_DELIVERY = 4;
	// 销售退货
	public static final int FETCH_SALE_RETURN_INFO = 5;
	// 拒收退货
	public static final int FETCH_CUSTOMER_REFUND = 6;
	// 已出库的订单
	public static final int FETCH_ORDER_ALREADY_OUT = 7;
	/**** ********************************订单付款状态说明 *************************/
	// 未支付
	public static final String PAY_STAY_PAID_DISPLAY = "未支付";
	// 部分支付
	public static final String PAY_PART_DISPLAY = "部分支付";
	// 已支付
	public static final String PAY_PAID_DISPLAY = "已支付";
	// 退款申请中
	public static final String PAY_REFUND_APPLY_DISPLAY = "退款申请中";
	// 部分退款
	public static final String PAY_REFUND_PART_DISPLAY = "部分退款";
	// 全额退款
	public static final String PAY_REFUND_ALL_DISPLAY = "已退款";
	// 退款失败
	public static final String PAY_REFUND_FAIL_DISPLAY = "退款失败";
	// 顾客退款申请
	public static final String PAY_REFUND_APPLY_CUS_DISPLAY = "顾客退款申请";
	// 已同意退款
	public static final String PAY_REFUND_AGREE_DISPLAY = "已同意退款";
	// 已同意退款
	public static final String PAY_REFUND_REFUSE_DISPLAY = "拒绝退款";
	/***** *******************************配送状态说明 *****************************/
	// 新建 (修改时间 2011-7--29)
	public static final String DELIVERY_PREPARE_DISPLAY = "新建";
	// 部分发货
	public static final String DELIVERY_PART_DISPLAY = "部分发货";
	// 已发货
	public static final String DELIVERY_SEND_DISPLAY = "已发货";
	// 已收货
	public static final String DELIVERY_RECEIVE_DISPLAY = "已收货";
	// 拒收
	public static final String DELIVERY_REFUSE_DISPLAY = "拒收";
	// 部分退货
	public static final String DELIVERY_PART_RETURN_DISPLAY = "部分取消";
	// 已退货
	public static final String DELIVERY_ALL_RETURN_DISPLAY = "已取消发货";
	// 已终止发货
	public static final String DELIVERY_STOP_SEND_DISPLAY = "已终止发货";
	// 真正备货中
	public static final String DELIVERY_PREPARE_REAl_DISPLAY = "备货中";

	/************************* 退款申请单状态 **************************************/
	// 退款申请的状态1未退款 2已退款 3退款失败 ApplyRefund state
	public static final String APPLYREFUND_STATE_NO = "1"; // 未退款
	public static final String APPLYREFUND_STATE_YES = "2"; // 已退款
	public static final String APPLYREFUND_STATE_FAIL = "3"; // 退款失败
	/************************* 操作权限说明 ***************************************/
	// 操作权限 只能查看
	public static final int OPERATE_POWER_LOOK = 1;
	// 操作权限 可以修改
	public static final int OPERATE_POWER_UPDATE = 2;
	/************************** 前后台单说明 **************************************/
	// 判断 后台订单或者前台订单
	// 1 表前台
	public static final int ORDER_BELONG_BEFORE = 1;
	// 2 表后台(无操作权限)
	public static final int ORDER_BELONG_AFTER = 2;
	// 3 表后台(有操作权限)
	public static final int ORDER_BELONG_AFTERS = 3;
	// 4 淘宝订单
	public static final int ORDER_BELONG_TAOBAO = 4;
	// 5 拍拍订单
	public static final int ORDER_BELONG_PAIPAI = 5;
	/************************ 备注类型 *****************************************/
	// 异常备注
	public static final int EXCEPTION_REMARK = 1;
	// 正常备注
	public static final int NORMAL_REMARk = 2;
	public static final int TRADE_GOODS_REMARk = 3;
	/************************* 父订单是否分单 ************************************/
	// 已拆分
	public static final int MAIN_SPLIT_YES = 1;
	// 未拆分
	public static final int MAIN_SPLIT_NOT = 2;
	/************************* 操作人 ******************************************/
	public static final String OPERATE_FINANCE_USER = "财务";
	/************************** 操作结果 **************************************/
	// 操作成功
	public static final int OPERATE_SUCCESS = 1;
	// 操作失败
	public static final int OPERATE_FAIL = 2;

	/**************** 申请退款类型 ****************************************/
	// 未发货退款
	public static final int REFUND_TYPE_NOT_SEND = 1;
	// 售后退货退款
	public static final int REFUND_TYPE_SALE = 2;
	// 其他退款
	public static final int REFUND_OTHER = 3;
	/********************* 发邮件时间戳 *********************************/
	public static final Long EMAIL_START_TIME = 0l;

	/********************* 销售订单同步状态 ************************************/
	public static final short SYNCHRONIZED = 1;

	public static final short UNSYNCHRONIZED = 0;
	/**
	 * 发送信息表示
	 */
	// 已经发送信息
	public static final String SEND_INFO_YES = "1";
	// 未发送信息
	public static final String SEND_INFO_NO = "0";
	/************* 判断是否可以退款 是否可解挂 ****************************/
	// 可操作
	public static final int OPERATE_YES = 1;
	// 不可操作
	public static final int OPERATE_NO = 2;
	/************************ 订单job type *****************************/
	// 订单过滤
	public static final String FILTER_ORDER = "cronTriggerOrderFilter";
	// 订单取消
	public static final String CANCEL_ORDER = "cronTriggerOrderCancel";
	// 订单线下支付赠送积分
	public static final String OFF_PAY = "cronTriggerBatchOffPay";
	// 订单批量取消优惠卷
	public static final String CANCEL_COUPON = "cronTriggerCancelCoupon";
	/************************* 订单过滤时间配置项 ***********************/
	// 【系统配置】订单过滤延迟时间设置
	public static final String FILTER_DELAY_TIME = "filterDelayTime";
	
	// 【系统配置】订单取消时间设置
	public static final String CANCEL_ORDER_TIME = "com.yougou.order.cancleorder.time";
	// 【系统配置】订单取消时间设置(针对手机订单)
	public static final String CANCEL_ORDER4MOBILE_TIME = "com.yougou.order.cancleorder4Mobile.time";

	/************************* 订单开关 *******************************/
	// 【系统配置】订单过滤器key
	public static final String SWITCH_FILTER_KEY = "switchFilterKey";
	public static final String FILTER_DISPLAY = "订单过滤";
	// 【系统配置】订单取消key
	public static final String SWITCH_CANCEL_KEY = "switchCancelKey";
	public static final String CANCEL_DISPLAY = "订单取消";
	// 【系统配置】订单分单key
	public static final String SWITCH_SPLIT_KEY = "switchSplitKey";
	public static final String SPLIT_DISPLAY = "订单分单";
	// 开
	public static final int SWITCH_OPEN = 1;
	// 关
	public static final int SWITCH_CLOSE = 2;
	/********************* 删除标记 ************************************/
	// 未删除
	public static final short DEL_NO = 1;
	// 已删除
	public static final short DEL_YES = 0;
	/******************** 赠送积分和发送邮件数量限制 *********************/
	public static final int LIMIT_NUM = 2000;
	/******************* 过滤限制和取消订单条数 ************************************/
	public static final int LIMIT_FILTER_NUM = 2000;
	/****************** 设置导出报表记录数 *****************************************/
	public static final int HIGH_RECORD = 15000;
	/****************** 订单查看显示默认天数的数据 *********************************/
	public static final int LIMIT_DAY = 30;

	/****************** 积压订单默认小时 ***************************************/
	public static final int DALAY_DAY = 24;
	/************************** 售后类型处理 pda **********************************************/
	// 退货或换货
	public static final int NORMAL_SALE = 1;
	// 拒收
	public static final int SPEAIAL_SALE = 2;
	/************************** 更新支付方式 ******************************************/
	// 没有确认下修改
	public static final String NOTCONFIRM = "1";
	// 仓库打回修改
	public static final String WAREHOUSECANCEL = "2";
	// 物流公司超区
	public static final String BEYONDLOGICTIS = "3";
	/************************* 时间配置 **********************************************/
	// 【系统配置】积压订单时间
	public static final String DELAY_TIME = "order_delay_time";
	// 【系统配置】查询的时间范围
	public static final String QUERY_LIMIT_DAY = "query_limit_day";
	/************************ 订单导出控制 ******************************************/
	/**
	 * 订单列表权限控制 1 查看 2 修改 3 审核 4 导出 5 导出详细地址 6.导出订单(含成本价) 7.订单激活
	 */
	public static final int ACCESSCONTROL1 = 1;
	public static final int ACCESSCONTROL2 = 2;
	public static final int ACCESSCONTROL3 = 3;
	public static final int ACCESSCONTROL4 = 4;
	public static final int ACCESSCONTROL5 = 5;
	public static final int ACCESSCONTROL6 = 6;
	public static final int ACCESSCONTROL7 = 7;

	/**
	 * 导出报表类型 1,导出订单 2，导出订单（含成本价） 3，地址导出 4，仓库打回订单导出
	 */
	public static final int REPORTTYPE1 = 1;
	public static final int REPORTTYPE2 = 2;
	public static final int REPORTTYPE3 = 3;
	public static final int REPORTTYPE4 = 4;

	/******************** 订单客服取消 判断标记 *******************************************/
	// 默认 0未同步 1 已同步
	public static final Short SYNC_FLAG = 1;
	/****************************** 可疑订单正则验证 *************************************/
	// 验证地址
	public static final String ADDRESS_REGEX = ".*t\\s*e\\s*s\\s*t.*|.*测\\s*试.*|.*c\\s*e\\s*s\\s*h\\s*i.*";
	// 验证收货地址是否包含6位连续及以上的数字
	public static final String ADDRESS_NUM6_REGEX = "\\S*[0-9]{6,100}\\S*";
	// 验证收货人
	public static final String RECEIVER_REGEX = ".*t\\s*e\\s*s\\s*t.*|.*测\\s*试.*|.*c\\s*e\\s*s\\s*h\\s*i.*";
	// 验证收货人 是否包含数字
	public static final String RECEIVER_NUMBER_REGEX = "\\S*[0-9]{1,100}\\S*";
	// 验证购买人
	public static final String BUYER_REGEX = ".*t\\s*e\\s*s\\s*t.*|.*c\\s*e\\s*s\\s*h\\s*i.*|.*\\s*y\\s*o\\s*u\\s*g\\s*\\s*o\\s*u\\s*";
	/*************************** 订单预售转仓 *************************************/
	// 预售仓库代码code
	public static final String WAREHOUE_PRESELL_CODE = "01110919060421";
	// 深圳仓库代码code
	public static final String WAREHOSE_SHENZHEN_CODE = "012011061084002";
	/************************** 订单状态修正 **************************************/
	// 1 更新基本状态 2 更新支付状态 3 更新发货状态 4 更新商品状态 5 更新同步状态
	public static final int U_BASE_STATUS_TYPE = 1;
	public static final int U_PAY_STATUS_TYPE = 2;
	public static final int U_DELIVERY_STATUS_TYPE = 3;
	public static final int U_PROD_STATUS_TYPE = 4;
	public static final int U_ISSYNC_STATUS_TYPE = 5;
	/************************** 订单仓库打回订单是否处理标记 ***********************/
	// 已处理
	public static final String IS_DEAL_YES = "2";
	/************************** 订单来源 *******************************/
	/**
	 * 优购
	 */
	public static final String ORDER_SOURCE_YOUGOU = "YG-YG";
	
	/**
	 * 优购(包含手机)
	 */
	public static final String ORDER_SOURCE_YOUGOU_ALL = "YG";
	
	/**
	 * 淘宝
	 */
	public static final String ORDER_SOURCE_TAOBAO = OrderSourceConstant.TB.getNo();
	
	/**
	 * 淘宝直销
	 */
	public static final String ORDER_SOURCE_TB_TBZXD = OrderSourceConstant.TB_TBZXD.getNo();
	/**
	 * 拍拍
	 */
	public static final String ORDER_SOURCE_PAIPAI = OrderSourceConstant.WBPT_PP.getNo();
	/**
	 * 加盟平台
	 */
	public static final String ORDER_SOURCE_DISTRACTION = OrderSourceConstant.FXPT.getNo();
	/**
	 * 优购手机
	 */
	public static final String ORDER_SOURCE_MOBILE = OrderSourceConstant.YG_SJYGSSSC.getNo();
	/**
	 * 移动商城
	 */
	public static final String ORDER_SOURCE_MOBILEMALL = OrderSourceConstant.WBPT_YDSC_YDSC.getNo();
	/**
	 * 大宗客户订单
	 */
	public static final String ORDER_SOURCE_LARGECUSTOMERS = OrderSourceConstant.FXPT.getNo();
	
	/**
	 * 当当客户订单
	 */
	public static final String ORDER_SOUECE_DANGDANG=OrderSourceConstant.WBPT_DD.getNo();
	
	/**
	 * 西街
	 * 
	 */
	public static final String ORDER_SOUECE_XIJIE=OrderSourceConstant.FXPT_XJ.getNo();

	/**
	 * 京东
	 */
	public static final String ORDER_SOUECE_JINGDONG=OrderSourceConstant.WBPT_JD.getNo(); 
	
	/**
	 * 一号店
	 */
	public static final String ORDER_SOUECE_YIHAODIAN=OrderSourceConstant.WBPT_1HD.getNo();
	
	/**
	 * 库8
	 */
	public static final String ORDER_SOUECE_COO8="";//已经废弃 
	
	/**
	 * 苏宁易购
	 */
	public static final String ORDER_SOUECE_SUNNING=OrderSourceConstant.WBPT_SN.getNo();
	
	/**
	 * 新分销
	 */
	public static final String ORDER_SOUECE_NEWDISTRACTION="14";//已经废弃
	
	/**
	 * 银泰
	 */
	public static final String ORDER_SOUECE_YINTAI=OrderSourceConstant.WBPT_YT.getNo(); 
	
	/**
	 * 亚马逊
	 */
	public static final String ORDER_SOUECE_YAMAXUN=OrderSourceConstant.WBPT_YMX.getNo();
	
	/**
	 * V+
	 */
	public static final String ORDER_SOUECE_VJIA=OrderSourceConstant.WBPT_FKVjia.getNo();
	
	/**
	 * QQ商城
	 */
	public static final String ORDER_SOUECE_QQ=OrderSourceConstant.WBPT_QQ.getNo();
	

	// 订单编号重复异常提示
	public static final String ORDERNO_REPEAT_EXCEPTION_PRESENTATION = "订单编号重复请检查订单";
	// 订单购买数量为空异常提示
	public static final String ORDER_COMMODITYNUM_EXCEPTION_PRESENTATION = "订单商品数据为空字符请检查订单";
	// 订单编号为空异常提示
	public static final String ORDERNO_ISNULL_EXCEPTION_PRESENTATION = "订单编号为空字符请检查订单";

	/**
	 * 订单导出状态：未导出
	 * 
	 * @author zhengzengjie
	 */
	public static final int ORDER_NO_EXPORT = 2;
	/**
	 * 订单导出状态：已导出
	 * 
	 * @author zhengzengjie
	 */
	public static final int ORDER_EXPORT = 1;
	/********************* 订单手工确认常量 ****************************/
	/**
	 * 下单IP所属地区
	 */
	public static final String ORDER_IP_ADDRESS = "orderIp";
	/**
	 * 收货人手机号码所属地区
	 */
	public static final String CONSIGNEE_MOBILE_ADDRESS = "consigneeMobile";
	/**
	 * 收货人邮编所属地区
	 */
	public static final String CONSIGNEE_ZIP_ADDRESS = "consigneeZip";
	/**
	 * 收货地区
	 */
	public static final String CONSIGNEE_ADDRESS = "consigneeAddress";
	/**
	 * 注册IP所属地区
	 */
	public static final String REGISTER_IP_ADDRESS = "registerIp";
	/***************** 订单会员类型 ******************************/
	/**
	 * 白名单
	 */
	public static final String WHITE_LIST = "A";
	/**
	 * 新会员
	 */
	public static final String NEW_LIST = "B";
	/**
	 * 红 名单
	 */
	public static final String RED_LIST = "C";
	/**
	 * 灰名单
	 */
	public static final String GREY_LIST = "D";
	/**
	 * 黄名单
	 */
	public static final String YELLOW_LIST = "E";
	/**
	 * 黑名单
	 */
	public static final String BLACK_LIST = "F";

	/**
	 * 会员
	 */
	public static final String MEMBER_LIST = "G";

	/**
	 * 异常订单
	 */
	public static final String EXCEPTION_LIST = "exception";
	// 黑名单
	public static final String MEMBER_TYPE_BLACK = "F";
	// 申请过退款
	public static final int REFUND_YES = 1;
	// 未申请过退款
	public static final int REFUND_NO = 0;

	/************************* 淘宝订单过滤价格配置项 ***********************/
	/**【系统配置】 淘宝订单过滤价格设置 */
	public static final String TAOBA_PRICE_AREA = "taobao.price.area";

	/**【系统配置】订单过滤Ip配置项 */
	public static final String VALID_IP = "valid_ip";

	public static final String DEFAULT_VALID_IP = "127.0.0.1";

	/**
	 * 在线支付提醒类型 邮件提醒
	 */
	public static final int WARNPAYTYPEOFEMAIL = 1;

	/**
	 * 在线支付提醒类型 短信提醒
	 */
	public static final int WARNPAYTYPEOFSMS = 2;

	/************************* 订单是否开启分离后webservice 接口配置项 ***********************/

	/**
	 * 【系统配置】key 是否开启webservice调用
	 */
	public static final String WEBSERVICE_SWITCH = "ORDER_WMS_INTERFACE_IS_WEBSERVICE";
	
	/**
	 * 【系统配置】key 是否开启jar调用
	 */
	public static final String JAR_SWITCH = "ORDER_WMS_INTERFACE_IS_JAR";
	
	/**
	 * 【系统配置】key 是否开启 union dubbo调用
	 */
	public static final String DUBBO_SWITCH = "UNION_ORDER_MSG_SWITCH";
	

	/**
	 * 【系统配置】key 是否开启 union dubbo调用
	 */
	public static final String UNION_PARAM_IS_PRODNO_OR_GOODS = "UNION_PROD_PARAM";

	/**
	 * value 0 close 1 open
	 */
	public static final String WEBSERVICE_ISOPEN = "1";
	
	/**
	 * value 0 close 1 open
	 */
	public static final String JAR_ISOPEN = "1";
	
	/**
	 * value 0 close 1 open
	 */
	public static final String DUBBO_ISOPEN = "1";
	
	/**
	 * value 0 close 1 open
	 */
	public static final String UNION_PARAM_ISOPEN = "1";

	/**
	 * 淘宝订单货品条码异常 状态
	 */
	public static final String TAOBAOORDERPRODUCTERROR = "1";
	/**
	 * 淘宝订单货品条码正常 状态
	 */
	public static final String TAOBAOORDERPRODUCTNORMAL = "2";
	/**
	 * 淘宝订单货品条码正常 并且已经生成了订单 状态
	 */
	public static final String TAOBAOORDERGENERATE = "3";

	/**
	 * 淘宝订单货品条码异常 并且已经生手动取消了该订单 状态
	 */
	public static final String TAOBAOORDERCANCEL = "4";

	/**
	 * 淘宝订单货品条码异常的商品 状态为异常
	 */
	public static final String TAOBAOORDERCOMMODITYERROR = "1";

	/**
	 * 淘宝订单货品条码异常的商品 状态为正常
	 */
	public static final String TAOBAOORDERCOMMODITYNORMAL = "2";
	/**
	 * 淘宝订单货品条码异常的商品 状态为取消
	 */
	public static final String TAOBAOORDERCOMMODITYCANCEL = "3";

	/**
	 * 【系统配置】 大手笔合作优惠券方案ids
	 */
	public static final String DASHOUBI_COUPON_SCHEME_IDS = "DASHOUBI_COUPON_SCHEME_IDS";

	/**
	 * 订单取消类型 前台顾客取消
	 */
	public static final int ORDER_CANCLE_TYPE_WEB = 1;

	/**
	 * 订单取消类型 客服取消
	 */
	public static final int ORDER_CANCLE_TYPE_SERVICE = 2;

	/**
	 * 订单取消类型 系统取消
	 */
	public static final int ORDER_CANCLE_TYPE_SYSTEM = 3;
	
	/**
	 * 订单取消类型 订单未审核的时候取消
	 */
	public static final int ORDER_CANCLE_TYPE_NO_CONFIRM = 4;
	
	/**
	 * 订单取消类型 客服强制取消订单
	 */
	public static final int ORDER_CANCLE_TYPE_ENFORCE = 5;

	/********************** 订单操作权限 ******************/
	/**
	 * 查询
	 */
	public static final String ORDER_SEARCH = "1";
	/**
	 * 修改
	 */
	public static final String ORDER_UPDATE = "2";
	/************************* 移动商城订单状态 ***********************/
	//商家取消
	public static final int CMB_ORDER_STATUS_CANCLE = -1;
	//已出库
	public static final int CMB_ORDER_STATUS_OUTSTORE = 4;
	//客户拒收
	public static final int CMB_ORDER_STATUS_REJECT = 10;
	//完成
	public static final int CMB_ORDER_STATUS_DONE = 100;

	/**
	 * 问题订单日志的状态 未处理
	 */
	public static final int EXCEPTION_ORDER_LOG_NO_OPERATION = 0;
	/**
	 * 问题订单日志的状态 已处理
	 */
	public static final int EXCEPTION_ORDER_LOG_OPERATION = 1;
	
	
	/**
     * 当当退货标示
     */
    public static final String DANG_DANG_RETURN_EXCHANGE_ORDERS_RETURN="1";
    
    /**
     * 当当换货标示
     */
    public static final String DANG_DANG_RETURN_EXCHANGE_ORDERS_EXCHANGE="2";
    
    /**
     * 当当退换货申请单状态 未处理
     */
    public static final String DANG_DANG_RETURN_EXCHANGE_STATUS_1="1";
    
    /**
     * 当当退换货申请单状态 已处理
     */
    public static final String DANG_DANG_RETURN_EXCHANGE_STATUS_2="2";
    
    /**
     * 当当退换货申请单状态 延期
     */
    public static final String DANG_DANG_RETURN_EXCHANGE_STATUS_3="3";
    
    /**
	 * 【系统配置】是否合并包裹 value 0 close 1 open 
	 */
	public static final String PARCELMERGEROFORDER_SWITCH = "PARCELMERGEROFORDER_SWITCH";
	
	/**
	 * value 0 close 1 open
	 */
	public static final String PARCELMERGEROFORDER_ISOPEN = "1";
	
	/**
	 * 【系统配置】是否开启、关闭系统自动审单  value 0 close 1 open 
	 */
	public static final String SYSTEMCHECKORDER_SWITCH = "SYSTEMCHECKORDER_SWITCH";
	
	/**
	 * value 0 close 1 open
	 */
	public static final String SYSTEMCHECKORDER_ISOPEN = "1";
	/**
	 * value 0 close 1 open
	 */
	public static final String SYSTEMCHECKORDER_ISCLOSE = "0";
	
	/**
	 * 【系统配置】系统自动审单单次审核最大数量 
	 */
	public static final String SYSTEMCHECKORDERCOUNT = "SYSTEMCHECKORDERCOUNT";

    /**
     * 锁定查询淘宝sku库存校验表是否能导出  Y 为锁定，N为释放
     */
    public static String SKU_CHECK_DATA_STATUS="N";
	
	/**
	 * 苏宁订单同步状态    第一次同步
	 */
	public static final String CREATE_ORDER="CREATE_ORDER";
	
	/**
	 * 苏宁订单同步状态    第二次同步
	 */
	public static final String WAIT_YOUGOU_SEND_GOODS="WAIT_YOUGOU_SEND_GOODS";
	
	/**
	 * 苏宁订单同步状态    直接第二次、不发第一次
	 */
	public static final String CREATE_AND_SEND_ORDER="CREATE_AND_SEND_ORDER";
	
	/**
	 * 【系统配置】第三方订单切换新接口 value=1 新接口 value=0 老接口
	 */
	public static final String SWITCHOFORDERMETHOD = "SWITCHOFORDERMETHOD";
	
	/**
	 * vjia指令取消订单
	 */
	public static final int VJIACOMMANDCANCEL =9;
	
}
