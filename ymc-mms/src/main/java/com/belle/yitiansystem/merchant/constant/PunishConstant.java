package com.belle.yitiansystem.merchant.constant;

/**
 * 定义常量
 * 
 * @author he.wc
 * 
 */
public class PunishConstant {

	/** 是否发货 ，1：是 */
	public final static String SHIPMENT_YES = "1";
	/** 是否发货 ，0：否 */
	public final static String SHIPMENT_NO = "0";

	/** 审核状态, 0:已删除 */
	public final static String ORDER_STATUS_DEL = "0";
	/** 审核状态, 1:待审核 */
	public final static String ORDER_STATUS_NORMAL = "1";
	/** 审核状态, 2:已审核 */
	public final static String ORDER_STATUS_VALID = "2";
	/** 日期类型，日期与时间 */
	public final static String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	/** 日期类型，日期 */
	public final static String DATE_FORMAT_DATE = "yyyy-MM-dd";
	
	/** 是否扣款 ,1:是*/
	public final static String IS_SETTLE_YES = "1";
	/** 是否扣款 ,0:否*/
	public final static String IS_SETTLE_NO = "0";
	
	/** 处罚类型,1:超时效 */
	public final static String PUNISH_TYPE_TIMEOUT= "1";
	/** 处罚类型,0:缺货 */
	public final static String PUNISH_TYPE_STOCK= "0";
	
	/** 延时发货处罚,1：固定金额 */
	public final static String PUNISH_PRICE_TYPE_PRICE = "1";
	/** 延时发货处罚,0：折扣 */
	public final static String PUNISH_PRICE_TYPE_DISCOUNT = "0";
	
	/** 邮件提醒是否启用,1：启用 */
	public final static String EMAIL_NOTIFICATION_YES = "1";
	
	/** 订单是否取消,1：是*/
	public final static String ORDER_CANCEL_YES = "1";
	/** 订单是否取消,0：否*/
	public final static String ORDER_CANCEL_NO = "0";
	
	/** 订单状态，5：作废 */
	public final static String ORDER_BASE_STATUS_INVALID = "5";
	
	/** 订单状态，6：客服取消 */
	public final static String ORDER_BASE_STATUS_CANCEL = "6";

}
