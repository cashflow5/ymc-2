package com.belle.yitiansystem.merchant.service;

import javax.servlet.http.HttpServletRequest;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.other.model.pojo.SupplierContactSp;
import com.belle.other.model.pojo.SupplierContract;
import com.belle.other.model.pojo.SupplierSp;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog.OperationType;
import com.belle.yitiansystem.merchant.model.pojo.MerchantRejectedAddress;
import com.belle.yitiansystem.merchant.model.pojo.MerchantUser;
import com.yougou.fss.api.vo.ShopVO;
import com.yougou.merchant.api.supplier.vo.SupplierVo;

public interface IMerchantOperationLogService {

	/**
	 * 添加商家操作日志
	 * 
	 * @param operationLog
	 */
	void saveMerchantOperationLog(MerchantOperationLog operationLog) throws Exception;
	
	/**
	 * 分页查询商家操作日志
	 * 
	 * @param merchantCode
	 * @param query
	 * @return PageFinder
	 * @throws Exception
	 */
	PageFinder<MerchantOperationLog> queryMerchantOperationLog(String merchantCode, Query query) throws Exception;
	
	public PageFinder<MerchantOperationLog> queryMerchantOperationLogByOperationType(String merchantCode, com.yougou.merchant.api.supplier.vo.MerchantOperationLog.OperationType operationType, Query query) throws Exception;

	public PageFinder<MerchantOperationLog> queryMerchantOperationLogByOperationType(String merchantCode, OperationType operationType, Query query) throws Exception;
	
	/**
	 * 构造商家基础资料操作日志
	 * 
	 * @param source
	 * @param target
	 * @return String
	 * @throws Exception
	 */
	String buildMerchantBasicDataOperationNotes(SupplierSp source, SupplierSp target) throws Exception;
	
	/**
	 * 构造商家帐户操作日志
	 * 
	 * @param source
	 * @param target
	 * @return String
	 * @throws Exception
	 */
	String buildMerchantAccountOperationNotes(MerchantUser source, MerchantUser target) throws Exception;
	
	/**
	 * 构造商家联系人操作日志
	 * 
	 * @param source
	 * @param target
	 * @return String
	 * @throws Exception
	 */
	String buildMerchantContactOperationNotes(SupplierContactSp source, SupplierContactSp target) throws Exception;
	
	/**
	 * 构造商家合同操作日志
	 * 
	 * @param source
	 * @param target
	 * @return String
	 * @throws Exception
	 */
	String buildMerchantContractOperationNotes(SupplierContract source, SupplierContract target) throws Exception;
	
	/**
	 * 构造商家退换货地址操作日志
	 * 
	 * @param source
	 * @param target
	 * @return String
	 * @throws Exception
	 */
	String buildMerchantAfterServiceAddrOperationNotes(MerchantRejectedAddress source, MerchantRejectedAddress target) throws Exception;

	/** 
	 * buildMerchantShopOperationNotes:商家店铺信息记录日志 
	 * @author li.n1 
	 * @param object
	 * @param shopVo
	 * @return 
	 * @since JDK 1.6 
	 */  
	String buildMerchantShopOperationNotes(ShopVO source, ShopVO target) throws Exception;
	/**
	 * buildMerchantShopRuleOperationNotes:商家店铺规则记录日志 
	 * @author li.n1 
	 * @param source
	 * @param target
	 * @return
	 * @throws Exception 
	 * @since JDK 1.6
	 */
	public String buildMerchantShopRuleOperationNotes(String source, String target) throws Exception;

	PageFinder<MerchantOperationLog> queryMerchantOperationLog(
			SupplierVo supplierVo, Query query);
	
	/**
	 * 保存日志
	 * 
	 * @param merchantCode
	 * @param type
	 * @param msg
	 * @param request
	 */
	void addMerchantOperationLog(String merchantCode,OperationType type,String msg,HttpServletRequest request);
	void addMerchantOperationLog(String merchantCode,OperationType type,String msg,String userName);

	PageFinder<MerchantOperationLog> queryMerchantOperationLogByUser(
			String userId,
			com.yougou.merchant.api.supplier.vo.MerchantOperationLog.OperationType account,
			Query query);

}
