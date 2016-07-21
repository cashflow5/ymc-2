package com.belle.infrastructure.util;

/**
 * 支付宝支付协议参数配置(通过自定义注解方式读取资源配置文件)
 *<P>
 * 2011-4-8下午06:18:47
 *<P>
 * @author liudi 
 */

public class AlipayConfig {

	/**
	 *  合作身份者ID，以2088开头由16位纯数字组成的字符串
	 */
	/*@Property(name = "alipay.partner")
	public static  String partner ;*/

	/**
	 *  交易安全检验码，由数字和字母组成的32位字符串
	 */
	/*@Property(name = "alipay.key")
	public static String key ;*/

	/**
	 *  签约支付宝账号或卖家收款支付宝帐户
	 *//*
	@Property(name = "alipay.seller.email")
	public static String seller_email ;*/
	
	/**
	 * des加密私钥
	 */
	public static String DES_KEY_DATA = "com.belle.des.Key.Data";
	
	/**
	 * 服务器地址
	 */
	//@Property(name = "server.url")
	public static String PAY_ALIPAY_SERVER_URL="com.belle.pay.alipay.server.url";

	/**
	 * 接收支付宝异步支付结果
	 */
	//@Property(name = "alipay.notify.url")
	public static String PAY_ALIPAY_NOTIFY_URL="com.belle.pay.alipay.notify.url";

	/**
	 * 接收支付宝同步支付结果
	 */
	//@Property(name = "alipay.return.url")
	public static String PAY_ALIPAY_RETURN_URL ="com.belle.pay.alipay.return.url";
	
	/**
	 * 退款后台通知
	 */
	//@Property(name = "alipay.refund.notify.url")
	public static String PAY_ALIPAY_REFUND_NOTIFY_URL = "com.belle.pay.alipay.refund.notify.url";
	
	/**
	 * 超卖-退款后台通知
	 */
	public static String PAY_EXCEED_ALIPAY_REFUND_NOTIFY_URL = "com.belle.pay.exceed.alipay.refund.notify.url";
	
	/**
	 * 无线支付宝退款后台通知
	 */
	public static String PAY_IOS_ALIPAY_REFUND_NOTIFY_URL = "com.belle.pay.ios.alipay.refund.notify.url";
	
	/**
	 * 超卖-无线支付宝退款后台通知
	 */
	public static String PAY_IOS_EXCEED_ALIPAY_REFUND_NOTIFY_URL = "com.belle.pay.ios.exceed.alipay.refund.notify.url";
	
	/**
	 * 快捷登陆同步通知
	 * @author Mrvoce
	 */
	public static String PAY_ALIPAY_FAST_LOGIN_REFUND_URL = "com.belle.pay.alipay.fast_login.return.url";
	
	/**
	 * 物流地址查询同步通知
	 * @author Mrvoce
	 */
	public static String PAY_ALIPAY_QUERY_ADDRESS_REFUND_URL = "com.belle.pay.alipay.query_address.return.url";
	
	/**
	 *  网站商品的展示地址，不允许加?id=123这类自定义参数
	 */
	//@Property(name = "alipay.show.url")
	public static String PAY_ALIPAY_SHOW_URL="com.belle.pay.alipay.refund.show.url";

	
	/**
	 *  收款方名称，如：公司名称、网站名称、收款人姓名等
	 */
	//@Property(name = "alipay.main.name")
	public static String PAY_ALIPAY_MAINNAME="com.belle.pay.alipay.main.name";

	
	/**
	 *  字符编码格式 目前支持 gbk 或 utf-8
	 */
	//@Property(name = "alipay.input.charset")
	public static String PAY_ALIPAY_INPUT_CHARSET="com.belle.pay.alipay.input.charset";

	
	/**
	 *  签名方式
	 */
	//@Property(name = "alipay.sign.type")
	public static String PAY_ALIPAY_SIGN_TYPE ="com.belle.pay.alipay.sign.type";

	
	/**
	 *  交易网关
	 */
	//@Property(name = "alipay.gateway")
	public static String PAY_ALIPAY_GATEWAY ="com.belle.pay.alipay.gateway";

	/**
	 *  交易网关(new)
	 *  @author Mrvoce
	 */
	//@Property(name = "alipay.gateway")
	public static String PAY_ALIPAY_GATEWAY_NEW ="com.belle.pay.alipay.gateway.new";
	
	
	/**
	 *  访问模式,根据自己的服务器是否支持ssl访问，若支持请选择https；若不支持请选择http
	 */
	//@Property(name = "alipay.transport")
	public static String PAY_ALIPAY_TRANSPORT ="com.belle.pay.alipay.transport";

	
	/**
	 * 退款接口名称
	 */
	//@Property(name = "alipay.refund.service")
	public static String PAY_ALIPAY_REFUND_SERVICE ="com.belle.pay.alipay.refund.service";

	
	/**
	 * 支付接口名称
	 */
	//@Property(name = "alipay.service")
	public static String PAY_ALIPAY_SERVICE ="com.belle.pay.alipay.service";

