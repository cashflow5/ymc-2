package com.yougou.dto.output;

import java.util.List;
import java.util.Map;

public class DistributorsAccountOutputDto extends OutputDto {
	
	public List<Map<String, Object>> getDistributorsAccountList() {
		return distributorsAccountList;
	}

	public void setDistributorsAccountList(List<Map<String, Object>> distributorsAccountList) {
		this.distributorsAccountList = distributorsAccountList;
	}
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 分销商账户信息列表 */
	private List<Map<String,Object>> distributorsAccountList;
	
}
