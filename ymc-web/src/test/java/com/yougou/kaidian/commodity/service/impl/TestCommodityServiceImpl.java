package com.yougou.kaidian.commodity.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.vo.ImageMessage;

/**
 * Test Case
 * 
 * @author huang.tao
 *
 */

@ContextConfiguration(locations = { "classpath:applicationContext-Test.xml" })
public class TestCommodityServiceImpl extends AbstractTransactionalJUnit4SpringContextTests{
	
//	@Resource
//	private ICommodityService commodityService;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * supplier [商家（非入优发）]
	 */
	
	final static String MERCHANTCODE = "SP20120412312125";
	
	final static String SUPPLIERID = "8a8a8a1736a28db90136a2b754920001";
	
	final static String BRAND_ID = "2c9481b130b516910130b531db940171";

	/*@Test
	public void queryBrandList() {
		List<Brand> list = commodityService.queryBrandList(MERCHANTCODE);
		
		assertTrue(list.size() > 0);
	}
	
	@Test
	public void queryCatList() {
		List<Cat> list = commodityService.queryCatList(null, SUPPLIERID, "10-11");
		
		assertTrue(list.size() > 0);
	}
	
	@Test
	public void getAllCategoryByBrandId() {
		List<Category> cats = commodityService.getAllCategoryByBrandId(MERCHANTCODE, BRAND_ID);
		
		assertTrue(cats.size() > 0);
	}
	
	@Test
	public void queryCatListByLevel() {
		List<Cat> list = commodityService.queryCatList(MERCHANTCODE, "");
		
		assertTrue(list.size() > 0);
	}*/
	
	@Test
	public void clearImageCache() {
		List<Object> masterMessages = this.redisTemplate.opsForHash().values(CacheConstant.C_IMAGE_MASTER_JMS_KEY);
		for (Object imageMessage : masterMessages) {
			ImageMessage _imageMessage = (ImageMessage) imageMessage;
			//System.out.println(_imageMessage.getCommodityNo());
			this.redisTemplate.opsForHash().delete(CacheConstant.C_IMAGE_MASTER_JMS_KEY, _imageMessage.getCommodityNo());
		}
		
		assertTrue(masterMessages.size() > 0);
	}
}
