package com.yougou.kaidian.stock.dao;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Test Case
 * 
 * @author huang.tao
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-Test.xml" })
public class StockDaoImpl extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Resource
	private StockMapper stockMapper;
	
	@Test
	public void queryStorageModel() {
		Map<String, Object> map = stockMapper.queryStorageModel("SP20130821678648");
		
		assertTrue(map.size() >= 1);
	}
}
