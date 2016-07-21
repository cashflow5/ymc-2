package com.yougou.kaidian.asm.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 售后质检查询（新_tmall风格）
 * 
 * @author huang.tao
 * @date 2013-12-19 14:56:00
 */
public class QualityNotPassResultVo implements Serializable {

    private static final long serialVersionUID = 1L;

    // 退换货质检明细id
    private String id;
    // 订单号
    private String orderSubNo;
    // 顾客回寄快递单号
    private String expressCode;
    // 顾客回寄快递公司名称
    private String expressName;
    // 质检人
    private String qaPerson;
    // 质检时间
    private Date qaDate;
    // 收货人
    private String userName;
    // 收货人联系方式
    private String mobilePhone;
    // 货品条码
    private String qaInsideCode;
    // 货品名称
    private String commodityName;
    // 客服审核状态 0：待确认；1：退回顾客；2：维修后退顾客；3：转正常质检
    private String csConfirmStatus;
    // 质检处理状态 0：待处理1：待维修；2：维修中；3：已维修待退回；4：待退回；5：已退回；6：待转为正常质检；7：已转为正常质检
    private String zjHandleStatus;
    // 商家寄出快递单号
    private String returnExpressCode;
    // 商家寄出快递公司编码
    private String returnLogisticsCode;
    // 商家寄出快递公司编码
    private String returnLogisticsName;
    // 质检确认维修时间
    private Date repairDate;
    /** 售后申请单号 */
    private String applyNo;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getQaPerson() {
        return qaPerson;
    }

    public void setQaPerson(String qaPerson) {
        this.qaPerson = qaPerson;
    }

    public Date getQaDate() {
        return qaDate;
    }

    public void setQaDate(Date qaDate) {
        this.qaDate = qaDate;
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

    public String getQaInsideCode() {
        return qaInsideCode;
    }

    public void setQaInsideCode(String qaInsideCode) {
        this.qaInsideCode = qaInsideCode;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCsConfirmStatus() {
        return csConfirmStatus;
    }

    public void setCsConfirmStatus(String csConfirmStatus) {
        this.csConfirmStatus = csConfirmStatus;
    }

    public String getZjHandleStatus() {
        return zjHandleStatus;
    }

    public void setZjHandleStatus(String zjHandleStatus) {
        this.zjHandleStatus = zjHandleStatus;
    }

    public String getReturnExpressCode() {
        return returnExpressCode;
    }

    public void setReturnExpressCode(String returnExpressCode) {
        this.returnExpressCode = returnExpressCode;
    }

    public String getReturnLogisticsCode() {
        return returnLogisticsCode;
    }

    public void setReturnLogisticsCode(String returnLogisticsCode) {
        this.returnLogisticsCode = returnLogisticsCode;
    }

    public String getReturnLogisticsName() {
        return returnLogisticsName;
    }

    public void setReturnLogisticsName(String returnLogisticsName) {
        this.returnLogisticsName = returnLogisticsName;
    }

    public Date getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(Date repairDate) {
        this.repairDate = repairDate;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }
    
}
