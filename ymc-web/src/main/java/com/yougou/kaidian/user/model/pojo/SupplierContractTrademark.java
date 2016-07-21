package com.yougou.kaidian.user.model.pojo;

import java.util.ArrayList;
import java.util.List;

public class SupplierContractTrademark {
	private String id;
	private String contractId;
	private String trademark;
	private String authorizer;
	private String type;
	private String registeredTrademark;
	private String registeredStartDate;
	private String registeredEndDate;
	private List<SupplierContractTrademarkSub> trademarkSubList = new ArrayList<SupplierContractTrademarkSub>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getTrademark() {
		return trademark;
	}
	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}
	public String getAuthorizer() {
		return authorizer;
	}
	public void setAuthorizer(String authorizer) {
		this.authorizer = authorizer;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRegisteredTrademark() {
		return registeredTrademark;
	}
	public void setRegisteredTrademark(String registeredTrademark) {
		this.registeredTrademark = registeredTrademark;
	}
	public String getRegisteredStartDate() {
		return registeredStartDate;
	}
	public void setRegisteredStartDate(String registeredStartDate) {
		this.registeredStartDate = registeredStartDate;
	}
	public String getRegisteredEndDate() {
		return registeredEndDate;
	}
	public void setRegisteredEndDate(String registeredEndDate) {
		this.registeredEndDate = registeredEndDate;
	}
	public List<SupplierContractTrademarkSub> getTrademarkSubList() {
		return trademarkSubList;
	}
	public void setTrademarkSubList(
			List<SupplierContractTrademarkSub> trademarkSubList) {
		this.trademarkSubList = trademarkSubList;
	}
} 
