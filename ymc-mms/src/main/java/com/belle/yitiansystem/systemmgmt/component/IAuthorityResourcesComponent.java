package com.belle.yitiansystem.systemmgmt.component;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.belle.infrastructure.tree.BizNode;
import com.belle.infrastructure.tree.ITreeTools;
import com.belle.yitiansystem.systemmgmt.model.pojo.AuthorityResources;
import com.belle.yitiansystem.systemmgmt.model.vo.TreeNodeVo;

public interface IAuthorityResourcesComponent extends ITreeTools {
	
	/**
	 * 构建树结构数据
	 * @param rootVo  根节点
	 * @param authList	资源列表
	 * @param isShowRoot 是否显示根节点
	 * @return
	 */
	public String getTreeJsonDate(HttpServletRequest request,TreeNodeVo rootVo,List<AuthorityResources> authList,boolean isShowRoot);

	/**
	 * 增加树节点
	 * @param parentStruc  父亲节点结构
	 * @param resource		当前节点信息
	 * @return
	 * @throws Exception 
	 */
	public BizNode addTreeNode(String parentStruc, AuthorityResources resource) throws Exception;
	
	/**
	 * 获取所有的父亲节点的结构
	 * @param struc
	 * @return
	 */
	public String [] getParentStruts(String root_struc,String struc); 
	
	/**
	 * 删除节点
	 * @param resource
	 * @return
	 * @throws Exception 
	 */
	public String delChirldTree(AuthorityResources resource) throws Exception;
	
	/**
	 * 修改子节点状态
	 * @param struc
	 * @return
	 * @throws Exception
	 */
	public boolean updateLeaf(String struc,String strucUp) throws Exception;
	

}
