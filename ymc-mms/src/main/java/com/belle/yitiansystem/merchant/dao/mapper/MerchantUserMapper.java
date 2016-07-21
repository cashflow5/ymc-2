
package com.belle.yitiansystem.merchant.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yougou.merchant.api.supplier.vo.MerchantUser;
import com.yougou.merchant.api.supplier.vo.MerchantUserQuery;

public interface MerchantUserMapper {
    int countByQuery(MerchantUserQuery example);

    int deleteByQuery(MerchantUserQuery example);

    int deleteByPrimaryKey(String id);

    int insert(MerchantUser record);

    int insertSelective(MerchantUser record);

    List<MerchantUser> selectByQuery(MerchantUserQuery example);

    MerchantUser selectByPrimaryKey(String id);

    int updateByQuerySelective(@Param("record") MerchantUser record, @Param("example") MerchantUserQuery example);

    int updateByQuery(@Param("record") MerchantUser record, @Param("example") MerchantUserQuery example);

    int updateByPrimaryKeySelective(MerchantUser record);

    int updateByPrimaryKey(MerchantUser record);
}