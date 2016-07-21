package com.yougou.kaidian.order.constant;

public class OrderConstant {

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
	// 新建
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
	public static final String BASE_UPDATE_DISPLAY = "客服修改";
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
	/**
	 * 分派状态
	 */
	// 未分派 (默认值)
	public static final int ALLOT_NOT = 1;
	// 已分派
	public static final int ALLOT_YES = 2;
	/************************* 异常订单标示 *************************************/
	// 常规异常
	public static final int IS_EXCEPTION = 1;
	// 留言异常
	public static final int EXCEPTION_MESSAGE = 2;
	// 价格异常
	public static final int EXCEPTION_PRICE = 3;
	// 字段完整性异常
	public static final int EXCEPTION_VALID = 4;
	// 获取物流公司异常
	public static final int EXCEPTION_LOGICTIS = 5;
	// 可疑订单异常
	public static final int EXCEPTION_DUBIOUS = 6;
	// 灰名单异常
	public static final int EXCEPTION_BLACK = 7;
	// 物流公司超区异常
	public static final int EXCEPTION_LOGICTIS_BEYOND = 8;
	// 商品条码错误 add by daixiaowei
	public static final int EXCEPTION_PRODUCTCODE_ERROR = 9;
	
	public static final int NO_EXCEPTION = 0; // 正常
	/** 缺货异常 */ 
	public static final int EXCEPTION_WAREHOUSE_ERROR = 10;
	// 会员身份黑名单异常
	public static final int EXCEPTION_MEMBER_BLACK = 11;
	/** 订单货品真实库存不足 */ 
	public static final int EXCEPTION_LACK_STOCK = 12;
	// 可疑代销商
	public static final int EXCEPTION_SUSPECTED_DEALERS = 13;
	// 超卖异常
	public static final int EXCEPTION_OVER_SELL = 14;
	/** 活动异常 */ 
	public static final int EXCEPTION_ACTIVITY = 15;
	
	/** 大额礼品卡 */ 
	public static final int EXCEPTION_LARGE_GIFTCARD  = 16;
	/** 未授权商品 */ 
	public static final int EXCEPTION_UNAUTHORIZED_GOODS = 19;	
	/** 疑似重复订单 */ 
	public static final int EXCEPTION_RESEMBLE_REPEAT_ORDER = 20;
	/** 淘宝店铺预售异常 */ 
	public static final int EXCEPTION_TB_YU = 21;
	/** 换款未补差 */ 
	public static final int EXCEPTION_CHANGEDS_NOTPAY = 22;
	/** 可疑套现用户 */ 
	public static final int EXCEPTION_RESEMBLE_CASH = 23;
	/** 同步异常 */ 
	public static final int EXCEPTION_SYNC = 24;
	/** 不支持货到付款 */ 
	public static final int EXCEPTION_CAN_NOT_COD = 25;
	/** 京东锁定 */ 
	public static final int EXCEPTION_JINGDONG_LOCKED = 26;
	/** 退款又发货 */ 
	public static final int EXCEPTION_REFUND_AND_SEND = 27;
	/** 第三方取消订单  异常 */ 
	public static final int EXCEPTION_THIRD_PARTY_CANCEL = 28;
	
	/** 收货地址为港澳台地区的淘宝订单*/
	public static final int EXCEPTION_HONGKONGMACAUTAIWAN_TAOBAO = 29;

	/** 多种异常  */
	public static final int EXCEPTION_MULTIPLE =31 ;

	/** 淘宝超卖异常 */
	public static final int EXCEPTION_TB_OVERSOLD = 30;
	
	/** 淘宝同步退款异常 */ 
	public static final int EXCEPTION_TAOBAO_SYNC_REFUND = 101;
	/** 财务拒绝退款异常 */ 
	public static final int EXCEPTION_REFUSE_REFUND = 102;
	/** 拦截成功异常 */ 
	public static final int EXCEPTION_INTERCEPT_SUCCESS = 103;
	
	/** 换货异常 */ 
	public static final int EXCEPTION_TRADE_GOODS = 104;
	
	/** 客服取消拦截异常 */ 
	public static final int EXCEPTION_SPECIAL_CANCEL = 105;
	
	/** 首尔站订单申报异常-海关申报 */ 
	public static final int EXCEPTION_SEOULORDER_DECLARE_CUSTOMS = 106;
	
