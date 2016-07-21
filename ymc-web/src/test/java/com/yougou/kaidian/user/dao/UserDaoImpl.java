package com.yougou.kaidian.user.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.yougou.component.area.api.IAreaApi;
import com.yougou.component.area.model.Area;
import com.yougou.kaidian.user.model.pojo.MerchantsAuthority;


/**
 * Test Case
 * 
 * @author huang.tao
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext*.xml" })
public class UserDaoImpl extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Resource
	private MerchantUsersMapper userMapper;
	
	@Resource
    private IAreaApi areaApi;
	
	@Test
	public void getMerchantAuthorityById() {
		List<MerchantsAuthority> lists = userMapper.getMerchantAuthorityById("8a8a8a1736cb96600136cdad2c5506af");
		
		assertTrue(lists.size() >= 1);
	}
	
	   @Test
	    public void areaApi() {
	       List<Area> lists = areaApi.getAreaByLevel(1);
	        
	        assertTrue(lists.size() >= 1);
	    }
}
