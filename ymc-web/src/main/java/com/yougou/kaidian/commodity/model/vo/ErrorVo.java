/**
 * 
 */
package com.yougou.kaidian.commodity.model.vo;

/**
 * @author huang.tao
 *
 */
public class ErrorVo {
	
	private String errorFiled;
	
	private String errMsg;
	
	public ErrorVo(String filed, String errMsg) {
		this.errorFiled = filed;
		this.errMsg = errMsg;
	}
	
	public String getErrorFiled() {
		return errorFiled;
	}

	public void setErrorFiled(String errorFiled) {
		this.errorFiled = errorFiled;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
}
