package com.yougou.kaidian.framework.constant;

public class Constant {

	/**
	 * 删除标识符  1 没有删除  0已删除
	 */
	public static final Short NO_DELTE_FLAG = 1;
	public static final Short HAS_DELTE_FLAG = 0;


	/**
	 * 折表标示‘是’
	 */
	public static final Short SPLIT_FLAG_IS = 1 ;
	public static final Short SPLIT_FLAG_NO = 0 ;

	/**
	 * 编号（公用）
	 */
	public static final String COMMON_NO = "1111111111111";
	
	
    public static final String FAIL_TAG = "0"; //失败

    public static final String SUCCESS_TAG = "1";  //成功

    public static final String WAITING_TAG = "2";//待发送
    
	public static final int LOGIN_COOKIE_TIME = 7*24*60*60;

	/**
	 * 广告图片是大值 2M
	 */
	public static final long ADV_IMAGE_SIZE = 2*1024*1024;
	/***
	 * 采购单为空
	 */
	public static final String NULLDETAIL = "nullDetail" ;
	/**
	 * 成功
	 */
	public static final String SUCCESS = "success";
	/**
	 * 失败
	 */
	public static final String FAIL = "fail";

	/**
	 * 图片验证码
	 */
	public static final String  LOGIN_VALIDATE_IMAGE = "login_validate_image";
	/**
	 * 图片验证码2
	 */
	public static final String  LOGIN_VALIDATE_IMAGE2 = "login_validate_image2";
	/**
	 * 系统用户登录常量标识
	 */
	public static final String LOGIN_SYSTEM_USER = "login_system_user";

	/**
	 * 登录用户Cookie的ID值
	 */
	public static final String LOGIN_SYSTEM_USER_COOKIE_ID = "login_system_user_cookie_id";
	/**
	 * 登录用户资源集合
	 */
	public static final String LOGIN_SYSTEM_USER_RESOURCES = "login_system_user_resources";

	/**
	 * 系统所有的权限资源
	 */
	public static final String ALL_SYSTEM_RESOURCES = "all_system_resources";

	 /**
     * 系统所有模块
     */
    public static final String ALL_SYSTEM_MODEL_LIST = "all_system_model_list";

    /**
     * 未登录或session过期
      */
    public  static final String NOT_LOGIN = "notLogIn";

    /**
     * 无访问权限
     */
    public static final String NO_PRIVILEGE = "noPrivilege";

    /**
     * 可访问
     */
    public static final String ACCESSABLE = "accessable";

    public static final String SYSTEM_LEVEL = "0";  //系统超级管理员

    /**
     * 权限过滤地址系统配置键
     */
    public static final String EXCLUDEURL = "privilege.excludeUrl";   //无限制路径(未登录用户)
    public static final String GRANTEDURL = "privilege.grantedUrl";

    public static final String CONTROLLER_MODEL = "privilege.controller.model";   //需要进行权限处理的模块
    public static final String MODE = "privilege.mode";
    public static final String NULLCONTROLLER = "filter.nullController";			//是过滤没有录入的资源  0：不过滤  1: 过虑



    /**
     * 邮件配置键值
     */
    public static final String MAIL_HOST="mail.host";
    public static final String MAIL_SERVICE_USERNAME="mail.service.username";
    public static final String MAIL_SERVICE_PASSWORD="mail.service.password";

    /**
     * 手机短信账号配置
     */
    public static final String CELLPHONE_SN="cellphone.sn";
    public static final String CELLPHONE_PWD="cellphone.pwd";
    public static final String CELLPHONE_SERVICE_URL="cellphone.service.url";
	/**
	 * 日志类型
	 */
	public static final String LOG_COMMON_SYSTEM = "log_common_system";   //普通信息日志
	public static final String LOG_SYSTEM_EXCEPTION = "log_system_exception";  //系统错误日志
	public static final String LOG_APP_EXCEPTION = "log_app_exception";  //业务异常日志

	public static final String WEB_TEMPLATE = "template/webtemplate/";

	/**********************JDBC Utils Configs*************************/
	public static final String JDBC_URL = "com.belle.infrastructure.jdbc.url";
	public static final String JDBC_USERNAME = "com.belle.infrastructure.jdbc.username";
	public static final String USER_PASSWORD = "com.belle.infrastructure.jdbc.password";
	public static final String BATCH_SIZE = "com.belle.infrastructure.jdbc.batchsize";
	/**********************JDBC Utils Configs*************************/

	/** 全局部署模式 */
	public static final String GLOBAL_MODE = "com.belle.infrastructure.globalmode";

	/**********************JS文件版本常量 Start****************************/
	// 商品详情页js 路径:template/web/js/goods.js
	public static final String JS_VERSION_GOODS_KEY = "com.belle.yitianmall.commodityshow.commoditydetail.goodsjs";

	// 优购Core Js 路径:template/web/js/goods.js
	public static final String JS_VERSION__KEY = "com.belle.core.yougoujs";
	/**********************JS文件版本常量End****************************/
	