	/** 首尔站订单申报异常-支付申报 */ 
	public static final int EXCEPTION_SEOULORDER_DECLARE_PAY = 107;
	/*****************************************************************************/
	/************************* 退款申请单状态 **************************************/
	// 退款申请的状态1未退款 2已退款 3退款失败 ApplyRefund state
	public static final String APPLYREFUND_STATE_NO = "1"; // 未退款
	public static final String APPLYREFUND_STATE_YES = "2"; // 已退款
	public static final String APPLYREFUND_STATE_FAIL = "3"; // 退款失败
	/************************* 取消订单类型 ***************************************/
	/**
	 * 订单取消类型 前台顾客取消
	 */
	public static final Integer ORDER_CANCLE_TYPE_WEB = 1;

	/**
	 * 订单取消类型 客服取消
	 */
	public static final Integer ORDER_CANCLE_TYPE_SERVICE = 2;

	/**
	 * 订单取消类型 系统取消
	 */
	public static final Integer ORDER_CANCLE_TYPE_SYSTEM = 3;
	
	/**
	 * 订单取消类型 取消订单 （订单未审核的时候取消）
	 */
	public static final Integer ORDER_CANCLE_TYPE_NO_CONFIRM = 4;
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
	/************************* 父订单是否分单 ************************************/
	// 已拆分
	public static final int MAIN_SPLIT_YES = 1;
	// 未拆分
	public static final int MAIN_SPLIT_NOT = 2;
	// 所有
	public static final int MAIN_ALL = 0;
	/*************************** 发票状态 **************************************/
	// 不开发票
	public static final int BILL_NOT = 1;
	// 开发票
	public static final int BILL_YES = 0;
	/************************* 操作人 ******************************************/
	public static final String OPERATE_FINANCE_USER = "财务";
	/************************** 操作结果 **************************************/
	// 操作成功
	public static final int OPERATE_SUCCESS = 1;
	// 操作失败
	public static final int OPERATE_FAIL = 2;
	/* 用户中心多条件模糊查询(商品编号占不支持) */
	public static final String FINDORDERINFO = "商品名称、订单编号";
	public static final int AREALEVEL = 1;
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
	// 订单过滤延迟时间设置
	public static final String FILTER_DELAY_TIME = "filterDelayTime";
	// 订单取消时间设置
	public static final String CANCEL_ORDER_TIME = "cancelOrderTime";

	/************************* 订单开关 *******************************/
	// 订单过滤器key
	public static final String SWITCH_FILTER_KEY = "switchFilterKey";
	public static final String FILTER_DISPLAY = "订单过滤";
	// 订单取消key
	public static final String SWITCH_CANCEL_KEY = "switchCancelKey";
	public static final String CANCEL_DISPLAY = "订单取消";
	// 订单分单key
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
	// 积压订单时间
	public static final String DELAY_TIME = "order_delay_time";
	// 查询的时间范围
	public static final String QUERY_LIMIT_DAY = "query_limit_day";
	/************************ 订单导出控制 ******************************************/
	/**
	 * 订单列表权限控制
	 */
	// 1 查看 2 修改 3 审核 4 导出 5 导出详细地址 6.导出订单(含成本价)
	public static final Integer flag = 1;
	public static final int DETAIL_FLAG = 5;
	/******************** 订单客服取消 判断标记 *******************************************/
	// 默认 0未同步 1 已同步
	public static final Short SYNC_FLAG = 1;
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
	// 优购
	public static final String ORDER_SOURCE_YOUGOU = "1";
	// 淘宝
	public static final String ORDER_SOURCE_TAOBAO = "2";
	// 拍拍
	public static final String ORDER_SOURCE_PAIPAI = "3";
	// 加盟平台
	public static final String ORDER_SOURCE_DISTRACTION = "4";

	/**
	 * 支付方式
	 * 
	 * @author xieqingang
	 * @date 2011-5-10
	 */
	public enum Paymentmethod {
		ONLINE_PAYMENT("1", "在线支付"), 
		CASH_ON_DELIVERY("2", "货到付款"), 
		BANK_TRANSFER("3", "银行转帐"), 
		MAIL_REMITTANCE("4", "邮局汇款"), 
		OFFLINE_TRANSFER("5", "线下转账");
		private final String key;
		private final String value;

		Paymentmethod(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		public String getKey() {
			return this.key;
		}
	}

	/**
	 * 送货日期选项
	 * 
	 * @author xieqingang
	 * @date 2011-5-16
	 */
	public enum DeliveryDate {
		WORKDAY_DELIVERY {

			public String getName() {
				return "只工作日送货(双休日、假日不用送)";
			}
		},
		ALL_CAN_DELIVER {

			public String getName() {
				return "工作日、双休日与假日均可送货";
			}
		},
		HOLIDAY_DELIVERY {

			public String getName() {
				return "只双休日、假日送货(工作日不用送)";
			}
		},
		OTHER_TIME_DELIVERY {

			public String getName() {
				return "学校地址/地址白天没人，请尽量安排其他时间送货 (注：特别安排可能会超出预计送货天数)";
			}
		};
		public abstract String getName();
	}

