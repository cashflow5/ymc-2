package com.belle.yitiansystem.merchant.service;

import com.yougou.dto.input.QueryInventoryInputDto;
import com.yougou.dto.input.UpdateInventoryInputDto;
import com.yougou.dto.output.QueryInventoryOutputDto;
import com.yougou.dto.output.UpdateInventoryOutputDto;


public interface IMerchantApiInventoryService {

	/**
	 * 更新商家库存信息
	 * 
	 * @param UpdateInventoryInputDto
	 * @return UpdateInventoryOutputDto
	 * @throws Exception
	 */
	Object updateMerchantInventory(UpdateInventoryInputDto dto) throws Exception;

	/**
	 * 查询商家库存信息
	 * 
	 * @param QueryInventoryInputDto
	 * @return QueryInventoryOutputDto
	 * @throws Exception
	 */
	QueryInventoryOutputDto queryMerchantInventory(QueryInventoryInputDto dto) throws Exception;
}
