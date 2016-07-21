package com.belle.yitiansystem.merchant.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.yougou.merchant.api.common.Query;
import com.yougou.merchant.api.supplier.vo.MerchantContractUpdateHistory;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog;
import com.yougou.merchant.api.supplier.vo.YmcResult;

/**
 * 
 * @author le.sm
 * 
 * @description
 *【商家后台】账号启用时，通过接口发消息通知WMS自动匹配商家仓库（给货品的接口和调用WMS的接口）
      【商家后台】账号启用或停用时，分配或撤销相应的权限（向货品系统提供接口）

 */
public interface IMerchantOperatorApi {

	/**
	 * 启用账号时候，将主账号分配基本权限，如果主账号已经有权限，不做分配操作，只启动该商家所有账号
	 * 通知WMS 自动分配仓库
	 * @param user
	 */
	YmcResult  startUpAccout(String merchantCode,String operator,String[] baseRoleIdArray,HttpServletRequest req);
	
	/**
	 * 关闭该商家所有账号
	 * 通知WMS关闭仓库
	 * @param user
	 */
	YmcResult  stopAccout(String merchantCode,String operator);
	
	/**
	 * 提供给商品系统写商家操作日志 
	 * operationType:
	 *  BASIC_DATA("商家资料"), 
		ACCOUNT("商家帐户"), 
		CONTRACT("合同"), 
		CONTACT("联系人"),
	 */
	YmcResult  addMerchantLog(MerchantOperationLog log);
	/**
	 * 合同操作日志接口
	 * @param log
	 * @return
	 */
	YmcResult  addContractLog(MerchantContractUpdateHistory log);
	
	/**
	 * 判断是否有到期，将商品库存清零
	 * 
	 * mark_remaining_days` int(11) DEFAULT NULL COMMENT '授权资质剩余有效天数',
	  `contract_remaining_days` int(11) DEFAULT NULL COMMENT '合同剩余有效天数',
	  `business_remaining_days` int(11) DEFAULT NULL COMMENT '营业执照剩余有效天数',
	  `institutional_remaining_days` int(11) DEFAULT NULL COMMENT '组织机构代码证剩余有效天数'
	 * @return
	 */
	YmcResult  checkOverDaysMerchant();
	
	
	List<MerchantContractUpdateHistory> listHistory(String supplierId);
	
	List<MerchantOperationLog>   listOperationLog(String supplierCode);
	
	/**
	 * 根据合同编号查询合同状态流转日志
	 * @param contractNo
	 * @return
	 */
    List<MerchantContractUpdateHistory> listContractHistory(String contractNo);
	
    /**
	 * 根据合同编号查询合同操作日志
	 * @param contractNo
	 * @return
	 */
	List<MerchantOperationLog>   listContractOperationLog(String contractNo);
	/**
	 * 
	 * @return
	 */
	YmcResult overDaySendMessage();
	
	YmcResult useNewContract();

	/**
	 * 合同到期超过60天，且未续签的商家，对其切换权限组关闭部分权限。
	 * @param strings 
	 */
	void overDay60CloseOperation(String[] baseRoleIdArray, Date targetDate);
	
	void overDay90CloseOperation();
	
	void  initExpand();

	PageFinder<MerchantOperationLog> queryMerchantOperationLog(String merchantCode,
			com.yougou.merchant.api.common.Query query);

	PageFinder<MerchantOperationLog> queryMOperationLog(String merchantCode,
			String operationType, Query query);

	

}