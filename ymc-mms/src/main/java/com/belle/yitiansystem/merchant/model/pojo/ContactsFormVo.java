/**
 * 商家创建和编辑页面用到的表单类
 */
package com.belle.yitiansystem.merchant.model.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.belle.yitiansystem.merchant.constant.MerchantConstant;
import com.yougou.dms.api.util.ArrayUtils;
import com.yougou.merchant.api.common.UUIDGenerator;
import com.yougou.merchant.api.supplier.vo.ContactsVo;
import com.yougou.purchase.model.SupplierContact;

/**
 * @author huang.tao
 *
 */
public class ContactsFormVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private String supplyId;
	//供应商名称
	private String supplier;
	//商家编码
	private String supplierCode;
	
	/** 店铺负责人 （必输）  */
	private String chiefId;
	//姓名
	private String chiefContact;
	//座机
	private String chiefTelePhone;
	//手机
	private String chiefMobilePhone;
	//邮箱
	private String chiefEmail;
	//qq
	private String chiefQQ;
	
	/** 业务联系人 （选输）  */
	private String businessId;
	//姓名
	private String businessContact;
	//座机
	private String businessTelePhone;
	//手机
	private String businessMobilePhone;
	//邮箱
	private String businessEmail;
	//qq
	private String businessQQ;
	
	/** 售后联系人 （选输）  */
	private String afterSaleId;
	//姓名
	private String afterSaleContact;
	//座机
	private String afterSaleTelePhone;
	//手机
	private String afterSaleMobilePhone;
	//邮箱
	private String afterSaleEmail;
	//qq
	private String afterSaleQQ;
	
	/** 财务联系人 （选输）  */
	private String financeId;
	//姓名
	private String financeContact;
	//座机
	private String financeTelePhone;
	//手机
	private String financeMobilePhone;
	//邮箱
	private String financeEmail;
	//qq
	private String financeQQ;
	

	/** 技术联系人 （选输）  */
	private String techId;
	//姓名
	private String techContact;
	//座机
	private String techTelePhone;
	//手机
	private String techMobilePhone;
	//邮箱
	private String techEmail;
	//qq
	private String techQQ;
	
	private List<ContactsVo> contactList;
	
	public List<ContactsVo> getContactList() {
		return contactList;
	}
	public void setContactList(List<ContactsVo> contactList) {
		this.contactList = contactList;
	}
	public String getSupplyId() {
		return supplyId;
	}
	public void setSupplyId(String supplyId) {
		this.supplyId = supplyId;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getChiefId() {
		return chiefId;
	}
	public void setChiefId(String chiefId) {
		this.chiefId = chiefId;
	}
	public String getChiefContact() {
		return chiefContact;
	}
	public void setChiefContact(String chiefContact) {
		this.chiefContact = chiefContact;
	}
	public String getChiefTelePhone() {
		return chiefTelePhone;
	}
	public void setChiefTelePhone(String chiefTelePhone) {
		this.chiefTelePhone = chiefTelePhone;
	}
	public String getChiefMobilePhone() {
		return chiefMobilePhone;
	}
	public void setChiefMobilePhone(String chiefMobilePhone) {
		this.chiefMobilePhone = chiefMobilePhone;
	}
	public String getChiefEmail() {
		return chiefEmail;
	}
	public void setChiefEmail(String chiefEmail) {
		this.chiefEmail = chiefEmail;
	}
	public String getChiefQQ() {
		return chiefQQ;
	}
	public void setChiefQQ(String chiefQQ) {
		this.chiefQQ = chiefQQ;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getBusinessContact() {
		return businessContact;
	}
	public void setBusinessContact(String businessContact) {
		this.businessContact = businessContact;
	}
	public String getBusinessTelePhone() {
		return businessTelePhone;
	}
	public void setBusinessTelePhone(String businessTelePhone) {
		this.businessTelePhone = businessTelePhone;
	}
	public String getBusinessMobilePhone() {
		return businessMobilePhone;
	}
	public void setBusinessMobilePhone(String businessMobilePhone) {
		this.businessMobilePhone = businessMobilePhone;
	}
	public String getBusinessEmail() {
		return businessEmail;
	}
	public void setBusinessEmail(String businessEmail) {
		this.businessEmail = businessEmail;
	}
	public String getBusinessQQ() {
		return businessQQ;
	}
	public void setBusinessQQ(String businessQQ) {
		this.businessQQ = businessQQ;
	}
	public String getAfterSaleId() {
		return afterSaleId;
	}
	public void setAfterSaleId(String afterSaleId) {
		this.afterSaleId = afterSaleId;
	}
	public String getAfterSaleContact() {
		return afterSaleContact;
	}
	public void setAfterSaleContact(String afterSaleContact) {
		this.afterSaleContact = afterSaleContact;
	}
	public String getAfterSaleTelePhone() {
		return afterSaleTelePhone;
	}
	public void setAfterSaleTelePhone(String afterSaleTelePhone) {
		this.afterSaleTelePhone = afterSaleTelePhone;
	}
	public String getAfterSaleMobilePhone() {
		return afterSaleMobilePhone;
	}
	public void setAfterSaleMobilePhone(String afterSaleMobilePhone) {
		this.afterSaleMobilePhone = afterSaleMobilePhone;
	}
	public String getAfterSaleEmail() {
		return afterSaleEmail;
	}
	public void setAfterSaleEmail(String afterSaleEmail) {
		this.afterSaleEmail = afterSaleEmail;
	}
	public String getAfterSaleQQ() {
		return afterSaleQQ;
	}
	public void setAfterSaleQQ(String afterSaleQQ) {
		this.afterSaleQQ = afterSaleQQ;
	}
	public String getFinanceId() {
		return financeId;
	}
	public void setFinanceId(String financeId) {
		this.financeId = financeId;
	}
	public String getFinanceContact() {
		return financeContact;
	}
	public void setFinanceContact(String financeContact) {
		this.financeContact = financeContact;
	}
	public String getFinanceTelePhone() {
		return financeTelePhone;
	}
	public void setFinanceTelePhone(String financeTelePhone) {
		this.financeTelePhone = financeTelePhone;
	}
	public String getFinanceMobilePhone() {
		return financeMobilePhone;
	}
	public void setFinanceMobilePhone(String financeMobilePhone) {
		this.financeMobilePhone = financeMobilePhone;
	}
	public String getFinanceEmail() {
		return financeEmail;
	}
	public void setFinanceEmail(String financeEmail) {
		this.financeEmail = financeEmail;
	}
	public String getFinanceQQ() {
		return financeQQ;
	}
	public void setFinanceQQ(String financeQQ) {
		this.financeQQ = financeQQ;
	}
	public String getTechId() {
		return techId;
	}
	public void setTechId(String techId) {
		this.techId = techId;
	}
	public String getTechContact() {
		return techContact;
	}
	public void setTechContact(String techContact) {
		this.techContact = techContact;
	}
	public String getTechTelePhone() {
		return techTelePhone;
	}
	public void setTechTelePhone(String techTelePhone) {
		this.techTelePhone = techTelePhone;
	}
	public String getTechMobilePhone() {
		return techMobilePhone;
	}
	public void setTechMobilePhone(String techMobilePhone) {
		this.techMobilePhone = techMobilePhone;
	}
	public String getTechEmail() {
		return techEmail;
	}
	public void setTechEmail(String techEmail) {
		this.techEmail = techEmail;
	}
	public String getTechQQ() {
		return techQQ;
	}
	public void setTechQQ(String techQQ) {
		this.techQQ = techQQ;
	}
	
	public void initFormVoByContactList(){
		
		if( null!=contactList && 0<contactList.size() ){
			int size = contactList.size();
			for(int i=0;i<size;i++){
				
				ContactsVo contact = contactList.get(i);
				Integer type = contact.getType();
				if( null==type ){
					continue;
				}
				switch (type){
					case MerchantConstant.CONTACT_TYPE_CHIEF :
						chiefId = contact.getId();
						chiefContact = contact.getContact();
						chiefEmail =contact.getEmail();
						chiefMobilePhone = contact.getMobilePhone();
						chiefTelePhone = contact.getTelePhone();
						chiefQQ = contact.getFax();
						break;
					case MerchantConstant.CONTACT_TYPE_BUSINESS :
						businessId = contact.getId();
						businessContact = contact.getContact();
						businessEmail =contact.getEmail();
						businessMobilePhone = contact.getMobilePhone();
						businessTelePhone = contact.getTelePhone();
						businessQQ = contact.getFax();
						break;
					case MerchantConstant.CONTACT_TYPE_AFTERSALE :
						afterSaleId = contact.getId();
						afterSaleContact = contact.getContact();
						afterSaleEmail =contact.getEmail();
						afterSaleMobilePhone = contact.getMobilePhone();
						afterSaleTelePhone = contact.getTelePhone();
						afterSaleQQ = contact.getFax();
						break;
					case MerchantConstant.CONTACT_TYPE_FINANCE :
						financeId = contact.getId();
						financeContact = contact.getContact();
						financeEmail =contact.getEmail();
						financeMobilePhone = contact.getMobilePhone();
						financeTelePhone = contact.getTelePhone();
						financeQQ = contact.getFax();
						break;
					case MerchantConstant.CONTACT_TYPE_TECH :
						techId = contact.getId();
						techContact = contact.getContact();
						techEmail =contact.getEmail();
						techMobilePhone = contact.getMobilePhone();
						techTelePhone = contact.getTelePhone();
						techQQ = contact.getFax();
						break;
					default:
						break;
					
				}
			}
			
		}
		
	}
	
	public List<SupplierContact> generateContactVoList(){
		
		if(StringUtils.isBlank(chiefContact)){
			return null;
		}
		// 1
		SupplierContact chiefVo = new SupplierContact();
		chiefVo.setContact(chiefContact);
		chiefVo.setEmail(chiefEmail);
		chiefVo.setMobilePhone(chiefMobilePhone);
		chiefVo.setTelePhone(chiefTelePhone);
		chiefVo.setFax(chiefQQ);
		chiefVo.setSupplyId(supplyId);
		if( StringUtils.isBlank(chiefId) ){
			chiefVo.setId( UUIDGenerator.getUUID() );
		}else{
			chiefVo.setId( chiefId );
		}
		chiefVo.setType( MerchantConstant.CONTACT_TYPE_CHIEF );
		
		List<SupplierContact> contactVoList = new ArrayList<SupplierContact>();
		contactVoList.add(chiefVo);
		//2
		if(StringUtils.isNotBlank(businessContact)){
		
			SupplierContact businessVo = new SupplierContact();
			businessVo.setContact(businessContact);
			businessVo.setEmail(businessEmail);
			businessVo.setMobilePhone(businessMobilePhone);
			businessVo.setTelePhone(businessTelePhone);
			businessVo.setFax(businessQQ);
			businessVo.setSupplyId(supplyId);
			if( StringUtils.isBlank(businessId) ){
				businessVo.setId( UUIDGenerator.getUUID() );
			}else{
				businessVo.setId( businessId );
			}
			businessVo.setType( MerchantConstant.CONTACT_TYPE_BUSINESS );
			contactVoList.add(businessVo);
		}
		//3
		if(StringUtils.isNotBlank(afterSaleContact)){
			
			SupplierContact afterSaleVo = new SupplierContact();
			afterSaleVo.setContact(afterSaleContact);
			afterSaleVo.setEmail(afterSaleEmail);
			afterSaleVo.setMobilePhone(afterSaleMobilePhone);
			afterSaleVo.setTelePhone(afterSaleTelePhone);
			afterSaleVo.setFax(afterSaleQQ);
			afterSaleVo.setSupplyId(supplyId);
			if( StringUtils.isBlank(afterSaleId) ){
				afterSaleVo.setId( UUIDGenerator.getUUID() );
			}else{
				afterSaleVo.setId( afterSaleId );
			}
			afterSaleVo.setType( MerchantConstant.CONTACT_TYPE_AFTERSALE );
			contactVoList.add(afterSaleVo);
		}
		//4
		if(StringUtils.isNotBlank(financeContact)){
			
			SupplierContact financeVo = new SupplierContact();
			financeVo.setContact(financeContact);
			financeVo.setEmail(financeEmail);
			financeVo.setMobilePhone(financeMobilePhone);
			financeVo.setTelePhone(financeTelePhone);
			financeVo.setFax(financeQQ);
			financeVo.setSupplyId(supplyId);
			if( StringUtils.isBlank(financeId) ){
				financeVo.setId( UUIDGenerator.getUUID() );
			}else{
				financeVo.setId( financeId );
			}
			financeVo.setType( MerchantConstant.CONTACT_TYPE_FINANCE );
			contactVoList.add(financeVo);
		}
		//5
		if(StringUtils.isNotBlank(techContact)){
			
			SupplierContact techVo = new SupplierContact();
			techVo.setContact(techContact);
			techVo.setEmail(techEmail);
			techVo.setMobilePhone(techMobilePhone);
			techVo.setTelePhone(techTelePhone);
			techVo.setFax(techQQ);
			techVo.setSupplyId(supplyId);
			if( StringUtils.isBlank(techId) ){
				techVo.setId( UUIDGenerator.getUUID() );
			}else{
				techVo.setId( techId );
			}
			techVo.setType( MerchantConstant.CONTACT_TYPE_TECH );
			contactVoList.add(techVo);
		}
		
		
		return contactVoList;
	}

	
}
