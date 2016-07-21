package com.yougou.dto.output;

/**
 * 
 * @author 杨梦清
 * 
 */
public class QueryLogisticsCompanyOuputDto extends OutputDto {
	
	private static final long serialVersionUID = 554327699479708268L;
	
	private String logistics_company_code;
	private String logistics_company_name;

	public String getLogistics_company_code() {
		return logistics_company_code;
	}

	public void setLogistics_company_code(String logistics_company_code) {
		this.logistics_company_code = logistics_company_code;
	}

	public String getLogistics_company_name() {
		return logistics_company_name;
	}

	public void setLogistics_company_name(String logistics_company_name) {
		this.logistics_company_name = logistics_company_name;
	}
}
