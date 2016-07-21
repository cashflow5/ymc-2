package com.belle.infrastructure.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.belle.yitiansystem.merchant.web.controller.MerchantsController;

/**
 *
 * @author yhb
 *
 * @version 创建时间：2011-4-11 下午01:08:23
 */
public class DefaultHandlerExceptionResolver extends SimpleMappingExceptionResolver {

	private static Logger logger = Logger.getLogger(DefaultHandlerExceptionResolver.class);
	
	/**
	 * 重写父类异常入口方法
	 */
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		if(ex != null){
			logger.error(ex.getMessage(), ex);
		}

		if (shouldApplyTo(request, handler)) {

			//可以增加多个异常处理
			String message = "";
//			if(ex instanceof IllegalArgumentException){
//				IllegalArgumentException illEx = (IllegalArgumentException)ex;
//				message = illEx.getMessage();
//
//			}else if(ex instanceof SQLException){
//				SQLException sqlEx = (SQLException)ex;
//				message = sqlEx.getMessage();
//			}

			//所有前台异常转为MallfriendlyException,跳转友好提示页面
			//System.out.println("异常抛出包路径："+ex.getStackTrace()[0].getClassName());
			
			StackTraceElement[] stacks = ex.getStackTrace();
			for (int i = 0; i < stacks.length; i++) {
				String exceptionPath = ex.getStackTrace()[i].getClassName();
				if (StringUtils.isNotBlank(exceptionPath)
						&& (exceptionPath.indexOf("com.belle.yitianmall") >= 0
								|| exceptionPath.indexOf("com.belle.pay") >= 0 || exceptionPath
								.indexOf("com.belle.solr") >= 0)) {
					ex = new MallfriendlyException(ex);
					break;
				}
			}
			

			message = (ex.getMessage() != null ) ? ex.getMessage() : "" ;
			ex.printStackTrace();

			request.setAttribute("errorMessageException",message);
			return doResolveException(request, response, handler, ex);

		} else {
			return null;
		}
	}

}
