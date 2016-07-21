package com.belle.yitiansystem.merchant.service;

import java.util.List;
import java.util.Map;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.yitiansystem.merchant.model.pojo.AttachmentFormVo;
import com.belle.yitiansystem.merchant.model.pojo.SupplierContract;
import com.belle.yitiansystem.merchant.model.pojo.SupplierSp4MyBatis;
import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.yougou.merchant.api.common.Query;
import com.yougou.purchase.model.Supplier;

public interface ISupplierContractService {
	void saveSupplierContract(SupplierContract contract,String curUser,String[]contract_attachment,String[] trademark,
			String[] authorizer,String[] type,String[] registeredTrademark,String[]registeredStartDate,String[] registeredEndDate,String[]beAuthorizer,
			String[]authorizStartdate,String[]authorizEnddate) throws BusinessException;
	
	PageFinder<SupplierContract> findSupplierContractList(Map<String,Object> param, Query query);
	
	SupplierContract getSupplierContract(String id);
	
	List<String> getMerchantCodeByCurUser(String curUser);
	
	List<SupplierSp4MyBatis> selectSupplierSpByYgContact(String curUser,String supplierCode,String supplierType);

	/**
	 * 查询供应商列表
	 * @param curUser
	 * @param supplierCode
	 * @param supplierType
	 * @return
	 */
	PageFinder<SupplierSp4MyBatis> selectSupplier4Contact(Map<String,Object> param, Query query);

	
	void bindContractIfExited(Supplier supplier);
	
	List<SupplierContract> findSupplierContractListForExport(
			Map<String, Object> param) ;
	void updateRemainingDaysForContract();
	
	/**
	 * 根据供应商ID查询合同
	 * @param supplierId
	 * @return 供应商合同列表
	 */
	List<SupplierContract> selectSupplierContractListBySupplierId(String supplierId);
	/**
	 * 
	 * @param supplierId
	 * @return 当前合同
	 */
	SupplierContract getSupplierContractBySupplierId(String supplierId);

	void saveContractForSupplier(Map<String, Object> params)  throws Exception ;

	void updateContractForSupplier(Map<String, Object> params) throws BusinessException;
	
	/**
	 * 保存招商供应商合同
	 */
	void saveSupplierContractMerchant(SupplierContract contract, Supplier supplier, String curUser, boolean isSubmit,  AttachmentFormVo attachmentFormVo) throws BusinessException;
	
	void updateSupplierContract(SupplierContract contract);

	void saveOrUpdateByAttachmentFormVo(AttachmentFormVo attachmentFormVo) throws Exception;
	/**
	 * 
	 * @param supplierId
	 * @return 合同 、商家资质、 合同附件、 商标附件
	 */
	SupplierContract buildSupplierContractSet(String supplierId);

	/** 获取商家的续签合同 */
	SupplierContract getSupplierRenewContract(String supplierId);
	
	/** 获取商家的当前合同 */
	SupplierContract getSupplierCurrentContract(String supplierId);
	
	/** 续签招商合同 */
	void renewSupplierContract(SupplierContract contract,Supplier supplier, String curUser, boolean isSubmit,
									   Map<String, Object> params) throws BusinessException;
	/** 获取续签合同和合同的附件 */
	SupplierContract getSupplierRenewContractAndItsAttachments(String supplierId);

	/** 修改续签招商合同 */
	void updateRenewSupplierContract(SupplierContract supplierContract,
			Supplier supplier, String username, boolean isSubmit,
			Map<String, Object> params)throws BusinessException;
	/**
	 * 根据合同ID查询合同
	 * @param id
	 * @return
	 */
	SupplierContract getSupplierContractById(String id);

	/**
	 * 更新保证金收款单
	 * @param contract
	 * @param userName
	 * @param currentContract
	 * @param preContract
	 * @throws Exception
	 */
	boolean updateFinanceDepositCashBill(SupplierContract contract, String userName,
			SupplierContract currentContract,SupplierContract preContract);

	/**
	 * 创建使用费收款单
	 * @param contract
	 * @param userName
	 * @return TODO
	 * @throws Exception
	 */
	boolean createFinanceUseFeeCashBill(SupplierContract contract, String userName);

	/**
	 * 更新使用费收款单
	 * @param contract
	 * @param userName
	 * @param currentContract
	 * @throws Exception
	 */
	boolean updateFinanceUseFeeCashBill(SupplierContract contract, String userName, SupplierContract currentContract);

	/**
	 * 创建保证金收款单
	 * @param contract
	 * @param userName
	 * @param preContract
	 * @return TODO
	 * @throws Exception
	 */
	boolean createFinanceDepositCashBill(SupplierContract contract, String userName, SupplierContract preContract);

	/**
	 * 创建退款单
	 * @param contract
	 * @param userName
	 * @param type
	 * @throws Exception
	 */
	boolean createFinanceRefundBill(SupplierContract contract, String userName, String type);
	
	/**
	 * 恢复API
	 * @param supplierId
	 */
	void recoverApiLicenceBySupplierId(String supplierId);
	/**
	 * 保存草稿到redis之前，处理表单数据。
	 * @param attachmentFormVo
	 * @param contract
	 */
	void updateSupplierContractAccordingFormVo(
			AttachmentFormVo attachmentFormVo, SupplierContract contract);

	/* 合同保存（只需要保存所填部分信息）*/
	void saveSupplierContractSimple(SupplierContract contract,
			Supplier supplier, String curUser,
			AttachmentFormVo attachmentFormVo) throws BusinessException;
}
