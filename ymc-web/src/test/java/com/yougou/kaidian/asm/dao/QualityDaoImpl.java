package com.yougou.kaidian.asm.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.yougou.kaidian.asm.vo.AsmQcDetailVo;
import com.yougou.kaidian.asm.vo.AsmQcVo;
import com.yougou.wms.wpi.returnqaproduct.domain.vo.QualityQueryVo;


/**
 * Test Case
 * 
 * @author huang.tao
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-Test.xml" })
public class QualityDaoImpl extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Resource
	private QualityMapper qualityMapper;
	
	@Test
	public void queryQualityDetailFromReturn() {
		QualityQueryVo vo = new QualityQueryVo();
		vo.setOrderSubNo("OT620418101298_1");
		vo.setStatusName("已确认");
		List<Map<String, Object>> lists = qualityMapper.queryQualityDetailFromReturn(vo);
		
		assertTrue(lists.size() >= 1);
	}
	
	@Test
	public void queryQualityDetailFromInspection() {
		QualityQueryVo vo = new QualityQueryVo();
		vo.setOrderSubNo("A161618242643_1");
		vo.setStatusName("已确认");
		List<Map<String, Object>> lists = qualityMapper.queryQualityDetailFromInspection(vo);
		
		assertTrue(lists.size() >= 1);
	}
	
//	@Test
//	public void queryQualityListByVoCount() {
//		QualityQueryVo vo = new QualityQueryVo();
//		vo.setMerchantCode("SP20130821678648");
//		vo.setQaTimeStart("2013-09-01");
//		vo.setQaTimeEnd("2013-09-27");
//		
//		int count = qualityMapper.queryQualityListByVoCount(vo);
//		
//		assertTrue(count >= 1);
//	}
//	
//	@Test
//	public void queryQualityListByVo() {
//		QualityQueryVo vo = new QualityQueryVo();
//		vo.setMerchantCode("SP20130821678648");
//		vo.setQaTimeStart("2013-09-01");
//		vo.setQaTimeEnd("2013-09-27");
//		
//		RowBounds rowBounds = new RowBounds(0, 30);
//		List<Map<String, Object>> lists = qualityMapper.queryQualityListByVo(vo, rowBounds);
//		
//		assertTrue(lists.size() >= 1);
//	}
	
	@Test
	public void queryAsmQcListByVo() {
		QualityQueryVo vo = new QualityQueryVo();
		vo.setMerchantCode("SP20130821678648");
		vo.setQaTimeStart("2013-09-01");
		vo.setQaTimeEnd("2013-09-27");
		
		RowBounds rowBounds = new RowBounds(0, 30);
		List<AsmQcVo> lists = qualityMapper.queryAsmQcListByVo(vo, rowBounds);
		
		assertTrue(lists.size() >= 1);
	}
	
	@Test
	public void queryAsmQcListByVoCount() {
		QualityQueryVo vo = new QualityQueryVo();
		vo.setMerchantCode("SP20130821678648");
		vo.setQaTimeStart("2013-09-01");
		vo.setQaTimeEnd("2013-09-27");
		
		int count = qualityMapper.queryAsmQcListByVoCount(vo);
		
		assertTrue(count >= 1);
	}
	
	@Test
	public void queryAsmQcDetailsByOrderNo() {
		//C731114140002_1
		List<AsmQcDetailVo> list = qualityMapper.queryAsmQcDetailsByOrderNo("C731114140002_1");
		
		assertTrue(list.size() >= 1);
	}
}
