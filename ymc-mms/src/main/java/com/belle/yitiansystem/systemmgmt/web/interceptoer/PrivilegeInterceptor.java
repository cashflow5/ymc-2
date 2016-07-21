package com.belle.yitiansystem.systemmgmt.web.interceptoer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.spring.DataSourceSwitcher;
import com.belle.yitiansystem.systemmgmt.component.IPrivilegeComponent;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.belle.yitiansystem.systemmgmt.service.ISystemmgtUserService;


@Service
public class PrivilegeInterceptor extends HandlerInterceptorAdapter {
	
	private static String noPrivilege = "/yitiansystem/systemmgmt/mainmanage/noPrivilege.sc";
	
	private static String notLogIn = "/yitiansystem/systemmgmt/mainmanage/tologin.sc";
  
	@Resource
	private IPrivilegeComponent privilegeHelper;
	
	@Value("${'system.debug.login.mode'}")
	private String system_debug_login_mode;
	
	@Resource
    private ISystemmgtUserService systemmgtUserService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		String rootStruc = request.getParameter("root_struc");
		String result = privilegeHelper.doCheck(request);
		if(null ==  request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER)){
			//自动登录
			if(StringUtils.isNotBlank(system_debug_login_mode) && "true".equals(system_debug_login_mode.trim())) {
				 HttpSession session = request.getSession();
				 String noNeedToAutoLogin = (String)(session.getAttribute("noNeedToAutoLogin"));
				if(null == noNeedToAutoLogin) {
					DataSourceSwitcher.setDataSourceSys();
					SystemmgtUser loginUser =systemmgtUserService.systemUserLogin(system_debug_login_mode);
			        request.getSession().setAttribute(Constant.LOGIN_SYSTEM_USER, loginUser);
			        DataSourceSwitcher.clearDataSource();
			    }
			}
		}
	
		if (Constant.ACCESSABLE.equals(result)) {
			return true;
		} else if (Constant.NOT_LOGIN.equals(result)) {
			if(StringUtils.isNotBlank(rootStruc)){
				response.sendRedirect(request.getContextPath()+notLogIn+"?root_struc="+rootStruc);
			}else{
				response.sendRedirect(request.getContextPath()+notLogIn);
			}
			return false;
		} else if (Constant.NO_PRIVILEGE.equals(result)) {
			response.sendRedirect(request.getContextPath()+noPrivilege);
			return false; //需要替换这个返回值以进入一个无权限警告页面（有返回按钮）
		} 
		
		return true;
	}
	
}
