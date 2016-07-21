package com.belle.yitiansystem.systemmgmt.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.systemmgmt.model.pojo.AuthorityResources;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.yougou.permission.remote.ResourceRemote;

/**
 * 
 *@author yhb
 * 
 * @version 创建时间：2011-3-30 下午02:44:41
 */
public interface ISystemmgtUserService {
	
	public List<AuthorityResources> convertResources(List<ResourceRemote> resourceRemoteList);
	
	/**
	 * 增加系统用户
	 * @return
	 */
	public String addSystemmgtUser(SystemmgtUser systemmgtUser,HttpServletRequest req)  throws Exception ;
	
	/**
	 * 根据ID查询系统用户
	 * @param id
	 * @return
	 */
	public SystemmgtUser getSystemmgtUserById(String id);
	
	/**
	 * 根据ID查询系统用户 级联权限小组
	 * @author wang.M
	 * @param id
	 * @return
	 */
	public SystemmgtUser getSystemmgtUserJoinPermissionById(String id);
	/**
	 * 根据ID查询系统用户 级联查询用户角色
	 * @param id
	 * @return
	 */
	public SystemmgtUser getSystemmgtUserJoinRoleById(String id);
	
	/**
	 * 获取所有系统用户
	 * @return
	 */
	public List<SystemmgtUser> getAllSystemmgtUser();
	
	/**
	 * 根据登录用户名查找
	 * @param loginName
	 * @return
	 */
	public List<SystemmgtUser> findSystemByLoginName(String loginName);
	
	
	   /**
     * 根据组织机构编号获取所有系统用户
     * @return
     */
    public List<SystemmgtUser> getAllSystemmgtUserByOrganizNo(String organizNo) throws Exception;
	
	/**
	 * 翻页查询
	 * @param systemmgtuser 查询条件
	 * @param query			翻页条件
	 * @return
	 */
	public PageFinder<SystemmgtUser> pageQuerySystemmgtUser(SystemmgtUser systemmgtuser,String roleName,String menuName,Query query);
	/**
	 * 修改系统用户基本信息
	 * @param Menu
	 * @return
	 * @throws Exception 
	 */
	public String updateSystemmgtUser(SystemmgtUser systemmgtUser,HttpServletRequest req) throws Exception;
	
	/**
	 * 给系统用户分配角色
	 * @param userid 系统用户id
	 * @param roleIdArry 角色ID集合
	 * @return
	 * @throws Exception
	 */
	public String allotAuthorityRole(String userid,String [] roleIdArry) throws Exception;
	
	
	/**
	 * 删除系统用户
	 * @param roleid
	 * @return
	 */
	public String removeSystemmgtUser(String roleid,HttpServletRequest req)  throws Exception;
	
	/**
	 * 系统用户登录
	 * @param loginName 登录用户名
	 * @param passWord	登录密码
	 * @return
	 */
	public SystemmgtUser systemUserLogin(String loginName,String passWord);
	
	
	/**
	 * 系统用户登录
	 * @param loginName 登录用户名
	 * @param passWord	登录密码
	 * @return
	 */
	public SystemmgtUser systemUserLogin(String loginName);
	
	
	/**
	 * 修改系统用户登录信息
	 * @param loginName 登录用户名
	 * @param passWord	登录密码
	 * @return
	 */
	public SystemmgtUser updateLoginInfo(String userid,String loginName,String passWord);
	
	/**
	 * 修改用户账户状态
	 * @param userid  用户ID
	 * @param state   用户状态
	 * @return
	 * @throws Exception 
	 */
	public SystemmgtUser updateSystemUserState(String userid , String state) throws Exception;
	
	/**
	 * 获取用户拥有的菜单资源(不包括小功能点)
	 * @param systemmgtUser 登录用户
	 * @return
	 * @throws Exception 
	 */
	public List<AuthorityResources> getSystemUserAllowResources(SystemmgtUser systemmgtUser) throws Exception;

	/**
	 * 创建拥有的权限树
	 * @param authResourceList
	 * @return
	 * @throws Exception 
	 */
	public String buildAuthResourceTree(HttpServletRequest request,List<AuthorityResources> authResourceList,String root_struc) throws Exception;
	

	/**
	  * 供应商登录验证
	  * @param loginname
	  * @param password
	  * @return
	  * @throws Exception
	  */
	 public SystemmgtUser supplierUserLogin(String loginname,String password) throws Exception;
}
