
package com.belle.yitiansystem.merchant.dao.mapper;

import com.yougou.merchant.api.supplier.vo.MerchantContractUpdateHistory;
import com.yougou.merchant.api.supplier.vo.MerchantContractUpdateHistoryQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerchantContractUpdateHistoryMapper {
    int countByQuery(MerchantContractUpdateHistoryQuery example);

    int deleteByQuery(MerchantContractUpdateHistoryQuery example);

    int deleteByPrimaryKey(String id);

    int insert(MerchantContractUpdateHistory record);

    int insertSelective(MerchantContractUpdateHistory record);

    List<MerchantContractUpdateHistory> selectByQuery(MerchantContractUpdateHistoryQuery example);

    MerchantContractUpdateHistory selectByPrimaryKey(String id);

    int updateByQuerySelective(@Param("record") MerchantContractUpdateHistory record, @Param("example") MerchantContractUpdateHistoryQuery example);

    int updateByQuery(@Param("record") MerchantContractUpdateHistory record, @Param("example") MerchantContractUpdateHistoryQuery example);

    int updateByPrimaryKeySelective(MerchantContractUpdateHistory record);

    int updateByPrimaryKey(MerchantContractUpdateHistory record);
}