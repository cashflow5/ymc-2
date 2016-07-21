package com.yougou.kaidian.framework.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.kaidian.framework.util.SessionUtil;

public class DefaultHandlerExceptionResolver implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(DefaultHandlerExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		Map<String, Object> unionUser = SessionUtil.getUnionUser(request);
		String merchantCode="";
		if(unionUser!=null){
			merchantCode = MapUtils.getString(unionUser, "supplier_code");
		}
		logger.error(ex.getMessage(), ex);
		logger.error("商家编码:{} 访问URL:[{}] Catch Exception: {}", new Object[]{merchantCode,request.getRequestURI(),ex});
		
		String contents = StringUtils.defaultString(ex.getMessage(), "null");
		
		// Http request by jQuery Ajax
		if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
			try {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/html; charset=utf-8");
				response.getWriter().print(contents);
				response.getWriter().flush();
			} catch (IOException ie) {
				logger.error("Response jQuery Ajax Contents Exception: ", ie);
			}
			return null;
		} 
		
		//业务异常分别进行处理
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean isReturn = true;
		if (ex instanceof YMCException) {
			isReturn = false;
		} else if (ex instanceof DubboServiceException) {
			isReturn = false;
			DubboServiceException _e = (DubboServiceException) ex;
			contents += "(:" + _e.getDubboProvider() + ")"; 
		} else if (isReturn && ex instanceof RuntimeException) {
			contents = "系统产生运行时异常,请联系管理员!";
		} else {
			contents = "系统异常,请联系管理员进行处理!";
		}
		// 将错误信息传递给view
		resultMap.put("errorDesc", contents);
		return new ModelAndView("manage/comm/error", resultMap);
	}
}
