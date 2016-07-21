package com.belle.yitiansystem.merchant.service;

import com.yougou.dto.input.ReturnQualityQueryInputDto;
import com.yougou.dto.output.ReturnQualityQueryOutputDto;

/**
 * <p>售后服务接口</p>
 * 
 * @author huang.tao
 *
 */
public interface IMerchantsAfterSaleService {
	/**
	 * 售后API退换货质检明细查询接口
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	ReturnQualityQueryOutputDto queryReturnQualityList(ReturnQualityQueryInputDto dto) throws Exception;
	
	/**
	 * 通过供应商编码获取商家绑定的仓库Code
	 * @param supplierCode
	 * @return
	 * @throws Exception
	 */
	String getMerchantWarehouseBySupplierCode(String supplierCode) throws Exception;
}
