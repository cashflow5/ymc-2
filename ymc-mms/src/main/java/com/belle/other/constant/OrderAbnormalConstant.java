package com.belle.other.constant;

/**
 * 订单跟踪状态常量类
 * @author wang.m
 *
 */
public class OrderAbnormalConstant {
	 // 未处理(默认值)
    public static final int    BASE_STAY_CHECK              = 1;
    // 已确认
    public static final int    BASE_CONFIRM                 = 2;
    // 已挂起 (订单表加入挂起类型 类型有缺货挂起、预售挂起)
    public static final int    BASE_SUSPEND                 = 3;
    // 已完成
    public static final int    BASE_FINISH                  = 4;
    // 已作废
    public static final int    BASE_CANCEL                  = 5;
    // 客服取消（传给PDA做拦截）
    public static final int    BASE_SPECIAL_CANCEL          = 6;

    // 訂單已被刪除
    public static final int  DEL_FLAG_TRUE                = 0;
    // 訂單正常
    public static final int  DEL_FLAG_FALSE               = 1;
    
    // 0配货中  
    public static final int    TRACK_STATE_PLAT                = 0;
    // 1已支付   
    public static final int    TRACK_STATE_PAY                  = 1;
    //  2已发货  
    public static final int    TRACK_STATE_CONSIGNMENT          = 2;
    // 3已签收 
    public static final int    TRACK_STATE_SIGNON                = 3;
    // 4已退货   
    public static final int    TRACK_STATE_RETRUNGOODS           = 4;
    //  5 已取消 
    public static final int    TRACK_STATE_CANCEL                = 5;
    //  6已完成  
    public static final int    TRACK_STATE_CUSSCUSS              = 6;
    // 7 商品缺货
    public static final int    TRACK_STATE_LACK                  = 7;
    //  8 商品预订
    public static final int    TRACK_STATE_SCHEDULE              = 8;
    
    
    public static final Integer ORDER_PAY_TYPE=1;//未支付
}
