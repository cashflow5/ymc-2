package com.belle.yitiansystem.systemmgmt.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.orm.basedao.CritMap;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.util.DateUtil;
import com.belle.infrastructure.util.JDBCUtils;
import com.belle.yitiansystem.systemmgmt.common.JsonTreeUtils;
import com.belle.yitiansystem.systemmgmt.common.SystemgmtConstant;
import com.belle.yitiansystem.systemmgmt.dao.IAuthorityRoleDao;
import com.belle.yitiansystem.systemmgmt.dao.IOperateLogDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.AuthorityResources;
import com.belle.yitiansystem.systemmgmt.model.pojo.AuthorityRole;
import com.belle.yitiansystem.systemmgmt.model.pojo.OperateLog;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.belle.yitiansystem.systemmgmt.service.IAuthorityResourcesService;
import com.belle.yitiansystem.systemmgmt.service.IAuthorityRoleService;

@Service
public class AuthorityRoleServiceImpl implements IAuthorityRoleService {


	@Resource
	private IAuthorityRoleDao roleDao;
	
	@Resource
	private IAuthorityResourcesService resourcesService;   
	
	@Resource
	private IOperateLogDao operateLogDao;
	
	@Transactional
	public String addAuthorityRole(AuthorityRole authorityRole)
			throws Exception {
		authorityRole.setRoleCreatedate(DateUtil.getCurrentDateTime());
		return (String)roleDao.save(authorityRole);
	}

	
	public AuthorityRole getAuthorityRoleById(String id) {
		return roleDao.getById(id);
	}

	public List<AuthorityRole> getAllAuthorityRole() {
		return roleDao.getAll("roleCreatedate",true);
	}
	
	@Transactional
	public String updateAuthorityRole(AuthorityRole authorityRole)
			throws Exception {
		AuthorityRole role = getAuthorityRoleById(authorityRole.getId().trim());
		role.setRoleName(authorityRole.getRoleName());
		role.setRemark(authorityRole.getRemark());
		roleDao.merge(role);
		return role.getId();
	}

	@Transactional
	public String allotAuthorityRes(String roleid, String[] resIdArry,String ip,SystemmgtUser user) throws Exception {
		AuthorityRole role = getAuthorityRoleById(roleid);   //获取角色
		//获取用户现有的资源
		Set<AuthorityResources> oleResSet = role.getAuthorityResources();
		Iterator<AuthorityResources> it = oleResSet.iterator();
		//获取用户现有更换的资源
		Set<AuthorityResources> resSet = new HashSet<AuthorityResources>();
		StringBuffer oldRole = new StringBuffer(1024);
		StringBuffer newRole = new StringBuffer(1024);
		while (it.hasNext()) {
			AuthorityResources authorityResources = it.next();
			if(StringUtils.equals("1", authorityResources.getIsleaf())){
				oldRole.append(authorityResources.getMenuName());
				oldRole.append(",");
			}
		}
		//获取当前资源信息
		for (int i = 0; i < resIdArry.length; i++) {
			if("".equals(resIdArry[i].trim())) {continue;}
			//获取新资源的名称（三级分类名称）
			AuthorityResources authorityResources = resourcesService.getAuthorityResourcesById(resIdArry[i].trim());
			if(StringUtils.equals("1", authorityResources.getIsleaf())){
				newRole.append(authorityResources.getMenuName());
				newRole.append(",");
			}
			resSet.add(authorityResources);
		}
		role.setAuthorityResources(resSet);  //重新分配资源
		roleDao.merge(role);
		//添加日志信息
		OperateLog operateLog = new OperateLog();
		operateLog.setCreate_time(new Date());
		operateLog.setMod_name("客服系统");
		operateLog.setOperate_ip(ip);
		operateLog.setPortal_id(5);//5表示客服系统
		operateLog.setUser_id(user.getId());
		operateLog.setUser_name(user.getUsername());
		operateLog.setOperate_type(3);//1表示添加  2表示删除  3表示更新
		String operateDesc = "分配资源：资源由"+oldRole.toString()+"变为"+newRole.toString()+"。";
		if(operateDesc.length()>500){
			operateDesc = operateDesc.substring(0, 490)+"...";
		}
		operateLog.setOperate_desc(operateDesc);
		operateLogDao.save(operateLog);
		return role.getId();
	}

