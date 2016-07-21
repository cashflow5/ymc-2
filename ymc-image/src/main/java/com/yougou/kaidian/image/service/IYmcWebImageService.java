/**
 * 
 */
package com.yougou.kaidian.image.service;

import com.yougou.kaidian.common.vo.Image4BatchUploadMessage;
import com.yougou.kaidian.common.vo.Image4SingleCommodityMessage;
import com.yougou.kaidian.common.vo.ImageTaobaoMessage;

/**
 * 图片处理逻辑接口
 * 
 * @author huangtao
 *
 */
public interface IYmcWebImageService {
	
	/**
	 * 单个处理图片(以一张图片为逻辑, 角度图或描述图的处理及上传)
	 * 
	 * @param message
	 * @throws Exception
	 */
	void handlePhotoBySinglePic(Image4BatchUploadMessage message) throws Exception;
	
	/**
	 * 批量处理图片(以一个单品为逻辑, 包含角度图|描述图的处理及上传)
	 * 
	 * @param message
	 * @throws Exception
	 */
	void handlePhotoByCommodity(Image4SingleCommodityMessage message) throws Exception;
	
	/**
	 * 处理淘宝图片
	 * @param message
	 * @throws Exception
	 */
	void handleTaobaoPhoto(ImageTaobaoMessage message) throws Exception;
	
	/**
	 * 邮件发送处理图片失败信息
	 * @param message
	 * @param title
	 */
	void sandMail(String message,String title);
	
	/**
	 * 获取本地IP
	 * @return
	 */
	String getLocalIP();
}
