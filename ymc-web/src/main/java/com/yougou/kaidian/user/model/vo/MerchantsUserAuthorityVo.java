package com.yougou.kaidian.user.model.vo;


/**
 * 用户权限vo实体类
 * @author wang.m
 *
 */
public class MerchantsUserAuthorityVo {
 private String id;//用户ID
 private Integer authrityModule;//权限所属模块
 private String authrityName;//资源根名称
 
public String getAuthrityName() {
	return authrityName;
}
public void setAuthrityName(String authrityName) {
	this.authrityName = authrityName;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public Integer getAuthrityModule() {
	return authrityModule;
}
public void setAuthrityModule(Integer authrityModule) {
	this.authrityModule = authrityModule;
}
public MerchantsUserAuthorityVo(String id, Integer authrityModule) {
	super();
	this.id = id;
	this.authrityModule = authrityModule;
}
public MerchantsUserAuthorityVo() {
	super();
}
 
}