	// 订单编号重复异常提示
	public static final String ORDERNO_REPEAT_EXCEPTION_PRESENTATION = "订单编号重复请检查订单";
	// 订单购买数量为空异常提示
	public static final String ORDER_COMMODITYNUM_EXCEPTION_PRESENTATION = "订单商品数据为空字符请检查订单";
	// 订单编号为空异常提示
	public static final String ORDERNO_ISNULL_EXCEPTION_PRESENTATION = "订单编号为空字符请检查订单";
	// 售后产品失效天数
	public static final long SALEDAYS = 10;
	// 优惠卷失效天数
	public static final long MARKINGNODATE = 15;
	// 地区仓订单状态
	// 未调出地区仓，默认
	public static final int WAREHOUSEDELIVERYSTATUS_NO = 0;
	public static final String WAREHOUSEDELIVERYSTATUS_NO_String = "0";
	// 未调出地区仓，默认
	public static final int WAREHOUSEDELIVERYSTATUS_YES = 1;
	public static final String WAREHOUSEDELIVERYSTATUS_YES_String = "1";
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
	// 淘宝订单过滤价格设置
	public static final String TAOBA_PRICE_AREA = "taobao.price.area";

	/************************* 订单过滤Ip配置项 ****************************/
	public static final String VALID_IP = "valid_ip";

	public static final String DEFAULT_VALID_IP = "127.0.0.1";

	/**
	 * 标识在线支付订单提醒邮件 已发送
	 */
	public static final Integer IS_SEND_WARNPAY_EMAIL = 1;

	/**
	 * 标识在线支付订单提醒短信 已发送
	 */
	public static final Integer IS_SEND_WARNPAY_SMS = 2;

	/**
	 * 在线支付提醒类型 邮件提醒
	 */
	public static final Integer WARNPAYTYPEOFEMAIL = 1;

	/**
	 * 在线支付提醒类型 短信提醒
	 */
	public static final Integer WARNPAYTYPEOFSMS = 2;

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
	 * wms 地区仓商品出库(其他出库-二级分类)
	 */
	public static final int OUT_AREA_WAREHOUSE = 118;
	/**
	 * wms 其它出库
	 */
	public static final String OTHER_OUT_STORE_CODE_HEAD = "OO";
	/**
	 * wms 审核常量
	 */
	public static final int CHECK_APPLY = 1;
	/**
	 * wms 已审核
	 */
	public static final String CHECK_APPLY_NAME = "已审核";
	/**
	 * wms 未分摊
	 */
	public static final Integer DIS_APPORTION = 100;

	/********************** 订单打印状态 ***************************/
	/**
	 * 已打印
	 */
	public static final Integer YESPRINT = 1;
	/**
	 * 未打印
	 */
	public static final Integer NOPRINT = 0;
	/**
	 * 订单货品配送方 0优购 1商家
	 */
	public static final Integer YOUGOU = 0;
	public static final Integer MERCHANT = 1;
	
	/**
	 * 分页每页显示最大值
	 */
	public static final Integer MAXPAGESIZE = 20000;
	
	/** 违规订单 发货状态，1： 是*/
	public static final String PUNISH_ORDER_SHIPMENT_YES = "1";
	/** 违规订单 发货状态，0： 否*/
	public static final String PUNISH_ORDER_SHIPMENT_NO = "0";
	
	/** 按固定值扣钱 */
	public static final String STOCKPUNISHTYPE_MONEY = "1";
	
	/** 违规订单类型 ,0:缺货 */
	public static final String PUNISHTYPE_OUTSTOCK = "0";
	/** 违规订单类型 ,1:超时效 */
	public static final String PUNISHTYPE_SHIPMENT = "1";
	
	/** 违规订单是否结算,0:否 */
	public static final String ISSETTLE_NO = "0";
	
	/** 违规订单状态,1:正常 */
	public static final String PUNISHORDERSTATUS_NORMAL = "1";
	
	/** 商家订单排序,1:下单时间正序  */
	public static final Integer ORDER_SORT_ORDERTIME_ASC = 1;
	
	/** 商家订单排序,2:下单时间倒序  */
	public static final Integer ORDER_SORT_SHIPMENTTIME_DESC = 2;
	
	/** 商家订单排序,3:缺货时间倒序  */
	public static final Integer ORDER_SORT_OUTSTOCKTIME_DESC_ORDERTIME_DESC = 3;
	

	
	
	
}
