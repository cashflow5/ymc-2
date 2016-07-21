package com.belle.other.model.vo;

import java.util.Date;

/**
 * 
 * 类描述：TODO 步骤信息 
 * @author 段思远
 * @date May 6, 2011 12:40:06 PM
 * @email duan.sy@belle.com.cn
 */
public class LogisticsStepVO {

    // 快件单号
    private String expressCode;
    // 时间;
    private Date stepDate;
    // 快件流程;
    private String stepProcess;
    // 操作员
    private String operater;

    public java.lang.String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(java.lang.String expressCode) {
        this.expressCode = expressCode;
    }

    public void setStepDate(Date stepDate) {
        this.stepDate = stepDate;
    }

    public Date getStepDate() {
        return stepDate;
    }

    public String getStepProcess() {
        return stepProcess;
    }

    public void setStepProcess(String stepProcess) {
        this.stepProcess = stepProcess;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater;
    }

}
