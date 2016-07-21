package com.belle.yitiansystem.systemmgmt.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.systemmgmt.model.pojo.PermissionDataObject;
import com.belle.yitiansystem.systemmgmt.model.pojo.UserPermissionGroup;
import com.belle.yitiansystem.systemmgmt.model.vo.PermissionDateVo;

/**
 * 数据权限小组
 * @author user
 *
 */
public interface IUserPermissionGroupService {
	/**
	 * 添加数据权限小组
	 * @param UserPermissionGroup
	 */
	public String addUserPermissionGroup(PermissionDateVo permissionDateVo)  throws Exception ;

	/**
	 * 删除小组
	 * @author li.sk
	 * @param userPermissionGroupId 
	 */
	public String deleteUserPermissionGroup(String userPermissionGroupId)throws Exception;

	/**
	 * 根据ID查询用户小组的信息小组
	 * @author li.sk
	 * @param userPermissionGroupId 
	 */
	public UserPermissionGroup findUserPermissionGroupById(String userPermissionGroupId); 

	/**
	 * 根据ID查询用户小组的资源
	 * @author li.sk
	 * @param userPermissionGroupId 
	 */
	public List<PermissionDataObject>  findPermissionDataObject(String userPermissionGroupId);

	/**
	 * 删除
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public String deleteUserPermissionById(String  groupId)throws Exception ;

	/**
	 * 查询用户权限小组集合
	 * @author wang.m
	 * @Date 2011-12-20
	 */
	public List<UserPermissionGroup> findUserPermissionGroupList();
	
	/**
	 * 根据用户组id查找用户订单数据权限
	 * @param groupId
	 * @date 2011-12-22 
	 */
	public List<PermissionDataObject> findOrderUserPermissionByGroupId(String groupId);

	
	/**
	 * 根据用户组id查找用户商品数据权限
	 * @param groupId
	 * @date 2011-12-22 
	 */
	public List<PermissionDataObject> findCommodityUserPermissionByGroupId(String groupId);
	/**
	 * 删除用户权限小组对象数据
	 * @author wang.m
	 * @Date 2011-12-20
	 */
	public void deleteUserPermissionGroupById(String id)throws Exception ;
	
	/**
	 *记录数据权限小组删除日志信息
	 *@author wang.m
	 *@Date 2012-10-31
	 * 
	 */
	public void saveUserAuthorityOperateLog(String userGroupId,String remark,HttpServletRequest req);
	
	/**
	 * 查看数据权限操作日志信息
	 * @author wang.m
	 * @Date 2012-10-30
	 * @param id
	 * @return
	 */
	public PageFinder<Map<String, Object>> queryUserAuthorityOperateLogList(Query query,String id);
}
