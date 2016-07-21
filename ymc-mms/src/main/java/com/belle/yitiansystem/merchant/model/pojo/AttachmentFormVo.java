package com.belle.yitiansystem.merchant.model.pojo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 附件  表单
 * @author luo.q1
 *
 */
public class AttachmentFormVo implements Serializable{
	
		/**
		 * 序列化ID
		 */
		private static final long serialVersionUID = -7337809857901147164L;

	    private String supplierId;
	    private String contractId;
	    private Integer markRemainingDays;
	    private String[] contract_attachment;
	    private String[] trademark;
	    private String[] deductionPoint;
	    private String[] brandNo;
	    private String[] authorizer;
	    private String[] type;
	    private String[] registeredTrademark;
	    private String[] registeredStartDate;
	    private String[] registeredEndDate;
	    private String[] beAuthorizer;
	    private String[] authorizStartdate;
	    private String[] authorizEnddate;
	    private String isNeedRenew;
	    private String bankNoHidden;
	    private String catNameHidden;
	    private String bankNoNameHidden;
		
	    public String getSupplierId() {
			return supplierId;
		}
		public String getContractId() {
			return contractId;
		}
		public String[] getContract_attachment() {
			return contract_attachment;
		}
		public String[] getTrademark() {
			return trademark;
		}
		public String[] getAuthorizer() {
			return authorizer;
		}
		public String[] getType() {
			return type;
		}
		public String[] getRegisteredTrademark() {
			return registeredTrademark;
		}
		public String[] getRegisteredStartDate() {
			return registeredStartDate;
		}
		public String[] getRegisteredEndDate() {
			return registeredEndDate;
		}
		public String[] getBeAuthorizer() {
			return beAuthorizer;
		}
		public String[] getAuthorizStartdate() {
			return authorizStartdate;
		}
		public String[] getAuthorizEnddate() {
			return authorizEnddate;
		}
		public void setSupplierId(String supplierId) {
			this.supplierId = supplierId;
		}
		public void setContractId(String contractId) {
			this.contractId = contractId;
		}
		public void setContract_attachment(String[] contract_attachment) {
			this.contract_attachment = contract_attachment;
		}
		public void setTrademark(String[] trademark) {
			this.trademark = trademark;
		}
		public void setAuthorizer(String[] authorizer) {
			this.authorizer = authorizer;
		}
		public void setType(String[] type) {
			this.type = type;
		}
		public void setRegisteredTrademark(String[] registeredTrademark) {
			this.registeredTrademark = registeredTrademark;
		}
		public void setRegisteredStartDate(String[] registeredStartDate) {
			this.registeredStartDate = registeredStartDate;
		}
		public void setRegisteredEndDate(String[] registeredEndDate) {
			this.registeredEndDate = registeredEndDate;
		}
		public void setBeAuthorizer(String[] beAuthorizer) {
			this.beAuthorizer = beAuthorizer;
		}
		public void setAuthorizStartdate(String[] authorizStartdate) {
			this.authorizStartdate = authorizStartdate;
		}
		public void setAuthorizEnddate(String[] authorizEnddate) {
			this.authorizEnddate = authorizEnddate;
		}
		
		public Integer getMarkRemainingDays() {
			return markRemainingDays;
		}
		public void setMarkRemainingDays(Integer markRemainingDays) {
			this.markRemainingDays = markRemainingDays;
		}
		public String getIsNeedRenew() {
			return isNeedRenew;
		}
		public void setIsNeedRenew(String isNeedRenew) {
			this.isNeedRenew = isNeedRenew;
		}
		public String toString(){
			return ToStringBuilder.reflectionToString(this);
		}
		public String[] getDeductionPoint() {
			return deductionPoint;
		}
		public void setDeductionPoint(String[] deductionPoint) {
			this.deductionPoint = deductionPoint;
		}
		public String[] getBrandNo() {
			return brandNo;
		}
		public void setBrandNo(String[] brandNo) {
			this.brandNo = brandNo;
		}
		public String getBankNoHidden() {
			return bankNoHidden;
		}
		public void setBankNoHidden(String bankNoHidden) {
			this.bankNoHidden = bankNoHidden;
		}
		public String getCatNameHidden() {
			return catNameHidden;
		}
		public void setCatNameHidden(String catNameHidden) {
			this.catNameHidden = catNameHidden;
		}
		public String getBankNoNameHidden() {
			return bankNoNameHidden;
		}
		public void setBankNoNameHidden(String bankNoNameHidden) {
			this.bankNoNameHidden = bankNoNameHidden;
		}
			
}
