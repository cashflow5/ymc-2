package com.yougou.kaidian.framework.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.kaidian.framework.util.SessionUtil;

public class CommonInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);
	
	/** 
     * 在业务处理器处理请求之前被调用 
     * 如果返回false 
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
     *  
     * 如果返回true 
     *    执行下一个拦截器,直到所有的拦截器都执行完毕 
     *    再执行被拦截的Controller 
     *    然后进入拦截器链, 
     *    从最后一个拦截器往回执行所有的postHandle() 
     *    接着再从最后一个拦截器往回执行所有的afterCompletion() 
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = StringUtils.trimToEmpty(request.getServletPath());
		if (url.contains("/merchants/login/to_login.sc")) return true;
		else if (url.contains("/merchants/login/to_Back.sc")) return true;
		else if (url.contains("merchantsLogin.sc")) return true;
		else if (url.contains("merchantsCode.sc")) return true;
		else if (url.contains("findpassword.sc")) return true;
		else if (url.contains("checkpassport.sc")) return true;
		else if (url.contains("resetpassword.sc")) return true;
		else if (url.contains("setpassword.sc")) return true;
		else if (url.contains("activatemail.sc")) return true;
		else if (url.contains("exitsLoginAccount.sc")) return true;
		else if (url.contains("to_index.sc")) return true;
		//else if (url.contains("deltemppicfile.sc")) return true;
		else if (url.contains("delygorder.sc")) return true;
		//else if (url.contains("imgJmsCount.sc")) return true;
		else if (url.contains("httptaskjob")) return true;
		else if (url.matches("/commodity/(pics/)?(upload|import|uploaddescribe)\\.sc")) return true;
		else if (url.matches("/picture/(upload|import|uploaddescribe)\\.sc")) return true;
		else if (url.contains("taobao/toTaobaoGetCatPropData.sc")) return true;
		
		
		Map<String, Object> unionUser = SessionUtil.getUnionUser(request);
		//拦截用户是否登录
		String loginName = MapUtils.getString(unionUser, "login_name");
		if (StringUtils.isBlank(loginName)) {
			logger.error("interceptor user session Invalid. url:{}.", url);
			response.sendRedirect("/merchants/login/to_login.sc");
			return false;
		}
		//以下请求不用关联
		if (url.contains("toSetMerchant.sc")) return true;
		if (url.contains("/merchants/login/toSetPresentMerchant.sc")) return true;
		if (url.contains("/merchants/security/authentication.sc")) return true;
		if (url.contains("/merchants/security/checkCode.sc")) return true;
		if (url.contains("/merchants/security/getMobileCode.sc")) return true;
		if (url.contains("/merchants/security/checkMobilephone.sc")) return true;
		if (url.contains("/merchants/security/checkPwdStrength.sc")) return true;
		if (url.contains("/merchants/security/checkOverDays.sc")) return true;
		if (url.contains("/merchants/security/toShowNote.sc")) return true;
		
		//拦截管理员账户是否选择关联商家
		String merchantCode = MapUtils.getString(unionUser, "supplier_code");
		if (StringUtils.isBlank(merchantCode)) {
			logger.error("interceptor user Unbounded merchants. url:{}.", url);
			response.sendRedirect("/merchants/login/to_index.sc");
			return false;
		}
		return true;
	}

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作   
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用  
     *  
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion() 
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
