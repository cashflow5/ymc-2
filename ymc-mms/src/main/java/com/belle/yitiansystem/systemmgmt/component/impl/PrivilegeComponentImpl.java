/**
 *
 */
package com.belle.yitiansystem.systemmgmt.component.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.web.servlet.CookieUtils;
import com.belle.yitiansystem.systemmgmt.component.IPrivilegeComponent;
import com.belle.yitiansystem.systemmgmt.model.pojo.AuthorityResources;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemConfig;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.belle.yitiansystem.systemmgmt.service.ISystemConfigService;
import com.belle.yitiansystem.systemmgmt.service.ISystemmgtUserService;
import com.belle.yitiansystem.systemmgmt.service.impl.SystemConfigServiceImpl;
import com.yougou.tools.common.utils.ServiceLocator;

@Service
public class PrivilegeComponentImpl implements IPrivilegeComponent {
	
	@Resource
	private ServiceLocator serviceLocator;
	
    @Resource
    private ISystemmgtUserService systemmgtUserService;

//	@Property(name = "privilege.excludeUrl")
	private static String excludeUrl;

//	@Property(name = "privilege.grantedUrl")
	private static String grantedUrl;

//	@Property(name = "privilege.controller.model")
	private static String controllerModel;

//	@Property(name = "privilege.mode")
	private static String mode;

//	@Property(name = "filter.nullController")
	private static String filterNullController;

	private String[] excludeUrls;

	private String[] grantedUrls;

	private String[] controllerModels;

	 private void initProperty(){
		 try {
			excludeUrl = getConfigValueByKey(Constant.EXCLUDEURL);
			grantedUrl = getConfigValueByKey(Constant.GRANTEDURL);
			controllerModel = getConfigValueByKey(Constant.CONTROLLER_MODEL);
			mode = getConfigValueByKey(Constant.MODE);
			filterNullController = getConfigValueByKey(Constant.NULLCONTROLLER);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	 private String getConfigValueByKey(String key) throws Exception{
		 ISystemConfigService configService = serviceLocator.getBeanFactory().getBean(SystemConfigServiceImpl.class);
			try {
				SystemConfig config = configService.getSystemConfigByKey(key);
				if(config!=null){
					return config.getValue();
				}else{
					return "";
				}
			} catch (Exception e) {
				throw new Exception();
			}
	 }



	@SuppressWarnings("unchecked")
	@Override
	public String doCheck(HttpServletRequest request) {


	    SystemmgtUser loginUser = (SystemmgtUser) request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER);
	    //获取登录后是否保存Cookie
        Cookie cookie = CookieUtils.getCookie(request, Constant.LOGIN_SYSTEM_USER_COOKIE_ID);
        if(loginUser == null && cookie != null && cookie.getMaxAge() > 0){
            String userid = cookie.getValue();
            loginUser = systemmgtUserService.getSystemmgtUserById(userid);
            cookie.setMaxAge(Constant.LOGIN_COOKIE_TIME);

            request.getSession().setAttribute(Constant.LOGIN_SYSTEM_USER, loginUser);
        }

		singletonUrls ();

		String servletPath = request.getServletPath().substring(1);


		//不需要处理的模块直接放行
		if (!checkControllerModel(servletPath))
			return Constant.ACCESSABLE;

		//放行不需要限制的请求
		if (checkExUrls(servletPath))
			return Constant.ACCESSABLE;

		//判断是否登录
		if (!checkIsLogin(loginUser)){
		       return Constant.NOT_LOGIN;
		}

		//是否校验系统资源里面没有的请求
        if(!filterNullController.equals("1")){
            //校验当前请求 是否存在系统管理的权限资源
            List<AuthorityResources> allResourceList = (List<AuthorityResources>) request.getSession().getAttribute(Constant.ALL_SYSTEM_RESOURCES);
            if (!checkUserGranted(allResourceList, servletPath)) {
                return Constant.ACCESSABLE;
            }
        }



		//系统超级管理员标识
		if(loginUser.getLevel() != null && loginUser.getLevel().equals(Constant.SYSTEM_LEVEL))
			return Constant.ACCESSABLE;


		List<AuthorityResources> resourceList = (List<AuthorityResources>) request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER_RESOURCES);

		if (!checkUserGranted(resourceList, servletPath) && !checkGrtUrls(servletPath)) {
			return Constant.NO_PRIVILEGE;
		}
		return Constant.ACCESSABLE;

	}

	// 检查用户是否登录或session过期
	private boolean checkIsLogin(SystemmgtUser loginUser) {
		if (null != loginUser)
			return true;
		return false;
	}

	// 检查是否是需要进行权限处理的模块
	private boolean checkControllerModel(String servletPath) {
		if (null == controllerModel)
			return false;

		for (String url : controllerModels) {
			if (servletPath.toLowerCase().startsWith(url.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	// 检查是否匹配无限制路径(未登录用户)
	private boolean checkExUrls(String servletPath) {
		if (null == excludeUrls)
			return false;

		for (String url : excludeUrls) {
			if (urlMatcher(servletPath, url)) {
				return true;
			}
		}
		return false;
	}

	// 检查是否匹配登录用户公共授权路径
	private boolean checkGrtUrls(String servletPath) {

		for (String url : grantedUrls) {
			if (urlMatcher(servletPath, url)) {
				return true;
			}
		}
		return false;
	}

	// 检查是否匹配登录用户授权
	private boolean checkUserGranted(List<AuthorityResources> resourceList,String servletPath) {
		if (null == resourceList)
			return false;

		for (AuthorityResources resource : resourceList) {
			String menuUrl = resource.getMemuUrl();
			if (menuUrl != null && !"".equals(menuUrl) && urlMatcher(servletPath, menuUrl))
				return true;
		}

		return false;
	}

	private boolean urlMatcher(String sourceStr, String targetStr) {
		if (null == sourceStr || null == targetStr)
			return false;

//		if (null == mode || !"controller".equals(mode)) {
//			sourceStr = sourceStr.lastIndexOf("/") > 0 ? sourceStr.substring(0,sourceStr.lastIndexOf("/")) : sourceStr;
//			targetStr = targetStr.lastIndexOf("/") > 0 ? targetStr.substring(0,targetStr.lastIndexOf("/")) : targetStr;
//		} else {
			sourceStr = sourceStr.indexOf("?") > 0 ? sourceStr.substring(0,sourceStr.indexOf("?")) : sourceStr;
			targetStr = targetStr.indexOf("?") > 0 ? targetStr.substring(0,targetStr.indexOf("?")) : targetStr;
//		}
		targetStr = targetStr.replaceAll("\\.\\.\\/", "");
//		if (sourceStr.equals(targetStr))
//			return true;
		if (sourceStr.endsWith(targetStr))
			return true;
		return false;
	}

	private void singletonUrls () {

		initProperty();

		if (null != excludeUrl){
			excludeUrl = excludeUrl +",checkActiveRemainDay.sc";
			excludeUrls = excludeUrl.split(",");
		}
		if (null != grantedUrl)
			grantedUrls = grantedUrl.split(",");

		if (null != controllerModel)
			controllerModels = controllerModel.split(",");

	}

}
