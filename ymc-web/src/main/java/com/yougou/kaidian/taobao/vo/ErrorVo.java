/**
 * 
 */
package com.yougou.kaidian.taobao.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author huang.tao
 *
 */
public class ErrorVo  implements Comparable<ErrorVo>{
	
	private String errorFiled;
	
	private String errMsg;
	/**
	 * 错误信息下标，用于淘宝导入错误信息提示的排序功能 
	 */
	private int index;
	
	public ErrorVo(String filed, String errMsg) {
		this.errorFiled = filed;
		this.errMsg = errMsg;
	}
	
	public ErrorVo(int index,String errMsg) {
		super();
		this.errMsg = errMsg;
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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
	
	@Override
	public int compareTo(ErrorVo o) {

		int flag = o.getIndex() - this.getIndex();
		if (flag <= 0) {
			return 1;
		}

		return 0;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
