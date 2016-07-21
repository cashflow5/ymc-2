package com.yougou.kaidian.framework.util;

import com.taobao.api.TaobaoClient;

/**
 * YMC全局变量，解决多线程并发问题
 * @author le.sm
 *
 */
public class YmcThreadLocalHolder {
	/**
	 * 商家编码
	 */
	private static final ThreadLocal<String> merchant_threadLocal = new ThreadLocal<String>(){
		protected String initialValue() {
			return "";
		};
	}; 
	
	/***
	 * 登录账户名称
	 */
	private static final ThreadLocal<String> operater_threadLocal = new ThreadLocal<String>(){
		protected String initialValue() {
			return "system";
		};
	}; 
	
	/***
	 * 商家名称
	 */
	private static final ThreadLocal<String> merchantName_threadLocal = new ThreadLocal<String>(){
		protected String initialValue() {
			return "";
		};
	}; 
	
	/**
	 * nickId
	 */
	private static final ThreadLocal<Long> nickId_threadLocal = new ThreadLocal<Long>(){
		protected Long initialValue() {
			return 0L;
		};
	}; 
	/**
	 * 款色名称
	 */
	private static final  ThreadLocal<String> colorName_threadLocal = new ThreadLocal<String>(){
		protected String initialValue() {
			return "";
		};
	}; 
	 /**
	  * 淘宝client
	  */
	private static final ThreadLocal<TaobaoClient> taobaoClient_threadLocal = new ThreadLocal<TaobaoClient>(); 
	 /**
	  * 淘宝sessionKey
	  */
	private static final ThreadLocal<String> sessionKey_threadLocal = new ThreadLocal<String>(); 
	
	public static String getMerchantCode() {
		if( null!=merchant_threadLocal && null!=merchant_threadLocal.get() ){
			return merchant_threadLocal.get();
		}else{
			return "unknown";
		}
	}
	
	
	public static String getOperater() {
		if( null!=operater_threadLocal && null!=operater_threadLocal.get() ){
			return operater_threadLocal.get();
		}else{
			return "unknown";
		}
	}
	
	
	public static String getColorName() {
		return colorName_threadLocal.get();
	}
	
	public static Long getNickId(){
		return nickId_threadLocal.get();
	} 
	
	public static TaobaoClient getTaobaoClient() {
		return taobaoClient_threadLocal.get();
	}
	
	public static String getSessionKey(){
		return sessionKey_threadLocal.get();
	}
	
	public static String getMerchantName() {
		return merchantName_threadLocal.get();
	}

	public static void setMerchantName(String merchantName ){
		merchantName_threadLocal.set(merchantName);
	}
	
	public static void setNickId(Long nickId){
		nickId_threadLocal.set(nickId);
	}
	
	public static void setColorName(String colorName){
		colorName_threadLocal.set(colorName);
	}
	
	public static void setOperater(String operater){
		operater_threadLocal.set(operater);
	}
	
	
	public static void setTaobaoClient(TaobaoClient taobaoClient){
		taobaoClient_threadLocal.set(taobaoClient);
	}
	public static void setMerchantCode(String merchantCode){
		merchant_threadLocal.set(merchantCode);
	}
	
	public static void setSessionKey(String sessionKey) {
		sessionKey_threadLocal.set(sessionKey);
	}
	
	/**
	 * 清理所有
	 */
	public static void clearAll() {
		merchant_threadLocal.remove();
		operater_threadLocal.remove();
		colorName_threadLocal.remove();
		taobaoClient_threadLocal.remove();
		nickId_threadLocal.remove();
		sessionKey_threadLocal.remove();
		merchantName_threadLocal.remove();
	}
	public static final int  MERCHANT_LOCAL = 1;
	public static final int  OPERATER_LOCAL = 2;
	public static final int  COLOR_NAME_LOCAL = 3;
	public static final int  TAOBAO_CLIENT_LOCAL = 4;
	public static final int  NIKEID_LOCAL = 5;
	public static final int  SESSION_KEY_LOCAL = 6;
	public static final int  MERCHANT_NAME_LOCAL = 7;
	/**
	 * 清理单个缓存
	 * @param local
	 */
	public static void clearSimple(int local){
		switch (local) {
		case MERCHANT_LOCAL:
			merchant_threadLocal.remove();
			break;
		case OPERATER_LOCAL:
			operater_threadLocal.remove();
			break;
		case COLOR_NAME_LOCAL:
			colorName_threadLocal.remove();
			break;
		case TAOBAO_CLIENT_LOCAL:
			taobaoClient_threadLocal.remove();
			break;
		case NIKEID_LOCAL:
			nickId_threadLocal.remove();
			break;
		case SESSION_KEY_LOCAL:
			sessionKey_threadLocal.remove();
			break;
		case MERCHANT_NAME_LOCAL:
			merchantName_threadLocal.remove();
			break;
		default:
			break;
		}
	}

}
