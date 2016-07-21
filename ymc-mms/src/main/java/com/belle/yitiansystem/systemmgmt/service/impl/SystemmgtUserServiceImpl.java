package com.belle.yitiansystem.systemmgmt.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.orm.basedao.CritMap;
import com.belle.infrastructure.orm.basedao.CriteriaAdapter;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.util.CommonUtil;
import com.belle.infrastructure.util.DateUtil;
import com.belle.infrastructure.util.JDBCUtils;
import com.belle.yitiansystem.systemmgmt.common.SystemgmtConstant;
import com.belle.yitiansystem.systemmgmt.component.IAuthorityResourcesComponent;
import com.belle.yitiansystem.systemmgmt.dao.IAuthorityResourcesDao;
import com.belle.yitiansystem.systemmgmt.dao.ISystemmgtUserDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.AuthorityResources;
import com.belle.yitiansystem.systemmgmt.model.pojo.AuthorityRole;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.belle.yitiansystem.systemmgmt.model.pojo.UserPermissionGroup;
import com.belle.yitiansystem.systemmgmt.model.vo.TreeNodeVo;
import com.belle.yitiansystem.systemmgmt.service.IAuthorityRoleService;
import com.belle.yitiansystem.systemmgmt.service.ISystemmgtUserService;
import com.belle.yitiansystem.systemmgmt.service.IUserPermissionGroupService;
import com.yougou.permission.remote.RemoteServiceInterface;
import com.yougou.permission.remote.ResourceRemote;

import edu.emory.mathcs.backport.java.util.Collections;

@Service
public class SystemmgtUserServiceImpl implements ISystemmgtUserService{

	@Resource
	private ISystemmgtUserDao userDao;
	
	@Resource
	private IAuthorityRoleService roleService;   
	
	@Resource
	private IAuthorityResourcesDao authorityResourcesDao;
	
	@Resource
	IUserPermissionGroupService userPermissionGroupService;
	
	@Resource
	private IAuthorityResourcesComponent authorityResourcesComponent;
	
	@Resource
	private RemoteServiceInterface remoteService;
	
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public String addSystemmgtUser(SystemmgtUser aystemmgtUser,HttpServletRequest req)throws Exception {
		String passWord = CommonUtil.md5(aystemmgtUser.getLoginPassword()); //md5加密
		aystemmgtUser.setState(SystemgmtConstant.UNLOCK);
		aystemmgtUser.setLoginPassword(passWord);
		UserPermissionGroup userPermissionGroup=null;
		if(null!=aystemmgtUser.getPermissionGroup()&&null!=aystemmgtUser.getPermissionGroup().getId()){
		   userPermissionGroup=userPermissionGroupService.findUserPermissionGroupById(aystemmgtUser.getPermissionGroup().getId());
		}
		//添加数据权限小组对象
		if(null!=userPermissionGroup){
		  aystemmgtUser.setPermissionGroup(userPermissionGroup);
		  //记录用户权限组日志信息  
		  String remark="用户权限小组:"+userPermissionGroup.getGroupName()+",添加新的用户成员："+aystemmgtUser.getLoginName();//添加内容
		  userPermissionGroupService.saveUserAuthorityOperateLog(userPermissionGroup.getId(), remark, req);
		}
		
		return (String)userDao.save(aystemmgtUser);
	}

	
	public SystemmgtUser getSystemmgtUserById(String id) {
		return userDao.getSystemmgtUserJoinPermissionById(id);
	}
	
	public SystemmgtUser getSystemmgtUserJoinPermissionById(String id) {
		return userDao.getSystemmgtUserJoinPermissionById(id);
	}
	
	public SystemmgtUser getSystemmgtUserJoinRoleById(String id){
		return userDao.getSystemmgtUserJoinRoleById(id);
	}

	public List<SystemmgtUser> getAllSystemmgtUser() {
		return userDao.getAll("id",true);
	}