	/**
	 * 快捷登陆接口名称
	 * @author Mrvoce
	 */
	public static String FAST_LOGIN_ALIPAY_SERVICE ="com.belle.pay.alipay.fast_login.service";
	
	/**
	 * 查询物流地址接口名称
	 */
	public static String QUERY_ADDRESS_ALIPAY_SERVICE ="com.belle.pay.alipay.query_address.service";
	
	/**
	 * 快捷登陆接口-目标
	 * @author Mrvoce
	 */
	public static String FAST_LOGIN_ALIPAY_TARGET_SERVICE = "com.belle.pay.alipay.fast_login.target.service";
	
	/**
	 * 支付宝退款后返回财务的URL
	 */
	public static String redirectUrl = "finance.income.returndebt.responseurl";
	

	/**
	 * 响应订单url
	 */
	public static String responseUrl = "";

	/**
	 * 原商户订单号
	 */
	public static String orderId = "";

	public static String onlinePayStyle = "";

	/**
	 * 支付编码
	 */
	// 支付宝
	public static final String PAY_TYPE_ALIPAY = "1001";
	 //拉卡拉
    public static final String PAY_TYPE_LAKALAPAY = "1002";
	// 快钱
	public static final String PAY_TYPE_BILLPAY = "1003";
	// 财付通
	public static final String PAY_TYPE_TENPAY = "1004";
	// 手机钱包
	public static final String PAY_TYPE_MOBILEPAY = "1005";
	
	// 手机-支付宝
	public static final String PAY_TYPE_MOBILEALIPAY = "2001";
	
	// 手机-wap支付宝
	public static final String PAY_TYPE_MOBILEWAP = "2002";
	
	// 手机-招行直连
	public static final String PAY_TYPE_MOBILECMB = "2003";
	
	//手机-中国银联
    public static final String TRADE_TYPE_MOBILE_UNIONPAY = "2004";

	
	// 移动商城嗖付
	public static final String PAY_TYPE_MOBILEMALL = "4001";
	
	/** 支持支付宝的银行 */
	
	//工商银行
	public static final String PAY_TYPE_BANKPAY_ICBC = "10011";
	//建设银行
	public static final String PAY_TYPE_BANKPAY_CCB = "10012";
	//中国银行
	public static final String PAY_TYPE_BANKPAY_BOC = "10013";
	
	/** 快捷支付银行*/
	
	//工商银行
	public static final String PAY_TYPE_CREDIT_ICBC = "10010107";
	//建设银行
	public static final String PAY_TYPE_CREDIT_CCB = "10010207";
	//中国银行
	public static final String PAY_TYPE_CREDIT_BOC = "10010307";
	//中国农业银行
	public static final String PAY_TYPE_CREDIT_ABC = "10010407";
	//平安银行
	public static final String PAY_TYPE_CREDIT_SPABANK = "10010507";
	//深发展银行
	public static final String PAY_TYPE_CREDIT_SDB = "10010607";
	//华夏银行 
	public static final String PAY_TYPE_CREDIT_HXBANK = "10010707";
	//中国民生银行
	public static final String PAY_TYPE_CREDIT_CMBC = "10010807";
	//兴业银行
	public static final String PAY_TYPE_CREDIT_CIB = "10010907";
	//北京银行
	public static final String PAY_TYPE_CREDIT_BJBANK = "10011007";
	//上海农商银行
	public static final String PAY_TYPE_CREDIT_SHRCB = "10011107";
	//广发银行
	public static final String PAY_TYPE_CREDIT_GDB = "10011207";
	//光大银行
	public static final String PAY_TYPE_CREDIT_CEB = "10011307 ";
	//宁夏银行
	public static final String PAY_TYPE_CREDIT_NXBANK = "10011407";
	//大连银行
	public static final String PAY_TYPE_CREDIT_DLB = "10011507";
	//东莞银行
	public static final String PAY_TYPE_CREDIT_BOD = "10011607";
	//宁波银行
	public static final String PAY_TYPE_CREDIT_NBBANK = "10011707";
	//天津银行
	public static final String PAY_TYPE_CREDIT_TCCB = "10011807";
	//杭州银行
	public static final String PAY_TYPE_CREDIT_HZCB = "10011907";
	//河北银行 
	public static final String PAY_TYPE_CREDIT_BHB = "10012007";
	//江苏银行
	public static final String PAY_TYPE_CREDIT_JSBANK = "10012107";
	//南京银行
	public static final String PAY_TYPE_CREDIT_NJCB = "10012207";
	//徽商银行
	public static final String PAY_TYPE_CREDIT_HSBANK = "10012307";
	//浙江泰隆商业银行
	public static final String PAY_TYPE_CREDIT_ZJTLCB = "10012407";
	//吴江农商银行
	public static final String PAY_TYPE_CREDIT_WJRCB = "10012507";
	//招商银行
	public static final String PAY_TYPE_CREDIT_CMB = "10012607";
	//中信银行
	public static final String PAY_TYPE_CREDIT_CITIC = "10012707";
	//浦发银行
	public static final String PAY_TYPE_CREDIT_SPDB = "10012807";
	
