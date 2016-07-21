package com.yougou.dto.output;


public class ReturnQualityOutputDto extends OutputDto {

	private static final long serialVersionUID = -3547416916611124183L;
	
	/** 操作结果 true|false */
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