	@Transactional
	public String updateSystemmgtUser(SystemmgtUser systemmgtUser,HttpServletRequest req)
			throws Exception {
		SystemmgtUser user = getSystemmgtUserById(systemmgtUser.getId());
		//判断是否修改了数据权限小组
		if(systemmgtUser.getPermissionGroup()!=null && user.getPermissionGroup()!=null){
			if(systemmgtUser.getPermissionGroup().getId()!=null && user.getPermissionGroup().getId()!=null){
				if(!systemmgtUser.getPermissionGroup().getId().equals(user.getPermissionGroup().getId())){
					 //如果修改了就添加日志
					 //记录用户权限组日志信息  
					 String remark="用户成员："+user.getLoginName()+",被移除该用户权限小组";//删除内容
					 userPermissionGroupService.saveUserAuthorityOperateLog(user.getPermissionGroup().getId(), remark, req);
				     //记录用户权限组日志信息  
					 String remark1="该用户权限小组添加新的用户成员："+systemmgtUser.getLoginName();//添加内容
					 userPermissionGroupService.saveUserAuthorityOperateLog(systemmgtUser.getPermissionGroup().getId(), remark1, req);
				}
			}
		}
		
//		user.setCategory(aystemmgtUser.getCategory());
		user.setLoginName(systemmgtUser.getLoginName());
		user.setEmail(systemmgtUser.getEmail());
		user.setMobilePhone(systemmgtUser.getMobilePhone());
		user.setMsnNum(systemmgtUser.getMsnNum());
		user.setQqNum(systemmgtUser.getQqNum());
		user.setOrganizName(systemmgtUser.getOrganizName());
		user.setOrganizNo(systemmgtUser.getOrganizNo());
		user.setSex(systemmgtUser.getSex());
		user.setTelPhone(systemmgtUser.getTelPhone());
		user.setUsername(systemmgtUser.getUsername());
		user.setWarehouseCode(systemmgtUser.getWarehouseCode());
		user.setGiftCardPermission(systemmgtUser.getGiftCardPermission());
		if(null !=systemmgtUser.getLoginPassword() && !"".equals(systemmgtUser.getLoginPassword().trim())) {
			user.setLoginPassword(systemmgtUser.getLoginPassword());
		}
		
		UserPermissionGroup userPermissionGroup=null;
		if(null!=systemmgtUser.getPermissionGroup()&&StringUtils.isNotBlank(systemmgtUser.getPermissionGroup().getId())){
			userPermissionGroup = userPermissionGroupService.findUserPermissionGroupById(systemmgtUser.getPermissionGroup().getId());
		}
		//添加数据权限小组对象
		if(null!=userPermissionGroup){
			user.setPermissionGroup(userPermissionGroup);
		}else{
			user.setPermissionGroup(null);
		}
		userDao.merge(user);
		return user.getId();
	}

	@Transactional
	public String allotAuthorityRole(String roleid, String[] roleIdArry)
			throws Exception {
		SystemmgtUser role = getSystemmgtUserById(roleid);   //获取角色
		Set<AuthorityRole> roleSet = new HashSet<AuthorityRole>();
		//获取当前资源信息
		for (String arryStr : roleIdArry) {
		    roleSet.add(roleService.getAuthorityRoleById(arryStr.trim()));
        }
		role.setAuthorityRoles(roleSet);  //重新分配资源
		
		userDao.merge(role);
		return role.getId();
	}

	@Transactional
	public String removeSystemmgtUser(String roleid,HttpServletRequest req) throws Exception {
		SystemmgtUser user = userDao.getById(roleid);
		if(user != null){
			org.hibernate.Query query = userDao.getHibernateSession().createQuery("update SystemmgtUser o set o.state='3' where o.id=?");
			query.setParameter(0, roleid).executeUpdate();
//			userDao.removeById(roleid);
			//记录用户权限组日志信息  
			UserPermissionGroup userPermissionGroup=user.getPermissionGroup();//用户权限小组对象
			if(userPermissionGroup!=null){
				String remark="用户成员："+user.getLoginName()+",被移除该用户权限小组";//删除内容
				userPermissionGroupService.saveUserAuthorityOperateLog(userPermissionGroup.getId(), remark, req);
			}
			return Constant.SUCCESS;
		}else{
			return Constant.FAIL;
		}
	}
	
	public List<SystemmgtUser> findSystemByLoginName(String loginName){
		return userDao.findBy("loginName", loginName);
	}
	
