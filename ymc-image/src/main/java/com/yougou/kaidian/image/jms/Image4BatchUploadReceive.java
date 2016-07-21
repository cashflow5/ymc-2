package com.yougou.kaidian.image.jms;

import java.text.MessageFormat;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.yougou.kaidian.common.vo.Image4BatchUploadMessage;
import com.yougou.kaidian.image.service.IYmcWebImageService;

/**
 * 接受jms消息(图片批量上传)
 * 
 * @author liming
 *
 */
@Component(value="image4BatchUploadReceive")
public class Image4BatchUploadReceive {

	private static final Logger logger = LoggerFactory.getLogger(Image4BatchUploadReceive.class);
	
	@Resource
	private IYmcWebImageService ymcWebImageService;
	
	public void handleMessage(Image4BatchUploadMessage message) {
		if (this.checkMessage(message)) {
			try {
				ymcWebImageService.handlePhotoBySinglePic(message);
			} catch (Exception e) {
				ymcWebImageService.sandMail("消息："+message.toString()+" 切图产生异常："+e.getMessage(),"商家中心切图有问题(批量发布),请及时留意!");
				logger.error(MessageFormat.format("(批量发布)切图产生异常. | message:{0}.", message.toString()), e);
			}
		} else {
			ymcWebImageService.sandMail("消息校验不通过,不执行切图操作."+message.toString(),"商家中心切图有问题(批量发布),请及时留意!");
			logger.warn("(批量发布)消息校验不通过,不执行切图操作. | message:{}.", message.toString());
		}
	}
	
	/**
	 * 校验MQ消息内容
	 * @param message
	 * @return
	 */
	private boolean checkMessage(Image4BatchUploadMessage message){
		if(StringUtils.isBlank(message.getMerchantCode())){
			logger.error("接收消息校验,商家编码为空,请检查!");
			return false;
		}
		if(StringUtils.isBlank(message.getCommodityNo())){
			logger.error("接收消息校验,商品编号为空,请检查!");
			return false;
		}
		if(StringUtils.isBlank(message.getUrlFragment())){
			logger.error("接收消息校验,图片线上相对路径为空,请检查!");
			return false;
		}
		if(StringUtils.isBlank(message.getFtpRelativePath())){
			logger.error("接收消息校验,FTP临时空间相对路径为空,请检查!");
			return false;
		}
		if(StringUtils.isBlank(message.getPicName())){
			logger.error("接收消息校验,图片名称为空,请检查!");
			return false;
		}
		if(StringUtils.isBlank(message.getPicUrl())){
			logger.error("接收消息校验,图片地址为空,请检查!");
			return false;
		}
		return true;
	}
	
}
