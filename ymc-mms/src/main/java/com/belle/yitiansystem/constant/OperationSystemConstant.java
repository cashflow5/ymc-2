package com.belle.yitiansystem.constant;

/**
 * 宜天商城后台维护公用常量类
 * @author yangpinggui
 * @version 2011.04.20
 **/
public class OperationSystemConstant {

	/**
	 * 删除标识
	 */
	public static final Short DEL_FLAG_CLOSE = 0 ;

	/**
	 * 删除标识
	 */
	public static final Short DEL_FLAG_OPEN = 1 ;

	/**
	 * 会员启用状态
	 */
	public static final Short LEVEL_ENABLED_STATE = 1 ;

	/**
	 * 会员禁用状态
	 */
	public static final Short LEVEL_DISENABLED_STATE = 0 ;


	/**
	 * 折表标示‘是’
	 */
	public static final Short SPLIT_FLAG_IS = 1 ;

	/**
	 * 折表标示‘否’
	 */
	public static final Short SPLIT_FLAG_NO = 0 ;

	/**
	 * 字符串拆分字符表示
	 */
	public static final String SPLIT_CHARACTER = ",";


	/**
	 * 是否有用数量初始化(后台评论)
	 */

	public static final Integer BACKSTAGEISUSER = 1;

	/**
	 * 是否有用数量初始化
	 */

	public static final Integer ISUSER = 0;

	/**
	 * 用于品牌数组拼接的常量
	 */
	public static final String QITA = "其它";


	/**
	 * 是否显示：【否】
	 */
	public static final Short SHOW_STATE_NO = 0;

	/**
	 * 是否显示：【是】
	 */
	public static final Short SHOW_STATE_YES = 1;

	public static final String COMMODITY_ADVANTGE_FAULTSMGMT_PATH ="/yitiansystem/mallmgmt/baseconfig/commodityAdvantageFaults_";

	/**
	 * 定义品牌拼音数组
	 */
	public static String [] BRAND_SIGN = {"A","B","C","D","E","F","G","H","I","J","K","M","N","L","O","P","Q","R","S","T","U","V","W","X","Y","Z",OperationSystemConstant.QITA};

	/**
	 * 最近7天
	 */
	public static final String SEARCH_ALL = "0";

	/**
	 * 最近7天
	 */
	public static final String SEARCH_7DAYS = "1";

	/**
	 * 最近15天
	 */
	public static final String SEARCH_15DAYS = "2";

	/**
	 * 最近30天
	 */
	public static final String SEARCH_30DAYS = "3";

	/**
	 * 上个月
	 */
	public static final String SEARCH_UP_MONTH = "4";

	/**
	 * 最近3个月
	 */
	public static final String SEARCH_3MONTH = "5";

	/**
	 * 最近半年
	 */
	public static final String SEARCH_6MONTH = "6";


	/**
	 * 性别:女
	 */
	public static final String SEARCH_SEX_F = "2";

	/**
	 * 性别:男
	 */
	public static final String SEARCH_SEX_M = "1";


	/**
	 * 性别:所有
	 */
	public static final String SEARCH_SEX_ALL = "0";

	/**
	 * 按登录用户名查询
	 */
	public static final String SEARCH_TYPE_LOGINACCOUNT = "1";

	/**
	 * 按Email查询
	 */
	public static final String SEARCH_TYPE_EMAIL = "2";

	/**
	 * 活动申请中
	 */
	public static final short PROMOTION_ACTIVE_STATE_RELEASE = 1;

	/**
	 *  活动已审核
	 */
	public static final short PROMOTION_ACTIVE_STATE_AUDIT = 2;

	/**
	 *  活动拒绝中
	 */
	public static final short PROMOTION_ACTIVE_STATE_REFUSE = 3;

	/**
	 *  活动终止中
	 */
	public static final short PROMOTION_ACTIVE_STATE_FINAL = 4;


	/**
	 * 活动过期中
	 */
	public static final short PROMOTION_ACTIVE_STATE_EXPIRED = 2;

	/**
	 * 编号（公用）
	 */
	public static final String COMMON_NO = "1111111111111";

	/**
	 * 所有
	 */
	public static final int QUERY_ALL = -1;

	/**
	 * 定义页面传入的货品类型
	 */
	public static final String PRODUCT_TYPE = "0";

	/**
	 * 会员等级是否存在(存在)
	 */
	public static final String LEVEL_TRUE = "1";

	/**
	 * 会员等级是否存在(不存在)
	 */
	public static final String LEVEL_FALSE = "0";


	public static final int DEFALUT_LEVEL =1;

	/**
	 * 会员未激活
	 */
	public static final short USERNAME_NO_ACTIVE = 0;

	/**
	 * 解锁会员状态
	 */
	public static final short LOCK_MEMBER_STATE = 1;


	/**
	 * 省份与仓库维护添加是否成功(成功)
	 */
	public static final String STORAGE_CODE_SUCCESS = "1";

	/**
	 * 省份与仓库维护添加是否成功(失败)
	 */
	public static final String STORAGE_CODE_ERROR = "0";

	/**
	 * 处理状态 未处理
	 */
	public static final short DEALWITH_STATE_NO = 0;

	/**
	 * 处理状态 已处理
	 */
	public static final short DEALWITH_STATE_YES = 1;

	/**
	 * 活动默认积分数
	 */
	public static final int DEFAULT_SENDINTEGRAL_NUMBER  = 0;

	/**
	 * 满额活动
	 */
	public static final int  QUOTA_PROMOTIO_ACTIVE =1;

	/**
	 * 限时抢活动
	 */
	public static final int  CESSOR_ROB_PROMOTIO_ACTIVE = 2;

