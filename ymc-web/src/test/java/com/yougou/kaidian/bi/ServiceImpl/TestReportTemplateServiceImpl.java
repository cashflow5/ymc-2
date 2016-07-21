package com.yougou.kaidian.bi.ServiceImpl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import com.yougou.kaidian.asm.service.IQualityService;
import com.yougou.kaidian.bi.service.IReportTemplateService;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.wms.wpi.returnqaproduct.domain.vo.QualityQueryVo;
/** 
 * 商家中心数据报表-指标模板 服务类单元测试
 * @author zhang.f1
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-Test.xml" })
public class TestReportTemplateServiceImpl  extends AbstractTransactionalJUnit4SpringContextTests{
	
	private static final Logger logger = LoggerFactory.getLogger(TestReportTemplateServiceImpl.class);
	
	@Autowired
	private IReportTemplateService reportTemplateService;
	
	@Test
	@Rollback(false)
	public void addReportTemplate()  {
		Map params = new HashMap();
		String templateId = UUIDGenerator.getUUID();
		System.out.println(templateId+"----------");
		params.put("template_id",templateId);
		params.put("name", "单元测试模板");
		params.put("merchant_code", "shangjia001");
		params.put("login_name", "zhangfeng");
		
		List<String> indexList = new ArrayList<String>();
		indexList.add("Subscribe_count");
		indexList.add("Payment_count");
		params.put("indexList", indexList);
		
		try {
			reportTemplateService.addReportTemplate(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	@Rollback(false)
	public void updateReportTemplate()  {
		Map params = new HashMap();
		String templateId = UUIDGenerator.getUUID();
		System.out.println(templateId+"----------");
		params.put("template_id","9d0de9ed34b54db592038a6fd2eac450");
	
		List<String> indexList = new ArrayList<String>();
		indexList.add("Delivery_count");
		indexList.add("Subscribe_amount");
		params.put("indexList", indexList);
		
		try {
			reportTemplateService.updateReportTemplate(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void queryReportTemplate()  {
		Map params = new HashMap();
		params.put("template_id","9d0de9ed34b54db592038a6fd2eac450");
	
		List<String> indexList = new ArrayList<String>();
		indexList.add("Delivery_count");
		indexList.add("Subscribe_amount");
		params.put("indexList", indexList);
		
		try {
			//Map defaultTmp = reportTemplateService.queryDefaultTemplate();
			//System.out.println(defaultTmp+"=========");
			//List<Map> tmpList = reportTemplateService.queryReportTemplate("shangjia001", "zhangfeng");
			//System.out.println(tmpList+"=========");
			//List<Map> map = reportTemplateService.queryReportIndexes("9d0de9ed34b54db592038a6fd2eac450");
			//System.out.println(map+"=========");
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		
	}
}
