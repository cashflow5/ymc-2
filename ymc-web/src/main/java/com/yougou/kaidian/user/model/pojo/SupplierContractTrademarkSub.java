package com.yougou.kaidian.user.model.pojo;

public class SupplierContractTrademarkSub {
	private String id;
	private String contractId;
	private String trademarkId;
	private String beAuthorizer;
	private Integer level;
	private String levelStr;
	private String authorizStartdate;
	private String authorizEnddate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTrademarkId() {
		return trademarkId;
	}
	public void setTrademarkId(String trademarkId) {
		this.trademarkId = trademarkId;
	}
	public String getBeAuthorizer() {
		return beAuthorizer;
	}
	public void setBeAuthorizer(String beAuthorizer) {
		this.beAuthorizer = beAuthorizer;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		if(level!=null){
			if(level.intValue()==1){
				this.levelStr = "一";
			}else if(level.intValue()==2){
				this.levelStr = "二";
			}else if(level.intValue()==3){
				this.levelStr = "三";
			}else if(level.intValue()==4){
				this.levelStr = "四";
			}else if(level.intValue()==5){
				this.levelStr = "五";
			}else if(level.intValue()==6){
				this.levelStr = "六";
			}
		}
		this.level = level;
	}
	public String getAuthorizStartdate() {
		return authorizStartdate;
	}
	public void setAuthorizStartdate(String authorizStartdate) {
		this.authorizStartdate = authorizStartdate;
	}
	public String getAuthorizEnddate() {
		return authorizEnddate;
	}
	public void setAuthorizEnddate(String authorizEnddate) {
		this.authorizEnddate = authorizEnddate;
	}
	public String getLevelStr() {
		return levelStr;
	}
	public void setLevelStr(String levelStr) {
		this.levelStr = levelStr;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	
}
