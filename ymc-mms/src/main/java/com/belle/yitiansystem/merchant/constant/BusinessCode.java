package com.belle.yitiansystem.merchant.constant;

/**
 * 业务代码
 * 
 * 说明：数值 10000 以下为系统预留代码值,定义代码值请从 10000 以下为起点
 * 
 * @author 杨梦清
 *
 */
public class BusinessCode {

	// 系统代码定义处(基础级业务)
	
	/** 业务处理成功 **/
	public static final String SUCCESS = "200";
	
	/** 业务禁止访问 **/
	public static final String FORBIDDEN = "403";
	
	/** 业务处理异常 **/
	public static final String ERROR = "500";
	
	/******************** 分割线 ********************/
	
	// 业务代码定义处(系统级业务)
	
	/** 缺少必需的业务参数值 **/
	public static final String VALIDATOR_NOT_REQUIRED_PARAMETER = "10000";
	
	/** 参数类型不匹配 **/
	public static final String VALIDATOR_PARAMETER_TYPE_MISMATCH = "10001";
	
	/** 正规表达式不匹配 **/
	public static final String VALIDATOR_REGULAR_EXPRESSION_NOT_MATCHED = "10002";
	
	/** 超出字符串最小长度 **/
	public static final String VALIDATOR_STRING_MINLENGTH_OVERFLOW = "10003";
	
	/** 超出字符串最大长度 **/
	public static final String VALIDATOR_STRING_MAXLENGTH_OVERFLOW = "10004";
	
	/** 超出数字最小值 **/
	public static final String VALIDATOR_NUMBER_MINVALUE_OVERFLOW = "10005";
	
	/** 超出数字最大值 **/
	public static final String VALIDATOR_NUMBER_MAXVALUE_OVERFLOW = "10006";
	
	/** 空字符串 **/
	public static final String VALIDATOR_STRING_IS_EMPTY = "10007";
	
	
	/** API app_version 无效 **/
	public static final String API_VERSION_IS_INVALID = "10100";
	
	/** API method 无效 **/
	public static final String API_METHOD_IS_INVALID = "10101";
	
	/** API timestamp 无效 **/
	public static final String API_TIMESTAMP_IS_INVALID = "10102";
	
	/** API sign 无效 **/
	public static final String API_SIGN_IS_INVALID = "10103";
	
	/** API sign_method 无效 **/
	public static final String API_SIGN_METHOD_IS_INVALID = "10104";
	
	/** API app_key 无效 **/
	public static final String API_APP_KEY_IS_INVALID = "10105";

	/** API app_key 禁用 **/
	public static final String API_APP_KEY_IS_CLOSED = "10106";

	
	/******************** 分割线 ********************/
	
	// 业务代码定义处(应用级业务)
	
	/** 商品持有人无效 **/
	public static final String COMMODITY_HOLDER_IS_INVALID = "11003";
	
	/** 货品库存更新失败 **/
	public static final String INVENTORY_UPDATE_FAILURE = "11004";
	

}
