package com.belle.yitiansystem.systemmgmt.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.systemmgmt.model.pojo.AuthorityRole;
import com.belle.yitiansystem.systemmgmt.model.pojo.OperateLog;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;

/**
 * 
 *@author yhb
 * 
 * @version 创建时间：2011-3-30 下午02:44:41
 */
public interface IAuthorityRoleService {
	
	/**
	 * 增加角色
	 * @return
	 */
	public String addAuthorityRole(AuthorityRole authorityRole)  throws Exception ;
	
	/**
	 * 根据ID查询角色
	 * @param id
	 * @return
	 */
	public AuthorityRole getAuthorityRoleById(String id);
	
	/**
	 * 翻页查询
	 * @param systemmgtuser 查询条件
	 * @param query			翻页条件
	 * @return
	 */
	public PageFinder<AuthorityRole> pageQueryAuthorityRole(AuthorityRole authorityRole,String menuName,Query query);
	
	/**
	 * 获取所有角色
	 * @return
	 */
	public List<AuthorityRole> getAllAuthorityRole();
	
	
	/**
	 * 修改角色基本信息
	 * @param Menu
	 * @return
	 * @throws Exception 
	 */
	public String updateAuthorityRole(AuthorityRole authorityRole) throws Exception;
	
	/**
	 * 给角色分配资源
	 * @param roleid 角色id
	 * @param resIdArry 角色id
	 * @return
	 * @throws Exception
	 */
	public String allotAuthorityRes(String roleid,String [] resArry,String ip,SystemmgtUser user) throws Exception;
	
	
	/**
	 * 删除角色
	 * @param roleid
	 * @return
	 */
	public String removeAuthorityRole(String roleid)  throws Exception;

	/**
	 * 获取用户不拥有的角色
	 * @param userid
	 * @return
	 */
	public List<AuthorityRole> getnoAllotAuthorityRole(String userid);
	

	/**
	 * 获取角色拥有的资源
	 * @param id
	 * @return
	 */
	public String getResourceTreeByRoleId(HttpServletRequest request,String id);
	
	/**
	 * 添加操作日志信息(审计)
	 * @author zhao.my
	 * @param operateLog
	 * @throws Exception 
	 */
	public OperateLog saveOperateLog(OperateLog operateLog) throws Exception;
	

}
