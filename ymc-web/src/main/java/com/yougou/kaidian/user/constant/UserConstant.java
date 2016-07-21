package com.yougou.kaidian.user.constant;

public class UserConstant {

	/**
	 * 日志类型标识符  1 登录日志  0操作日志
	 */
	public static final Integer LOG_TYPE_LOGIN = 1;
	public static final Integer LOG_TYPE_OPERATION = 0;
	
	/**
	 * 类型：  查看  操作
	 */
	public static final String OPERATION_TYPE_READ = "查看";
	public static final String OPERATION_TYPE_OPERATE = "操作";

	/**
	 * 菜单定义
	*	订单模块-单据打印 
	*	订单模块-快捷发货
	*	订单模块-单据打印(新)
	*	售后模块-质检查询
	*	售后模块-质检不通过
	*	售后模块-售后单查询
	 */
	public static final String MENU_DJDY = "订单模块-单据打印";
	public static final String MENU_KJFH = "订单模块-快捷发货";
	public static final String MENU_DJDYX = "订单模块-单据打印(新)";
	public static final String MENU_ZJCX = "售后模块-质检查询";
	public static final String MENU_ZJBTG = "售后模块-质检不通过";
	public static final String MENU_SHDCX = "售后模块-售后单查询";
	public static final String MENU_GDCL = "售后模块-工单处理";
	public static final String MENU_ZJDJ = "售后模块-质检登记";
	
	
	//8a8a8ab3395699c1013956b87c6b0035	TIME_GET	获取优购系统当前时间
	
	//8a8a8ab240c8c2f50140c8c716420003	ORDER_INTERCEPT_QUERY	查询客服订单拦截信息
	//8a8a8ab240c8c2f50140c8cc5244000b	ORDER_INTERCEPT_UPDATE	同意/不同意客服申请拦截订单信息更新
	//8a8a8ab3394f857b0139515d32ae0001	ORDER_SYNCHRONIZED_UPDATE	商家成功同步订单后，向优购回写订单状态为拣货中
	//8a8a8ab3394f857b0139515ef6480002	ORDER_INCREMENT_QUERY	查询增量订单列表信息
	//b9060892b8fa11e1a9958beaf2012ddf	ORDER_QUERY	查询商家订单列表信息
	//b9082981b8fa11e1a9958beaf2012ddf	ORDER_GET	查询商家订单详情信息
	//b9085af1b8fa11e1a9958beaf2012ddf	ORDER_BARCODE_GET	获取订单条形码
	//b9096cd7b8fa11e1a9958beaf2012ddf	ORDER_CANCELED_QUERY	查询客服取消订单详情信息
	//b90a62b8b8fa11e1a9958beaf2012ddf	ORDER_STOCKOUT_UPDATE	将订单置为缺货异常
	//b90a9255b8fa11e1a9958beaf2012ddf	ORDER_COMPLETED_UPDATE	将订单置为已发货
	//b90ac125b8fa11e1a9958beaf2012ddf	ORDER_NONDELIVERY_UPDATE	将订单置为终止发货
	
	//b90c1fdeb8fa11e1a9958beaf2012ddf	BRAND_QUERY	获取品牌列表信息
	//b90c4efab8fa11e1a9958beaf2012ddf	CAT_QUERY	获取分类列表信息

	//b904001fb8fa11e1a9958beaf2012ddf	INVENTORY_UPDATE	更新商家库存信息
	//b9043014b8fa11e1a9958beaf2012ddf	INVENTORY_QUERY	查询商家库存信息
	
	//b90b7f9ab8fa11e1a9958beaf2012ddf	LOGISTICS_COMPANY_GET	获取可用物流公司信息
	
	//17个默认授权给商家的apiId
	public static final String[] APIIDS = {
		//线上数据
		"8a8a8ab3395699c1013956b87c6b0035",
		"8a8a8ab240c8c2f50140c8c716420003",
		"8a8a8ab240c8c2f50140c8cc5244000b",
		"8a8a8ab3394f857b0139515d32ae0001",
		"8a8a8ab3394f857b0139515ef6480002",
		"b9060892b8fa11e1a9958beaf2012ddf",
		"b9082981b8fa11e1a9958beaf2012ddf",
		"b9085af1b8fa11e1a9958beaf2012ddf",
		"b9096cd7b8fa11e1a9958beaf2012ddf",
		"b90a62b8b8fa11e1a9958beaf2012ddf",
		"b90a9255b8fa11e1a9958beaf2012ddf",
		"b90ac125b8fa11e1a9958beaf2012ddf",
		"b90c1fdeb8fa11e1a9958beaf2012ddf",
		"b90c4efab8fa11e1a9958beaf2012ddf",
		"b904001fb8fa11e1a9958beaf2012ddf",
		"b9043014b8fa11e1a9958beaf2012ddf",
		"b90b7f9ab8fa11e1a9958beaf2012ddf",
		
		//测试环境数据
		/*"8a8094333928820f01392887e3b50003",	//获取优购系统当前时间
		"ee501377c4f511e19c4f5cf3fc0c2d70",	//获取订单条形码
		"ee501544c4f511e19c4f5cf3fc0c2d70",	//将订单置为缺货异常
		"ee50162fc4f511e19c4f5cf3fc0c2d70",	//将订单置为已发货
		"ee501711c4f511e19c4f5cf3fc0c2d70",	//将订单置为终止发货
		"ee501c88c4f511e19c4f5cf3fc0c2d70",	//获取分类列表信息
		"ee5010b0c4f511e19c4f5cf3fc0c2d70",	//查询商家库存信息
*/		};
	
	/** 未删除 状态   is_delete_flag的枚举值：1 未删除 */
	public static final int NOT_DELETED = 1;
	public static final int DELETED = 0;
	
	public static final int YES = 1;
	public static final int NO = 0;
	
	/** 联系人种类 1 业务,2 售后 ,3 仓储,4  财务,5 技术 ,6店铺负责人 */
	public static final int CONTACT_TYPE_CHIEF = 6;
	public static final int CONTACT_TYPE_BUSINESS = 1;
	public static final int CONTACT_TYPE_AFTERSALE = 2;
	public static final int CONTACT_TYPE_STORAGE = 3;
	public static final int CONTACT_TYPE_FINANCE = 4;
	public static final int CONTACT_TYPE_TECH =5;
	
	/**商家状态-启用 */
	public static final int MERCHANT_STATUS_ENABLE = 1;
	
	/** 赔付管理 工单状态(订单组约定) */
	public static final Integer TRACE_STATUS_NEW = 3;//待处理	
	public static final Integer TRACE_STATUS_APPEALING = 0;//申诉中
	public static final Integer OPERATE_STATUS_APPROVE = 1;// 同意
	public static final Integer OPERATE_STATUS_APPEAL_SUCCESS = 2;//申诉成功
	public static final Integer OPERATE_STATUS_APPEAL_FAIL = 3;//申诉失败
	
	public static final Integer AUTO_REPLY_DAYS = 3;//自动回复天数
	
}
