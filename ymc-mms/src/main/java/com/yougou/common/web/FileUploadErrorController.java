/*
 * Copyright 2011 yougou.com All right reserved. This software is the
 * confidential and proprietary information of yougou.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with yougou.com.
 */
package com.yougou.common.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.web.controller.BaseController;

/**
 * FileUploadErrorController 类用于处理Spring MVC 文件上传时文件上传异常的Redirect跳转
 * 
 * @author hu.jp
 * @version 1.00 2013-04-01
 * 
 */
@Controller
@RequestMapping("/common/fileupload")
public class FileUploadErrorController extends BaseController {
	
	

	/**
	 * 去错误页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/fileuploaderror.sc")
	public ModelAndView fileUploadError(HttpServletRequest request){
		String errroMsg = "上传失败,请重新上传!";
		if ("1".equals(request.getParameter("errortype")) ) {
			errroMsg = "上传失败, 文件不能超过20M !";
		}
		
		return new ModelAndView("error","errorMessageException", errroMsg);
	}
	
}
