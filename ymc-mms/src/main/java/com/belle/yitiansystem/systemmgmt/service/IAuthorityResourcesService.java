package com.belle.yitiansystem.systemmgmt.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.belle.infrastructure.tree.BizNode;
import com.belle.yitiansystem.systemmgmt.model.pojo.AuthorityResources;

/**
 * 
 *@author yhb
 * 
 * @version 创建时间：2011-3-30 下午02:44:41
 */
public interface IAuthorityResourcesService {
	
	
	/**
	 * 增加资源
	 * @return
	 */
	public String addAuthorityResources(AuthorityResources authorityResources)  throws Exception ;
	
	/**
	 * 增加菜单类型的资源
	 * @param parentStruc 父节点结构
	 * @param treeNode	  当前节点基本信息
	 * @return
	 * @throws Exception
	 */
	public BizNode addAuthorityResources(String parentid,AuthorityResources resources)throws Exception;
	
	/**
	 * 根据ID查询资源
	 * @param id
	 * @return
	 */
	public AuthorityResources getAuthorityResourcesById(String id);
	
	/**
	 * 获取所有资源
	 * @return
	 */
	public List<AuthorityResources> getAllAuthorityResources();
	
	 /**
     * 获取子菜单
     * @return
     */
    public List<AuthorityResources> getChildAuthorityResources(String rootStruc);
	
	/**
	 * 根据资源类型获取资源
	 * @return
	 */
	public List<AuthorityResources> getAuthorityResourcesByType(String type);
	
	 /**
     * 获取菜单资源树 (type必须为菜单类型)
     * @return
     */
    public String getResourceTreeByType(HttpServletRequest request,String type);
	
	/**
	 * 修改资源
	 * @param Menu
	 * @return
	 * @throws Exception 
	 */
	public BizNode updateAuthorityResources(AuthorityResources authorityResources) throws Exception;
	
	
	/**
	 * 删除节点
	 * @param funsetId
	 * @return
	 */
	public String removeAuthorityResources(String resourceid)  throws Exception;


}
