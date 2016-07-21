/*
 * ���� com.yougou.kaidian.user.model.pojo.MerchantCenterOperationLog
 * 
 * ����  Mon May 25 13:59:25 CST 2015
 * 
 * ��Ȩ����Copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
package com.yougou.kaidian.user.model.pojo;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.builder.ToStringBuilder;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.order.util.GetAddressByIpUtil;
import com.yougou.kaidian.user.constant.UserConstant;

public class MerchantCenterOperationLog {
    private String id;

    private String loginName;

    private Date operated;

    private String loginHost;

    private String loginAddress;

    private String operationType;

    private String operationMenu;

    private String operationOrder;

    private String operationNum;

    private Integer type;

    private String merchantCode;

    private String operationNotes;
    
    public MerchantCenterOperationLog(){};
    
    public MerchantCenterOperationLog(HttpServletRequest request){
         String merchantCode = YmcThreadLocalHolder.getMerchantCode();
         String loginName = YmcThreadLocalHolder.getOperater();
         String ip = GetAddressByIpUtil.getIPAddress(request);
         this.setId(UUIDGenerator.getUUID());
         this.setLoginAddress(GetAddressByIpUtil.GetAddressByIpWithSina(ip,"json"));
         this.setLoginHost(ip);
         this.setLoginName(loginName);
         this.setMerchantCode(merchantCode==null ? "":merchantCode);
         this.setOperated(new Date(System.currentTimeMillis()));
    }
    
    public MerchantCenterOperationLog(HttpServletRequest request,String operationMenu,
    		String operationNotes,String operationNum,String operationOrder,
    		String operationType){
    	this(request);
    	this.setOperationMenu(operationMenu);
    	this.setOperationNotes(operationNotes);
    	this.setOperationNum(operationNum);
    	this.setOperationOrder(operationOrder);
    	this.setOperationType(operationType);
    	this.setType(UserConstant.LOG_TYPE_OPERATION);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public Date getOperated() {
        return operated;
    }

    public void setOperated(Date operated) {
        this.operated = operated;
    }

    public String getLoginHost() {
        return loginHost;
    }

    public void setLoginHost(String loginHost) {
        this.loginHost = loginHost == null ? null : loginHost.trim();
    }

    public String getLoginAddress() {
        return loginAddress;
    }

    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress == null ? null : loginAddress.trim();
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType == null ? null : operationType.trim();
    }

    public String getOperationMenu() {
        return operationMenu;
    }

    public void setOperationMenu(String operationMenu) {
        this.operationMenu = operationMenu == null ? null : operationMenu.trim();
    }

    public String getOperationOrder() {
        return operationOrder;
    }

    public void setOperationOrder(String operationOrder) {
        this.operationOrder = operationOrder == null ? null : operationOrder.trim();
    }

    public String getOperationNum() {
        return operationNum;
    }

    public void setOperationNum(String operationNum) {
        this.operationNum = operationNum == null ? null : operationNum.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public String getOperationNotes() {
        return operationNotes;
    }

    public void setOperationNotes(String operationNotes) {
        this.operationNotes = operationNotes == null ? null : operationNotes.trim();
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}