	public SystemmgtUser systemUserLogin(String loginName, String passWord) {
		
		String truePassword = CommonUtil.md5(passWord);
		org.hibernate.Query query = userDao.getHibernateSession().createQuery(" select o from SystemmgtUser o where o.loginName=?" +
				" and o.loginPassword=? and (o.state='1' or o.state is null)");
		List<SystemmgtUser> users = query.setParameter(0, loginName).setParameter(1, truePassword).list();
		if(users!=null && users.size()>0) {
			return users.get(0);
		}
		return null;
//		CritMap critMap = new CritMap();
//		critMap.addEqual("loginName", loginName);
//		String truePassword = CommonUtil.md5(passWord);
//		critMap.addEqual("loginPassword", truePassword);
//		critMap.addEqual("state", "1");
//		return userDao.getObjectByCritMap(critMap);
	}
	
	@Override
	public SystemmgtUser systemUserLogin(String loginName) {
		CritMap critMap = new CritMap();
		critMap.addEqual("loginName", loginName);
		return userDao.getObjectByCritMap(critMap);
	}

	@Transactional
	public SystemmgtUser updateLoginInfo(String userid ,String loginName, String passWord) {
		SystemmgtUser user = getSystemmgtUserById(userid);
		user.setLoginName(loginName);
		user.setLoginPassword(CommonUtil.md5(passWord));
		user.setPwdUpdateTime(new Date());
		userDao.merge(user);
		return user;
	}

	@Transactional
	public SystemmgtUser updateSystemUserState(String userid, String state) {
		SystemmgtUser user = getSystemmgtUserById(userid);
		user.setState(state);
		userDao.merge(user);
		return user;
	}
	
	/**
	 * 查询用户列表
	 */
	public PageFinder<SystemmgtUser> pageQuerySystemmgtUser(SystemmgtUser aystemmgtUser, String roleName,String menuName,Query query) {
		StringBuffer bufSql = new StringBuffer();
		bufSql.append("SELECT DISTINCT t1.id,t1.username,t1.login_name,t1.sex,t1.mobile_phone,t1.supplier_code,t1.organiz_name,t1.tel_phone,t1.email,t1.qq_num,t1.state"+
				" FROM  tbl_systemmgt_user t1 LEFT JOIN tbl_systemmgt_user_role t2 ON t1.id = t2.uid "+
				" LEFT JOIN tbl_authority_role t3 ON t2.role_id = t3.id LEFT JOIN tbl_authority_role_menu t4 ON t3.id=t4.role_id "+
				" LEFT JOIN tbl_authority_resources t5 ON t4.menu_id = t5.id  WHERE 1= 1 ");
		// 拼装动态where子句条件
		if(StringUtils.isNotBlank(aystemmgtUser.getUsername())){
			bufSql.append(" and t1.username like '%"+aystemmgtUser.getUsername()+"%'");
		}
		if(StringUtils.isNotBlank(aystemmgtUser.getLoginName())){
			bufSql.append(" and t1.login_name like '%"+aystemmgtUser.getLoginName()+"%'");
		}
		if(StringUtils.isNotBlank(roleName)){
			bufSql.append(" and t3.role_name like '%"+roleName+"%'");
		}
		if(StringUtils.isNotBlank(menuName)){
			bufSql.append(" and t5.menu_name = '"+menuName+"'");
		}
		
		String querySql = bufSql.toString();
		// 总记录的计算
		String countSql = "";
		int index = querySql.indexOf("FROM");
		if (index != -1) {
			countSql = "select count(DISTINCT t1.id) " + querySql.substring(index);
		}
		
		int totalCount = JDBCUtils.getInstance().count(countSql);
		PageFinder<SystemmgtUser> pageFinder = null;
		if(totalCount>0){
			querySql = querySql + " limit "+(query.getPage()-1)*query.getPageSize()+","+query.getPageSize();
			pageFinder = new PageFinder<SystemmgtUser> (query.getPage(),query.getPageSize(),totalCount);
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet result = null;
			try {
				// //获取数据库连接
				conn = JDBCUtils.getInstance().getQueryConnection();
				pstmt = conn.prepareStatement(querySql);
				result = pstmt.executeQuery();
				List<SystemmgtUser> systemmgtUserList = new ArrayList<SystemmgtUser>();
				while (result.next()) {
					SystemmgtUser systemmgtUser = new SystemmgtUser();
					systemmgtUser.setId(result.getString("id"));
					systemmgtUser.setUsername(result.getString("username"));
					systemmgtUser.setLoginName(result.getString("login_name"));
					systemmgtUser.setSex(result.getString("sex"));
					systemmgtUser.setMobilePhone(result.getString("mobile_phone"));
					systemmgtUser.setSupplierCode(result.getString("supplier_code"));
					systemmgtUser.setOrganizName(result.getString("organiz_name"));
					systemmgtUser.setTelPhone(result.getString("tel_phone"));
					systemmgtUser.setEmail(result.getString("email"));
					systemmgtUser.setQqNum(result.getString("qq_num"));
					systemmgtUser.setState(result.getString("state"));
					systemmgtUserList.add(systemmgtUser);
				}
				
				pageFinder.setData(systemmgtUserList);
			} catch (Exception e) {
			} finally {
				try {
					if (result != null) {
						result.close();
					}
					if (pstmt != null)
						pstmt.close();
				} catch (Exception e) {
				} finally {
					try {
						if (conn != null)
							conn.close();
					} catch (Exception e) {
					}
				}
			}
			
		}
		return pageFinder;
	}
	
