package com.yougou.api.constant;

public class Constants {

	public static final String MD5 = "md5";
	
	public static final String SHA1 = "sha-1";
	
	/** 订单系统 web service url 前缀配置 key **/
	public static final String OS_WS_URL_KEY = "OS_WS_URL_PREFIX";
	
	/** 商品系统 web service url 前缀配置 key **/
	public static final String CS_WS_URL_KEY = "CS_WS_URL_PREFIX";
	
	/** 日期格式化模式 **/
	public static final String[] DATE_PATTERNS = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS" };


	//17个默认授权给商家的apiId
	public static final String[] APIIDS = {
			//线上数据
		/**  */	
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
			/*
			 */
			 "8a8094333928820f01392887e3b50003",	//获取优购系统当前时间
			"ee501377c4f511e19c4f5cf3fc0c2d70",	//获取订单条形码
			"ee501544c4f511e19c4f5cf3fc0c2d70",	//将订单置为缺货异常
			"ee50162fc4f511e19c4f5cf3fc0c2d70",	//将订单置为已发货
			"ee501711c4f511e19c4f5cf3fc0c2d70",	//将订单置为终止发货
			"ee501c88c4f511e19c4f5cf3fc0c2d70",	//获取分类列表信息
			"ee5010b0c4f511e19c4f5cf3fc0c2d70",	//查询商家库存信息
			};

}