	/**
	 * 订单发送邮件开关  默认不发送
	 */
	public static final String ORDER_SEND_EMAIL_SWITCH = "com.belle.order.send.email.switch";
	
	/**
	 * 当前是否开发环境 默认开发环境  true
	 */
	public static final String SYSTEM_ENVIRONMENT_DEV = "com.belle.system.dev";
	
	/**
	 * 淘秀虚拟仓库id
	 */
	public static final String TAOXIU_VIRTUALWAREHOUSEID = "taoxiu_virtualwarehouse_id";
	/**
	 * 淘秀仓库库存上传ftp主机
	 */
	public static final String FTP_TAOXIU_IP = "ftp_taoxiu_ip";
	
	/**
	 * 淘秀仓库库存上传ftp用户名
	 */
	public static final String FTP_TAOXIU_USERNAME = "ftp_taoxiu_username";
	
	/**
	 * 淘秀仓库库存上传ftp密码
	 */
	public static final String FTP_TAOXIU_PASSWORD = "ftp_taoxiu_password";
	
	/**
	 * 淘秀仓库库存上传ftp端口号
	 */
	public static final String FTP_TAOXIU_PORT = "ftp_taoxiu_port";
	
	/**
	 * 淘秀仓库库存上传ftp文件夹名称
	 */
	public static final String FTP_TAOXIU_DIRECTORY_NAME = "ftp_taoxiu_directory_name";
	
	/**
	 * 进销存月结日
	 */
	public static final String INVENTORYMONTHKNOT_DAY = "inventorymonthknot_day";
	
	/**
	 * 注册送积分
	 */
	public static final String LOGINACCOUNT_INTEGRAL = "001";

	/**
	 * 购物送积分
	 */
	public static final String PAY_INTEGRAL = "002";

	/**
	 *商品评价送积分
	 */
	public static final String COMMDITY_COMMENT_INTEGRAL = "003";
	/**
	 *登录送积分
	 */
	public static final String LOGIN_INTEGRAL = "004";
	
	/**
	 * 是否自动升级
	 */
	public static final String UPGRADES_Y = "1";//是
	public static final String UPGRADES_N = "0";//否
	
	/**
	 * 会员等级
	 */
	public static final String MEMBER_VIP = "VIP会员";//是
	public static final String MEMBER_GENERAL = "普通会员";//否
	
	/**
	 * 是否自动升级默认
	 */
	public static final Integer UPGRADES_DEFAULT = 1;
	/**
	 *会员类型默认 
	 */
	public static final Integer MEMBER_TYPE_DEFAULT = 1;
	
	
	/** ********************JDBC Utils Configs(上海体育商城)************************ */
	public static final String JDBC_URL_SH = "com.belle.infrastructure.jdbc.url_sh";
	public static final String JDBC_USERNAME_SH = "com.belle.infrastructure.jdbc.username_sh";
	public static final String USER_PASSWORD_SH = "com.belle.infrastructure.jdbc.password_sh";
	public static final String BATCH_SIZE_SH = "com.belle.infrastructure.jdbc.batchsize_sh";
	/** ********************JDBC Utils Configs(上海体育商城)************************ */
	
	public static int FLAT_DELETE_YGORDER = 0;
	
	/**上架状态*/
	public static int COMMODITY_STATUS_AVIL = 2;
	
	/**下架状态*/
	public static int COMMODITY_STATUS_DOWN = 1;
	
	/**
	 * 培训中心文件读取路径配置名称
	 */
	public static final String TRAINING_FTP_READ_PATH = "training.ftp.path.read";
	
	/**
	 * 根据ip获取IP所在地信息接口URL
	 */
	public static final String GET_ADDRESS_BYID_URL= "http://ip.taobao.com/service/getIpInfo.php?ip=";
	
	
	/**
	 * 通过新浪的接口。根据ip获取IP所在地信息接口URL
	 */
	public static final String GET_ADDRESS_BYID_URL_USE_SINA= "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format={0}&ip={1}";
	
	
	/**
	 * 版式设置缓存ID前缀
	 */
	public final static String LAYOUTSETTING_REDIS_PREFIX = "layoutSetting_";
	
	//==============================================数据报表经营概况指标Start=====================================================
	
