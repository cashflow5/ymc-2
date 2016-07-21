package com.belle.yitiansystem.systemmgmt.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.log.factory.AppLogFactory;
import com.belle.infrastructure.log.model.vo.AppLog;
import com.belle.infrastructure.spring.DataSourceSwitcher;
import com.belle.infrastructure.util.CommonUtil;
import com.belle.infrastructure.util.DateUtil;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.infrastructure.web.controller.BaseController;
import com.belle.infrastructure.web.servlet.CookieUtils;
import com.belle.infrastructure.web.servlet.ServletUtils;
import com.belle.yitiansystem.systemmgmt.common.SystemgmtConstant;
import com.belle.yitiansystem.systemmgmt.model.pojo.AuthorityResources;
import com.belle.yitiansystem.systemmgmt.model.pojo.OperateLog;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUserOperateLog;
import com.belle.yitiansystem.systemmgmt.service.IAuthorityRoleService;
import com.belle.yitiansystem.systemmgmt.service.ISystemmgtOperaterLogService;
import com.belle.yitiansystem.systemmgmt.service.ISystemmgtUserService;
import com.belle.yitiansystem.systemmgmt.util.SysconfigProperties;
import com.yougou.cms.api.ICMSApi;
import com.yougou.common.web.CasConfigValues;
import com.yougou.permission.remote.AppMenuInfo;
import com.yougou.permission.remote.RemoteServiceInterface;
import com.yougou.permission.remote.ResourceRemote;


/**
 * 
 *@author yhb
 * 
 * @version 创建时间：2011-3-30 下午05:49:43
 */
@Controller
@RequestMapping("/yitiansystem/systemmgmt/mainmanage")
public class MainManageController extends BaseController {
	
	AppLog log = AppLogFactory.getLog(MainManageController.class);
	
	private static final String CAS_LOGIN_REDIRECT_URL = "redirect:/cas/setcasuser/login.sc";

	private static final String CAS_LOGIN_FORWARD_URL = "forward:/cas/setcasuser/login.sc";
	
	@Resource
	private ISystemmgtUserService systemmgtUserService;
	
	@Resource
	private IAuthorityRoleService roleService;
	@Resource(name="cmsApiService")
	private ICMSApi cmsApi;
	
	 @Resource
	 private ISystemmgtOperaterLogService logService;
	 
	 @Resource
	 private CasConfigValues casConfigValue;
	
	 @Resource
	 private SysconfigProperties sysconfig;
	 
	 @Resource
	 private RemoteServiceInterface remoteService;
	 
	 private static final String ROOTSTRUCT = "root-17";
	 
