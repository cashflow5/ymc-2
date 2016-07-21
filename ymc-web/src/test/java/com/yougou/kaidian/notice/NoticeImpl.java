package com.yougou.kaidian.notice;

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

import com.yougou.kaidian.notice.model.pojo.MctNotice;
import com.yougou.kaidian.notice.service.IMctNoticeService;
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
public class NoticeImpl extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Resource
	private IMctNoticeService mctNoticeService;
	
	@Test
	public void query() {
/*		try {
			RowBounds rowBounds=new RowBounds(0, 20);
			List<MctNotice> list1=mctNoticeService.queryMctNoticeList(rowBounds);
			System.out.println(list1.size());
			List<MctNotice> list2=mctNoticeService.queryMctNoticeListTop5();
			System.out.println(list2.size());
			MctNotice mctNotice=mctNoticeService.queryMctNoticeById("1");
			System.out.println(mctNotice.getTitle());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
