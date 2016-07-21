package com.yougou.kaidian.taobao.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TaobaoCommodityImportInfo implements Serializable{

    private static final long serialVersionUID = 1L;
    private String result;      //失败返回ERROR，成功返回SUCCESS
    private String id;      //id
    private List<String> errorList;    //错误信息集合
    private List<String[]> infoList;    //提示信息集合
    
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}
	public List<String[]> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<String[]> infoList) {
		this.infoList = infoList;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
