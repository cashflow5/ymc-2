package com.yougou.kaidian.order.constant;

/**
 * 订单货品常量类
 */
public class ProdConstant {
    
    // 商品未发货
    public static final int PROD_NOT_SEND = 1;
    
    // 商品取消
    public static final int PROD_CANCEL = 2;
    
    // 退款申请中
    public static final int PROD_STAY_REFUND = 3;
    
    // 商品已退款
    public static final int PROD_REFUND = 4;
    
    // 商品已发货
    public static final int PROD_SEND = 5;
    
    // 商品未预占库存
    public static final int PROD_NO_INVENTORY = 6;
    
    // 商品全额退款
    public static final int REFUND_STATE_ALL = 1;
    
    // 商品部分退款
    public static final int REFUND_STATE_PART = 2;
    
    // 商品其他退款
    public static final int OTHER_REFUND = 3;
    
    // 限制购买数量
    public static final int LIMIT_BUY_NUM = 20;
    
    // 解挂成功
    public static final String UMOUNT_SUCCESS = "1";
    // 非法订单
    public static final String ORDER_NOT = "2";
    // 解挂失败
    public static final String UMOUNT_FAIL = "3";
    // 该订单缺货中不能解挂
    public static final String UMOUNT_LACK = "4";

    //待售商品
    public static final short SHOWIN_NO=1;
    
    //上架商品
    public static final short SHOWIN_YES=2;
    
    // 产品尺寸常量
    public static final String PROD_SIZE= "size";
    
}
