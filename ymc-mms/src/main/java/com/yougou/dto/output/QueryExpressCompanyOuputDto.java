package com.yougou.dto.output;

/**
 * 
 * @author huang.tao
 *
 */
public class QueryExpressCompanyOuputDto extends OutputDto {
	
	private static final long serialVersionUID = 5732182001925279706L;
	
	private String express_company_code;
	private String express_company_name;
	
	public String getExpress_company_code() {
		return express_company_code;
	}
	public void setExpress_company_code(String express_company_code) {
		this.express_company_code = express_company_code;
	}
	public String getExpress_company_name() {
		return express_company_name;
	}
	public void setExpress_company_name(String express_company_name) {
		this.express_company_name = express_company_name;
	}

	
}