	/** 支持快钱的银行 */
	
	//招商银行
	public static final String PAY_TYPE_BANKPAY_CMB = "10031";
	//交通银行
	public static final String PAY_TYPE_BANKPAY_BCOM = "10032";
	//兴业银行
	public static final String PAY_TYPE_BANKPAY_CIB = "10033";
	//民生银行
	public static final String PAY_TYPE_BANKPAY_CMBC = "10034";
	//广发银行
	public static final String PAY_TYPE_BANKPAY_GDB = "10035";
	//深发展银行
	public static final String PAY_TYPE_BANKPAY_SDB = "10036";
	//华厦银行
	public static final String PAY_TYPE_BANKPAY_HXB = "10037";
	//中国邮政储蓄银行
	public static final String PAY_TYPE_BANKPAY_PSBC = "10038";
	//上海银行
	public static final String PAY_TYPE_BANKPAY_SHB = "10039";
	//广州银行
	public static final String PAY_TYPE_BANKPAY_GZCB = "100310";
	//杭州银行
	public static final String PAY_TYPE_BANKPAY_HZB = "100311";
	//渤海银行
	public static final String PAY_TYPE_BANKPAY_CBHB = "100312";
	//BEA东亚银行
	public static final String PAY_TYPE_BANKPAY_BEA = "100313";
	//宁波银行
	public static final String PAY_TYPE_BANKPAY_NBCB = "100314";
	
	
	//当当代收
	public static final String PAY_TYPE_DANGDANG = "8001";
	
	//京东代收
	public static final String PAY_TYPE_360BUY = "8002";
	
	//一号店代收
	public static final String PAY_TYPE_YIHAODIAN = "8003";
	
	//分销账户
	public static final String PAY_TYPE_FENGXIAO = "8004";
	
	//苏宁代收
	public static final String PAY_TYPE_SUNING = "8005";
	//银泰代收
	public static final String PAY_TYPE_YINTAI = "8006";
	//亚马逊代收
	public static final String PAY_TYPE_YAMAZON = "8007";
	/**
	 * V+代收
	 */
	public static final String PAY_TYPE_VJIA = "8008";
	/**
	 * QQ代收
	 */
	public static final String PAY_TYPE_QQ = "8009";

    /**银行直连 **/
	
	//招商银行
	public static final String TRADE_TYPE_BANKPAY_CMB = "3001";
	
	//工商银行
	public static final String TRADE_TYPE_BANKPAY_ICBC = "3002";
	
	//农业银行
	public static final String TRADE_TYPE_BANKPAY_ABC = "3003";

	//建设银行
	public static final String TRADE_TYPE_BANKPAY_CCB = "3004";

	// 中国银行
	public static final String TRADE_TYPE_BANKPAY_BOC = "3005";

	//中国银联
	public static final String TRADE_TYPE_UNIONPAY = "3006";
	
	//交通银行
    public static final String TRADE_TYPE_BANKPAY_BCOM = "3007";
	
	/** 支持财付通的银行 */
	
	//农业银行
	public static final String PAY_TYPE_BANKPAY_ABC = "10041";
	//浦发银行
	public static final String PAY_TYPE_BANKPAY_SPDB = "10042";
	//平安银行
	public static final String PAY_TYPE_BANKPAY_PAB = "10043";
	//北京银行
	public static final String PAY_TYPE_BANKPAY_BOB = "10044";
	
	/** 支持手机支付的银行 */
	
	//光大银行
	public static final String PAY_TYPE_BANKPAY_CEB = "10051";
	//中信银行
	public static final String PAY_TYPE_BANKPAY_CITIC = "10052";
	
	public static boolean readFlag = false;
	/**
	 * 待支付
	 */
	public static final String PAY_STATUS_WAIT = "waiting";
	/**
	 * 支付成功
	 */
	public static final String PAY_STATUS_SUCCESS = "success";
	/**
	 * 支付失败
	 */
	public static final String PAY_STATUS_FAIL = "fail";
}

