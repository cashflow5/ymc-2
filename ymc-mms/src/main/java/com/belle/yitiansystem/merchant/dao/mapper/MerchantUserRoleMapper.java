
package com.belle.yitiansystem.merchant.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yougou.merchant.api.supplier.vo.MerchantUser;
import com.yougou.merchant.api.supplier.vo.MerchantUserRole;
import com.yougou.merchant.api.supplier.vo.MerchantUserRoleQuery;

public interface MerchantUserRoleMapper {
    int countByQuery(MerchantUserRoleQuery example);
    // 假删除：用户的所有有效权限，‘已关闭部分权限组’除外
    int delValidUserRole(@Param("user")MerchantUser user,@Param("excludeRoleId")String excludeRoleId);
    
    int deleteByPrimaryKey(String id);//真删除
    
    int deleteUserRole( @Param("userId")String userId,@Param("roleId")String roleId );//真删除

    int insert(MerchantUserRole record);

    int insertSelective(MerchantUserRole record);

    List<MerchantUserRole> selectByQuery(MerchantUserRoleQuery example);

    MerchantUserRole selectByPrimaryKey(String id);

    int updateByQuerySelective(@Param("record") MerchantUserRole record, @Param("example") MerchantUserRoleQuery example);

    int updateByQuery(@Param("record") MerchantUserRole record, @Param("example") MerchantUserRoleQuery example);

    int updateByPrimaryKeySelective(MerchantUserRole record);

    int updateByPrimaryKey(MerchantUserRole record);
    
	void updateUserAuthorityFromBak(@Param("userId")String userId);// 子账号的权限，恢复
}