/*
 * com.belle.yitiansystem.merchant.enums.MERCHANTStatusEnum
 * 
 *  Tue Jun 23 13:25:28 CST 2015
 * 
 * Copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */

package com.belle.yitiansystem.merchant.constant;

/**
 * 供应商静态常量类
 * @author li.j1
 * @history 2015-06-23 Created
 */
public class MerchantConstant {
	

	
	/**商家状态-新建 */
	public static final int MERCHANT_STATUS_NEW = 2;
	public static final String MERCHANT_STATUS_NEW_ZH = "新建";
	/**商家状态-待业务审核 */
	public static final int MERCHANT_STATUS_BUSINESS_AUDITING = 3;
	public static final String MERCHANT_STATUS_BUSINESS_AUDITING_ZH = "待审核";
	/**商家状态-业务审核通过 */
	public static final int MERCHANT_STATUS_BUSINESS_AUDITED = 4;
	public static final String MERCHANT_STATUS_BUSINESS_AUDITED_ZH = "业务审核通过";
	/**商家状态-业务审核不通过 */
	public static final int MERCHANT_STATUS_BUSINESS_REFUSED = 6;
	public static final String MERCHANT_STATUS_BUSINESS_REFUSED_ZH = "业务审核不通过";
	/**商家状态-财务审核通过 */
	public static final int MERCHANT_STATUS_FINANCE_AUDITED = 5;
	public static final String MERCHANT_STATUS_FINANCE_AUDITED_ZH = "财务审核通过";
	/**商家状态-财务审核不通过 */
	public static final int MERCHANT_STATUS_FINANCE_REFUSED = 7;
	public static final String MERCHANT_STATUS_FINANCE_REFUSED_ZH = "财务审核不通过";
	/**商家状态-启用 */
	public static final int MERCHANT_STATUS_ENABLE = 1;
	public static final String MERCHANT_STATUS_ENABLE_ZH = "启用";
	/**商家状态-停用 */
	public static final int MERCHANT_STATUS_DISABLE = -1;
	public static final String MERCHANT_STATUS_DISABLE_ZH = "停用";
	
	/**商家类型-招商供应商 */
	public static final int SUPPLIER_TYPE_MERCHANT = 1;
	public static final String SUPPLIER_TYPE_MERCHANT_ZH = "招商供应商";
	/**商家类型-普通供应商 */
	public static final int SUPPLIER_TYPE_BELLE = 2;
	public static final String SUPPLIER_TYPE_BELLE_ZH = "普通供应商";
	
	/**合同状态-新建 */
	public static final String CONTRACT_STATUS_NEW = "1";
	public static final String CONTRACT_STATUS_NEW_ZH = "新建";
	/**合同状态-待业务审核 */
	public static final String CONTRACT_STATUS_BUSINESS_AUDITING = "2";
	public static final String CONTRACT_STATUS_BUSINESS_AUDITING_ZH = "待审核";
	/**合同状态-业务审核通过 */
	public static final String CONTRACT_STATUS_BUSINESS_AUDITED = "3";
	public static final String CONTRACT_STATUS_BUSINESS_AUDITED_ZH = "业务审核通过";
	/**合同状态-业务审核不通过 */
	public static final String CONTRACT_STATUS_BUSINESS_REFUSED = "4";
	public static final String CONTRACT_STATUS_BUSINESS_REFUSED_ZH = "业务审核不通过";
	/**合同状态-财务审核通过 */
	public static final String CONTRACT_STATUS_FINANCE_AUDITED = "5";
	public static final String CONTRACT_STATUS_FINANCE_AUDITED_ZH = "财务审核通过";
	/**合同状态-财务审核不通过 */
	public static final String CONTRACT_STATUS_FINANCE_REFUSED = "6";
	public static final String CONTRACT_STATUS_FINANCE_REFUSED_ZH = "财务审核不通过";
	/**合同状态-生效 */
	public static final String CONTRACT_STATUS_EFFECTIVE = "7";
	public static final String CONTRACT_STATUS_EFFECTIVE_ZH = "生效";
	/**合同状态-已过期 */
	public static final String CONTRACT_STATUS_EXPIRED = "8";
	public static final String CONTRACT_STATUS_EXPIRED_ZH = "已过期";


