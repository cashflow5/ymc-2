package com.yougou.dto.output;


/**
 * 业务错误输出DTO
 * @author he.wc
 *
 */
public class BusinessServiceErrorDto extends OutputDto {

	private static final long serialVersionUID = -3142160205351904716L;

	private  String message;
	
	
	public BusinessServiceErrorDto(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
