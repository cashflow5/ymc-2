package com.belle.yitiansystem.merchant.model.entity;

import java.util.Date;

public class SimpleContract {

	private String contractNo;
	
	
	private String merchantCode;
	
	
	private int contractRemainingDays;
	
	/**
	 * 是否已經到新合同時間，是的話，系統自動啟用新合同，否則，否則服務期限合同到期時間
	 */
	//select c.id,c.contract_no new_contract, e.contract_no old_contract,c.renew_flag,  c.supplier_id,e.contract_remaining_days,
    
	private String newContract;
	
	private  String oldContract;
	private String renewFlag;
	private String supplierId;
	//剩余时间
	private String leftdays;
	
	//主賬號EMAIL
	private String email;


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getNewContract() {
		return newContract;
	}


	public void setNewContract(String newContract) {
		this.newContract = newContract;
	}


	public String getOldContract() {
		return oldContract;
	}


	public void setOldContract(String oldContract) {
		this.oldContract = oldContract;
	}


	public String getRenewFlag() {
		return renewFlag;
	}


	public void setRenewFlag(String renewFlag) {
		this.renewFlag = renewFlag;
	}


	public String getSupplierId() {
		return supplierId;
	}


	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}


	public String getLeftdays() {
		return leftdays;
	}


	public void setLeftdays(String leftdays) {
		this.leftdays = leftdays;
	}


	public String getContractNo() {
		return contractNo;
	}


	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}


	public String getMerchantCode() {
		return merchantCode;
	}


	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}


	public int getContractRemainingDays() {
		return contractRemainingDays;
	}


	public void setContractRemainingDays(int contractRemainingDays) {
		this.contractRemainingDays = contractRemainingDays;
	}


 

	 
	
}
