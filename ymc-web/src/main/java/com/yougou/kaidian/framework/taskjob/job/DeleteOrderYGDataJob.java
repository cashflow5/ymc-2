package com.yougou.kaidian.framework.taskjob.job;

import java.util.Calendar;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yougou.kaidian.framework.constant.Constant;
import com.yougou.kaidian.order.dao.MerchantOrderMapper;
import com.yougou.kaidian.order.model.NotMerchantOrderBean;


public class DeleteOrderYGDataJob  implements Runnable{

	private MerchantOrderMapper merchantOrderMapper;
	private int count;
	private int start;
	private int pagesize;
	private Logger logEx = LoggerFactory.getLogger(DeleteOrderYGDataJob.class);
	
	public DeleteOrderYGDataJob(MerchantOrderMapper merchantOrderMapper,int count,int start,int pagesize){
		this.count=count;
		this.start=start;
		this.pagesize=pagesize;
		this.merchantOrderMapper=merchantOrderMapper;
	}
	
	@Override
	public void run() {
		logEx.info("线程:["+this.getClass()+"]开始删除非商家订单数据任务!"+"count:"+count+"   start:"+start);
		Calendar rightNow = null;
		long index=0;
		try {
			int page=count/pagesize+1;
			RowBounds rowBounds=null;
			List<NotMerchantOrderBean> notMerchantOrders=null;
			do{
				rowBounds = new RowBounds(start, pagesize);
				notMerchantOrders=merchantOrderMapper.queryNotMerchantOrderList(rowBounds);
				if(notMerchantOrders!=null&&notMerchantOrders.size()>0){
					for(NotMerchantOrderBean notMerchantOrder:notMerchantOrders){
						index++;
						merchantOrderMapper.deleteOrderApplyRefund(notMerchantOrder.getOrderSubNo());
						merchantOrderMapper.deleteOrderCancelGoods(notMerchantOrder.getOrderSubID());
						merchantOrderMapper.deleteOrderSaleApplyBill(notMerchantOrder.getOrderSubID());
						merchantOrderMapper.deleteOrderConsignee(notMerchantOrder.getConsigneeID());
						merchantOrderMapper.deleteOrderBuyInfo(notMerchantOrder.getOrderMainNo());
						merchantOrderMapper.deleteOrderSubExpand(notMerchantOrder.getOrderSubID());	
						merchantOrderMapper.deleteOrderSub(notMerchantOrder.getOrderSubID());
						merchantOrderMapper.deleteOrderDetail4sub(notMerchantOrder.getOrderSubID());
					}
				}else{
					logEx.warn("该线程没有数据清理了,退出任务.");
					break;
				}
				rightNow=Calendar.getInstance();
				if(rightNow.get(Calendar.HOUR_OF_DAY)>7){
					logEx.warn("8点时间到,停止执行删除非商家订单数据任务.");
					break;
				}
				page--;
			}while(page>0);
			logEx.warn("线程:["+this.getClass()+"]删除非商家订单数据-删除数据成功!(总数:"+count+")本次删除数据量："+index);
		} catch (Exception e) {
			logEx.error("线程:["+this.getClass()+"]删除非商家订单数据-删除数据产生:",e);
		}finally {
			Constant.FLAT_DELETE_YGORDER=0;
			logEx.warn("线程:["+this.getClass()+"]清理非商家订单数据完毕!");
	    }
	}
}
