package com.belle.infrastructure.constant;

public class Constant {

	/**
	 * 删除标识符  1 没有删除  0已删除
	 */
	public static final Short NO_DELTE_FLAG = 1;
	public static final Short HAS_DELTE_FLAG = 0;


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
	 * 系统用户登陆常量标识
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
	/**
	 *VPN访问地址 
	 */
	public static final String VPN_N = "0";//普通登录
	public static final String VPN_Y = "1";//VPN登录
	
	/** ********************JDBC Utils Configs(上海体育商城)************************ */
	public static final String JDBC_URL_SH = "com.belle.infrastructure.jdbc.url_sh";
	public static final String JDBC_USERNAME_SH = "com.belle.infrastructure.jdbc.username_sh";
	public static final String USER_PASSWORD_SH = "com.belle.infrastructure.jdbc.password_sh";
	public static final String BATCH_SIZE_SH = "com.belle.infrastructure.jdbc.batchsize_sh";
	/** ********************JDBC Utils Configs(上海体育商城)************************ */
	
	/** ********************JDBC Utils Configs(pos采购单)************************ */
	public static final String JDBC_URL_POS = "com.belle.infrastructure.jdbc.url_pos";
	public static final String JDBC_USERNAME_POS = "com.belle.infrastructure.jdbc.username_pos";
	public static final String USER_PASSWORD_POS = "com.belle.infrastructure.jdbc.password_pos";
	public static final String BATCH_SIZE_POS = "com.belle.infrastructure.jdbc.batchsize_pos";
	/** ********************JDBC Utils Configs(pos采购单)************************ */
	
	/** ********************Training config (培训中心用配置)************************ */
	/**文档类课程**/
	public static final short TRAINING_FILE_TYPE_DOC = 0;
	/**视频类课程 **/
	public static final short TRAINING_FILE_TYPE_VEDIO = 1;
	
	/** 是：1 */
	public static final short YES = 1;
	/** 否：0 */
	public static final short NO = 0;
	/** 培训中心：文件处理用到的处理中课程文件的缓存*/
	public static final String PROCESS_POOL_REDIS_KEY = "process_pool_redis_key";

	/** 缓存培训中心课程浏览次数 */
	public static final String C_TRIANING_TOTAL_CLICK_KEY = "com.yougou.kaidian.training.id";
	
	/** ********************export config (导出用配置)************************ */
	/** 导出店铺列表用的模板文件名称*/
	public static final String SHOP_XLS = "shop.xls";
	/** 导出合同列表用的模板文件名称*/
	public static final String CONTRACT_XLS = "contract.xls";
	/** 导出店铺列表用的模板文件名称*/
	public static final String MERCHANT_XLS = "merchant.xls";
	/** 导出用的模板文件所在相对路径*/
	public static final String TEMPLATE_PATH = "download/";
	/**
	 * 缓存用户与权限资源（对象类型菜单对应）
	 */
	public static final String C_USER_AUTH = "com.yougou.kaidian.user.auth";
	/**
	 * 缓存用户与权限资源（String类型URL资源）
	 */
	public static final String C_USER_REOURCE_AUTH = "com.yougou.kaidian.user.resource.auth";
	/**
	 * 缓存所有资源（String类型URL资源）
	 */
	public static String C_All_RESOURCE = "com.yougou.kaidian.all.resource";
}
