package com.yougou.kaidian.commodity.dao;

import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import com.yougou.kaidian.common.commodity.pojo.Brand;
import com.yougou.kaidian.common.commodity.pojo.Cat;

/**
 * Test Case
 * 
 * @author huang.tao
 *
 */

@ContextConfiguration(locations = { "classpath:applicationContext-Test.xml" })
public class CommodityDaoImpl extends AbstractTransactionalJUnit4SpringContextTests{

	@Resource
	private CommodityPropertyMapper catMapper;
	
	@Resource
	private CommodityMapper commodityMapper;
	
	/**
	 * supplier [商家（非入优发）]
	 */
	
	final static String MERCHANTCODE = "SP20130821678648";
	
	@Test
	public void querySupplierBrandList() {
		List<Brand> brands = catMapper.querySupplierBrandList(MERCHANTCODE);
		
		assertTrue(brands.size() > 1);
	}

	@Test
	public void querySupplierCatList() {
		List<Cat> cats = catMapper.querySupplierCatList(MERCHANTCODE);
		
		assertTrue(cats.size() > 1);
	}
	
	@Test
	public void getProductByprouductNo() {
		Map<String, Object> map = commodityMapper.getProductByprouductNo("99872206001");
		
		assertTrue(map.size() > 1);
	}
}
