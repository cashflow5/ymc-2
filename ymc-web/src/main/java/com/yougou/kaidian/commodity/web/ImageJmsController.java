package com.yougou.kaidian.commodity.web;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.vo.CommodityImage;
import com.yougou.kaidian.common.vo.Image4BatchUploadMessage;
import com.yougou.kaidian.common.vo.Image4SingleCommodityMessage;
import com.yougou.kaidian.common.vo.ImageTaobaoMessage;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.merchant.api.supplier.service.IMerchantImageService;
import com.yougou.merchant.api.supplier.vo.ImageJmsVo;

/**
 * 商家图片处理jms
 * 
 * @author huang.tao
 *
 */
@Controller
@RequestMapping("/image_jms")
public class ImageJmsController {
    
	private static final Logger logger = LoggerFactory.getLogger(ImageJmsController.class);
	
	@Resource
	private IMerchantImageService merchantImageService;
	@Resource
	private CommoditySettings commoditySetting;
	@Resource
	@Qualifier(value="jmsTemplate")
	private AmqpTemplate amqpTemplate;
	@Resource
	private CommoditySettings settings;
	private Pattern imgPattern = Pattern.compile("([^(/)]*).jpg");
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 查询消息队列列表
	 * @param modelMap
	 * @param request
	 * @param query
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryImageJms")
	public String queryImageJmsList(ModelMap modelMap, HttpServletRequest request, Query query, ImageJmsVo vo) throws Exception {
		//时间转换
		if (StringUtils.isNotBlank(vo.getStartTime())) {
			vo.setStartTime(vo.getStartTime() + " 00:00:00");
		}
		if (StringUtils.isNotBlank(vo.getEndTime())) {
			vo.setEndTime(vo.getEndTime() + " 23:59:59");
		}
		
		if (vo != null && StringUtils.isBlank(vo.getPicType())) vo.setPicType(null);
		List<ImageJmsVo> list = merchantImageService.queryImageJmsList(vo, query.getPage(), query.getPageSize());
		
		int count = merchantImageService.queryImageJmsCount(vo);
		PageFinder<ImageJmsVo> pageFinder = new PageFinder<ImageJmsVo>(query.getPage(), query.getPageSize(), count);
		pageFinder.setData(list);
		
		if(!isEmptyForPageFinder(pageFinder)){
			modelMap.put("pageFinder", pageFinder);
		}
		modelMap.put("vo", vo);
		return "/manage/monitor/jms_message_monitoring";
	}
	
	/**
	 * 重发消息
	 * @param vo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("retryJmsMessage")
	public String retryJmsMessage(ImageJmsVo vo, HttpServletRequest request) {
		JSONObject jsonObj = new JSONObject();
		List<ImageJmsVo> jmsVos = merchantImageService.queryImageJmsList(vo);
		
		jsonObj.put("result", "false");
		if (CollectionUtils.isNotEmpty(jmsVos)) {
			ImageJmsVo jmsVo = jmsVos.get(0);
			boolean result = this.retryImageJms(jmsVo);
			if (result) jsonObj.put("result", "true");
		}
		return jsonObj.toString();
	}
	
	/**
	 * 重发消息(选择)
	 * @param messageid
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("retryJmsMessageChecked")
	public String retryJmsMessageChecked(String[] messageid, HttpServletRequest request) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("result", "false");
		if(messageid!=null&&messageid.length>0){
			try {
				List<ImageJmsVo> jmsVos=merchantImageService.queryImageJmsListByIds(messageid);
				if (CollectionUtils.isNotEmpty(jmsVos)) {
					for(ImageJmsVo jmsVo:jmsVos){
						//超过两分钟以上的处理，避免出现误操作
						if(System.currentTimeMillis()-jmsVo.getCreateTime().getTime()>1000*60*5){
							this.retryImageJms(jmsVo);
						}
					}
				    jsonObj.put("result", "true");
				}
			} catch (Exception e) {
				logger.error("重发消息异常!");
			}
		}
		return jsonObj.toString();
	}
	
	/**
	 * 重发消息(所有未处理)
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("retryJmsMessageUntreated")
	public String retryJmsMessageUntreated(HttpServletRequest request) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("result", "false");
		try {
			List<ImageJmsVo> jmsVos=merchantImageService.getUntreated();
			if (CollectionUtils.isNotEmpty(jmsVos)) {
				for(ImageJmsVo jmsVo:jmsVos){
					//超过5分钟以上的处理，避免出现误操作
					if(System.currentTimeMillis()-jmsVo.getCreateTime().getTime()>1000*60*5){
						this.retryImageJms(jmsVo);
					}
				}
			    jsonObj.put("result", "true");
			}
		} catch (Exception e) {
			logger.error("重发消息异常!");
		}
		return jsonObj.toString();
	}
	
	/**
	 * 作废处理(选择)
	 * @param messageid
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("jmsObsoleteChecked")
	public String jmsObsoleteChecked(String[] messageid,HttpServletRequest request) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("result", "false");
		if(messageid!=null&&messageid.length>0){
			try {
				merchantImageService.updateImageJmsStatusInvalid(messageid);
				jsonObj.put("result", "true");
			} catch (Exception e) {
				logger.error("作废消息异常!");
			}
		}
		return jsonObj.toString();
	}
	
	/**
	 * 删除数据库消息记录(7天前)
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delMessage")
	public String delMessage(HttpServletRequest request) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("result", "false");
		try {
			merchantImageService.delMessage();
			jsonObj.put("result", "true");
		} catch (Exception e) {
			logger.error("删除消息异常!");
		}
		return jsonObj.toString();
	}
	
	private boolean retryImageJms(ImageJmsVo vo) {
		boolean result = true;
		
		String queueName = "";
		Object message=null;
		if("t".equalsIgnoreCase(vo.getPicType())){
			queueName = "ymc.handleimage.taobao.queue";
			message=this.convertImage4TabaoMessageJmsVo(vo);
		}else if (StringUtils.isBlank(vo.getImageId())) {
			queueName = "ymc.handleimage.batch.queue";
			message=this.convertImage4BatchUploadMessageJmsVo(vo);
		}else{
			queueName = "ymc.handleimage.queue";
			message=this.convertImage4SingleCommodityMessageJmsVo(vo);
		}
		
		try {
			amqpTemplate.convertAndSend(queueName, message);
		} catch (AmqpException e) {
			logger.error("重发Image jms消息异常.", e);
			result = false;
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("updateJmsStatus")
	public String updateJmsStatus(ImageJmsVo vo, HttpServletRequest request) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("result", "true");
		try {
			merchantImageService.updateImageJmsStatus(vo);
		} catch (Exception e) {
			logger.error("作废jms消息异常。", e);
			jsonObj.put("result", "false");
		}
		
		return jsonObj.toString();
	} 
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.GET, value="/timerRetryImageJms")
	public void timerRetryJmsMessage(){
		logger.info( "{}:接收到执行定時重發Image jms任务.",dateFormat.format(new Date()));
		ImageJmsVo vo = new ImageJmsVo();
		vo.setStatus(0);
		List<ImageJmsVo> list = merchantImageService.queryImageJmsList(vo);
		if (CollectionUtils.isNotEmpty(list)) {
			for (ImageJmsVo imageJmsVo : list) {
				boolean result = this.retryImageJms(imageJmsVo);
				if(!result){
					logger.info("{}:接收到jms重发失败. merchantCode:{} | commodityNo:{}", 
							new Object[]{dateFormat.format(new Date()),imageJmsVo.getMerchantCode(), imageJmsVo.getCommodityNo()});
				}
			}
		}
		 
		logger.info("{}:接收到执行定時重發Image jms任务完成.",dateFormat.format(new Date()));
	}
	
	/**
	 * <p>判断数据结果集是否为空</p>
	 * 
	 * @param page
	 * @return 空 :true  | 非空:false
	 */
	private boolean isEmptyForPageFinder(PageFinder<?> page) {
		if (page == null) 
			return true;
		
		if (page.getData() == null) 
			return true;
		
		if (page.getData().size() == 0) 
			return true;

		return false;
	}
	
