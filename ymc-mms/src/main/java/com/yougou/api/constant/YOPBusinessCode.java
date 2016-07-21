package com.yougou.api.constant;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 业务代码
 * 
 * 说明：数值 10000 以下为系统预留代码值,定义代码值请从 10000 以下为起点
 * 
 * @author 杨梦清
 *
 */
public abstract class YOPBusinessCode {
	
	private static final Set<String> BUSINESS_CODE_SET = new HashSet<String>();

	// 系统代码定义处(基础级业务)
	
	/** 业务请求方式异常(系统只支持 GET 或 POST) **/
	public static final String METHOD_UNSUPPORTED = "100";
	
	/** 业务处理成功 **/
	public static final String SUCCESS = "200";
	
	/** 业务禁止访问 **/
	public static final String FORBIDDEN = "403";
	
	/** 业务不存在 **/
	public static final String NOT_FOUND = "404";
	
	/** 业务处理异常(级别:致命) **/
	public static final String ERROR = "500";
	
	/** 业务处理异常(级别:一般) **/
	public static final String RUNTIME_ERROR = "501";
	
	/** 输入/输出异常(级别:一般) **/
	public static final String IO_ERROR = "502";
	
	/** 未定义异常(级别:一般) **/
	public static final String UNDEFINITION_ERROR = "503";
	
	/******************** 分割线 ********************/
	
	// 业务代码定义处(系统级业务)
	
	/** 缺少必需的业务参数值 **/
	public static final String PARAM_NOT_REQUIRED = "10000";
	
	/** 参数类型不匹配 **/
	public static final String PARAM_TYPE_MISMATCH = "10001";
	
	/** 正规表达式不匹配 **/
	public static final String PARAM_REGULAR_EXPRESSION_NOT_MATCHED = "10002";
	
	/** 超出字符串最小长度 **/
	public static final String PARAM_STRING_MINLENGTH_OVERFLOW = "10003";
	
	/** 超出字符串最大长度 **/
	public static final String PARAM_STRING_MAXLENGTH_OVERFLOW = "10004";
	
	/** 超出数字最小值 **/
	public static final String PARAM_NUMBER_MINVALUE_OVERFLOW = "10005";
	
	/** 超出数字最大值 **/
	public static final String PARAM_NUMBER_MAXVALUE_OVERFLOW = "10006";
	
	/** 空字符串 **/
	public static final String PARAM_STRING_IS_EMPTY = "10007";
	
	/** 参数组长度不匹配 **/
	public static final String PARAM_GROUP_LENGTH_IS_INCONSISTENCY = "11005";
	
	/** 参数名称重复 **/
	public static final String PARAM_NAME_DUPLICATE = "10008";
	
	
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

	/** API page_index/page_size 无效 **/
	public static final String API_PAGING_IS_INVALID = "10107";
	
	/** API 授权认证失败 **/
	public static final String API_AUTH_IS_FAILURE = "10108";
	
	/** API timestamp 误差范围超出 **/
	public static final String API_TIMESTAMP_ERROR_RANGE_OVERFLOW = "10109";
	
	/** API 已被禁用 **/
	public static final String API_IS_UNABLE = "10110";
	
	/******************** 分割线 ********************/
	
	// 业务代码定义处(应用级业务)
	
	/** 商品持有人无效 **/
	public static final String COMMODITY_HOLDER_IS_INVALID = "11003";
	
	/** 货品库存更新失败 **/
	public static final String INVENTORY_UPDATE_FAILURE = "11004";
	
	/** 订单出库失败 **/
	public static final String ORDER_OUT_STORE_FAILURE = "11006";
	
	static {
		try {
			// 校验业务代码唯 一性及聚合业务代码
			for (Field field : YOPBusinessCode.class.getFields()) {
				String value = (String) field.get(null);
				if (!BUSINESS_CODE_SET.add(value)) {
					throw new IllegalStateException(MessageFormat.format("Duplicate entry ''{0}'' for field {1}", value, field.getName()));
				}
			}
		} catch (IllegalAccessException ex) {
			throw new IllegalStateException(ex);
		}
	}
	
	/**
	 * 获取业务代码集
	 * 
	 * @return Set
	 */
	public static Set<String> getBusinessCodeSet() {
		return Collections.unmodifiableSet(BUSINESS_CODE_SET);
	}
}
