package com.yougou.kaidian.commodity.service.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.yougou.pc.api.ICommodityMerchantApiService;

/**
 * Test Case
 * 
 * @author huang.tao
 *
 */

@ContextConfiguration(locations = { "classpath:applicationContext-Test.xml" })
public class TestCommodityAPI extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Resource
	private ICommodityMerchantApiService commodityApi;
	
	@Test
	public void getCommodity() {
		String commodityNo = "100117873";
		String merchantCode = "SP20150319022700";
		String picPath = "/basto/2015/100117873/";
		String desc = "<html><head></head><body><p > <strong>爆款推荐66666</strong><br /> <strong>第一回合2121</strong><br /> <br /> <a href='http://kaidian.yougou.com:9080/commodity/goWaitSaleCommodity.sc' target='_Blank' ><img border='0' src='http://10.0.30.193/pics/basto/2015/100117873/100117873_01_b.jpg?12' /> </a><img src='http://10.0.30.193/pics/basto/2015/100117873/100117873_02_b.jpg?74' /> </p></body></html>";
		
		String result = commodityApi.updateCommodityDescForMerchant(commodityNo, merchantCode, desc);
		
		System.out.println("!!!!!!!!!!!!result1: "+result+"!!!!!!!!!!!");
		String _result = commodityApi.updateCommodityPicFlag(commodityNo, merchantCode ,picPath );
		System.out.println("!!!!!!!!!!!!result2: "+_result+"!!!!!!!!!!!");
		com.yougou.pc.model.commodityinfo.Commodity commodity = commodityApi.getCommodityByNo(commodityNo);
		System.out.println(commodity.getCommodityDesc());
	}
}