	/**
	 * 访次 
	 */
	public static final String INDEX_NAME_VISIT_COUNT = "visitCount";
	/**
	 * 浏览量
	 */
	public static final String INDEX_NAME_PAGE_VIEW = "pageView";
	/**
	 * 支付订单数
	 */
	public static final String INDEX_NAME_PAYED_ORDER_NUM = "payedOrderNum";
	/**
	 * 支付金额
	 */
	public static final String INDEX_NAME_PAYED_ORDER_AMT = "payedOrderAmt";
	/**
	 * 订单均价
	 */
	public static final String INDEX_NAME_PAYED_ORDER_AVG_AMT = "payedOrderAvgAmt";
	/**
	 * 转化率
	 */
	public static final String INDEX_NAME_CHANGE_PERCENT = "changePercent";
	/**
	 * 发货率
	 */
	public static final String INDEX_NAME_DELIVERY_PERCENT = "deliveryPercent";
	/**
	 * 新上架商品数
	 */
	public static final String INDEX_NAME_NEW_SALE_COMMODITY_NUM= "newSaleCommodityNum";
	/**
	 * 支付商品数
	 */
	public static final String INDEX_NAME_PAYED_COMMODITY_NUM = "payedCommodityNum";
	/**
	 * 商品均价
	 */
	public static final String INDEX_NAME_AVG_COMMODITY_AMT = "avgCommdotiyAmt";
	/**
	 * 收订订单数
	 */
	public static final String INDEX_NAME_ORDER_NUM = "orderNum";
	/**
	 * 收订金额
	 */
	public static final String INDEX_NAME_TOTAL_AMT = "totalAmt";
	/**
	 * 发货订单数
	 */
	public static final String INDEX_NAME_DELIVERY_ORDER_NUM = "deliveryOrderNum";
	/**
	 * 发货金额
	 */
	public static final String INDEX_NAME_DELIVERY_ORDER_AMT = "deliveryOrderAmt";
	/**
	 * 退货拒收数
	 */
	public static final String INDEX_NAME_REJECTED_COMMODITY_NUM = "rejectedCommodityNum";
	/**
	 * 退货拒收额
	 */
	public static final String INDEX_NAME_REJECTED_COMMODITY_AMT = "rejectedCommodityAmt";
	/**
	 * 退货拒收率
	 */
	public static final String INDEX_NAME_REJECTED_COMMODITY_PERCENT= "rejectedCommodityPercent";
	
	//==============================================数据报表经营概况指标End=====================================================
	
	
	public static final short OFFICIAL_ACTIVE_STATUS_NEW = 1; //新建
	public static final short OFFICIAL_ACTIVE_STATUS_AUDITING = 2; //待审批
	public static final short OFFICIAL_ACTIVE_STATUS_AUDITED = 3; //审批通过
	public static final short OFFICIAL_ACTIVE_STATUS_REFUSED = 4; //审批不通过
	public static final short OFFICIAL_ACTIVE_STATUS_TEMINATED = 5; //已结束
	//最高支持优惠券最大金额key
	public static final String YMC_COUPON_HIGHEST_VALUE = "ymc_coupon_highest_value";
	//最高支持优惠券最大金额key
	public static final String YMC_COUPON_DEFAULT_VALUE = "ymc_coupon_default_value";
	//最高支持优惠券商家统一设置
	public static final String YMC_COUPON_UNIQUE_VALUE = "ymc_coupon_unique_value";
	//活动商家统一最高支持优惠券金额
	public static final String OFFICIAL_ACTIVE_COUPON_UNIQUE = "1";
	
	/**
	 * 韩国站首尔直发订单来源编码起始部分，用于判断是否首尔直发订单
	 */
	public static final String KOREA_ORDER_SOURCE_NO_START ="SOZF"; 
	/**
	 * 韩国站首尔直发订单，指定物流公司编码配置项key
	 */
	public static final String KOREA_ORDER_LOGISTIC_COMPANY_CODE ="korea.order.logistic.company.code"; 
	
	//================================版式设置优化Start======================================
	/**
	 * 版式设置优化，生成静态html 页面存放路径
	 */
	public static final String FILE_NAME_URL_PATH = "/virtual/inc.war/ymc_layoutset_link/";
	
	/**
	 * 版式设置优化，静态页浏览器访问地址
	 */
	public static final String FILE_NAME_FOR_BROWER_URL_PATH = "/inc/ymc_layoutset_link/";
	
	/**
	 * 版式设置优化，生成静态页redis 识别标记
	 */
	public static final String REDIS_CHANNEL_NAME_OF_PUBLISH_FILE = "chl_html_content";
	
	/**
	 * 版式设置优化，指向所有商品的版式静态页 文件名称开始部分（后部分为商家编码）
	 */
	public static final String LAYOUT_SET_FOR_ALL_COMMODITY_HTML_FILE_START="/layout_template_all_";
	
	/**
	 * 版式设置优化，指向部分商品的版式静态页 文件名称开始部分（后部分为模板在数据库中的id字段值）
	 */
	public static final String LAYOUT_SET_FOR_SOME_COMMODITY_HTML_FILE_START="/layout_template_some_";
	
	/**
	 * 版式设置优化，静态页文件后缀.shtml
	 */
	public static final String LAYOUT_SET_HTML_FILE_SUFFIX=".shtml";
	
	/**
	 * 版式类型，固定版式
	 */
	public static final String LAYOUT_SET_TEMPLATE_TYPE_FIXED="0";
	
	/**
	 * 固定模板名称后缀
	 */
	public static final String LAYOUT_SET_FIXED_TEMPLATE_SUFFIX = "_FixedTemplate";
	//================================版式设置优化end======================================
}