	/**
	 * 多买多折扣活动
	 */
	public static final int  MUCH_BUY_MORE_DISCOUNTS_PROMOTIO_ACTIVE = 3;

	/**
	 * 送赠品活动
	 */
	public static final int  SEND_GIFT_PROMOTIO_ACTIVE = 4;

	/**
	 * 早买越便宜活动
	 */
	public static final int  EARLY_BUY_CHEAPER_PROMOTIO_ACTIVE = 5;

	/**
	 * 团购活动
	 */
	public static final int  GROUP_PROMOTIO_ACTIVE = 6;
	
	/**
	 * 初体验活动
	 */
	public static final int NEW_FACT_ACTIVE = 7;

	/**
	 * 操作成功标识
	 */
	public static final int OPERATION_SUCCESS_FLAG = 1;

	/**
	 * 操作失败标识
	 */
	public static final int OPERATION_FAILE_FLAG = 0;


	/**
	 * 用户已经对商品评价有用(不可以评价)
	 */
	public static final String NO_IS_USE = "0";


	/**
	 * 查询有用的点评
	 */
	public static final String COMMENT_IS_USE = "0";


	/**
	 * 查询最新点评
	 */
	public static final String COMMENT_IS_NEW = "1";

	/**
	 * 参与对象：全站会员
	 */
	public static final short  MEMBER_REQUIRE_QUERY_ALL = 0;

	/**
	 * 参与对象：具体等级会员
	 */
	public static final short  MEMBER_REQUIRE_QUERY_LEVEL = 1;

	/**
	 * 保存所有会员等级所有
	 */
	public static final String  QUERY_MEMBER_LEVEL_ALL = "all";

	/**
	 * 团购默认人数
	 */
	public static final int DEFAULT_GROUP_PERSON_COUNT = 0;


	/**
	 * 补充秒数
	 */
	public static final String DATE_SECONDS = ":00";

	/**
	 * 团购价(单一价格)
	 */
	public static final Short GROUP_SIMPLE_TYPE = 1;

	/**
	 * 团购价(多级价格)
	 */
	public static final Short GROUP_MANY_TYPE = 2;

	/**
	 * 今日团购
	 */
	public static final int TODAY_GROUP_TYPE = 1;

	/**
	 * 下期团购
	 */
	public static final int NEXT_TODAY_GROUP_TYPE = 2;

	/**
	 * 往期团购
	 */
	public static final int LAST_TODAY_GROUP_TYPE = 3;

	/**
	 * 会员默认等级
	 */
	public static final int DEFAUTL_MEMBERLEVEL = 1;

	/**
	 * 商品优点，缺点，项目评分状态标示(通过)
	 */
	public static final Short IS_AFP_FLAG  = 1 ;

	/**
	 * 商品优点，缺点，项目评分状态标示(不通过)
	 */
	public static final Short NO_AFP_FLAG  = 0 ;

	/**
	 * 商品一级分类定义
	 */
	public static final int COMMODITY_LEVEL_TWO  = 2 ;

	/**
	 * 按时间排序
	 */
	public static final int SORT_TYPE_BY_ORDERDATE = 1;

	/**
	 * 按消费金额排序
	 */
	public static final int SORT_TYPE_BY_CONSUMPTION_AMOUNT = 2;

	/**
	 * 投诉回复状态  未回复
	 */
	public static final short REPLY_STATE_NO = 0;

	/**
	 * 投诉回复状态 已回复
	 */
	public static final short REPLY_STATE_YES = 1;

	/**
	 * 默认商品点评
	 */
	public static final short IS_DEFAULE_COMMODITY_CONTENT = 0;


	/**
	 * 不是默认商品点评
	 */
	public static final short NO_DEFAULE_COMMODITY_CONTENT = 1;

	/**
	 * 点评项目评分
	 */
	public static final int PROJECT_SCORE_CONTENT_TYPE = 1;

	/**
	 * 点评优点
	 */
	public static final int ADVANTGE_CONTENT_TYPE = 2;

	/**
	 * 点评缺点
	 */
	public static final int FAULT_CONTENT_TYPE = 3;

	/**
	 * 操作成功标识
	 */
	public static final String OPERATION_SUCCESS_CODE="1";

	/**
	 * 操作失败标识
	 */
	public static final String OPERATION_FAILE_CODE="0";

	/**
	 * 活动跟踪条件默认选中
	 */
	public static final int SELECT_PROMOTION_TRACK_EXPORT_CONDITION = 1;

	/**
	 * 活动跟踪条件默认没选中
	 */
	public static final int NO_SELECT_PROMOTION_TRACK_EXPORT_CONDITION = 0;

	/**
	 * 性别:女
	 */
	public static final short LOGINACCOUNT_SEX_GIRL = 2;

	/**
	 * 性别:男
	 */
	public static final short LOGINACCOUNT_SEX_BOY = 1;

	/**
	 * 供应商
	 */
	public static final short IS_DISTRRIBUTOR_YES = 1;

	/**
	 * 普通会员
	 */
	public static final short IS_MEMBER_YES = 0;

	/**
	 * 查询所有产品线
	 */
	public static final String QUERY_PRODUCT_LINE = "all";

	/**
	 * 会员缓存名称
	 */
	public static final String MEMBER_MEMCACHED_NAME = "memberclient1";

	/**
	 *
	 */
	public static final String QUERY_MEMBER_BASIC_INFO_MEMCACHED_KEY = "memberclient2";
	/**
	 * 忘记密码email发送模板链接（最后上线确定路径进行改变）
	 */
	public static final String TO_FORGET_PWD_URL="http://www.yougou.com/toResetPwdEmail.jhtml";
	/**
	 * 用户中心链接（最后上线确定路径进行改变）
	 */
	public static final String TO_LOGIN_URL="http://www.yougou.com/my/ucindex.jhtml";
}

