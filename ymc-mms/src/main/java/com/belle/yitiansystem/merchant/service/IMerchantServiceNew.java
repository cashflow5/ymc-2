/**
 * 
 */
package com.belle.yitiansystem.merchant.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.belle.finance.costsettlement.costsetofbooks.model.vo.CostSetofBooks;
import com.belle.other.model.pojo.SupplierSp;
import com.belle.yitiansystem.merchant.model.pojo.AttachmentFormVo;
import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.yougou.merchant.api.supplier.vo.ContactsVo;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog;
import com.yougou.merchant.api.supplier.vo.MerchantRejectedAddressVo;
import com.yougou.merchant.api.supplier.vo.MerchantSupplierExpand;
import com.yougou.merchant.api.supplier.vo.MerchantUser;
import com.yougou.merchant.api.supplier.vo.SupplierVo;
import com.yougou.purchase.model.SupplierContact;

/**
 * 供应商信息维护服务
 * 
 * @author huang.tao
 *
 */
public interface IMerchantServiceNew {
	
	List<CostSetofBooks> getAllCostSetofBooksList() throws Exception;
	
	List<CostSetofBooks> getCostSetofBooksList() throws Exception;
	
	SupplierVo getSupplierVoById(String id);
	
	public SupplierVo getMerchantVoByCode(String merchantCode);
	
	/**
	 * 添加招商供应商(包括品牌分类授权)
	 * 
	 * @param req
	 * @param supplierSp
	 * @param bankNoHidden
	 * @param catNameHidden
	 * @return
	 */
	//boolean addMerchant1(HttpServletRequest req, SupplierSp supplierSp, String bankNoHidden, String catNameHidden);
	
	/**
	 * 修改招商供应商信息(包括分类品牌授权)
	 * 
	 * @param req
	 * @param supplierSp
	 * @param bankNameHidden
	 * @param catNameHidden
	 * @param brandList
	 * @param catList
	 * @return
	 */
	//boolean updateMerchant1(HttpServletRequest req, SupplierSp supplierSp, String bankNoHidden, String catNameHidden);
	
	/**
	 * 更新供应商品牌和分类授权(包括商家账户信息)
	 * 
	 * @param req
	 * @param supplierSp
	 * @param bankNameHidden
	 * @param catNameHidden
	 * @return
	 */
	boolean updateHistoryMerchants(HttpServletRequest req, SupplierSp supplierSp, String bankNoHidden, String catNameHidden);
	
	/**
	 * 更新供应商品牌分类授权
	 * 
	 * @param req
	 * @param supplierVo
	 * @param bankNameHidden
	 * @param catNameHidden
	 * @return
	 */
	boolean updateMerchantsBankAndCat( SupplierVo supplier, String bankNoHidden, String catNameHidden);
	
	boolean updateEmail(String id, String email, String operatorName) throws Exception;
	
	/**
	 * 标记删除账户
	 * 
	 * @param id
	 * @param user
	 * @return
	 */
	boolean deleteMerchantUser(String id, String user);
	
	/**
	 * 更改商家账户状态
	 * 
	 * @param id
	 * @param status 1启用 0锁定
	 * @param user
	 * @return
	 */
	boolean updateMerchantState(String id, Integer status, String user);
	
	/**
	 * 添加供应商联系人
	 * 
	 * @param contact
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	boolean addLinkmanList(SupplierContact contact, String operator) throws Exception;
	
	boolean updateLinkmanList(SupplierContact contact, String operator) throws Exception;
	
	
	/********* 新商家入驻 . Start from here . Add by LQ on 20150714 ************/
	/**
	 * 添加供应商
	 * @param attachmentFormVo 
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String saveSupplierMerchant(Map<String,Object> params) throws  Exception;
	
	public MerchantRejectedAddressVo getRejectedAddressBySupplierCode(
			String supplierCode);
	/** 
	 * 
	 * 保存招商商家退换货地址 
	 * @param merchantRejectedAddress
	 * @throws Exception
	 */
	public int saveMerchantRejectedAddress(MerchantRejectedAddressVo merchantRejectedAddressVo) throws  Exception;

	public int updateRejectedAddress(
			MerchantRejectedAddressVo merchantRejectedAddressVo)
			throws Exception;
	/**
	 * 查询招商商家的扩展表信息
	 * @param id
	 * @return MerchantSupplierExpand
	 */
	public MerchantSupplierExpand getSupplierExpandVoById(String supplierId);
	
	/**
	 * 查询招商商家的扩展表信息
	 * @param id
	 * @return MerchantSupplierExpand
	 */
	public MerchantSupplierExpand getSupplierExpandVoByCode(String merchantCode);

	public int saveSupplierExpand(MerchantSupplierExpand supplierExpand)
			throws BusinessException;

	public int updateSupplierExpand(MerchantSupplierExpand supplierExpand)
			throws BusinessException;
	
	public int saveMerchantUser(MerchantUser merchantUser) throws BusinessException;

	public int updateMerchantUser(MerchantUser merchantUser) throws BusinessException ;
	
	public MerchantUser getMerchantsBySuppliceCode(String supplierCode);

	public List<ContactsVo> getContactsBySupplierId(String supplierId);

	public void updateSupplierMerchant(Map<String, Object> params) throws Exception;

	/** 增加操作日志 */
	public void insertMerchantLog(MerchantOperationLog log);

	/** 更新资质 */
	void updateNatural(MerchantSupplierExpand supplierExpand,
			AttachmentFormVo attachmentFormVo) throws Exception;


	boolean updateMobile(String id,String mobile, String loginName);

	boolean updateEmail(MerchantUser merchantUser, String operatorName)
			throws Exception;

	boolean updateMobile(MerchantUser merchantUser, String loginName);
	/*
	 * 查询该商家的该种联系人是否已经存在 存在 返回true
	 */
	boolean queryExistContactOfThisType(SupplierContact contact) throws Exception;
	
	List<Map<String,Object>>  getMerchantInfo(Map<String,List<String>> map);
}
