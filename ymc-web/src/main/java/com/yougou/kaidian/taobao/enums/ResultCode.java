package com.yougou.kaidian.taobao.enums;

/**
 * 错误码
 * 
 * @author luo.hl
 * @date 2013-12-22 上午11:55:56
 * @version 3.0 
 * @copyright www.ulewo.com 
 */
public enum ResultCode {
	SUCCESS("200"), ERROR("500");
	private String code;
	
	private ResultCode(String code){
		this.code = code;
	}
	public String getCode(){
		return this.code;
	}
	
	@Override
	public String toString(){
		return this.code;
	}
}
