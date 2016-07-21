package com.belle.yitiansystem.systemmgmt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.orm.basedao.CritMap;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.other.service.ISqlService;
import com.belle.yitiansystem.systemmgmt.dao.IPermissionDataObjectDao;
import com.belle.yitiansystem.systemmgmt.dao.IUserPermissionGroupDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.PermissionDataObject;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.belle.yitiansystem.systemmgmt.model.pojo.UserPermissionGroup;
import com.belle.yitiansystem.systemmgmt.model.vo.PermissionDateVo;
import com.belle.yitiansystem.systemmgmt.service.IUserPermissionGroupService;

/**
 * 数据权限小组
 * @author liyanbin
 *
 */
@Service
public class UserPermissionGroupServiceImpl implements IUserPermissionGroupService{
	@Resource
	private IUserPermissionGroupDao userPermissionGroupDao;
	@Resource
	private IPermissionDataObjectDao permissionDataObjectDao;
//	@Resource 
//	private IUserAuthorityOperateLogDao userAuthorityOperateLogDao;
	@Resource
	private ISqlService sqlService;

	/**
	 * 新增用户权限组
	 * @author liyanbin
	 * @param userPermissionGroup ,permissionDataObject
	 */
	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public String addUserPermissionGroup(PermissionDateVo permissionDateVo)	throws Exception {
		//取出分类数组
		String[] cats = permissionDateVo.getCategorys();
		//取出品牌编号数组
		String [] brandnos = permissionDateVo.getBrands();
		//取出来源数组
		String[] sources = permissionDateVo.getSources();
		//取出店铺数组
		String[] sellers = permissionDateVo.getSellers();
		UserPermissionGroup userPermissionGroup= new UserPermissionGroup();
		userPermissionGroup.setGroupName(permissionDateVo.getGroupName());
		userPermissionGroup.setDeleteFlag(1);
		userPermissionGroup.setGroupState(0);
		userPermissionGroup.setCreateDate(new Date());
		userPermissionGroup.setUpdateDate(new Date());
		userPermissionGroup.setGroupRemark(permissionDateVo.getRemark());
		Set<PermissionDataObject> permissionDataList = new HashSet<PermissionDataObject>();
		int sourcesTemp =0;
		if(sources != null){
			for(int i=0; i<sellers.length; i++){
				if(sellers[i] != null && StringUtils.isNotBlank(sellers[i].trim())){
					String sellersStr = sellers[i].substring(0, sellers[i].length()-1);
					PermissionDataObject permissionDataObject = new PermissionDataObject();
					permissionDataObject.setDateType("1");
					permissionDataObject.setDataName(sources[sourcesTemp]);
					permissionDataObject.setDataValue(sellersStr);
					permissionDataObject.setRemark(permissionDateVo.getRemark());
					permissionDataObject.setCreateDate(new Date());
					permissionDataObject.setDeleteFlag(1);
					permissionDataObject.setUserPermissionGroup(userPermissionGroup);
					permissionDataList.add(permissionDataObject);
					sourcesTemp++;
				}
			}
		}
		int catTemp =0;
		if(brandnos != null){
			for(int i=0; i<brandnos.length; i++){
				if(brandnos[i] != null && StringUtils.isNotBlank(brandnos[i].trim())){
					String brandnoStr = brandnos[i].substring(0, brandnos[i].length()-1);
					PermissionDataObject permissionDataObject = new PermissionDataObject();
					permissionDataObject.setDateType("2");
					permissionDataObject.setDataName(cats[catTemp]);
					permissionDataObject.setDataValue(brandnoStr);
					permissionDataObject.setRemark(permissionDateVo.getRemark());
					permissionDataObject.setCreateDate(new Date());
					permissionDataObject.setDeleteFlag(1);
					permissionDataObject.setUserPermissionGroup(userPermissionGroup);
					permissionDataList.add(permissionDataObject);
					catTemp++;
				}
			}
		}
		userPermissionGroup.setPermissionDataObjects(permissionDataList);
		userPermissionGroupDao.merge(userPermissionGroup);
		return "success";
	}
	
