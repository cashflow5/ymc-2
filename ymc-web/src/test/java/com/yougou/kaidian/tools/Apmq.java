package com.yougou.kaidian.tools;

import javax.annotation.Resource;
import org.junit.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import com.yougou.kaidian.common.vo.ImageMessage;
import com.yougou.kaidian.common.util.UUIDGenerator;


/**
 * Test Case
 * 
 * @author huang.tao
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-Test.xml","classpath:rabbitmq-config.xml" })
public class Apmq extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Resource
	@Qualifier(value="jmsTemplate")
	private AmqpTemplate amqpTemplate;
	
	@Test
	public void statisticMerchantData() {
		//发送MQ
		ImageMessage message = new ImageMessage();
		message.setId(UUIDGenerator.getUUID());
		message.setCommodityNo("99900130");
		message.setMerchantCode("SP20130821678648");
		message.setPicType("l");
		message.setImgFileId(new String[]{"","","","","","",""});
		message.setProDesc("<br /><img src=\"http://i1.ygimg.cn/pics/merchantpics/SP20130822262196/DB14531-37_01.jpg?27\" /><br /><img src=\"http://i1.ygimg.cn/pics/merchantpics/SP20130822262196/DB14531-37_02.jpg?27\" /><br /><img src=\"http://i1.ygimg.cn/pics/merchantpics/SP20130822262196/DB14531-37_03_01.jpg?27\" /><br /><img src=\"http://i1.ygimg.cn/pics/merchantpics/SP20130822262196/DB14531-37_03_02.jpg?27\" /><br /><img src=\"http://i1.ygimg.cn/pics/merchantpics/SP20130822262196/DB14531-37_04.jpg?27\" /><br />");
		message.setUrlFragment("/belle/2014/99900130/");
		message.setFtpRelativePath("/merchantpics/temp/SP20130821678648/");
		message.setSeqNo(0);
		try {
			amqpTemplate.convertAndSend("ymc.image.queue", message);
			System.out.println("================================");
		} catch (AmqpException e) {
			e.printStackTrace();
		}
	}
	
}
