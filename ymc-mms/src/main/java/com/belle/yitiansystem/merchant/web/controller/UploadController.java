/**
 * 
 */
package com.belle.yitiansystem.merchant.web.controller;

import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.belle.yitiansystem.merchant.service.IMerchantHelpService;
import com.belle.yitiansystem.merchant.util.HelpCenterUtil;

/**
 * 图片上传Controller
 * 
 * @author huang.tao
 *
 */
@Controller
@RequestMapping("/upload")
public class UploadController {
	
	private Logger logger = Logger.getLogger(UploadController.class);
	
	@Resource
	private HelpCenterUtil helpCenterutil;
	
	@Resource
	private IMerchantHelpService helpService;
	
	/**
	 * 处理帮助中心图片上传
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/help_image")
	public String startUploadPic(DefaultMultipartHttpServletRequest request, HttpServletResponse response) {
		InputStream is = null;
		try {
			// 商品图片
			MultipartFile multipartFile = request.getFile("Filedata");
			// 被替换商品图片
			//String replacement = request.getParameter("replacement");
			// 是否验证图片名称
			//boolean editFilename = Boolean.valueOf(StringUtils.defaultIfEmpty(request.getParameter("editFilename"), "true"));
			// 上传图片
			is = multipartFile.getInputStream();
			boolean isTrue = helpCenterutil.uploadImage(is, multipartFile.getOriginalFilename());
			helpService.addImageName(multipartFile.getOriginalFilename());
			
			return isTrue ? "success" : "fail";
		} catch (Exception ex) {
			logger.error(ex);
			return StringUtils.defaultIfEmpty(ex.getMessage(), "null");
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
}