	/**
     * 转到首页
     * @return
     */
    @RequestMapping("/tologin")
    public ModelAndView tologin(HttpServletRequest request,Map<String, Object> map){
    	String rootStruc = request.getParameter("root_struc");
    	map.put("rootStruc", rootStruc);
    	if(null !=  request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER)) {
    		return new ModelAndView("redirect:mainFrame.sc");
    	}
    	if(StringUtils.isNotBlank(rootStruc)){
    		return new ModelAndView("yitiansystem/tologin",map);
    	}
    	return new ModelAndView("yitiansystem/tologin",map);
    }
	
	/**
	 * 转到首页
	 * @return
	 */
	@RequestMapping("/toIndex")
	public ModelAndView toIndex(HttpServletRequest request,Map<String, Object> map){
		String rootStruc = request.getParameter("root_struc");
		if(null !=  request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER)) {
			if(StringUtils.isNotBlank(rootStruc)){
				return new ModelAndView("redirect:mainFrame.sc?root_struc="+rootStruc);
			}else{
				return new ModelAndView("redirect:mainFrame.sc");
			}
			
    	}
		 
		//if no logon bms user and cas server enabled
		if ("1".equals(this.casConfigValue.getEnabled())) {
			Subject suject = SecurityUtils.getSubject();// 获取shiro的主体suject
		   	if (suject != null) {
		   		log.info("already cas login=" + suject);
		   		return new ModelAndView(CAS_LOGIN_FORWARD_URL+"?root_struc="+rootStruc);
		   	}
		}
		 
		return new ModelAndView("yitiansystem/login");
	}
	
	@RequestMapping("/tomain")
	public ModelAndView tomain(){
		return new ModelAndView("yitiansystem/Main");
	}

	/**
	 * 登录
	 * @param loginName  用户名称
	 * @param loginPassword	用户密码
	 * @param validateCode	验证码
	 * @param request	
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/login")
	public ModelAndView login(String loginName,String loginPassword,String cookieTime,
	                          HttpServletRequest request,HttpServletResponse response) throws Exception{
	    Map<String,Object> warnMap = new HashMap<String,Object>();
	    
		//if no logon bms user and cas server enabled
		if ("1".equals(this.casConfigValue.getEnabled())) {
			 //if already logon CAS then go to CAS_LOGIN_FORWARD_URL
		   	Assertion assertion = AssertionHolder.getAssertion();
		   	if (assertion != null) {
		   		log.info("already cas login=" + assertion);
		   		return new ModelAndView(CAS_LOGIN_REDIRECT_URL);
		   	}
		}
	    
	    String indexUrl = "yitiansystem/login";
	    //判断用户名不能为空
		if(loginName == null || loginName.equals("")){
			warnMap.put("warnMessagekey", "validator.systemuser.loginName.notempty");
			return new ModelAndView(indexUrl,warnMap);
		}
	    
	    //如果用户已登录  则直接跳过登录过程
	    HttpSession session = request.getSession();
	    SystemmgtUser systemUser = (SystemmgtUser)session.getAttribute(Constant.LOGIN_SYSTEM_USER);
	    if(systemUser != null){
	    	
	    	if(loginName.equals(systemUser.getLoginName())){
	    		
	    		return new ModelAndView("redirect:mainFrame.sc");
	    		
	    	}else{
	    		logoutMethod(request,response);
	    	}
	    	
	    }
		
	    DataSourceSwitcher.setDataSourceSys();
		systemUser = systemmgtUserService.systemUserLogin(loginName, loginPassword);
		DataSourceSwitcher.clearDataSource();
		
		//校验用户是否存在
		if(systemUser == null){
			warnMap.put("warnMessagekey", "validator.systemuser.loginuser.loginNoPass");
            return new ModelAndView(indexUrl,warnMap);
		}
		
		//用户被锁定则不让登录
		if(systemUser.isLockFlag()){
            warnMap.put("warnMessagekey", "validator.systemuser.loginuser.haslocked");
            return new ModelAndView(indexUrl,warnMap);
		}
		
		systemUser.setLoginIp(ServletUtils.getIpAddr(request));   //记录访问者IP地址
		session.setAttribute(Constant.LOGIN_SYSTEM_USER, systemUser);
		
		//设置cookie保存登录信息
		if(cookieTime != null && cookieTime.equals("on")){
	        CookieUtils.addCookie(request, response, Constant.LOGIN_SYSTEM_USER_COOKIE_ID, systemUser.getId(), Constant.LOGIN_COOKIE_TIME);
		}
		this.saveOperateLog(request, 7);
		session.removeAttribute("passwordMatchRulesResult");
		return new ModelAndView("redirect:mainFrame.sc");
	}
	
	
	/**
	 * 登录
	 * @param loginName  用户名称
	 * @param loginPassword	用户密码
	 * @param validateCode	验证码
	 * @param request	
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/checkActiveRemainDay")
	@ResponseBody
	public String checkActiveRemainDay(String loginName,String loginPassword,String newLoginPassword,String validateCode,String cookieTime,
	                          HttpServletRequest request,HttpServletResponse response) throws Exception{
	    
		   HttpSession session = request.getSession();
	  
	  //判断用户名不能为空
		if(loginName == null || loginName.equals("")){
			return "fail";
		}
		//校验验证码是否正确
				String sysValicateCode = (String)session.getAttribute(Constant.LOGIN_VALIDATE_IMAGE);
				
				if (!StringUtils.isNotBlank(validateCode)) {
				
				}
				
				if(!validateCode.equals(sysValicateCode)){
					
		            return "validateCodeFail";
				}	
				
	    //如果用户已登录  则直接跳过登录过程
	 
	    SystemmgtUser systemUser = (SystemmgtUser)session.getAttribute(Constant.LOGIN_SYSTEM_USER);
	   
	    if(systemUser != null){
	    	try {
				if(this.cmsApi.isPwdExpired(systemUser.getId())){
					return "0";
				}
			} catch (Exception e) {
				log.error("调用dubbo服务验证账号是否过期报错", e);
			}
	    	if(loginName.equals(systemUser.getLoginName())){
	    		return ""+systemUser.getActiveRemainDay();
	    	}else{
	    		logoutMethod(request,response);
	    	}
	    	
	    }
		
	    DataSourceSwitcher.setDataSourceSys();
		systemUser = systemmgtUserService.systemUserLogin(loginName, loginPassword);
		DataSourceSwitcher.clearDataSource();
		
		session.setAttribute(Constant.LOGIN_SYSTEM_USER,systemUser);
		
		if(null != systemUser) {
			if(this.cmsApi.isPwdExpired(systemUser.getId())){
	    		return "0";
	    	}
			return ""+systemUser.getActiveRemainDay();
		}else {
			systemUser = systemmgtUserService.systemUserLogin(loginName);
			if(null == systemUser) {
				return "loginNameFail";
			}else{
				return "passwordFail";
			}
			
		}
	}
	
	/**
     * 到修改用户密码
     */
    @RequestMapping("/toUpdateSystemUserPassword")
    public String toUpdateSystemUserPassword(String id,String oldPasswrod ,String closeFlag,ModelMap map) {
    	if(StringUtils.isNotBlank(closeFlag)){
    		map.addAttribute("closeFlag", closeFlag);
    	}
    	map.addAttribute("id", id);
    	map.addAttribute("oldPasswrod", oldPasswrod);
        return "yitiansystem/update_systemuser_passwordWhenLogin";
    }
    
    @RequestMapping("/u_updateSystemUserPassword")
    @ResponseBody
    public String u_updateSystemUserPassword(String id ,String oldPassrod ,HttpServletRequest request, String newPassword,String parentSourcePage,ModelMap map) throws Exception {
    	  HttpSession session = request.getSession();
  	    SystemmgtUser systemUser = (SystemmgtUser)session.getAttribute(Constant.LOGIN_SYSTEM_USER);
  	    if(systemUser != null){
  	    	systemUser.setLoginPassword(CommonUtil.md5(newPassword));
		     systemmgtUserService.updateLoginInfo(systemUser.getId(), systemUser.getLoginName(), newPassword);
		     //添加操作日志
		     SystemmgtUserOperateLog operateLog = new SystemmgtUserOperateLog();
		     operateLog.setUserId(systemUser.getId());
		     operateLog.setOperateIp(getIpAddr(request));
		     operateLog.setOperateRemark("修改用户密码");
		     operateLog.setOperateDate(new Date());
		     operateLog.setOperateAccount(systemUser.getUsername());
		     logService.addUserOperateLog(operateLog,request);
				session.setAttribute(Constant.LOGIN_SYSTEM_USER, systemUser);
				 return "success";
		}
  	    	return "fail";
    }
    

	 /** 
	  * 通过HttpServletRequest返回IP地址 
	  * @param request HttpServletRequest 
	  * @return ip String 
	  * @throws Exception 
	  */  
	 public String getIpAddr(HttpServletRequest request) throws Exception {  
	     String ip = request.getHeader("x-forwarded-for");  
	     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	         ip = request.getHeader("Proxy-Client-IP");  
	     }  
	     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	         ip = request.getHeader("WL-Proxy-Client-IP");  
	     }  
	     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	         ip = request.getHeader("HTTP_CLIENT_IP");  
	     }  
	     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	         ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	     }  
	     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	         ip = request.getRemoteAddr();  
	     }  
	     return ip;  
	 } 
	 
	 
	 
	 
	 
	/**
	 * 系统管理入口
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mainFrame")
	public ModelAndView mainFrame(HttpServletRequest request,String root_struc ,ModelMap map) throws Exception{
		
		HttpSession session = request.getSession();

		SystemmgtUser systemUser = (SystemmgtUser)session.getAttribute(Constant.LOGIN_SYSTEM_USER);
		if(systemUser == null){
		    return new ModelAndView("redirect:toIndex.sc?root_struc"+root_struc);
		}

		//获取登录用户拥有的资源
		List<AuthorityResources> authResourceList = (List<AuthorityResources>)session.getAttribute(Constant.LOGIN_SYSTEM_USER_RESOURCES);
		
		if(authResourceList == null ){
		    //获取用户已拥有的权限资源
			AppMenuInfo menuInfos = remoteService.getMenus(systemUser.getLoginName(), ROOTSTRUCT);//root需要自己去找
			List<ResourceRemote> resourceRemoteList = menuInfos.getAppMenuList();
		 
			authResourceList =  systemmgtUserService.convertResources(resourceRemoteList);
			session.setAttribute(Constant.LOGIN_SYSTEM_USER_RESOURCES, authResourceList);
		
			//获取系统所有的权限资源
			List<ResourceRemote> allRemoteResource = remoteService.getAllMenus();
			List<AuthorityResources> allAuthResourceList = systemmgtUserService.convertResources(allRemoteResource);
			session.setAttribute(Constant.ALL_SYSTEM_RESOURCES, allAuthResourceList);
		}
		
		/*
		//获取所有系统功能模块
		List<AuthorityResources> childItemList = (List<AuthorityResources>)session.getAttribute(Constant.ALL_SYSTEM_MODEL_LIST);
		if(childItemList == null){
//			DataSourceSwitcher.setDataSourceSys();
		    childItemList = authorityResourcesService.getChildAuthorityResources(SystemgmtConstant.MENU_ROOT_STRUC);
		    session.setAttribute(Constant.ALL_SYSTEM_MODEL_LIST, childItemList);
//		    DataSourceSwitcher.clearDataSource();
		}
		
		
		//1.查出数据权限,根据当前系统用户ID级联查询包含数据权限组的用户对象
		DataSourceSwitcher.setDataSourceSys();
		SystemmgtUser user =  systemmgtUserService.getSystemmgtUserJoinPermissionById(systemUser.getId());
		if(user.getPermissionGroup() != null){
			List<PermissionDataObject> orderUserPermissionDataList = permissionGroupService.findOrderUserPermissionByGroupId(user.getPermissionGroup().getId());
			List<PermissionDataObject> commodityUserPermissionDataList = permissionGroupService.findCommodityUserPermissionByGroupId(user.getPermissionGroup().getId());
			session.setAttribute("commodityPermission",commodityUserPermissionDataList );
			session.setAttribute("orderPermission", orderUserPermissionDataList);
		}
		DataSourceSwitcher.clearDataSource();
		*/
		
        map.put("systemUser", systemUser);
        if(root_struc != null && !("null".equals(root_struc))){
        	map.put("root_struc", root_struc);
        }
        
        prepareHost(request, map, session);

        int hours = DateUtil.getHour(DateUtil.getCurrentDateTime());
        map.put("hours", hours);
	    
		return new ModelAndView("yitiansystem/product_list");
	}
	
	
	/**
	 * 获取树节点数据
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/loadResourceDate")
	public String  loadResourceDate(HttpServletRequest request,String root_struc) throws Exception{
		HttpSession session = request.getSession();
		List<AuthorityResources> authResourceList = (List<AuthorityResources>)session.getAttribute(Constant.LOGIN_SYSTEM_USER_RESOURCES);
//		DataSourceSwitcher.setDataSourceSys();
		String jsonDate = systemmgtUserService.buildAuthResourceTree(request,authResourceList,root_struc);
		jsonDate = jsonDate.replaceFirst(SystemgmtConstant.CLOSED_TREE, SystemgmtConstant.OPEN_TREE);  //默认展开第一项
//		DataSourceSwitcher.clearDataSource();
		return jsonDate;
	}
	/**
	 * 注销
	 * @throws Exception 
	 */
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse  response) throws Exception{
		saveOperateLog(request, 8);
		logoutMethod(request,response);
		 
		if ("1".equals(this.casConfigValue.getEnabled())) {
			//do cas logout
			String urlStr = request.getRequestURL().toString();
			
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
				log.info("logout  invalidate  session=" + session.getId());
			}
			
			urlStr = urlStr.substring(0, urlStr.lastIndexOf('/')) + "/toIndex.sc";
			log.info(" cas config=" + casConfigValue.getCasServerUrl() + " logout return=" + urlStr);
			
			 SecurityUtils.getSubject().logout();
			
			return new ModelAndView("redirect:" +  urlStr);
		} else {
			return new ModelAndView("yitiansystem/login");
		}
		 
	}
	
	private void logoutMethod(HttpServletRequest request,HttpServletResponse  response){
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(Constant.LOGIN_SYSTEM_USER);  //登录用户
			session.removeAttribute(Constant.LOGIN_SYSTEM_USER_RESOURCES);  //登录用户资源
			session.removeAttribute(Constant.ALL_SYSTEM_RESOURCES);       //系统所有权限资源
			session.removeAttribute(Constant.ALL_SYSTEM_MODEL_LIST);       //系统所有权限资源
			session.removeAttribute("commodityPermission");
			session.removeAttribute("orderPermission");
			session.setAttribute("noNeedToAutoLogin","noNeedToAutoLogin");
			Cookie cookie = CookieUtils.getCookie(request, Constant.LOGIN_SYSTEM_USER_COOKIE_ID);
			if(cookie != null) {
			    CookieUtils.cancleCookie(response,  Constant.LOGIN_SYSTEM_USER_COOKIE_ID, cookie.getDomain());
			}
		}
	}
	
	
	@RequestMapping("/product_manage_lfbar")
	public ModelAndView manageLfbar(String root_struc,Map<String, Object> map){
	    map.put("root_struc", root_struc);
		return new ModelAndView("yitiansystem/product_manage_lfbar",map);
	}
	
	/**
	 * 没有权限提示页
	 * @return
	 */
	@RequestMapping("/noPrivilege")
	public ModelAndView noPrivilege(){
		return new ModelAndView("noPrivilege");
	}
	
	
	@RequestMapping("/sealnetreview")
	public ModelAndView sealnetreView(){
		return new ModelAndView("yitiansystem/sealnetreview");
	}
	
	/**
	 * 去错误页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/error")
	public ModelAndView error(HttpServletRequest request){
		return new ModelAndView("error","errorMessageException",request.getAttribute("errorMessageException"));
	}
	
	/**
	 * 添加日志信息
	 * @author zhao.my
	 * @param request 
	 * @param role  AuthorityRole 对象，可以传null  修改时不能为空
	 * @param type  操作状态
	 * @throws Exception
	 */
	private void saveOperateLog(HttpServletRequest request,Integer type) throws Exception{
		//获取后台用户登录的信息
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		//获取IP信息
		String ip = GetSessionUtil.getIpAddr(request);
		
		OperateLog operateLog = new OperateLog();
		operateLog.setCreate_time(new Date());
		operateLog.setMod_name("招商系统");
		operateLog.setOperate_ip(ip);
		operateLog.setPortal_id(5);//5表示客服系统
		operateLog.setUser_id(user.getId());
		operateLog.setUser_name(user.getUsername());
		operateLog.setOperate_type(type);//7表示登录 8表示退出 
		if(type == 7){
			operateLog.setOperate_desc("登录");
		}
		if(type == 8){
			operateLog.setOperate_desc("登出");
		}
		
		//保存日志信息
		DataSourceSwitcher.setDataSourceSys();
		roleService.saveOperateLog(operateLog);
		DataSourceSwitcher.clearDataSource();
	}
	
	/**
	 * 准备Host信息
	 * @param request
	 * @param map
	 * @param session
	 */
	private void prepareHost(HttpServletRequest request, ModelMap map, HttpSession session) {
		if (StringUtils.isNotBlank(sysconfig.getVpnhost()) && String.valueOf(request.getRequestURL()).startsWith(sysconfig.getVpnhost())) {
        	session.setAttribute("accessMethod", Constant.VPN_Y);
        	map.put("bmsHost", sysconfig.getBmshostvpn());
        	map.put("omsHost", sysconfig.getOmshostvpn());
        	map.put("mmsHost", sysconfig.getMmshostvpn());
        	map.put("cmsHost", sysconfig.getCmshostvpn());
        	map.put("wmsHost", sysconfig.getWmshostvpn());
        	map.put("outsideHost", sysconfig.getOutsidehostvpn());
        	map.put("commodityHost", sysconfig.getCommodityhostvpn());
        	map.put("fmsHost", sysconfig.getFmshostvpn());
        	map.put("dmsHost", sysconfig.getDmshostvpn());
        	map.put("workorderhost", sysconfig.getWorkorderhostvpn());
        } else {
        	session.setAttribute("accessMethod", Constant.VPN_N);
        	map.put("bmsHost", sysconfig.getBmshost());
        	map.put("omsHost", sysconfig.getOmshost());
        	map.put("mmsHost", sysconfig.getMmshost());
        	map.put("cmsHost", sysconfig.getCmshost());
        	map.put("wmsHost", sysconfig.getWmshost());
        	map.put("outsideHost", sysconfig.getOutsidehost());
        	map.put("commodityHost", sysconfig.getCommodityhost());
        	map.put("fmsHost", sysconfig.getFmshost());
        	map.put("dmsHost", sysconfig.getDmshost());
        	map.put("workorderhost", sysconfig.getWorkorderhost());
        }
    	map.put("jobHost", sysconfig.getJobhost());
	}
}
