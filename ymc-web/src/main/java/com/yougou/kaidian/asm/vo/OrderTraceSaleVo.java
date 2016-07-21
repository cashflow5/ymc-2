package com.yougou.kaidian.asm.vo;

import java.util.Date;

import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.user.constant.UserConstant;
import com.yougou.ordercenter.model.order.OrderSaleTrace;

public class OrderTraceSaleVo extends OrderSaleTrace {
	private static final long serialVersionUID = 1L;
	
	private String leftTime;// 剩余毫秒数
	
	private String[] leftTimeToArray;// 剩余时分秒

	public OrderTraceSaleVo(Date createTime,Integer traceStatus) {
		super();
		if( null!=createTime && UserConstant.TRACE_STATUS_NEW==traceStatus ){
			this.leftTime = DateUtil2.getReplyLeftTime(createTime, UserConstant.AUTO_REPLY_DAYS)+"";
			this.leftTimeToArray = DateUtil2.secToTime( DateUtil2.getReplyLeftTime(createTime, UserConstant.AUTO_REPLY_DAYS) );
		}
	}

	public String getLeftTime() {
		return leftTime;
	}

	public void setLeftTime(String leftTime) {
		this.leftTime = leftTime;
	}

	public String[] getLeftTimeToArray() {
		return leftTimeToArray;
	}

	public void setLeftTimeToArray(String[] leftTimeToArray) {
		this.leftTimeToArray = leftTimeToArray;
	}
}
