package com.belle.yitiansystem.merchant.dao.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.belle.yitiansystem.merchant.model.entity.SimpleContract;
import com.belle.yitiansystem.merchant.model.pojo.SupplierContract;
import com.belle.yitiansystem.merchant.model.pojo.SupplierContractAttachment;
import com.belle.yitiansystem.merchant.model.pojo.SupplierContractTrademark;
import com.belle.yitiansystem.merchant.model.pojo.SupplierContractTrademarkSub;
import com.belle.yitiansystem.merchant.model.pojo.SupplierSp4MyBatis;
import com.belle.yitiansystem.merchant.model.pojo.SupplierYgContact4MyBatis;
import com.yougou.merchant.api.common.Query;

public interface SupplierContractMapper {
	/**
	 * 查询合同小于15天的合同
	 * @return
	 */
	List<SimpleContract> selectSimpleContract();
	/**
	 * 查询未使用  未到期的合同
	 * @return
	 */
	List<SimpleContract> getNotNowContract();
	
	
	 void updateOverDayContract();
	
//	/**
//	 * 根据供应商ID查询当前有效合同
//	 * @param supplierId
//	 * @return 当前有效合同
//	 */
//	 String getContractIdBySupplierId(@Param("supplierId")String supplierId);

	 void insertSupplierContract(SupplierContract supplierContract);
	
	 void updateSupplierContract(SupplierContract supplierContract);
	
	 void insertSupplierContractAttachmentBatch(@Param("list")List<SupplierContractAttachment> list);
	
	 void insertSupplierContractTrademarkBatch(@Param("list")List<SupplierContractTrademark> list);
	
	 void insertSupplierContractTrademarkSub(@Param("list")List<SupplierContractTrademarkSub> list);
	
	 List<SupplierContract> selectSupplierContractList(
			@Param("param") Map<String,Object> param,
			@Param("list")List<String>list,
			@Param("userNameList") List<String> userNameList, 
			@Param("query") Query query);
	
	 int selectSupplierContractCount(
			@Param("param") Map<String,Object> param,
			@Param("list")List<String>list, 
			@Param("userNameList") List<String> userNameList);
	
	 SupplierContract selectSupplierContractById(@Param("id") String id);
	
	 List<SupplierContractAttachment> selectSupplierContractAttachmentByContractId(@Param("contractId") String contractId);
	
	 List<SupplierContractTrademark> selectSupplierContractTrademark(@Param("contractId") String contractId);
	
	 List<SupplierContractTrademarkSub> selectSupplierContractTrademarkSubByTrademarkId(@Param("trademarkId") String trademarkId);
	
	 void deleteSupplierContractAttachmentByContractId(@Param("contractId") String contractId);
	
	 void deleteSupplierContractTrademarkByContractId(@Param("contractId") String contractId);
	
	 void deleteSupplierContractTrademarkSubByContractIdId(@Param("contractId") String contractId);
	
	 SupplierContract selectSupplierContractByContractNo(@Param("contractNo") String contractNo);
	
	 List<SupplierYgContact4MyBatis> selectYgContact(@Param("userName") String userName);
	
	 List<String> selectMerchantCodeByYgContact(@Param("list") List<String> list);
	
	 List<SupplierSp4MyBatis> selectSupplierSpByYgContact(@Param("list") List<String> list, @Param("supplierType") String supplierType);
	
	 List<SupplierSp4MyBatis> selectSupplier4ContractList(@Param("param") Map<String,Object> param,	@Param("query") Query query);
	
	 int selectSupplier4ContractCount(@Param("param") Map<String,Object> param);

	 void updateSupplierContractBindStatus(SupplierContract supplierContract);

	 void autoUpdateContractRemainingDays1();
	
	 void autoUpdateContractRemainingDays2();
	
	 void autoUpdateMarkRemainingDays(  @Param("supplierContractList")List<SupplierContract> supplierContractList );

	 List<Map<String, String>> computeAndSelectTrademarkRemainingDays();
	
	 List<SupplierContract> selectSupplierContractListForExport(
			@Param("param") Map<String,Object> param,
			@Param("list")List<String>list,
			@Param("userNameList") List<String> userNameList
			);
	
	/**
	 * 根据供应商ID查询合同
	 * @param supplierId
	 * @return 供应商合同列表
	 */
	 List<SupplierContract> selectSupplierContractListBySupplierId(@Param("supplierId") String supplierId);
	
	/*
	 * ----------------add by lsm ---------------------
	 * 
	 * `mark_remaining_days` int(11) DEFAULT NULL COMMENT '授权资质剩余有效天数',
	  `contract_remaining_days` int(11) DEFAULT NULL COMMENT '合同剩余有效天数',
	  `business_remaining_days` int(11) DEFAULT NULL COMMENT '营业执照剩余有效天数',
	  `institutional_remaining_days` int(11) DEFAULT NULL COMMENT '组织机构代码证剩余有效天数',*/
	
	/**
	 * 由于  tbl_merchant_supplier_expand.supplier_id 是新建的，可能没有数据，所以需要同步一次
	 */
	void updateSupplierIdTOExpand();
	/**
	 * 更新之前，先将当前绑定的合同绑定到扩展表里
	 */
	void updateExpandContract();
	/**
	 * 单独更新合同表里 合同到期时间
	 */
	void updateContractOverdays();
	/**
	 * 单独更新合同表里商标到期时间
	 */
	void updateContractMarkOverdays();
	
	/**
	 * 商标剩余日期
	 */
	void markLeftDays();
	/**
	 * 合同剩余日期
	 */
	void contractLeftDays();
	/**
	 * 合同剩余日期,根据续签合同
	 */
	void contractLeftDaysByRenew();
	/**
	 * 营业执照剩余有效天数
	 */
	void businessRemainDays();
	/**
	 * 组织机构代码证剩余有效天数
	 */
	void institutionalDays();

	/** 删除某商家的除了合同之外的资质-- 为了更新资质 
	 * @param contractId */
	void delAttachmentExceptContract(@Param("supplierId") String supplierId,@Param("contractId")  String contractId);
	
	/** 根据供应商ID查询当前商家的资质附件（不包括合同的3种附件）*/
	 List<SupplierContractAttachment> getSupplierAttachmentBySupplierId(@Param("supplierId")String supplierId);
	 /**根据供应商ID查询当前商家的合同附件（不包括商家的5种附件）*/
	 List<SupplierContractAttachment> getContractAttachmentByContractId(@Param("contractId") String contractId);
	 /** 根据vo的内容准确查询附件VO，不使用id*/
	 List<SupplierContractAttachment> queryAttachmentExistByVO(@Param("vo")SupplierContractAttachment vo);
	 /** 根据vo的id删除附件 */
	 void deleteAttachmentByVO(@Param("vo")SupplierContractAttachment vo);
	 
	 int insertSupplierContractAttachment(@Param("vo")SupplierContractAttachment vo);
	 
   /**
	 * 根据供应商ID查询当前有效合同
	 * @param supplierId
	 * @return 当前有效合同
	 */
	 SupplierContract getCurrentContractBySupplierId(@Param("supplierId")String supplierId);
	   
	 /** 获取商家的续签合同 */
	SupplierContract getRenewContractBySupplierId(@Param("supplierId")String supplierId);
	/** 获取合同超期60天且未续签的商家 
	 * @param compareDate 
	 * @param compareDateTo */
	List<SimpleContract> getContractOverDayNumList(@Param("compareDateFrom")Date compareDateFrom,@Param("compareDateTo") Date compareDateTo);
}
