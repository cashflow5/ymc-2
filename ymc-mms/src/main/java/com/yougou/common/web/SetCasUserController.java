/*
 * Copyright 2013 yougou.com All right reserved. This software is the
 * confidential and proprietary information of yougou.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with yougou.com.
 */
package com.yougou.common.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.spring.DataSourceSwitcher;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.infrastructure.web.servlet.ServletUtils;
import com.belle.yitiansystem.systemmgmt.model.pojo.OperateLog;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.belle.yitiansystem.systemmgmt.service.IAuthorityRoleService;
import com.belle.yitiansystem.systemmgmt.service.ISystemmgtUserService;
import com.yougou.permission.remote.RemoteServiceInterface;
import com.yougou.permission.remote.UserRemote;

/**
 * SetCasUserController类用于将CAS的认证信息初始化到BMS的session中：
 * key为Constant.LOGIN_SYSTEM_USER
 * 
 * @author hu.jp
 * @version 1.00 2013-03-12
 * 
 */
@Controller
@RequestMapping("/cas/setcasuser")
public class SetCasUserController {

	private static final Logger logger = Logger
			.getLogger(SetCasUserController.class);

	private static final String INDEX_URL = "yitiansystem/login";
	
	private static final String INFO_URL = "yitiansystem/info";

	private static final String LOGIN_SUCCESS_REDIRECT_URL = "redirect:/yitiansystem/systemmgmt/mainmanage/mainFrame.sc";

	@Resource
	private ISystemmgtUserService systemmgtUserService;

	@Resource
	private IAuthorityRoleService roleService;
	 
	@Resource
	private RemoteServiceInterface remoteService;
	
	 private static final String ROOTSTRUCT = "root-17";

	private SystemmgtUser convertUser(UserRemote userRemote){
		SystemmgtUser user = null;
		if(userRemote != null){
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
	
	
	/**
	 * 登录
	 * 
	 * @param loginName
	 *            用户名称
	 * @param loginPassword
	 *            用户密码
	 * @param validateCode
	 *            验证码
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public ModelAndView login(String loginName, String loginPassword,
			String cookieTime, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		SystemmgtUser loginUser = (SystemmgtUser) session.getAttribute(Constant.LOGIN_SYSTEM_USER);

		Subject suject = SecurityUtils.getSubject();// 获取shiro的主体suject

		if (suject == null || suject.getPrincipal() == null) {
			loginUser = null;
			session.removeAttribute(Constant.LOGIN_SYSTEM_USER);
			logger.error("没有从PMS登陆");
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("msg", "没有从PMS登陆，请联系技术支持组！");
			return new ModelAndView(INFO_URL, map);
		} else {
			String loginUserName = suject.getPrincipal().toString();
			logger.info("PMS user=" + loginUserName);
			if (loginUser == null || !loginUser.getLoginName().equals(loginUserName)) {
				UserRemote userRemote = remoteService.getUser(loginUserName);
				 
				// clear old attribute
				session.removeAttribute(Constant.LOGIN_SYSTEM_USER); // 登录用户
				session.removeAttribute(Constant.LOGIN_SYSTEM_USER_RESOURCES); // 登录用户资源
				session.removeAttribute(Constant.ALL_SYSTEM_RESOURCES); // 系统所有权限资源
				session.removeAttribute(Constant.ALL_SYSTEM_MODEL_LIST); // 系统所有权限资源
				session.removeAttribute("commodityPermission");
				session.removeAttribute("orderPermission");

				if (userRemote != null) {
					loginUser = convertUser(userRemote);
					logger.info("set BMS cas assertion=" + loginUserName);
					session.setAttribute(Constant.LOGIN_SYSTEM_USER, loginUser);
					return doBmsLogin(loginUser, session, request, response);
				} else {
					// error no invalidate bms user
					logger.error("not found BMS user=" + loginUserName);
					session.invalidate();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("msg", "从PMS查找不到这个用户，请联系技术支持组！");
					return new ModelAndView(INFO_URL, map);
				}
			} else {
				logger.info("cas login BMS user=" + loginUserName);
				return new ModelAndView(LOGIN_SUCCESS_REDIRECT_URL);
			}
		}
	}

	private ModelAndView doBmsLogin(SystemmgtUser systemUser,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> warnMap = new HashMap<String, Object>();
//		String rootStruc = request.getParameter("root_struc");
		// 用户被锁定则不让登录
		if (systemUser.isLockFlag()) {
			warnMap.put("msg", "用户被锁定，禁止登录，请联系技术支持组！");
			return new ModelAndView(INFO_URL, warnMap);
		}
		systemUser.setLoginIp(ServletUtils.getIpAddr(request)); // 记录访问者IP地址
		session.setAttribute(Constant.LOGIN_SYSTEM_USER, systemUser);
 
		saveOperateLog(request, 7);
		session.removeAttribute("passwordMatchRulesResult");
		return new ModelAndView(LOGIN_SUCCESS_REDIRECT_URL + "?root_struc=" + ROOTSTRUCT);
	 
	}

	/**
	 * 添加日志信息
	 * 
	 * @author zhao.my
	 * @param request
	 * @param role
	 *            AuthorityRole 对象，可以传null 修改时不能为空
	 * @param type
	 *            操作状态
	 * @throws Exception
	 */
	private void saveOperateLog(HttpServletRequest request, Integer type)
			throws Exception {
		// 获取后台用户登录的信息
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		// 获取IP信息
		String ip = GetSessionUtil.getIpAddr(request);

		OperateLog operateLog = new OperateLog();
		operateLog.setCreate_time(new Date());
		operateLog.setMod_name("招商后台");
		operateLog.setOperate_ip(ip);
		operateLog.setPortal_id(5);// 5表示客服系统
		operateLog.setUser_id(user.getId());
		operateLog.setUser_name(user.getUsername());
		operateLog.setOperate_type(type);// 7表示登录 8表示退出
		if (type == 7) {
			operateLog.setOperate_desc("登录");
		}
		if (type == 8) {
			operateLog.setOperate_desc("登出");
		}

		// 保存日志信息
		DataSourceSwitcher.setDataSourceSys();
		roleService.saveOperateLog(operateLog);
		DataSourceSwitcher.clearDataSource();
	}

}
