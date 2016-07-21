package com.belle.yitiansystem.merchant.dao.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.belle.infrastructure.orm.basedao.Query;

public interface MerchantOperationLogMapper {
	
	public List<Map> selectMerchantOperationLog( @Param("merchantCode")String merchantCode,@Param("supplierId")String supplierId,
			Query query);

	public int selectMerchantOperationLogCount( @Param("merchantCode")String merchantCode,@Param("supplierId")String supplierId);	
	
}
