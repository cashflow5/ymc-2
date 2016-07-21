/**
 * 
 */
package com.yougou.kaidian.image.jms;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.yougou.kaidian.common.vo.ImageTaobaoMessage;
import com.yougou.kaidian.image.service.IYmcWebImageService;

/**
 * 接受jms消息
 * 
 * @author liming
 *
 */
@Component(value="image4TaobaoReceive")
public class Image4TaobaoReceive {
	
	private static final Logger logger = LoggerFactory.getLogger(Image4TaobaoReceive.class);
	
	@Resource
	private IYmcWebImageService ymcWebImageService;
	
	/**
	 * 接收MQ消息处理图片
	 * @param message
	 */
	public void handleMessage(ImageTaobaoMessage message) {
		boolean mesCheckScuess=this.checkMessage(message);
		if(mesCheckScuess){
	        try {
	        	ymcWebImageService.handleTaobaoPhoto(message);
			} catch (Exception e) {
				ymcWebImageService.sandMail("消息："+message.toString()+" 处理图片产生异常："+e.getMessage(),"商家中心处理淘宝图片有问题,请及时留意!");
				logger.error("消息："+message.toString()+" 处理淘宝图片有问题：",e);
			}
		}else{
			ymcWebImageService.sandMail("消息校验不通过."+message.toString(),"商家中心处理淘宝图片有问题,请及时留意!");
			logger.warn("商家中心处理淘宝图片有问题,不执行切图操作."+message.toString());
		}
	}
	
	/**
	 * 校验MQ消息内容
	 * @param message
	 * @return
	 */
	private boolean checkMessage(ImageTaobaoMessage message){
		if(StringUtils.isBlank(message.getMerchantCode())){
			logger.error("接收消息校验,商家编码为空,请检查!");
			return false;
		}
		return true;
	}
}
