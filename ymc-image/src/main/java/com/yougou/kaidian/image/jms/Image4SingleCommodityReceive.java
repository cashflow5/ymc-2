/**
 * 
 */
package com.yougou.kaidian.image.jms;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.yougou.kaidian.common.vo.CommodityImage;
import com.yougou.kaidian.common.vo.Image4SingleCommodityMessage;
import com.yougou.kaidian.image.service.IYmcWebImageService;

/**
 * 接受jms消息
 * 
 * @author liming
 *
 */
@Component(value="image4SingleCommodityReceive")
public class Image4SingleCommodityReceive {
	
	private static final Logger logger = LoggerFactory.getLogger(Image4SingleCommodityReceive.class);
	
	@Resource
	private IYmcWebImageService ymcWebImageService;
	
	/**
	 * 接收MQ消息处理图片
	 * @param message
	 */
	public void handleMessage(Image4SingleCommodityMessage message) {
		boolean mesCheckScuess=this.checkMessage(message);
		if(mesCheckScuess){
	        try {
	        	ymcWebImageService.handlePhotoByCommodity(message);
			} catch (Exception e) {
				ymcWebImageService.sandMail("消息："+message.toString()+" 切图产生异常失败："+e.getMessage(),"商家中心切图有问题(单品发布),请及时留意!");
				logger.error("(单品发布)消息："+message.toString()+" 切图产生异常失败：",e);
			}
		}else{
			ymcWebImageService.sandMail("消息校验不通过,不执行切图操作."+message.toString(),"商家中心切图有问题(单品发布),请及时留意!");
			logger.warn("(单品发布)消息校验不通过,不执行切图操作."+message.toString());
		}
	}
	
	/**
	 * 校验MQ消息内容
	 * @param message
	 * @return
	 */
	private boolean checkMessage(Image4SingleCommodityMessage message){
		if(StringUtils.isBlank(message.getMerchantCode())){
			logger.error("接收消息校验,商家编码为空,请检查!");
			return false;
		}
		if(StringUtils.isBlank(message.getCommodityNo())){
			logger.error("接收消息校验,商品编号为空,请检查!");
			return false;
		}
		if(message.getCommodityImages()==null||message.getCommodityImages().length==0){
			logger.error("接收消息校验,商品图片数组为空,请检查!");
			return false;
		}
		for(CommodityImage commodityImage:message.getCommodityImages()){
			if(StringUtils.isBlank(commodityImage.getPicName())||StringUtils.isBlank(commodityImage.getPicUrl())){
				logger.error("接收消息校验,商品图片数组内部对象,图片名称或者URL为空,请检查!");
				return false;
			}
		}
		if(StringUtils.isBlank(message.getUrlFragment())){
			logger.error("接收消息校验,图片访问相对路径为空,请检查!");
			return false;
		}
		if(StringUtils.isBlank(message.getFtpRelativePath())){
			logger.error("接收消息校验,FTP临时空间相对路径为空,请检查!");
			return false;
		}
		return true;
	}
}
