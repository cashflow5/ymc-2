/**
 * 
 */
package com.yougou.kaidian.image.test;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.yougou.kaidian.common.vo.CommodityImage;
import com.yougou.kaidian.common.vo.Image4BatchUploadMessage;
import com.yougou.kaidian.common.vo.Image4SingleCommodityMessage;
import com.yougou.kaidian.common.vo.ImageMessage;

/**
 * @author huangtao
 *
 */
public class Producer {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				HelloWorldConfiguration.class);
		AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
/*		ImageMessage message = new ImageMessage();
		message.setCommodityNo("3344555");
		message.setMerchantCode("SP20130821678648");
		message.setPicType("l");
		message.setUrlFragment("kailas/2013/" + message.getCommodityNo());
		message.setStatus(0);
		message.setImgFileId(new String[] {
				"35d6cfa8-190c-4ac5-b385-f092025b3d21_01_l",
				"607d922c-7301-449a-9aa4-b6f87ec5f7d8_02_l",
				"2619c990-a1dd-4840-a6e2-0c943c5b853d_03_l",
				"2db6718b-4f6c-4f42-a345-5b5bec713b24_04_l",
				"289c26b5-24bd-4f66-8a59-579757a0df26_05_l",
				"20800431-58aa-4878-b64e-04390c7fa4eb_06_l",
				"13202854-ea13-4843-952e-32dc306657e6_07_l" });
		message.setSeqNo(2);
		message.setId("122334565");
		message.setProDesc("<html></html>");
		message.setFtpRelativePath("merchantpics/temp/" + message.getMerchantCode());
		amqpTemplate.convertAndSend(message);
		System.out.println("Sent: scuess");*/
		Image4SingleCommodityMessage message=new Image4SingleCommodityMessage();
		CommodityImage[] commodityImageArray=new CommodityImage[7];
		commodityImageArray[0]=new CommodityImage();
		commodityImageArray[0].setIndex(1);
		commodityImageArray[0].setPicName("75c3db8a-3b72-4414-961a-5fa841b5d88c_01_l.jpg");
		commodityImageArray[0].setPicUrl("http://10.0.30.71:8080/merchantpics/merchantpics/temp/SP20130110458709/75c3db8a-3b72-4414-961a-5fa841b5d88c_01_l.jpg");
		
		commodityImageArray[1]=new CommodityImage();
		commodityImageArray[1].setIndex(2);
		commodityImageArray[1].setPicName("4c7ddfbe-ce13-4cb4-9f45-8346f5b19e6c_02_l.jpg");
		commodityImageArray[1].setPicUrl("http://10.0.30.71:8080/merchantpics/merchantpics/temp/SP20130110458709/4c7ddfbe-ce13-4cb4-9f45-8346f5b19e6c_02_l.jpg");
		
		commodityImageArray[2]=new CommodityImage();
		commodityImageArray[2].setIndex(3);
		commodityImageArray[2].setPicName("561835a8-0830-413c-b112-fa59528c5b5f_03_l.jpg");
		commodityImageArray[2].setPicUrl("http://10.0.30.71:8080/merchantpics/merchantpics/temp/SP20130110458709/561835a8-0830-413c-b112-fa59528c5b5f_03_l.jpg");
		
		commodityImageArray[3]=new CommodityImage();
		commodityImageArray[3].setIndex(4);
		commodityImageArray[3].setPicName("71361519-bee6-4f93-9311-73700face5c1_04_l.jpg");
		commodityImageArray[3].setPicUrl("http://10.0.30.71:8080/merchantpics/merchantpics/temp/SP20130110458709/71361519-bee6-4f93-9311-73700face5c1_04_l.jpg");
		
		commodityImageArray[4]=new CommodityImage();
		commodityImageArray[4].setIndex(5);
		commodityImageArray[4].setPicName("e0e858d6-f311-4aa5-88b1-70d998efcbf0_05_l.jpg");
		commodityImageArray[4].setPicUrl("http://10.0.30.71:8080/merchantpics/merchantpics/temp/SP20130110458709/e0e858d6-f311-4aa5-88b1-70d998efcbf0_05_l.jpg");
		
		commodityImageArray[5]=new CommodityImage();
		commodityImageArray[5].setIndex(6);
		commodityImageArray[5].setPicName("7ed8e8e8-1dd4-4ccb-9174-5e83cb0061a1_06_l.jpg");
		commodityImageArray[5].setPicUrl("http://10.0.30.71:8080/merchantpics/merchantpics/temp/SP20130110458709/7ed8e8e8-1dd4-4ccb-9174-5e83cb0061a1_06_l.jpg");
		
		commodityImageArray[6]=new CommodityImage();
		commodityImageArray[6].setIndex(7);
		commodityImageArray[6].setPicName("b60cf701-2271-442e-bc87-71c779d5210d_07_l.jpg");
		commodityImageArray[6].setPicUrl("http://10.0.30.71:8080/merchantpics/merchantpics/temp/SP20130110458709/b60cf701-2271-442e-bc87-71c779d5210d_07_l.jpg");
		
		message.setCommodityImages(commodityImageArray);
		message.setCommodityNo("99965442");
		message.setCreateTime(new Date());
		message.setFtpRelativePath("/kingcamp/2014/99965442/");
		message.setId("122334565");
		message.setMerchantCode("SP20130110458709");
		message.setProDesc("<img src=\"http://10.0.30.71:8080/merchantpics/merchantpics/SP20130110458709/5f16844b2e77477a90cda44fad4a5e6c.jpg?68\" /><br /><img src=\"http://10.0.30.71:8080/merchantpics/merchantpics/SP20130110458709/afc29e8fb492421da86aa80af67f6de9.jpg?68\" />");
		message.setSeqNo(2);
		message.setStatus(0);
		message.setUrlFragment("/kingcamp/2014/99965442/");
		
		//amqpTemplate.convertAndSend(message);
		//System.out.println("Sent: scuess");
		
		Image4BatchUploadMessage message2=new Image4BatchUploadMessage();
		message2.setCommodityNo("99965442");
		message2.setCreateTime(new Date());
		message2.setFtpRelativePath("/kingcamp/2014/99965442/");
		message2.setId("122334565");
		message2.setMerchantCode("SP20130110458709");
		message2.setPicName("99965442_01_l.jpg");
		message2.setPicType("p");
		message2.setPicUrl("http://10.0.30.71:8080/merchantpics/kingcamp/2014/99965442/99965442_01_l.jpg");
		message2.setSeqNo(1);
		message2.setStatus(0);
		message2.setUrlFragment("/kingcamp/2014/99965442/");
		amqpTemplate.convertAndSend(message2);
		System.out.println("Sent: scuess");
	} 
}
