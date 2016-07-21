package com.yougou.kaidian.order;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import com.yougou.kaidian.order.dao.MerchantOrderMapper;
import com.yougou.kaidian.order.model.NotMerchantOrderBean;
import com.yougou.ordercenter.api.order.IOrderForMerchantApi;
import com.yougou.ordercenter.vo.merchant.MerchantDailyStatisticsVo;
import com.yougou.pc.api.ICommodityMerchantApiService;

/**
 * Test Case
 * 
 * @author huang.tao
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-Test.xml" })
public class OrderImpl extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Resource
	private IOrderForMerchantApi orderMerchantApi;
	@Resource
	private ICommodityMerchantApiService commodityApi;
	@Resource 
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Test
	public void statisticMerchantData() {
		String merchantCode = "SP20130821678648";
		Map<String, MerchantDailyStatisticsVo> map = orderMerchantApi.statisticMerchantData(merchantCode);
		
		assertTrue(map.size() >= 1);
	}
	
	@Test
	public void queryCommodityTips() {
		Map<String, Integer> tips = commodityApi.getCommodityStatusInfoByMerchantCode("SP20130821678648");
		
		assertTrue(tips.size() > 0);
	}
	
	@Test
	public void deleteOrderYGData() {
		SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(false);
		int pagesize=20;
		int xx=0;
		MerchantOrderMapper merchantOrderMapper=session.getMapper(MerchantOrderMapper.class);
		try {
			session.getConnection().setAutoCommit(false);
			System.out.println();
			int count=merchantOrderMapper.queryNotMerchantOrderCount();
			int page=count/pagesize;
			RowBounds rowBounds=null;
			List<NotMerchantOrderBean> notMerchantOrders=null;
			for(int i=0;i<=page;i++){
				rowBounds = new RowBounds(0, pagesize);
				notMerchantOrders=merchantOrderMapper.queryNotMerchantOrderList(rowBounds);
				System.out.println("======count:"+count+"=======>"+notMerchantOrders.size());
				for(NotMerchantOrderBean notMerchantOrder:notMerchantOrders){
					System.out.println("====================="+(xx++)+"===========================");
					merchantOrderMapper.deleteOrderApplyRefund(notMerchantOrder.getOrderSubNo());
					merchantOrderMapper.deleteOrderCancelGoods(notMerchantOrder.getOrderSubID());
					merchantOrderMapper.deleteOrderConsignee(notMerchantOrder.getConsigneeID());
					merchantOrderMapper.deleteOrderSaleApplyBill(notMerchantOrder.getOrderSubID());
					merchantOrderMapper.deleteOrderDetail4sub(notMerchantOrder.getOrderSubID());
					merchantOrderMapper.deleteOrderSub(notMerchantOrder.getOrderSubID());
					merchantOrderMapper.deleteOrderSubExpand(notMerchantOrder.getOrderSubID());	
				}
				session.commit();
				session.clearCache();
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		}finally {
	        session.close();
	        try {
				session.getConnection().close();
			} catch (SQLException e) {
				System.out.println("关闭连接异常");
				e.printStackTrace();
			}
	    }
	}
}