	public List<AuthorityResources> getSystemUserAllowResources(SystemmgtUser systemmgtUser){
		List<AuthorityResources> resList = null;
		CritMap critMap = new CritMap();
		//系统超级管理员能看到所有非具体功能点菜单
		if(systemmgtUser.getLevel() == null || !systemmgtUser.getLevel().equals(Constant.SYSTEM_LEVEL)){
			critMap.addFech("roles", "authorityRoles");
			critMap.addFech("sysuser","roles.systemmgtUsers");
			critMap.addEqual("sysuser.id", systemmgtUser.getId());
		}
		String[] flagValues = {"all","old"};
		critMap.addIN("flag", flagValues);
	
		critMap.addAsc("sort");
		resList = authorityResourcesDao.findByCritMap(critMap);
		
	   List<AuthorityResources> resListNoPM = new ArrayList<AuthorityResources>();
       for (AuthorityResources authorityResources : resList) {
    	   //去除掉货品管理信息的菜单
           if(!"PM".equals(authorityResources.getRemark())){
        	   resListNoPM.add(authorityResources);
           }
       }
	   return resListNoPM;
	}
	
	public String buildAuthResourceTree(HttpServletRequest request,List<AuthorityResources> authResourceList,String root_struc) throws Exception{
		TreeNodeVo rootVo = new TreeNodeVo(); 
		if(root_struc == null || root_struc.equals("")){
		    root_struc = SystemgmtConstant.MENU_ROOT_STRUC;
		}
		rootVo.setNodeStruc(root_struc); 
		
		List<AuthorityResources> tempResource = new ArrayList<AuthorityResources>();
		
		for (AuthorityResources res : authResourceList) {
		    if(res.getStructure().startsWith(root_struc+"-") || res.getStructure().equals(root_struc)){
		        tempResource.add(res);
		    }
        }
		
		HashMap<String,AuthorityResources> authMap = invAuthListToMap(root_struc,tempResource);
		
		CritMap critMap = null;
		
		Set<String> strucSet = new HashSet<String>();
		
		for (AuthorityResources res : tempResource) {
			String [] strucs = authorityResourcesComponent.getParentStruts(root_struc,res.getStructure());
			
			if(strucs == null) continue;
			
			for (String struc : strucs) {
				if(authMap.get(struc) == null)
					strucSet.add(struc);
			}
			
			//如果当前节点为详细功能点，则排除在菜单外
			if(res.getType().equals(SystemgmtConstant.AUTH_TYPE_FUNCTION)){
				authMap.remove(res.getStructure());
			}
		}
		
		 
		String [] struts = new String[strucSet.size()];
		if(struts != null && struts.length > 0){
			struts = strucSet.toArray(struts);
			List<AuthorityResources> resList = this.convertResources(remoteService.getMenuNameList(struts)); 
			for (AuthorityResources parentRes : resList) {
				if(authMap.get(parentRes.getStructure()) == null){
					authMap.put(parentRes.getStructure(), parentRes);
				}
			}
		}
		 
		
		List<AuthorityResources> authList = invAuthMapToList(authMap);
		Collections.sort(authList, new ComparatorResource());
		
		try {
			String json = authorityResourcesComponent.getTreeJsonDate(request,rootVo, authList,false);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	class ComparatorResource implements Comparator{
        public int compare(Object obj01, Object obj02) {
            AuthorityResources resource01 = (AuthorityResources)obj01;
            AuthorityResources resource02 = (AuthorityResources)obj02;
           return  resource01.getSort().compareTo(resource02.getSort());
        }
	}
	
	public List<AuthorityResources> invAuthMapToList(HashMap<String,AuthorityResources> map){
		List<AuthorityResources> authList = new ArrayList<AuthorityResources>();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			authList.add(map.get(key));
		}
		return authList;
	}
	
	/**
	 * 将list转HashMap 为了重掉重复对象
	 * @param resList
	 * @return
	 */
	public HashMap<String,AuthorityResources> invAuthListToMap(String root_struc,List<AuthorityResources> resList){
		HashMap<String,AuthorityResources> map = new HashMap<String,AuthorityResources>();
		for (AuthorityResources res : resList) {
		    if(res.getStructure().startsWith(root_struc+"-"))
		        map.put(res.getStructure(), res);
		}
		return map;
	}
	
	
	 public List<SystemmgtUser> getAllSystemmgtUserByOrganizNo(String organizNo) throws Exception{
	     CritMap critMap = new CritMap();
         critMap.addEqual("organizNo", organizNo);
         
         List<SystemmgtUser> userList = userDao.findByCritMap(critMap);
         
         return userList;
	     
	 }
	
	 /**
	  * 供应商登录验证
	  * @param loginname
	  * @param password
	  * @return
	  * @throws Exception
	  */
	 public SystemmgtUser supplierUserLogin(String loginname,String password) throws Exception{
		CriteriaAdapter criteriaAdapter = userDao.createCriteriaAdapter();
		Criteria criteria = criteriaAdapter.getCriteria();
		 criteria.add(Restrictions.eq("loginName", loginname));
		 String truePassword = CommonUtil.md5(password);
		 criteria.add(Restrictions.eq("loginPassword", truePassword));
		 criteria.add(Restrictions.eq("state", SystemgmtConstant.UNLOCK));
		 criteria.add(Restrictions.or(Restrictions.ne("supplierCode", ""),Restrictions.ne("supplierCode", null)));
		 SystemmgtUser user = (SystemmgtUser)criteria.uniqueResult();
		 userDao.releaseHibernateSession(criteriaAdapter.getSession());
		 return user;
	 }
	 
	 public List<AuthorityResources> convertResources(List<ResourceRemote> resourceRemoteList){
		 List<AuthorityResources> authResourceList = null;
		 if(CollectionUtils.isNotEmpty(resourceRemoteList)){
			 authResourceList = new ArrayList<AuthorityResources>();
			 AuthorityResources authorityResources = null;
			 for(ResourceRemote resourceRemote: resourceRemoteList){
				 authorityResources = new AuthorityResources();
				 authorityResources.setId(resourceRemote.getId());
				 authorityResources.setFlag(resourceRemote.getFlag());
				 authorityResources.setIsleaf(resourceRemote.getIsleaf());
				 authorityResources.setMemuUrl(resourceRemote.getMemu_url());
				 authorityResources.setMenuName(resourceRemote.getMenu_name());
				 authorityResources.setNo(resourceRemote.getNo());
				 authorityResources.setRemark(resourceRemote.getRemark());
				 authorityResources.setStructure(resourceRemote.getStructure());
				 authorityResources.setType(resourceRemote.getType());
				 authorityResources.setSort(resourceRemote.getSort());
				 authResourceList.add(authorityResources);
			 }
		 }
		 return authResourceList;
	 }
}
