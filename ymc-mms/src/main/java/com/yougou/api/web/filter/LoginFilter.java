package com.yougou.api.web.filter;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.web.servlet.ServletUtils;
import com.belle.yitiansystem.systemmgmt.model.pojo.AuthorityResources;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.belle.yitiansystem.systemmgmt.service.ISystemmgtUserService;
import com.yougou.permission.remote.AppMenuInfo;
import com.yougou.permission.remote.RemoteServiceInterface;
import com.yougou.permission.remote.ResourceRemote;
import com.yougou.permission.remote.UserRemote;

public class LoginFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

	@Resource
	private ISystemmgtUserService systemmgtUserService;

	@Resource
	private RemoteServiceInterface remoteService;

	private static final String ROOTSTRUCT = "root-17";

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		String url = StringUtils.trimToEmpty(request.getServletPath());
		SystemmgtUser systemUser = (SystemmgtUser) session.getAttribute(Constant.LOGIN_SYSTEM_USER);
		//错误提示页面 系统JOB跳过
		if (url.indexOf("/sysinfo.sc") != -1 || url.indexOf("job/") != -1) {
			chain.doFilter(request, response);
		} else {
			String errorMsg = null;
			if (systemUser == null) {// 判断是否登录
				logger.info("[LoginFilter]用户未登陆或session过期，登录开始");
				// 获取shiro的主体suject
				Subject suject = SecurityUtils.getSubject();
				if (suject != null && suject.getPrincipal() != null) {
					String loginUserName = suject.getPrincipal().toString();
					logger.info("[LoginFilter]用户未登陆或session过期,PMS登录用户名:{}", loginUserName);
					// 获取用户信息
					UserRemote userRemote = remoteService.getUser(loginUserName);

					// 清除旧属性
					session.removeAttribute(Constant.LOGIN_SYSTEM_USER); // 登录用户
					session.removeAttribute(Constant.LOGIN_SYSTEM_USER_RESOURCES); // 登录用户资源
					session.removeAttribute(Constant.ALL_SYSTEM_RESOURCES); // 系统所有权限资源
					session.removeAttribute(Constant.ALL_SYSTEM_MODEL_LIST);       //系统所有权限资源
					session.removeAttribute("commodityPermission");
					session.removeAttribute("orderPermission");
					session.setAttribute("noNeedToAutoLogin","noNeedToAutoLogin");
					if (userRemote != null) {
						logger.info("[LoginFilter]用户未登陆或session过期,登录用户信息：{}", userRemote);
						if (userRemote.getLocked()) {
							errorMsg = "用户被锁定，请联系系统支持组！";
						} else {
							systemUser = convertUser(userRemote);
							systemUser.setLoginIp(ServletUtils.getIpAddr(request)); // 记录访问者IP地址
							session.setAttribute(Constant.LOGIN_SYSTEM_USER, systemUser);
							loadResource(session, systemUser);
						}
					} else {
						errorMsg = "获取登录用户信息失败,请重试！";
					}
				} else {
					errorMsg = "PMS系统未登陆，请重新登录！";
				}
			} 
			if (errorMsg != null) {	
				logger.error("[LoginFilter]登录失败，失败原因:{},用户信息:{}", errorMsg, systemUser);
				session.setAttribute(Constant.FAIL, errorMsg);
				response.sendRedirect("/mms/yitiansystem/systemmgmt/mainmanage/sysinfo.sc");
			} else {
				chain.doFilter(servletRequest, servletResponse);
			}
		}
	}

	/**
	 * 加载登录用户资源权限
	 * @param session
	 * @param systemUser
	 */
	@SuppressWarnings("unchecked")
	private void loadResource(HttpSession session, SystemmgtUser systemUser) {
		// 获取登录用户拥有的资源
		List<AuthorityResources> authResourceList = (List<AuthorityResources>)session
				.getAttribute(Constant.LOGIN_SYSTEM_USER_RESOURCES);

		if (authResourceList == null) {
			// 获取用户已拥有的权限资源
			AppMenuInfo menuInfos = remoteService.getMenus(systemUser.getLoginName(), ROOTSTRUCT);// root需要自己去找
			List<ResourceRemote> resourceRemoteList = menuInfos.getAppMenuList();

			authResourceList = systemmgtUserService.convertResources(resourceRemoteList);
			session.setAttribute(Constant.LOGIN_SYSTEM_USER_RESOURCES, authResourceList);

			// 获取系统所有的权限资源
			List<ResourceRemote> allRemoteResource = remoteService.getAllMenus();
			List<AuthorityResources> allAuthResourceList = systemmgtUserService.convertResources(allRemoteResource);
			session.setAttribute(Constant.ALL_SYSTEM_RESOURCES, allAuthResourceList);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	/**
	 * 用户对象转换
	 * @param userRemote
	 * @return
	 */
	private SystemmgtUser convertUser(UserRemote userRemote) {
		SystemmgtUser user = null;
		if (userRemote != null) {
			user = new SystemmgtUser();
			user.setId(userRemote.getId());
			user.setCategory(userRemote.getCategory());
			user.setEmail(userRemote.getEmail());
			user.setLevel(userRemote.getLevel());
			user.setLoginName(userRemote.getLogin_name());
			user.setMobilePhone(userRemote.getMobile_phone());
			user.setMsnNum(userRemote.getMsn_num());
			user.setNo(userRemote.getNo());
			user.setOrganizName(userRemote.getOrganiz_name());
			user.setOrganizNo(userRemote.getOrganiz_no());
			user.setQqNum(userRemote.getQq_num());
			user.setSex(userRemote.getSex());
			user.setState(userRemote.getState());
			user.setSupplierCode(userRemote.getSupplier_code());
			user.setTelPhone(userRemote.getTel_phone());
			user.setUsername(userRemote.getUsername());
			user.setWarehouseCode(userRemote.getWarehouse_code());
			user.setLockFlag(userRemote.getLocked());
		}
		return user;
	}	
}
