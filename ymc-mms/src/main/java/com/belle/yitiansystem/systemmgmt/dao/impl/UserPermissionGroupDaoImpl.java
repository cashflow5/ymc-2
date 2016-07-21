package com.belle.yitiansystem.systemmgmt.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.infrastructure.util.JDBCUtils;
import com.belle.other.constant.CommissionOrderConstant;
import com.belle.other.constant.OrderAbnormalConstant;
import com.belle.yitiansystem.systemmgmt.dao.IUserPermissionGroupDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.UserPermissionGroup;


/**
 * 数据权限小组
 * @author liyanbin
 *
 */
@Repository
public class UserPermissionGroupDaoImpl extends HibernateEntityDao<UserPermissionGroup> implements IUserPermissionGroupDao{
	/**
	 * 删除用户权限小组对象数据
	 * @author wang.m
	 * @throws Exception 
	 * @throws Exception 
	 * @Date 2011-12-20
	 */
	public void deleteUserPermissionGroupById(String id) throws Exception {
		String sql="update tbl_user_group set delete_flag=? where id=?";
		List<Object[]> object=new ArrayList<Object[]>();
        String[] str=new String[2];
        str[0]=String.valueOf(0);
        str[1]=id;
        object.add(str);
        //获取jdbc封装的对象
		JDBCUtils utils=JDBCUtils.getInstance();
		utils.batchSaveOrUpdate(sql, object);
	}
}
