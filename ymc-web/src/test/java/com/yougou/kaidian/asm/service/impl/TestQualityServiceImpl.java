package com.yougou.kaidian.asm.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.yougou.kaidian.asm.model.OrderProductQuantityVo;
import com.yougou.kaidian.asm.service.IQualityService;
import com.yougou.kaidian.asm.vo.QualityVo;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.wms.wpi.returnqaproduct.domain.vo.QualityQueryVo;

/**
 * Test Case
 * 
 * @author huang.tao
 *
 */

@ContextConfiguration(locations = { "classpath:applicationContext-Test.xml" })
public class TestQualityServiceImpl extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Resource
	private IQualityService qualityService;
	
	@Test
	public void queryQualityListByVo() {
		Query query = new Query(30);
		
		QualityQueryVo vo = new QualityQueryVo();
		vo.setMerchantCode("SP20130821678648");
		vo.setQaTimeStart("2013-09-01");
		vo.setQaTimeEnd("2013-09-27");
		
		PageFinder<Map<String,Object>> pageFinder = qualityService.queryQualityListByVo(vo, query);
		
		assertTrue(pageFinder.getData().size() > 0);
	}

	//拒收
	@Test
	public void queryQualityDetailInspection() {
		QualityQueryVo vo = new QualityQueryVo();
		vo.setOrderSubNo("A161618242643_1");
		vo.setStatusName("已确认");
		vo.setQualityType("拒收");
		List<Map<String, Object>> lists = qualityService.queryQualityDetail(vo);
		
		assertTrue(lists.size() > 0);
	} 
	
	//退换货
	@Test
	public void queryQualityDetailReturn() {
		QualityQueryVo vo = new QualityQueryVo();
		vo.setOrderSubNo("OT620418101298_1");
		vo.setStatusName("已确认");
		vo.setQualityType("换货");
		List<Map<String, Object>> lists = qualityService.queryQualityDetail(vo);
		
		assertTrue(lists.size() > 0);
	}
	
	//退换货
	@Test
	public void queryQuality4Order() {
		try {
			QualityVo vo = new QualityVo();
			vo.setMerchantCode("SP20130821678648");
			vo.setOrderNo("C740225150005_1");
			List<OrderProductQuantityVo> lists = qualityService.queryOrderAsmInfo(vo);
			System.out.println(lists.size());
			assertTrue(lists.size() > 0);
		} catch (Exception e) {
			// TODO: handle exception
		}
	} 
}
