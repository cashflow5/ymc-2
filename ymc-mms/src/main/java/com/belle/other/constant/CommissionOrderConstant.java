package com.belle.other.constant;


/**
 * 订单主表常量类
 * @author wang.m
 *
 */
public class CommissionOrderConstant {
	
	//联盟用户订单状态 1：有效订单
	public static final Integer ORDER_USER = 1;
	
	//联盟用户订单状态 2：无效订单
	public static final Integer ORDER_NOUSE = 2;
	
	//联盟用户订单状态 3：成交订单，
	public static final Integer ORDER_COMPLETE = 3;
	

	//联盟用户订单核销状态：未核销
	public static final Integer ORDE_NOEXAMINE_STATES = 0;
	
	//联盟用户订单核销状态：已核销，
	public static final Integer ORDE_EXAMINE_STATES = 1;
	
	//联盟用户支付状态 1：处理中，未支付
	public static final Integer ORDER_NOPAY = 1;
	
	//联盟用户支付状态 2：.已支付
	public static final Integer ORDER_YIPAY = 2;
	
	//联盟用户支付状态 3：.已取消
	public static final Integer ORDER_CANCLE = 3;
	
	//联盟用户支付状态 4：.已完成
	public static final Integer PAY_COMPLETE = 4;
	
	//联盟用户支付状态5：提现处理中
	public static final Integer ORDER_CHECK = 5;
	
	//商品搜索 类型 0，所有 1，商品名称  2，商品编号
	public static final String SEARCHALL_TYPE="0";
	public static final String SEARCHALL_NAME="1";
	public static final String SEARCHALL_ID="2";
	
	//销售总额状态
	public static final Integer TOTALORDERAMOUNT =4;
	//佣金收入状态
	public static final Integer COMMISSIONINCOME =5;


	//删除标志  0 已经删除
	public static final short DELETE_STATE_CLOSE =0;
	//删除标志 1 未删除
	public static final short DELETE_STATE_OPEN =1;
	
	//分割标志  0 已经分割
	public static final short SPLIT_STATE_CLOSE =0;
	//分割标志 1 未分割
	public static final short SPLIT_STATE_OPEN =1;
}
