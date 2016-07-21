package com.belle.yitiansystem.merchant.service;

import com.yougou.merchant.api.supplier.vo.YmcResult;

/**
 * 合同相关接口（提供外部系统调用）
 * @author le.sm
 *
 */
public interface IContractNewApi {
	/**
	 * 合同审核
	 * @param merchantCode 商家编码
	 * @param contractId 合同ID
	 * @param status 合同状态（1新建 2待审核 3业务审核通过 4业务审核不通过 5财务审核通过 6财务审核不通过 7生效 8已过期）  
	 * @return
	 */
	YmcResult  auditContract(String merchantCode,String contractId,String status,String operatorName);
	
	/**
	 * 提供给商品系统查询供应商合同，合同附件，授权资质等数据的接口
	 */

	YmcResult   getMerchantInfo(String merchantCode);
	
	/**
	 * 更新资质等时间 ，定时任务
	 * @return
	 */
	YmcResult updateDateInfo();
}