	/**
	 * 删除小组
	 * @author li.sk
	 * @param userPermissionGroup ,permissionDataObject
	 */
	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public String deleteUserPermissionGroup(String  userPermissionGroupIds)throws Exception {
		if(userPermissionGroupIds == null){
			throw new Exception("用户小组数据不可能为空！");
		}
		
		String[] userPermissionGroupIdStr = userPermissionGroupIds.split(";");
		if(userPermissionGroupIdStr != null)
		
		for(int i=0; i<userPermissionGroupIdStr.length;i++){
			
			//更加小组的状志为0
			UserPermissionGroup  group = userPermissionGroupDao.getById(userPermissionGroupIdStr[i]);
			if(group != null){
				group.setDeleteFlag(0);
				userPermissionGroupDao.save(group);
			}else{
				throw new Exception("查询不到该小组！");
			}
			
			//查询资源
			CritMap critMap = new CritMap();
			critMap.addFech("permissionDataObject");
			critMap.addEqual("deleteFlag", 1);
			critMap.addEqual("user_group_id", userPermissionGroupIdStr[i]);
			List<PermissionDataObject>  dataList = permissionDataObjectDao.findByCritMap(critMap);
		
			if(dataList != null && dataList.size() >0){
				//更改资源的状态为0:删除
				for(PermissionDataObject data:dataList)
				{
					data.setDeleteFlag(0);
					permissionDataObjectDao.save(data);
				}
			}
			
		}

			
		return "success";
	}
	
	

	/**
	 * 删除小组
	 * @author li.sk
	 * @param userPermissionGroup ,permissionDataObject
	 */
	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public String deleteUserPermissionById(String  groupId)throws Exception {
		if(groupId == null){
			throw new Exception("用户小组数据不可能为空！");
		}
		
		if(groupId != null){
		
			
			//更加小组的状志为0
			UserPermissionGroup  group = userPermissionGroupDao.getById(groupId);
			if(group == null){
				throw new Exception("查询不到该小组！");
			}
			
			
			Set<PermissionDataObject> dataList = group.getPermissionDataObjects();
			
			if(dataList != null && dataList.size() >0){
				//更改资源的状态为0:删除
				for(PermissionDataObject data:dataList)
				{
					data.setDeleteFlag(0);
					permissionDataObjectDao.save(data);
				}
			}
			
			group.setDeleteFlag(0);
			userPermissionGroupDao.save(group);
			
		}

			
		return "success";
	}
	
	
	
	/**
	 * 根据ID查询用户小组的信息小组
	 * @author li.sk
	 * @param userPermissionGroupId 
	 */
	public UserPermissionGroup findUserPermissionGroupById(String userPermissionGroupId){
		return userPermissionGroupDao.getById(userPermissionGroupId);
	}

	/**
	 * 根据ID查询用户小组的资源
	 * @author li.sk
	 * @param userPermissionGroupId 
	 */
	public List<PermissionDataObject> findPermissionDataObject(String userPermissionGroupId){
		//查询资源
		CritMap critMap = new CritMap();
		critMap.addFech("permissionDataObject");
		critMap.addEqual("deleteFlag", 1);
		critMap.addEqual("userPermissionGroup.id", userPermissionGroupId);
		List<PermissionDataObject>  dataList = permissionDataObjectDao.findByCritMap(critMap);
		return dataList;
	}
	
	

	/**
	 * 查询用户权限小组集合
	 * @author wang.m
	 * @Date 2011-12-20
	 */
	public List<UserPermissionGroup> findUserPermissionGroupList(){
		CritMap critMap = new CritMap();
		critMap.addEqual("deleteFlag", 1);
		critMap.addFech("systemmgtUsers");
		List<UserPermissionGroup>  dataList = userPermissionGroupDao.findByCritMap(critMap);
		return dataList;
	}
	/**
	 * 删除用户权限小组对象数据
	 * @author wang.m
	 * @throws Exception 
	 * @throws Exception 
	 * @Date 2011-12-20
	 */
	public void deleteUserPermissionGroupById(String id) throws Exception {
		userPermissionGroupDao.deleteUserPermissionGroupById(id);
	}
	/**
	 * 根据用户组id查找用户数据权限
	 * @param groupId
	 * @date 2011-12-22 
	 */
	@Override
	public List<PermissionDataObject> findOrderUserPermissionByGroupId(String groupId)
	{

		
			//查询资源
			CritMap critMap = new CritMap();
			critMap.addFech("permissionDataObject");
			critMap.addEqual("deleteFlag", 1);
			critMap.addEqual("userPermissionGroup.id", groupId);
			critMap.addEqual("dateType", "1"); 	//订单
			List<PermissionDataObject>  dataList = permissionDataObjectDao.findByCritMap(critMap);
		
		
		return dataList;
	}
	/**
	 * 根据用户组id查找用户商品数据权限
	 * @param groupId
	 * @date 2011-12-22 
	 */
	@Override
	public List<PermissionDataObject> findCommodityUserPermissionByGroupId(String groupId)
	{
		//查询资源
		CritMap critMap = new CritMap();
		critMap.addFech("permissionDataObject");
		critMap.addEqual("deleteFlag", 1);
		critMap.addEqual("userPermissionGroup.id", groupId);
		critMap.addEqual("dateType", "2"); 	//订单
		List<PermissionDataObject>  dataList = permissionDataObjectDao.findByCritMap(critMap);
		return dataList;
	}
	