	/**
	 * 重发消息，组装
	 * @param message
	 * @return
	 */
	private Image4BatchUploadMessage convertImage4BatchUploadMessageJmsVo(ImageJmsVo message) {
		String ftpUrl=MessageFormat.format(commoditySetting.imageFtpPreTempSpace, message.getMerchantCode());
		Image4BatchUploadMessage message2 = new Image4BatchUploadMessage();
		message2.setCommodityNo(message.getCommodityNo());
		message2.setCreateTime(new Date());
		message2.setFtpRelativePath(MessageFormat.format(commoditySetting.imageFtpPreTempSpace, message.getMerchantCode()));
		message2.setId(message.getId());
		message2.setMerchantCode(message.getMerchantCode());
		message2.setPicName(message.getProDesc());
		message2.setPicType(message.getPicType());
		message2.setPicUrl(settings.getCommodityPreviewDomain()+ftpUrl+"/"+message2.getPicName());
		message2.setSeqNo(message.getSeqNo());
		message2.setStatus(0);
		message2.setUrlFragment(message.getUrlFragment());
		
		return message2;
	}
	
	/**
	 * 重发消息，组装
	 * @param message
	 * @return
	 */
	private Image4SingleCommodityMessage convertImage4SingleCommodityMessageJmsVo(ImageJmsVo message) {
		Image4SingleCommodityMessage imgMessage=new Image4SingleCommodityMessage();
		String[] picURL=message.getImageId().split(",");
		CommodityImage[] commodityImageArray=new CommodityImage[picURL.length];
		for(int i=0;i<picURL.length;i++){
			commodityImageArray[i]=new CommodityImage();
			commodityImageArray[i].setIndex(i+1);
			if(picURL[i].length()>3){
				Matcher matcher = imgPattern.matcher(picURL[i]);
				if(matcher.find()){
					commodityImageArray[i].setPicName(matcher.group(0));
				}else{
					commodityImageArray[i].setPicName("0");
				}
			}else{
				commodityImageArray[i].setPicName("0");
			}
			commodityImageArray[i].setPicUrl(picURL[i]);
		}
		
		
		imgMessage.setCommodityImages(commodityImageArray);
		imgMessage.setCommodityNo(message.getCommodityNo());
		imgMessage.setCreateTime(new Date());
		imgMessage.setFtpRelativePath(MessageFormat.format(commoditySetting.imageFtpPreTempSpace, message.getMerchantCode()));
		imgMessage.setId(message.getId());
		imgMessage.setMerchantCode(message.getMerchantCode());
		imgMessage.setProDesc(message.getProDesc());
		imgMessage.setSeqNo(message.getSeqNo());
		imgMessage.setStatus(0);
		imgMessage.setUrlFragment(message.getUrlFragment());
		
		return imgMessage;
	}
	
	/**
	 * 重发消息，组装
	 * @param message
	 * @return
	 */
	private ImageTaobaoMessage convertImage4TabaoMessageJmsVo(ImageJmsVo message) {
		ImageTaobaoMessage imgMessage=new ImageTaobaoMessage();
		imgMessage.setId(message.getId());
		imgMessage.setImgArry(StringUtils.split(message.getImageId(),","));
		imgMessage.setMerchantCode(message.getMerchantCode());
		imgMessage.setNumIid(message.getCommodityNo());
		imgMessage.setProDesc(message.getProDesc());
		
		return imgMessage;
	}
}
