package com.yougou.kaidian.asm.vo;

import java.io.Serializable;

/**
 * @author mei.jf 质检不通过查询VO
 */
public class QualityNotPassQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;
    // 退换货质检明细id
    private String id;
    /** 商家编码 */
    private String merchantCode;
    /** 子订单号 */
    private String orderSubNo;
    /** 快递单号 */
    private String expressCode;
    /** 快递公司编码 */
    private String logisticsCode;
    /** 客服审核状态 0：待确认；1：退回顾客；2：维修后退顾客；3：转正常质检 */
    private String csConfirmStatus;
    /** 用户名 */
    private String userName;
    /** 用户手机号码 */
    private String mobilePhone;
    /** 质检处理状态 0：待处理1：待维修；2：维修中；3：已维修待退回；4：待退回；5：已退回；6：待转为正常质检；7：已转为正常质检 */
    private String zjHandleStatus;
    /** 质检确认维修起始时间 */
    private String repairTimeStart;
    private String repairTimeEnd;
    /** 质检起始时间 */
    private String qaTimeStart;
    private String qaTimeEnd;
    /** tab标识 */
    private String tab;

    /** 寄出快递费 */
    private String retrunFee;

    /** 是否到付 0：非到付；1：到付 */
    private String returnIsDelivery;

    /** 寄出备注 */
    private String returnRemark;

    /** 售后申请单号 */
    private String applyNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getOrderSubNo() {
        return orderSubNo;
    }

    public void setOrderSubNo(String orderSubNo) {
        this.orderSubNo = orderSubNo;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getCsConfirmStatus() {
        return csConfirmStatus;
    }

    public void setCsConfirmStatus(String csConfirmStatus) {
        this.csConfirmStatus = csConfirmStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getZjHandleStatus() {
        return zjHandleStatus;
    }

    public void setZjHandleStatus(String zjHandleStatus) {
        this.zjHandleStatus = zjHandleStatus;
    }

    public String getRepairTimeStart() {
        return repairTimeStart;
    }

    public void setRepairTimeStart(String repairTimeStart) {
        this.repairTimeStart = repairTimeStart;
    }

    public String getRepairTimeEnd() {
        return repairTimeEnd;
    }

    public void setRepairTimeEnd(String repairTimeEnd) {
        this.repairTimeEnd = repairTimeEnd;
    }

    public String getQaTimeStart() {
        return qaTimeStart;
    }

    public void setQaTimeStart(String qaTimeStart) {
        this.qaTimeStart = qaTimeStart;
    }

    public String getQaTimeEnd() {
        return qaTimeEnd;
    }

    public void setQaTimeEnd(String qaTimeEnd) {
        this.qaTimeEnd = qaTimeEnd;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getRetrunFee() {
        return retrunFee;
    }

    public void setRetrunFee(String retrunFee) {
        this.retrunFee = retrunFee;
    }

    public String getReturnIsDelivery() {
        return returnIsDelivery;
    }

    public void setReturnIsDelivery(String returnIsDelivery) {
        this.returnIsDelivery = returnIsDelivery;
    }

    public String getReturnRemark() {
        return returnRemark;
    }

    public void setReturnRemark(String returnRemark) {
        this.returnRemark = returnRemark;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

}