	/** 默认合同附件大小 */
	public static final int DEFAULT_CONTRACT_ATTACHMENT_FILE_SIZE = 10485760;
	/** 合同绑定 */
	public static final String CONTRACT_BIND = "1";
	
	/** 合同保证金是否转入下期*/
	public static final String CONTRACT_TRANSFER_FLAG = "1";
	public static final String CONTRACT_NO_TRANSFER_FLAG = "0";

	/*** renew_flag 续签标识的枚举类型 1当前合同无续签 ,2当前合同 有续签3本合同是续签合同 4 历史失效合同'*/
	public static final String CONTRACT_RENEW_FLAG_CURRENT = "1";
	public static final String CONTRACT_RENEW_FLAG_CURRENT_WITH_FUTURE = "2";
	public static final String CONTRACT_RENEW_FLAG_FUTURE = "3";
	public static final String CONTRACT_RENEW_FLAG_OLD = "4";
	/** 联系人种类 1 业务,2 售后 ,3 仓储（已废弃 .2015-10-30）,4  财务,5 技术 ,6店铺负责人 */
	public static final int CONTACT_TYPE_CHIEF = 6;
	public static final int CONTACT_TYPE_BUSINESS = 1;
	public static final int CONTACT_TYPE_AFTERSALE = 2;
	public static final int CONTACT_TYPE_STORAGE = 3;
	public static final int CONTACT_TYPE_FINANCE = 4;
	public static final int CONTACT_TYPE_TECH =5;
	
	/** 未删除 状态   is_delete_flag的枚举值：1 未删除 */
	public static final int NOT_DELETED = 1;
	public static final int DELETED = 0;
	
	public static final int YES = 1;
	public static final int NO = 0;
	
	/** 日志类型 1 商家日志 0 合同日志*/
	public static final String LOG_FOR_MERCHANT = "1";
	public static final String LOG_FOR_CONTRACT = "0";
	
	/** SupplierQueryVo.listKind的枚举值 ，查询商家的列表的标识-- 商家列表  ，业务审核列表  ，财务审核列表 ， 权限管理的商家列表 */
	public static final String LIST_MERCHANT_APPROVAL = "M_APPROVAL";//默认 商家列表 
	public static final String LIST_BUSINESS_APPROVAL = "B_APPROVAL";//业务审核列表 
	public static final String LIST_FINANCE_APPROVAL = "F_APPROVAL";//财务审核列表
	public static final String LIST_ASSIGN = "ASSIGN";//权限管理的商家列表 
	public static final String LIST_MANAGE_CONTRACT = "MANAGE_CONTRACT";//合同管理
	public static final String LIST_VIEW_CONTRACT = "VIEW_CONTRACT";//合同查看
	public static final String LIST_AUDIT_CONTRACT = "AUDIT_CONTRACT";//合同审核
	
	/** 帐号状态  0锁定 1有效  */
	public static final Integer ACCOUNT_UNLOCK = 1;
	public static final Integer ACCOUNT_LOCK = 0;
	/** 新老商家标志 1 新 2 旧*/
	public static final String NEW_MERCHANT_FLAG = "1";
	public static final String OLD_MERCHANT_FLAG =  "0";
	
	/** 商家续签标志 */
	public static final String IS_NEED_RENEW = "1";
	public static final String NO_NEED_RENEW = "0";
	
	/** 费用状态 1代收款，2已收款，3待退款，4已退款，5已转入下期 6等待转入下期 7上期已转入 8本期已收款*/
	public static final String FEE_STATUS_WAIT_PAY = "1";
	public static final String FEE_STATUS_PAYED = "2";
	public static final String FEE_STATUS_WAIT_REFUND = "3";
	public static final String FEE_STATUS_REFUNDED = "4";
	public static final String FEE_STATUS_TRANSPORT = "5";
	public static final String FEE_STATUS_WAIT_TRANSPORT = "6";
	public static final String FEE_STATUS_PARENT_PAYED = "7";
	public static final String FEE_STATUS_CURRENT_PAYED = "8";
	
	/** 收款单或者退款单的类型 */
	public static final String FEE_DEPOSIT = "deposit";
	public static final String FEE_USEFEE  = "useFee";
	
	/** 商家仓库名称拼接串  */
	public static final String WAREHOUSE_NAME_PRE = "商家_";
	public static final String WAREHOUSE_NAME_END = "仓";
}