	@Transactional
	public String removeAuthorityRole(String roleid) throws Exception {
		AuthorityRole role = roleDao.getJoinRoleById(roleid);
		
		//如果有关联则不让删除
		if(role.getSystemmgtUsers().size() > 0 || role.getAuthorityResources().size() > 0){
			return Constant.FAIL;
		}else{
			roleDao.removeById(roleid);
			return Constant.SUCCESS;
		}
	}
	
	public PageFinder<AuthorityRole> pageQueryAuthorityRole(AuthorityRole authorityRole,String menuName, Query query) {
		StringBuffer bufSql = new StringBuffer();
		bufSql.append(" SELECT DISTINCT t3.id,t3.role_name,t3.role_createdate,t3.remark FROM  tbl_authority_role t3 "+
		" LEFT JOIN tbl_authority_role_menu t4 ON t3.id=t4.role_id  LEFT JOIN tbl_authority_resources t5 ON t4.menu_id = t5.id where 1=1  ");
		// 拼装动态where子句条件
		if(StringUtils.isNotBlank(authorityRole.getRoleName())){
			bufSql.append(" and t3.role_name like '%"+authorityRole.getRoleName()+"%'");
		}
		if(StringUtils.isNotBlank(menuName)){
			bufSql.append(" and t5.menu_name = '"+menuName+"'");
		}
		
		String querySql = bufSql.toString();
		// 总记录的计算
		String countSql = "";
		int index = querySql.indexOf("FROM");
		if (index != -1) {
			countSql = "select count(DISTINCT t3.id) " + querySql.substring(index);
		}
		
		int totalCount = JDBCUtils.getInstance().count(countSql);
		PageFinder<AuthorityRole> pageFinder = null;
		if(totalCount>0){
			querySql = querySql + " limit "+(query.getPage()-1)*query.getPageSize()+","+query.getPageSize();
			pageFinder = new PageFinder<AuthorityRole> (query.getPage(),query.getPageSize(),totalCount);
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet result = null;
			try {
				// //获取数据库连接
				conn = JDBCUtils.getInstance().getQueryConnection();
				pstmt = conn.prepareStatement(querySql);
				result = pstmt.executeQuery();
				List<AuthorityRole> authorityRoleList = new ArrayList<AuthorityRole>();
				while (result.next()) {
					AuthorityRole tempAuthorityRole = new AuthorityRole();
					tempAuthorityRole.setId(result.getString("id"));
					tempAuthorityRole.setRoleName(result.getString("role_name"));
					tempAuthorityRole.setRoleCreatedate(result.getDate("role_createdate"));
					tempAuthorityRole.setRemark(result.getString("remark"));
					authorityRoleList.add(tempAuthorityRole);
				}
				pageFinder.setData(authorityRoleList);
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


	@Override
	public List<AuthorityRole> getnoAllotAuthorityRole(String userid) {
		return roleDao.getnoAllotAuthorityRole(userid);
	}


	public String getResourceTreeByRoleId(HttpServletRequest request,String id) {
		
		//获取角色拥有的资源
		CritMap critMap = new CritMap();
		critMap.addEqual("id", id);
		critMap.addFech("authorityResources");
		AuthorityRole role = roleDao.getObjectByCritMap(critMap);
		Set<AuthorityResources> roleResSet = role.getAuthorityResources();
		
		//所有资源
		String resJsonStr = resourcesService.getResourceTreeByType(request,SystemgmtConstant.AUTH_TYPE_ALL);
		
		String resultStr = JsonTreeUtils.selectRoleRes(resJsonStr,roleResSet);
		return resultStr;
	}
	
	
	public static void main(String[] args) {
	
	}
	
	
	/**
	 * 添加操作日志信息(审计)
	 * @author zhao.my
	 * @param operateLog
	 * @throws Exception 
	 */
	@Override
	@Transactional
	public OperateLog saveOperateLog(OperateLog operateLog) throws Exception{
		return operateLogDao.saveObject(operateLog);
	}

}
