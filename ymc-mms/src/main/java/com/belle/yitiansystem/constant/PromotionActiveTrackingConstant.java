package com.belle.yitiansystem.constant;

import org.springframework.stereotype.Component;

/**
 * 促销活动资源key文件配置(读取资源配置文件)
 * @author yangpinggui
 * @version 创建时间：2011-5-30
 *
 */
@Component
public class PromotionActiveTrackingConstant
{
	/**
	 * 支付状态(未支付)
	 */
	public static final String PAYSTAYPAID  = "promotion.active.paystaypaid";
	
	/**
	 * 支付状态(部分支付)
	 */
	public static final String PAYPART = "promotion.active.paypart";
	
	/**
	 * 支付状态(已支付)
	 */
	public static final String PAYPAID = "promotion.active.paypaid";
	
	/**
	 * 支付方式(在线支付)
	 */
	public static final String PAYONLINE_PAYMENT = "promotion.active.payonline.payment";
	
	
	/**
	 * 支付方式(货到付款)
	 */
	public static final String PAYCASH_AGAINSTDELIVERY = "promotion.active.paycash.againstdelivery";
	
	/**
	 * 支付方式(银行转帐)
	 */
	public static final String PAYBANKTRANSFER = "promotion.active.paybanktransfer";
	
	
	/**
	 * 支付方式(邮局汇款)
	 */
	public static final String PAYWANGPOS = "promotion.active.paywangpos" ;
	
	
	/**
	 * 订单状态(未处理)
	 */
	public static final String ORDERBASESTAYCHECK = "promotion.active.orderbasestaycheck";
	
	/**
	 * 订单状态(已处理)
	 */
	public static final String ORDERBASECONFIRM = "promotion.active.orderbaseconfirm";
	
	/**
	 * 订单状态(已挂起)
	 */
	public static final String ORDERBASESUSPEND = "promotion.active.orderbasesuspend";
	
	/**
	 * 订单状态(已完成)
	 */
	public static final String ORDERBASEFINISH = "promotion.active.orderbasefinish";
	
	/**
	 * 订单状态(已作废)
	 */
	public static final String ORDERBASECANCEL = "promotion.active.orderbasecancel";
	
	/**
	 * 订单状态(客服取消)
	 */
	public static final String ORDERSPECIALCANCEL = "promotion.active.orderspecialcancel";
	
	/**
	 * 订单状态(未支付)
	 */
	public static final String ORDERPAYSTAYPAID = "promotion.active.orderpaystaypaid" ;
	
	/**
	 * 订单状态(部分支付)
	 */
	public static final String ORDERPAYPART = "promotion.active.orderpaypart";
	
	/**
	 * 订单状态(已支付)
	 */
	public static final String ORDERPAYPAID = "promotion.active.orderpaypaid";
	
	/**
	 * 订单状态(退款申请中)
	 */
	public static final String ORDERPAYREFUNDAPPLY = "promotion.active.orderpayrefundapply";
	
	/**
	 * 订单状态(部分退款)
	 */
	public static final String ORDERPAYREFUNDPART = "promotion.active.orderpayrefundpart";
	
	/**
	 * 订单状态(全额退款)
	 */
	public static final String ORDERPAYREFUNDALL = "promotion.active.orderpayrefundall";
	
	/**
	 * 订单状态(退款失败)
	 */
	public static final String ORDERPAYREFUNFAIL = "promotion.active.orderpayrefunfail";
	
	/**
	 * 订单状态(备货中)
	 */
	public static final String ORDERDELIVERYPREPARE = "promotion.active.orderdeliveryprepare";
	
	/**
	 * 订单状态(部分发货)
	 */
	public static final String ORDERDELIVERYPART = "promotion.active.orderdeliverypart";
	
	/**
	 * 订单状态(已发货)
	 */
	public static final String ORDERDELIVERYSEND = "promotion.active.orderdeliverysend";
	
	/**
	 * 订单状态(已收货)
	 */
	public static final String ORDERDELIVERYRECEIVE = "promotion.active.orderdeliveryreceive";
	
	/**
	 * 订单状态(拒收)
	 */
	public static final String ORDERDELIVERYREFUSE = "promotion.active.orderdeliveryrefuse";
	
	/**
	 * 订单状态(部分退货)
	 */
	public static final String ORDERDELIVERYPARTRETURN = "promotion.active.orderdeliverypartreturn";
	
	/**
	 * 订单状态(已退货)
	 */
	public static final String ORDERDELIVERYALLRETURN = "promotion.active.orderdeliveryallreturn";
	
	/**
	 * 订单状态(已终止发货)
	 */
	public static final String ORDERDELIVERYSTOPSEND = "promotion.active.orderdeliverystopsend";
	
	/**
	 * 性别（男）
	 */
	public static final String LOGINACCOUNT_SEX_BOY ="member.loginaccount.sexboy";
	
	/**
	 * 性别（女）
	 */
	public static final String LOGINACCOUNT_SEX_GIRL ="member.loginaccount.sexgirl";
	
	
	public static final String PROMOTION_ACTIVE_LOGINACCOUNT ="promotion.active.member.loginaccount";
	
	public static final String PROMOTION_ACTIVE_AGE ="promotion.active.member.age";
	
	public static final String PROMOTION_ACTIVE_SEX ="promotion.active.member.sex";
	
	public static final String PROMOTION_ACTIVE_LEVEL ="promotion.active.member.level";
	
	public static final String PROMOTION_ACTIVE_LASTCOUNT ="promotion.active.member.lastcount";
	
	public static final String PROMOTION_ACTIVE_MOBILEPHONE ="promotion.active.member.mobilephone";
	
	public static final String PROMOTION_ACTIVE_EMAIL ="promotion.active.member.email";
	
	public static final String PROMOTION_ACTIVE_COMMODITYNUMBER ="promotion.active.commoditynumber";
	
	public static final String PROMOTION_ACTIVE_COMMODITYNAME ="promotion.active.commodityname";
	
	public static final String PROMOTION_ACTIVE_COMMODITYCOUNT ="promotion.active.commoditycount";
	
	public static final String PROMOTION_ACTIVE_COMMODITYCOLOR ="promotion.active.commoditycolor";

	public static final String PROMOTION_ACTIVE_COMMODITYSIZE ="promotion.active.commoditysize";
	
	public static final String PROMOTION_ACTIVE_ORDERAMOUNT ="promotion.active.orderamount";
	
	public static final String PROMOTION_ACTIVE_PREFERENTIALAMOUNT ="promotion.active.preferentialamount";
	
	public static final String PROMOTION_ACTIVE_PAYAMOUNT ="promotion.active.payamount";
	
	public static final String PROMOTION_ACTIVE_PAYMETHOD ="promotion.active.paymethod";

	public static final String PROMOTION_ACTIVE_PAYSTATE ="promotion.active.paystate";
	
	public static final String PROMOTION_ACTIVE_ADDRESS ="promotion.active.member.address";
	
	public static final String PROMOTION_ACTIVE_CODE ="promotion.active.member.code";
	
	public static final String PROMOTION_ACTIVE_DISTRIBUTIONCOMPANY ="promotion.active.distributioncompany";
	
	public static final String PROMOTION_ACTIVE_DISTRIBUTIONORDERNO ="promotion.active.distributionorderno";
	
	public static final String PROMOTION_ACTIVE_ORDERNO ="promotion.active.orderno";
	
	public static final String PROMOTION_ACTIVE_ORDERSTATE ="promotion.active.orderstate";
	
	

	

}