	/**
	 * 一级分类名称和struct_name对应
	 * 
	 */
	public Map<String,Integer> getCatStructName()
	{
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("女鞋",10);
		map.put("运动",11);
		map.put("男鞋",12);
		map.put("童鞋",13);
		map.put("包",14);
		map.put("户外休闲",15);
		map.put("童装",16);
		return map;
	}
	
	/**
	 *记录数据权限小组删除日志信息
	 *@author wang.m
	 *@Date 2012-10-31
	 * 
	 */
	@Transactional
	public void saveUserAuthorityOperateLog(String userGroupId,String remark,HttpServletRequest req){
		try {
			SystemmgtUser user = GetSessionUtil.getSystemUser(req);
			String userName = "";
			String loginName = "";
			if (user != null) {
				userName = user.getUsername();//用户姓名
				loginName = user.getLoginName();//登录账户
			}
			/*UserAuthorityOperateLog userAuthority=new UserAuthorityOperateLog();
			Date da = new Date();
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str = sim.format(da);
			userAuthority.setCreateDate(str);//创建时间
			//根据用户权限小组Id查询小组名称
			UserPermissionGroup userGgroup=userPermissionGroupDao.getById(userGroupId);
			if(StringUtils.isNotBlank(remark)){
				userAuthority.setOperateComment(remark);
			}else{
				userAuthority.setOperateComment("用户权限小组:"+userGgroup.getGroupName()+",被"+loginName+"删除了");
			}
			
			userAuthority.setUserAccount(loginName);
			userAuthority.setUserGroupId(userGroupId);//数据权限组Id
			InetAddress addr = InetAddress.getLocalHost();
			String UserIp=addr.getHostAddress().toString();//获得本机IP
			userAuthority.setUserIp(UserIp);
			userAuthority.setUserName(userName);
			userAuthorityOperateLogDao.save(userAuthority);*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 查看数据权限操作日志信息
	 * @author wang.m
	 * @Date 2012-10-30
	 * @param id
	 * @return
	 */
	public PageFinder<Map<String, Object>> queryUserAuthorityOperateLogList(Query query,String id){
		String sql="SELECT id,user_account,user_name,user_ip,create_date,operate_comment,user_group_id "+
					" FROM tbl_user_authority_operate_log WHERE 1=1 ";
		StringBuffer sqlWhere = new StringBuffer("");
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(id)) {
			sqlWhere.append(" and user_group_id=?");
			params.add(id.trim());
		}
		String orderBy=" create_date desc ";
		return sqlService
				.getObjectsBySql(sql, query, sqlWhere, params, orderBy);
	}
	/**
	 * 根据用户权限小组ID查询具体吧订单和商品的权限
	 * @author wang.m
	 * @Date 2012-11-3
	 * @param groupId 用户权限小组ID
	 * @param dateType 数据分类   1 订单  2商品
	 * @return
	 */
	public List<PermissionDataObject> getPermissionDataObjectListByUserGroupId(String groupId,String dateType){
		CritMap critMap = new CritMap();
		critMap.addFech("permissionDataObject");
		critMap.addEqual("deleteFlag", 1);
		critMap.addEqual("userPermissionGroup.id", groupId);
		critMap.addEqual("dateType", dateType); //分类   1 订单  2商品
		critMap.addAsc("dataName");
		List<PermissionDataObject>  dataList = permissionDataObjectDao.findByCritMap(critMap);
		return dataList;
	}
	
	/**
	 * 根据表明和id查询名称
	 * @author wang.m
	 * @Date 2012-11-3
	 * @param id 主键Id
	 * @param tableName 表明
	 * @return
	 */
	public String[] getNameById(Query query,String tableName,String id){
		String[] str=new String[2];
		String sql="SELECT id,name FROM "+tableName+" WHERE id=? ";
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(id)) {
			params.add(id.trim());
		}
		Map<String,Object> map=sqlService.getDataBySql(sql, null, params);
		if(map!=null && map.size()>0){
			str[0]=map.get("id")!=null?map.get("id").toString():"";
			str[1]=map.get("name")!=null?map.get("name").toString():"";
		}
		return str;
	}
	/**
	 * 根据表名和no查询名称
	* @Title: getNameByNo 
	  creator zhangxb
	  create time 上午10:40:47
	* @Description: TODO 
	* @param query
	* @param tableName
	* @param no
	* @return
	 */
	public String[] getNameByNo(Query query,String tableName,String no){
		String[] str=new String[2];
		String sql="SELECT no,name FROM "+tableName+" WHERE no=? ";
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(no)) {
			params.add(no.trim());
		}
		Map<String,Object> map=sqlService.getDataBySql(sql, null, params);
		if(map!=null && map.size()>0){
			str[0]=map.get("no")!=null?map.get("no").toString():"";
			str[1]=map.get("name")!=null?map.get("name").toString():"";
		}
		return str;
	}
}
